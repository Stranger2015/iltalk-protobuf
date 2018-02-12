package org.ltc.iltalk.core;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.googlecode.protobuf.pro.duplex.*;
import com.googlecode.protobuf.pro.duplex.client.DuplexTcpClientPipelineFactory;
import com.googlecode.protobuf.pro.duplex.client.RpcClientConnectionWatchdog;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerCallExecutor;
import com.googlecode.protobuf.pro.duplex.execute.ThreadPoolCallExecutor;
import com.googlecode.protobuf.pro.duplex.listener.RpcConnectionEventListener;
import com.googlecode.protobuf.pro.duplex.logging.CategoryPerServiceLogger;
import com.googlecode.protobuf.pro.duplex.server.DuplexTcpServerPipelineFactory;
import com.googlecode.protobuf.pro.duplex.util.RenamingThreadFactoryProxy;
import com.googlecode.protobuf.pro.duplex.wire.DuplexProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 *
 */
public class ILTalkProtocolHandler {

    protected static Logger log = getLogger(ILTalkProtocolHandler.class);

    public static final String LOCAL_HOST = "localhost";

    private final PeerInfo serverInfo;

    private final Properties talkProps = new Properties();

    private final PeerInfo clientInfo1;
    private final PeerInfo clientInfo2;

    private RpcClientChannel channel; //curr client

    /**
     *
     */
    private SimpleClient rpcClient1;
    /**
     *
     */
    private RpcClient rpcClient2;
    /**
     *
     */
    private RpcServer rpcServer;

    /**
     * @param fn
     * @throws IOException
     */
    public ILTalkProtocolHandler(String fn) throws IOException {

        talkProps.load(new BufferedReader(new FileReader(fn)));

        String clientHostNameKey = "client.host.name";


        String serverHostNameKey = "server.host.name";//String serverHostNameKey2 = "server.host.name.2";//todo single server

        String clientPortKey1 = "client.port.1";
        String clientPortKey2 = "client.port.2";
        String serverPortKey = "server.port";
        //String serverPortKey2 = "server.port.2";//todo single server

        //talkProps.getProperty("talk");
        //  ILTalkProtocol.MethodCall methodCall = ILTalkProtocol.MethodCall.getDefaultInstance();
        //methodCall..
        String lang1 = talkProps.getProperty("lang.1", "Java");
        String lang2 = talkProps.getProperty("lang.2", "Logtalk");

        //   List <String> calls = readProcCalls(talkProps.getProperty("procedure.calls"));
        //   int nesting_depth = IntegerTerm.parseInt(talkProps.getProperty("nesting.depth"));

        String clientHostName = talkProps.getProperty(clientHostNameKey, LOCAL_HOST);

        clientInfo1 = createPeerInfo(clientHostNameKey, clientPortKey1);
        clientInfo2 = createPeerInfo(clientHostNameKey, clientPortKey2);
        serverInfo = createPeerInfo(serverHostNameKey, serverPortKey);

        activate(clientInfo1, clientInfo2, serverInfo);
    }

    private void activate(PeerInfo clientInfo1, PeerInfo clientInfo2, PeerInfo serverInfo) {

    }

    private PeerInfo createPeerInfo(String hostKey, String portKey) {
        String host = talkProps.getProperty(hostKey, LOCAL_HOST);
        int port = Integer.parseInt(talkProps.getProperty(portKey));

        return new PeerInfo(host, port);
    }

    private void activateClients(PeerInfo client1, PeerInfo client2, PeerInfo server) throws IOException {
        DuplexTcpClientPipelineFactory clientFactory = new DuplexTcpClientPipelineFactory();

        clientFactory.setConnectResponseTimeoutMillis(10000);
        clientFactory.setRpcServerCallExecutor(new ThreadPoolCallExecutor(3, 10));


        // RPC payloads are uncompressed when logged - so reduce logging
        CategoryPerServiceLogger logger = new CategoryPerServiceLogger();
        logger.setLogRequestProto(false);
        logger.setLogResponseProto(false);
        clientFactory.setRpcLogger(logger);


        // Set up the event pipeline factory.
        // setup a RPC event listener - it just logs what happens
        RpcConnectionEventNotifier rpcEventNotifier = new RpcConnectionEventNotifier();

        final RpcConnectionEventListener listener = new RpcConnectionEventListener() {

            private RpcCallback<? extends Message> serverCallback
                    = (RpcCallback<Message>) parameter -> {
                log.info("dummy server callback " + parameter);
            };

            @Override
            public void connectionReestablished(RpcClientChannel
                                                        clientChannel) {
                log.info("connectionReestablished " + clientChannel);
                channel = clientChannel;
                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), serverCallback);
            }

            @Override
            public void connectionOpened(RpcClientChannel clientChannel) {
                log.info("connectionOpened " + clientChannel);
                channel = clientChannel;
                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), serverCallback);
            }

            @Override
            public void connectionLost(RpcClientChannel clientChannel) {
                log.info("connectionLost " + clientChannel);
            }

            @Override
            public void connectionChanged(RpcClientChannel clientChannel) {
                log.info("connectionChanged " + clientChannel);
                channel = clientChannel;
            }
        };
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workers = new NioEventLoopGroup(16,
                new RenamingThreadFactoryProxy("workers", Executors.defaultThreadFactory()));
        bootstrap.group(workers);
        bootstrap.handler(clientFactory);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        bootstrap.option(ChannelOption.SO_SNDBUF, 1048576);
        bootstrap.option(ChannelOption.SO_RCVBUF, 1048576);


        RpcClientConnectionWatchdog watchdog = new RpcClientConnectionWatchdog(clientFactory, bootstrap);
        rpcEventNotifier.addEventListener(watchdog);
        watchdog.start();

        CleanShutdownHandler shutdownHandler = new CleanShutdownHandler();
        shutdownHandler.addResource(workers);

        rpcClient =    clientFactory1.peerWith(server, bootstrap);
        simpleClient = clientFactory2.peerWith(server, bootstrap);
    }


    public int mainProc(String schemaUrl) throws IOException {
//        if (isDynamicSchemaMode) {
//            DynamicSchema.Builder schemaBuilder = DynamicSchema.newBuilder();
//            schemaBuilder.setName(schemaUrl);
//            MessageDefinition msgDef = MessageDefinition.newBuilder("Task") // message
//                    .addField("required", "int32", "id", 1)        // required int32 id = 1
//                    .addField("required", "string", "name", 2)    // required string name = 2
//                    .addField("optional", "string", "email", 3)    // optional string email = 3
//                    .build();
//
//            schemaBuilder.addMessageDefinition(msgDef);
//            DynamicSchema schema = null;
//            try {
//                schema = schemaBuilder.build();
//            } catch (Descriptors.DescriptorValidationException e) {
//                e.printStackTrace();
//                throw new Error("Can't build", e);
//            }
//            // Create dynamic message from schema
//            DynamicMessage.Builder msgBuilder = schema.newMessageBuilder("Person");
//            Descriptors.Descriptor msgDesc = msgBuilder.getDescriptorForType();
//
//        }
        DuplexProtocol.OobMessage taskMsg = DuplexProtocol.OobMessage.parseFrom(schemaUrl.getBytes());
        rpcClient1.sendOobMessage(taskMsg);

        /*
        FileInputStream fin = new FileInputStream("iltalk.desc");
        FileInputStream fin = new FileInputStream("iltalk.desc");
        Descriptors.Descriptor desc=Descriptors.
        Descriptors.FileDescriptor fd  =  Descriptors.FileDescriptor..buildFrom(fin).parseFrom(fin);
        Descriptors.Descriptor md = set.getFile(0).getMessageType(0);
        Descriptors.Descriptor
        */
        //       ILTalkProtocol.Start start.Start.parseFrom("lang1", "lang2", "");

        return 0;
    }

    public String getSchema(String s) {
        return null;
    }

    private class RpcConnectionEventListenerImpl implements RpcConnectionEventListener {
        /**
         * The RPC channel to a remote peer has closed. This can be
         * due to a clean {@link RpcClientChannel#close()} or if the
         * underlying TCP connection breaks - due to network problems
         * or JVM crash / kill. The reason for channel closure is not
         * discernible.
         *
         * @param clientChannel
         */
        @Override
        public void connectionLost(RpcClientChannel clientChannel) {

        }

        /**
         * An RPC channel to a new remote peer has been opened. The
         * remote peer has not been connected to before.
         *
         * @param clientChannel
         */
        @Override
        public void connectionOpened(RpcClientChannel clientChannel) {

        }

        /**
         * An RPC channel which was previously lost has reconnected, and it's PID
         * is the same as before - ie. it is the same running instance as before.
         * <p>
         * If the RPC peer exhibits the same {@link PeerInfo#getPid()} then the same
         * instance is reconnected, otherwise {@link #connectionChanged(RpcClientChannel)}
         * would be notified instead.
         *
         * @param clientChannel
         */
        @Override
        public void connectionReestablished(RpcClientChannel clientChannel) {

        }

        /**
         * An RPC channel which was previously lost has reconnected, and it's PID
         * is not the same as before - ie. it is not the same running instance as before.
         * If the System property "pid" is not set, then it is defaulted to "NONE", in
         * which case {@link #connectionReestablished(RpcClientChannel)} will always be
         * issued.
         * <p>
         * If the RPC peer exhibits the same {@link PeerInfo#getPid()} then the same
         * instance is reconnected, otherwise {@link #connectionChanged(RpcClientChannel)}
         * would be notified instead.
         *
         * @param clientChannel
         */
        @Override
        public void connectionChanged(RpcClientChannel clientChannel) {

        }
    }
}
////////////////////////

public class ILTalkServerSideController {

    protected static Logger log = LoggerFactory.getLogger(ILTalkServerSideController.class);
    private final DuplexTcpServerPipelineFactory serverFactory;

    protected ILTalkServerSideController(String serverHostname, int serverPort)
            throws InvalidProtocolBufferException, InterruptedException {

        PeerInfo serverInfo = new PeerInfo(serverHostname, serverPort);
        // RPC payloads are uncompressed when logged - so reduce logging
        CategoryPerServiceLogger logger = new CategoryPerServiceLogger();
        logger.setLogRequestProto(false);
        logger.setLogResponseProto(false);

        // Configure the server.
        serverFactory = new DuplexTcpServerPipelineFactory(serverInfo);
        RpcServerCallExecutor rpcExecutor = new ThreadPoolCallExecutor(10, 10);
        serverFactory.setRpcServerCallExecutor(rpcExecutor);
        serverFactory.setLogger(logger);


        RpcCallback<DuplexProtocol.OobMessage> clientStatusCallback = parameter -> log.info("Received " + parameter);

        // setup a RPC event listener - it just logs what happens
        RpcConnectionEventNotifier rpcEventNotifier = new RpcConnectionEventNotifier();
        RpcConnectionEventListener listener = new RpcConnectionEventListener() {

            @Override
            public void connectionReestablished(RpcClientChannel clientChannel) {
                final RpcCallback<DuplexProtocol.OobMessage> clientStatusCallback =
                        parameter -> log.info("Received " + parameter);
                log.info("connectionReestablished " + clientChannel);

                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), clientStatusCallback);
                log.info("connectionChanged " + clientChannel);
                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), clientStatusCallback);
            }
        };
        rpcEventNotifier.setEventListener(listener);
        serverFactory.registerConnectionEventListener(rpcEventNotifier);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup(2,
                new RenamingThreadFactoryProxy("boss", Executors.defaultThreadFactory()));
        EventLoopGroup workers = new NioEventLoopGroup(16,
                new RenamingThreadFactoryProxy("worker", Executors.defaultThreadFactory()));
        serverBootstrap.group(boss, workers);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.option(ChannelOption.SO_SNDBUF, 1048576);
        serverBootstrap.option(ChannelOption.SO_RCVBUF, 1048576);
        serverBootstrap.childOption(ChannelOption.SO_RCVBUF, 1048576);
        serverBootstrap.childOption(ChannelOption.SO_SNDBUF, 1048576);
        serverBootstrap.option(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childHandler(serverFactory);
        serverBootstrap.localAddress(serverInfo.getPort());

        CleanShutdownHandler shutdownHandler = new CleanShutdownHandler();

        shutdownHandler.addResource(boss);
        shutdownHandler.addResource(workers);
        shutdownHandler.addResource(rpcExecutor);

        // Bind and start to accept incoming connections.
        serverBootstrap.bind();
        //
        log.info("Serving " + serverBootstrap);
    }

    /**
     *
     */
    public void serverLoop() throws InvalidProtocolBufferException {

        List<RpcClientChannel> clients = serverFactory.getRpcClientRegistry().getAllClients();
        for (RpcClientChannel client : clients) {
            ByteString msgS = ByteString.copyFromUtf8(String.format("Server %s OK@%ld",
                    serverFactory.getServerInfo().toString(),
                    System.currentTimeMillis()));

            DuplexProtocol.OobMessage serverMsg = DuplexProtocol.OobMessage.parseFrom(msgS);

            ChannelFuture oobSend = client.sendOobMessage(serverMsg);
            if (!oobSend.isDone()) {
                log.info("Waiting for completion.");
                oobSend.syncUninterruptibly();
            }
            if (!oobSend.isSuccess()) {
                log.warn("org.ltc.iltalk.com.googlecode.protobuf.pro.duplex.wire.OobMessage send failed.", oobSend.cause());
            }
        }
    }
}

//++++++++++++++++++++

