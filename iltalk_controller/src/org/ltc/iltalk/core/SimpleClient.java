package org.ltc.iltalk.core;

import com.google.protobuf.*;
import com.googlecode.protobuf.pro.duplex.ClientRpcController;
import com.googlecode.protobuf.pro.duplex.PeerInfo;
import com.googlecode.protobuf.pro.duplex.RpcClientChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;

import java.net.InetSocketAddress;
import java.util.Map;

public class SimpleClient implements RpcClientChannel {
    /**
     * The remote peer information.
     * <p>
     * On the server side, this is the name of the {@link DuplexTcpClientPipelineFactory#getClientInfo()}
     * peer connected to the server.
     * <p>
     * On the client side, this is the name of the peer used in {@link DuplexTcpClientPipelineFactory#peerWith(InetSocketAddress, Bootstrap)}.
     *
     * @return the remote peer.
     */
    @Override
    public PeerInfo getPeerInfo() {
        return null;
    }

    /**
     * Create an RPC controller for handling an individual call.
     * Note, the controller can only be re-used after reset() is called.
     *
     * @return a new RPC controller.
     */
    @Override
    public ClientRpcController newRpcController() {
        return null;
    }

    /**
     * When the underlying channel closes, all pending calls
     * must fail. Otherwise blocking calls will block forever.
     */
    @Override
    public void close() {

    }

    /**
     * True if the underlying channel is closed.
     *
     * @return true if the underlying channel is closed.
     */
    @Override
    public boolean isClosed() {
        return false;
    }

    /**
     * Set the RpcClientAttribute value.
     *
     * @param name
     * @param attributeValue
     */
    @Override
    public void setAttribute(String name, Object attributeValue) {

    }

    /**
     * Get a RpcClientChannel attribute value.
     *
     * @param name
     * @return a RpcClientChannel attribute value with the name, or null if not present.
     */
    @Override
    public Object getAttribute(String name) {
        return null;
    }

    /**
     * Get an imutable copy of the RpcClientChannel's attributes.
     *
     * @return
     */
    @Override
    public Map<String, Object> getAttributes() {
        return null;
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
        return null;
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
        return null;
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
    public Message callBlockingMethod(Descriptors.MethodDescriptor method, RpcController controller, Message request, Message responsePrototype) throws ServiceException {
        return null;
    }

    /**
     * Call the given method of the remote service.  This method is similar to
     * {@code Service.callMethod()} with one important difference:  the caller
     * decides the types of the {@code Message} objects, not the callee.  The
     * request may be of any type as long as
     * {@code request.getDescriptor() == method.getInputType()}.
     * The response passed to the callback will be of the same type as
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

    }
}
