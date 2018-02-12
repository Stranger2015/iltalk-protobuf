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

import java.util.concurrent.Executors;

public class ILTalkServerSideController{

    protected static Logger log = LoggerFactory.getLogger(ILTalkServerSideController.class);

    private final DuplexTcpServerPipelineFactory serverFactory;
    private RpcCallback<DuplexProtocol.OobMessage> clientStatusCallback;

    protected ILTalkServerSideController(String serverHostname, int serverPort)
            throws InvalidProtocolBufferException, InterruptedException{

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


        clientStatusCallback = parameter -> log.info("Received " + parameter);

        // setup a RPC event listener - it just logs what happens
        RpcConnectionEventNotifier rpcEventNotifier = new RpcConnectionEventNotifier();
        RpcConnectionEventListener listener = new RpcConnectionEventListener(){

            @Override
            public void connectionReestablished(RpcClientChannel clientChannel){
                final RpcCallback<DuplexProtocol.OobMessage> clientStatusCallback =
                        parameter -> log.info("Received " + parameter);
                log.info("connectionReestablished " + clientChannel);

                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), clientStatusCallback);
            }

            @Override
            public void connectionOpened(RpcClientChannel clientChannel){
                log.info("connectionOpened " + clientChannel);

                clientChannel.setOobMessageCallback(
                        DuplexProtocol.OobMessage.getDefaultInstance(),
                        clientStatusCallback);
            }

            @Override
            public void connectionLost(RpcClientChannel clientChannel){
                log.info("connectionLost " + clientChannel);
            }

            @Override
            public void connectionChanged(RpcClientChannel clientChannel){
                log.info("connectionChanged " + clientChannel);
                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), clientStatusCallback);
            }
        };
        rpcEventNotifier.setEventListener(listener);
        serverFactory.registerConnectionEventListener(rpcEventNotifier);

        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup(2,
                new RenamingThreadFactoryProxy("boss", Executors.defaultThreadFactory()));
        EventLoopGroup workers = new NioEventLoopGroup(16,
                new RenamingThreadFactoryProxy("worker", Executors.defaultThreadFactory()));
        bootstrap.group(boss, workers);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option( ChannelOption.SO_SNDBUF, 1048576);
        bootstrap.option(ChannelOption.SO_RCVBUF, 1048576);
        bootstrap.childOption(ChannelOption.SO_RCVBUF, 1048576);
        bootstrap.childOption(ChannelOption.SO_SNDBUF, 1048576);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.childHandler(serverFactory);
        bootstrap.localAddress(serverInfo.getPort());

        CleanShutdownHandler shutdownHandler = new CleanShutdownHandler();

        shutdownHandler.addResource(boss);
        shutdownHandler.addResource(workers);
        shutdownHandler.addResource(rpcExecutor);

        // Bind and start to accept incoming connections.
        bootstrap.bind();
        //
        log.info("Serving " + bootstrap);
    }


    public static void main(String[] args) throws Exception{
        if (args.length < 2){
            System.err.println("usage: <serverHostname> <serverPort>");
            System.exit(-1);
        }
        String serverHostname = args[0];
        int serverPort = Integer.parseInt(args[1]);

        new ILTalkServerSideController(serverHostname, serverPort).mainLoop();
    }


    /**
     *
     */
    public void mainLoop() throws InvalidProtocolBufferException, InterruptedException{

            List<RpcClientChannel> clients = serverFactory.getRpcClientRegistry().getAllClients();
            for (RpcClientChannel client : clients) {
                ByteString msgS = ByteString.copyFromUtf8(String.format("Server %s OK@%ld",
                        serverFactory.getServerInfo().toString(),
                        System.currentTimeMillis()));

                DuplexProtocol.OobMessage serverMsg = DuplexProtocol.OobMessage.parseFrom(msgS);

                ChannelFuture oobSend = client.sendOobMessage(serverMsg);
                if (!oobSend.isDone()){
                    log.info("Waiting for completion.");
                    oobSend.syncUninterruptibly();
                }
                if (!oobSend.isSuccess()){
                    log.warn("org.ltc.iltalk.com.googlecode.protobuf.pro.duplex.wire.OobMessage send failed.", oobSend.cause());
                }
            }
        }
    }
