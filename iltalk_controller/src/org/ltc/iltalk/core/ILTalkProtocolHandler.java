package org.ltc.iltalk.core;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.RpcCallback;
import com.googlecode.protobuf.pro.duplex.CleanShutdownHandler;
import com.googlecode.protobuf.pro.duplex.PeerInfo;
import com.googlecode.protobuf.pro.duplex.RpcClientChannel;
import com.googlecode.protobuf.pro.duplex.RpcConnectionEventNotifier;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerCallExecutor;
import com.googlecode.protobuf.pro.duplex.execute.ThreadPoolCallExecutor;
import com.googlecode.protobuf.pro.duplex.listener.RpcConnectionEventListener;
import com.googlecode.protobuf.pro.duplex.logging.CategoryPerServiceLogger;
import com.googlecode.protobuf.pro.duplex.server.DuplexTcpServerPipelineFactory;
import com.googlecode.protobuf.pro.duplex.util.RenamingThreadFactoryProxy;
import com.googlecode.protobuf.pro.duplex.wire.DuplexProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;

import static org.slf4j.LoggerFactory.getLogger;

public class ILTalkProtocolHandler {

    protected static Logger log = getLogger(ILTalkProtocolHandler.class);


    private IPeer peer1;
    private IPeer peer2;

    private final Properties talkProps = new Properties();

    public ILTalkProtocolHandler(String fn) throws IOException {

        talkProps.load(new BufferedReader(new FileReader(fn)));

        String hostNameKey1 = "host.name.1";
        String hostNameKey2 = "host.name.2";

        String portKey1 = "host.port.1";
        String portKey2 = "host.port.2";



        //talkProps.getProperty("talk");

        //  ILTalkProtocol.MethodCall methodCall = ILTalkProtocol.MethodCall.getDefaultInstance();
        //methodCall..
        //   String lang1 = talkProps.getProperty("lang.1", "Java");
        //    String lang2 = talkProps.getProperty("lang.2", "Logtalk");

//        activate( clientInfo1, clientInfo2, serverInfo );
    }

    protected PeerInfo createPeerInfo(String hostKey, String portKey) {
        String host = talkProps.getProperty(hostKey, LOCAL_HOST);
        int port = Integer.parseInt(talkProps.getProperty(portKey));

        return new PeerInfo(host, port);
    }

    public void activate() {

    }
}

////////////////////////
`
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
     * +
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
