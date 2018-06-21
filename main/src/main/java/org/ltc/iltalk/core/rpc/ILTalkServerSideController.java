package org.ltc.iltalk.core.rpc;


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
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Properties;
import java.util.concurrent.Executors;

public class ILTalkServerSideController extends ILTalkRpcController {

    protected PeerInfo serverInfo;
    private DuplexTcpServerPipelineFactory serverFactory;
    private RpcCallback<DuplexProtocol.OobMessage> clientStatusCallback;

    protected ILTalkServerSideController(Properties talkProps, ILTalkClientSideController clCtrl) {
        super(State.SERVER, talkProps);
        //  PeerInfo serverInfo = new PeerInfo(serverHostname, serverPort);
        // RPC payloads are uncompressed when logged - so reduce logging
        CategoryPerServiceLogger logger = new CategoryPerServiceLogger();
        logger.setLogRequestProto(false);
        logger.setLogResponseProto(false);

        // Configure the server.
        serverFactory = new DuplexTcpServerPipelineFactory(serverInfo);
        RpcServerCallExecutor rpcExecutor = new ThreadPoolCallExecutor(10, 10);
        serverFactory.setRpcServerCallExecutor(rpcExecutor);
        serverFactory.setLogger(logger);

        clientStatusCallback = parameter -> {
            log.info("Received " + parameter);
        };

        // setup a RPC event listener - it just logs what happens
        RpcConnectionEventNotifier rpcEventNotifier = new RpcConnectionEventNotifier();
        RpcConnectionEventListener listener = new RpcConnectionEventListener() {

            @Override
            public void connectionReestablished(RpcClientChannel clientChannel) {
                final RpcCallback<DuplexProtocol.OobMessage> clientStatusCallback =
                        parameter -> log.info("Received " + parameter);
                log.info("connectionReestablished " + clientChannel);

                clientChannel.setOobMessageCallback(DuplexProtocol.OobMessage.getDefaultInstance(), clientStatusCallback);
            }

            @Override
            public void connectionOpened(RpcClientChannel clientChannel) {
                log.info("connectionOpened " + clientChannel);

                clientChannel.setOobMessageCallback(
                        DuplexProtocol.OobMessage.getDefaultInstance(),
                        clientStatusCallback);
            }

            @Override
            public void connectionLost(RpcClientChannel clientChannel) {
                log.info("connectionLost " + clientChannel);
            }

            @Override
            public void connectionChanged(RpcClientChannel clientChannel) {
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
        bootstrap.option(ChannelOption.SO_SNDBUF, 1048576);
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
    }

    /**
     * Resets the RpcController to its initial state so that it may be reused in
     * a new call.  This can be called from the host side only.  It must not
     * be called while an RPC is in progress.
     */
    @Override
    public void reset() {
        super.reset();
    }

    /**
     * After a call has finished, returns true if the call failed.  The possible
     * reasons for failure depend on the RPC implementation.  {@code failed()}
     * most only be called on the host side, and must not be called before a
     * call has finished.
     */
    @Override
    public boolean failed() {
        return super.failed();
    }

    /**
     * If {@code failed()} is {@code true}, returns a human-readable description
     * of the error.
     */
    @Override
    public String errorText() {
        return super.errorText();
    }

    /**
     * Advises the RPC system that the caller desires that the RPC call be
     * canceled.  The RPC system may cancel it immediately, may wait awhile and
     * then cancel it, or may not even cancel the call at all.  If the call is
     * canceled, the "done" callback will still be called and the RpcController
     * will indicate that the call failed at that time.
     */
    @Override
    public void startCancel() {
        super.startCancel();
    }

    /**
     * Causes {@code failed()} to return true on the host side.  {@code reason}
     * will be incorporated into the message returned by {@code errorText()}.
     * If you find you need to return machine-readable information about
     * failures, you should incorporate it into your response protocol buffer
     * and should NOT call {@code setFailed()}.
     *
     * @param reason
     */
    @Override
    public void setFailed(String reason) {
        super.setFailed(reason);
    }

    /**
     * If {@code true}, indicates that the host canceled the RPC, so the server
     * may as well give up on replying to it.  This method must be called on the
     * server side only.  The server should still call the final "done" callback.
     */
    @Override
    public boolean isCanceled() {
        return super.isCanceled();
    }

    /**
     * Asks that the given callback be called when the RPC is canceled.  The
     * parameter passed to the callback will always be {@code null}.  The
     * callback will always be called exactly once.  If the RPC completes without
     * being canceled, the callback will be called after completion.  If the RPC
     * has already been canceled when NotifyOnCancel() is called, the callback
     * will be called immediately.
     *
     * <p>{@code notifyOnCancel()} must be called no more than once per request.
     * It must be called on the server side only.
     *
     * @param callback
     */
    @Override
    public void notifyOnCancel(RpcCallback<Object> callback) {
        super.notifyOnCancel(callback);
    }
}