    package org.ltc.iltalk.core.rpc;

    import com.google.protobuf.*;
    import com.googlecode.protobuf.pro.duplex.client.DuplexTcpClientPipelineFactory;
    import com.googlecode.protobuf.pro.duplex.client.RpcClientConnectionWatchdog;
    import com.googlecode.protobuf.pro.duplex.execute.ThreadPoolCallExecutor;
    import com.googlecode.protobuf.pro.duplex.listener.RpcConnectionEventListener;
    import com.googlecode.protobuf.pro.duplex.listener.TcpConnectionEventListener;
    import com.googlecode.protobuf.pro.duplex.logging.CategoryPerServiceLogger;
    import com.googlecode.protobuf.pro.duplex.server.DuplexTcpServerPipelineFactory;
    import com.googlecode.protobuf.pro.duplex.util.RenamingThreadFactoryProxy;
    import com.googlecode.protobuf.pro.duplex.wire.DuplexProtocol;
    import io.netty.bootstrap.Bootstrap;
    import io.netty.channel.ChannelFuture;
    import io.netty.channel.ChannelOption;
    import io.netty.channel.ChannelPipeline;
    import io.netty.channel.EventLoopGroup;
    import io.netty.channel.nio.NioEventLoopGroup;
    import io.netty.channel.socket.nio.NioSocketChannel;
    import org.ltc.iltalk.core.util.SingletonLogger;
    import org.slf4j.Logger;

    import java.net.InetSocketAddress;
    import java.util.Map;
    import java.util.concurrent.Executors;

/**
 *
 */
public class ILTalkClient implements RpcClientChannel {
    protected  Logger log = SingletonLogger.getLogger(getClass());

    RpcClient delegate;
    RpcClient dbClient;

    RpcChannel channel;//CURRENTCXIEWT


    /**
     * @param clientInfo
     * @param serverInfo
     * @param dbServerInfo
     */
    public ILTalkClient(PeerInfo clientInfo, PeerInfo serverInfo, PeerInfo dbClientInfo , PeerInfo dbServerInfo ) {
        delegate = createRpcClient(clientInfo,serverInfo);
        dbClient = createRpcClient(dbClientInfo, dbServerInfo);

    }

    private RpcClient createRpcClient(PeerInfo clientInfo, PeerInfo serverInfo) {
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
            public void connectionReestablished(RpcClientChannel clientChannel) {
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

        rpcEventNotifier.setEventListener(listener);
        clientFactory.registerConnectionEventListener(rpcEventNotifier);


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

        try {
            return clientFactory.peerWith(serverInfo, bootstrap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

//    private static boolean equals(PeerInfo peerInfo, PeerInfo peerInfo1) {
//        if (peerInfo == null || peerInfo1 == null) {
//            return false;
//        }
//
//        return Objects.equals(peerInfo.getHostName(), peerInfo1.getHostName()) &&
//                peerInfo.getPort() == peerInfo1.getPort();
//    }


//
//
//       7 // Set up the event pipeline factory.
//        // setup a RPC event listener - it just logs what happens
//        RpcConnectionEventNotifier rpcEventNotifier = new RpcConnectionEventNotifier();
//        final RpcConnectionEventListener listener = new RpcConnectionEventListener() {
//            private RpcCallback<? extends Message> serverCallback
//                    = (RpcCallback<Message>) parameter -> {
//                log.info("dummy server callback " + parameter);
//            };
//+
//            @Override
//            public void connectionReestablished(RpcClientChannel clientChannel) {
//                log.info("connectionReestablished " + clientChannel);
//                channel = clientChannel;
//                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), serverCallback);
//            }
//
//            @Override
//            public void connectionOpened(RpcClientChannel clientChannel) {
//                log.info("connectionOpened " + clientChannel);
//                channel = clientChannel;
//                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), serverCallback);
//            }
//
//            @Override
//            public void connectionLost(RpcClientChannel clientChannel) {
//                log.info("connectionLost " + clientChannel);
//            }
//
//            @Override
//            public void connectionChanged(RpcClientChannel clientChannel) {
//                log.info("connectionChanged " + clientChannel);
//                channel = clientChannel;
//            }
//        };
//
//        rpcEventNotifier.setEventListener(listener);
//        clientFactory.registerConnectionEventListener(rpcEventNotifier);
//
//
//        Bootstrap bootstrap = new Bootstrap();
//        EventLoopGroup workers = new NioEventLoopGroup(16,
//                new RenamingThreadFactoryProxy("workers", Executors.defaultThreadFactory()));
//        bootstrap.group(workers);
//        bootstrap.handler(clientFactory);
//        bootstrap.channel(NioSocketChannel.class);
//        bootstrap.option(ChannelOption.TCP_NODELAY, true);
//        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
//        bootstrap.option(ChannelOption.SO_SNDBUF, 1048576);
//        bootstrap.option(ChannelOption.SO_RCVBUF, 1048576);
//
//
//        RpcClientConnectionWatchdog watchdog = new RpcClientConnectionWatchdog(clientFactory, bootstrap);
//        rpcEventNotifier.addEventListener(watchdog);
//        watchdog.start();
//
//        CleanShutdownHandler shutdownHandler = new CleanShutdownHandler();
//        shutdownHandler.addResource(workers);
//
//        try {
//            clientFactory.peerWith(delegate.getServerInfo(), bootstrap);
//            return 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }


    private void setDelegate(RpcClient rpcClient) {
        delegate = rpcClient;
    }
    private void setDbClient(RpcClient rpcClient) {
        dbClient = rpcClient;
    }

    /**
     * Create an RPC controller for handling an individual call.
     * Note, the controller can only be re-used after reset() is called.
     *
     * @return a new RPC controller.
     */
    @Override
    public ClientRpcController newRpcController() {
        return delegate.newRpcController();
    }

    /**
     * When the underlying channel closes, all pending calls
     * must fail. Otherwise blocking calls will block forever.
     */
    @Override
    public void close() {
        delegate.close();
    }

    /**
     * True if the underlying channel is closed.
     *
     * @return true if the underlying channel is closed.
     */
    @Override
    public boolean isClosed() {
        return delegate.isClosed();
    }

    /**
     * Set the RpcClientAttribute value.
     *
     * @param name
     * @param attributeValue
     */
    @Override
    public void setAttribute(String name, Object attributeValue) {
        delegate.setAttribute(name, attributeValue);
    }

    /**
     * Get a RpcClientChannel attribute value.
     *
     * @param name
     * @return a RpcClientChannel attribute value with the name, or null if not present.
     */
    @Override
    public Object getAttribute(String name) {
        return delegate.getAttribute(name);
    }

    /**
     * Get an imutable copy of the RpcClientChannel's attributes.
     *
     * @return
     */
    @Override
    public Map<String, Object> getAttributes() {
        return delegate.getAttributes();
    }

    /**
     * Return the clients underlying Netty Pipeline.
     * <p>
     * On a client, the RpcClient can customize the Netty Pipeline directly after the
     * {@link DuplexTcpClientPipelineFactory#peerWith(InetSocketAddress, Bootstrap)} completes.
     * <p>
     * On a server, the use registered {@link RpcConnectionEventNotifier} or {@link TcpConnectionEventListener}
     * on the {@link DuplexTcpServerPipelineFactory} to get notified of new or reconnecting clients,
     * allowing to customize the Netty pipeline.
     *
     * @return the low level Netty ChannelPipeline.
     */
    @Override
    public ChannelPipeline getPipeline() {
        return delegate.getPipeline();
    }

    /**
     * Send a message to the remote peer asynchronously, out-of-band with
     * respect to any ongoing RPC calls. Will cause the onUnsolicitedMessageCallback
     * called on the remote peer's RpcClient assuming the {@link } is set.
     *
     * @param message
     */
    @Override
    public ChannelFuture sendOobMessage(Message message) {
        return delegate.sendOobMessage(message);
    }

    /**
     * Allow to register a callback for unsolicited messages from the server.
     * Non correlated messages are messages which are not related to any ongoing RPC
     * call.
     * <p>
     * Only a single oobMessageListener is allowed at a time. Setting the responsePrototype
     * or oobMessageListener parameter to null will "switch off" the onMessage reception within the
     * client. This does not prohibit servers sending uncorrelated messages to a client which
     * wastes bandwidth and ignores the messages.
     *
     * @param responsePrototype  the prototype of the messages which can be handled out-of-band from the server.
     * @param oobMessageListener a callback function when an unsolicited messages is received from the server.
     */
    @Override
    public void setOobMessageCallback(Message responsePrototype, RpcCallback<? extends Message> oobMessageListener) {
        delegate.setOobMessageCallback(responsePrototype, oobMessageListener);
    }

    /**
     * Call the given method of the remote service and blocks until it returns.
     * {@code callBlockingMethod()} is the blocking equivalent to
     * {@link RpcChannel#callMethod}.
     *
     * @param method
     * @param controller
     * @param request
     * @param responsePrototype
     */
    @Override
    public Message callBlockingMethod(
            Descriptors.MethodDescriptor method,
            RpcController controller,
            Message request,
            Message responsePrototype) throws ServiceException {
        return delegate.callBlockingMethod(method, controller, request, responsePrototype);
    }

    /**
     * Call the given method of the remote service.  This method is similar to
     * {@code Service.callMethod()} with one important difference:  the caller
*     * decides the types of the {@code Message} objects, not the callee.  The
     * request may be of any type as long as
     * {@code request.getDescriptor() == method.getInputType()}.
     * The re
     * sponse passed to the callback will be of the same type as
     * {@code responsePrototype} (which must have
     * {@code getDescriptor() == method.getOutputType()}).
     *
     * @param method
     * @param controller
     * @param request
     * @param responsePrototype
     * @param done
     */
    @Override
    public void callMethod(Descriptors.MethodDescriptor method, RpcController controller, Message request, Message responsePrototype, RpcCallback<Message> done) {
        delegate.callMethod(method, controller, request, responsePrototype, done);
    }

    @Override
    public PeerInfo getPeerInfo() {
        return delegate.getPeerInfo();
    }

//    public IPeerWrapper getPeer1() {
//        return (IPeerWrapper) delegate;
//    }
//
//    public IPeerWrapper getPeer2() {
//        return null;
//    }
}

///===================

