package org.ltc.iltalk.core.rpc;

import com.google.protobuf.RpcCallback;

import java.util.Properties;

public class ILTalkClientServerController extends ILTalkRpcController {

private final ILTalkClientSideController clCtrl;
private final ILTalkServerSideController srvCtrl;


    public ILTalkClientServerController(Properties props) {
        super(State.UNDEFINED, props);

        this.clCtrl = new ILTalkClientSideController(props);
        this.srvCtrl = new ILTalkServerSideController(props, this.clCtrl);
    }

    /**
     * Resets the RpcController to its initial state so that it may be reused in
     * a new call.  This can be called from the host side only.  It must not
     * be called while an RPC is in progress.
     */
    @Override
    public void reset() {
        clCtrl.reset();
    }

    /**
     * After a call has finished, returns true if the call failed.  The possible
     * reasons for failure depend on the RPC implementation.  {@code failed()}
     * most only be called on the host side, and must not be called before a
     * call has finished.
     */
    @Override
    public boolean failed() {
        return clCtrl.failed();
    }

    /**
     * If {@code failed()} is {@code true}, returns a human-readable description
     * of the error.
     */
    @Override
    public String errorText() {
        return clCtrl.errorText();
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
        clCtrl.startCancel();
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
        clCtrl.setFailed(reason);
    }

    /**
     * If {@code true}, indicates that the host canceled the RPC, so the server
     * may as well give up on replying to it.  This method must be called on the
     * server side only.  The server should still call the final "done" callback.
     */
    @Override
    public boolean isCanceled() {
        return clCtrl.isCanceled();
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
        srvCtrl.notifyOnCancel(callback);
    }

    public ILTalkClientSideController getClCtrl() {
        return clCtrl;
    }

    public ILTalkServerSideController getSrvCtrl() {
        return srvCtrl;
    }
}
