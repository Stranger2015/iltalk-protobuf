package org.ltc.iltalk.core;


import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.googlecode.protobuf.pro.duplex.*;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerCallExecutor;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerExecutorCallback;
import com.googlecode.protobuf.pro.duplex.execute.ThreadPoolCallExecutor;
import com.googlecode.protobuf.pro.duplex.listener.RpcConnectionEventListener;
import com.googlecode.protobuf.pro.duplex.logging.CategoryPerServiceLogger;
import com.googlecode.protobuf.pro.duplex.logging.RpcLogger;
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

import java.util.List;
import java.util.concurrent.Executors;

import static com.googlecode.protobuf.pro.duplex.wire.DuplexProtocol.OobMessage;
import static com.googlecode.protobuf.pro.duplex.wire.DuplexProtocol.OobMessage.getDefaultInstance;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Copyright 2010-2013 Peter Klauser
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


public class ILTalkServer implements RpcServerExecutorCallback {

    protected Logger log = getLogger(getClass());

    RpcServer delegate;

    public ILTalkServer(RpcClient rcpClient,
                        RpcServiceRegistry rpcServiceRegistry,
                        RpcServerCallExecutor callExecutor,
                        RpcLogger logger) {
        //      super("NUL.PROPERTIES");
        delegate = new RpcServer(rcpClient, rpcServiceRegistry, callExecutor, logger);
    }

    @Override
    public int activate(PeerInfo serverInfo, PeerInfo remoteClientInfo, boolean isHost1Sender) throws InvalidProtocolBufferException {
        // PeerInfo serverInfo = new PeerInfo(hostname, port);
        // RPC payloads are uncompressed when logged - so reduce logging
        CategoryPerServiceLogger logger = new CategoryPerServiceLogger();
        logger.setLogRequestProto(false);
        logger.setLogResponseProto(false);
        // Configure the server.
        DuplexTcpServerPipelineFactory serverFactory = new DuplexTcpServerPipelineFactory(getPeerInfo());

        RpcServerCallExecutor rpcExecutor = new ThreadPoolCallExecutor(10, 10);
        serverFactory.setRpcServerCallExecutor(rpcExecutor);
        serverFactory.setLogger(logger);

        final RpcCallback<OobMessage> clientOobMessageCallback =
                parameter -> log.info("Received " + parameter);
        // setup a RPC event listener - it just logs what happens
        RpcConnectionEventNotifier rpcEventNotifier = new RpcConnectionEventNotifier();
        RpcConnectionEventListener listener = new RpcConnectionEventListener() {
            @Override
            public void connectionReestablished(RpcClientChannel clientChannel) {
                log.info("connectionReestablished " + clientChannel);

                clientChannel.setOobMessageCallback(getDefaultInstance(), clientOobMessageCallback);
            }

            @Override
            public void connectionOpened(RpcClientChannel clientChannel) {
                log.info("connectionOpened " + clientChannel);

                clientChannel.setOobMessageCallback(getDefaultInstance(), clientOobMessageCallback);
            }

            @Override
            public void connectionLost(RpcClientChannel clientChannel) {
                log.info("connectionLost " + clientChannel);
            }

            @Override
            public void connectionChanged(RpcClientChannel clientChannel) {
                log.info("connectionChanged " + clientChannel);
                clientChannel.setOobMessageCallback(getDefaultInstance(), clientOobMessageCallback);
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

        return mainLoop(serverFactory);
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
