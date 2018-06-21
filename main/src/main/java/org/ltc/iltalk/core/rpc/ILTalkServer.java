package org.ltc.iltalk.core.rpc;


import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerCallExecutor;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerExecutorCallback;
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
import org.ltc.iltalk.core.util.SingletonLogger;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.Executors;


public class ILTalkServer implements RpcServerExecutorCallback {

    protected Logger log = SingletonLogger.getLogger(getClass());

    RpcServer rpcServer;

    DuplexTcpServerPipelineFactory serverFactory;


    public ILTalkServer(PeerInfo serverInfo, PeerInfo clientInfo) {
        rpcServer = createRpcServer(serverInfo, clientInfo);
    }

    private RpcServer createRpcServer(PeerInfo serverInfo, PeerInfo clientInfo) {
        // PeerInfo serverInfo = new PeerInfo(hostname, port);
        // RPC payloads are uncompressed when logged - so reduce logging

        CategoryPerServiceLogger logger = new CategoryPerServiceLogger();
        logger.setLogRequestProto(false);
        logger.setLogResponseProto(false);
        // Configure the server.
        serverFactory = new DuplexTcpServerPipelineFactory(getPeerInfo());

        RpcServerCallExecutor rpcExecutor = new ThreadPoolCallExecutor(10, 10);
        serverFactory.setRpcServerCallExecutor(rpcExecutor);
        serverFactory.setLogger(logger);

        final RpcCallback<DuplexProtocol.OobMessage> clientOobMessageCallback =
                parameter -> log.info("Received " + parameter);
        // setup a RPC event listener - it just logs what happens
        RpcConnectionEventNotifier rpcEventNotifier = new RpcConnectionEventNotifier();
        RpcConnectionEventListener listener = new RpcConnectionEventListener() {
            @Override
            public void connectionReestablished(RpcClientChannel clientChannel) {
                log.info("connectionReestablished " + clientChannel);

                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), clientOobMessageCallback);
            }

            @Override
            public void connectionOpened(RpcClientChannel clientChannel) {
                log.info("connectionOpened " + clientChannel);

                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), clientOobMessageCallback);
            }

            @Override
            public void connectionLost(RpcClientChannel clientChannel) {
                log.info("connectionLost " + clientChannel);
            }

            @Override
            public void connectionChanged(RpcClientChannel clientChannel) {
                log.info("connectionChanged " + clientChannel);
                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), clientOobMessageCallback);
            }
        };
        rpcEventNotifier.setEventListener(listener);
        serverFactory.registerConnectionEventListener(rpcEventNotifier);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup(2, new RenamingThreadFactoryProxy("boss", Executors.defaultThreadFactory()));
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

        try {
            return mainLoop(serverFactory);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

}

    /**
     * Called once after the RPC server call finishes ( or fails ).
     * An RPC server call which is canceled will not be called back.
     * <p>
     * All NotifyOnCancel notification has been done by the
     * RpcServerCallExecutor.
     *
     * @param correlationId
     * @param message       Message of RPC response, or null if controller indicates error.
     */
    @Override
    public void onFinish(int correlationId, Message message) {
        delegate.onFinish(correlationId, message);
    }

    /**
     * +
     *
     * @param serverFactory
     */
    public int mainLoop(DuplexTcpServerPipelineFactory serverFactory) throws InvalidProtocolBufferException {

        List<RpcClientChannel> clients = serverFactory.getRpcClientRegistry().getAllClients();
        for (RpcClientChannel client : clients) {
            ByteString msgS = ByteString.copyFromUtf8(String.format("Server %s OK@%d",
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
        //
        return 0;
    }


    /**
     * @return
     */
//    @Override
    public PeerInfo getPeerInfo() {
        return null;
    }

}
