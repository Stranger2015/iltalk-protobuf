package org.ltc.iltalk.core.rpc;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import org.slf4j.Logger;

import java.util.Properties;

import static java.lang.String.format;
import static org.ltc.iltalk.core.rpc.ILTalkRpcController.State.CLIENT;
import static org.ltc.iltalk.core.rpc.ILTalkRpcController.State.SERVER;
import static org.ltc.iltalk.core.util.SingletonLogger.getLogger;

abstract public class ILTalkRpcController implements RpcController {
    protected Logger log = getLogger(getClass());

    protected int errorCode;
    protected String errorMessage;
    protected boolean canceled;

    public enum State {
        UNDEFINED,
        CLIENT,
        SERVER,
    }

    protected State currentState;

    protected Properties talkProps;


    public ILTalkRpcController(State state, Properties talkProps) {
        this.talkProps = talkProps;
        currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

    /**
     * Resets the RpcController to its initial state so that it may be reused in
     * a new call.  This can be called from the client side only.  It must not
     * be called while an RPC is in progress.
     */

    @Override
    public void reset() {
        checkState( CLIENT );

        errorCode = 0;
        errorMessage = "no.error";
        canceled = false;
    }

    private void checkState(State state) {
        if (currentState != state) {
            String msg = format("\nInstance of %s is used as the %s but is applied to the context for %s",
                    getClass(), currentState, state);
            throw new IllegalStateException(msg);
        }
    }

    /**
     * After a call has finished, returns true if the call failed.  The possible
     * reasons for failure depend on the RPC implementation.  {@code failed()}
     * most only be called on the client side, and must not be called before a
     * call has finished.
     */

    @Override
    public boolean failed() {
        checkState( CLIENT );
        return errorCode != 0;
    }

    /**
     * If {@code failed()} is {@code true}, returns a human-readable description
     * of the error.
     */
    @Override
    public String errorText() {
        checkState( CLIENT );
        return errorMessage;
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
        checkState( CLIENT );
        canceled = true;
    }

    /**
     * Causes {@code failed()} to return true on the client side.  {@code reason}
     * will be incorporated into the message returned by {@code errorText()}.
     * If you find you need to return machine-readable information about
     * failures, you should incorporate it into your response protocol buffer
     * and should NOT call {@code setFailed()}.
     *
     * @param reason
     */
    @Override
    public void setFailed(String reason) {
        checkState(CLIENT);
        errorCode = 1;//order of message in bundle reason assumed a key
    }

    /**
     * If {@code true}, indicates that the client canceled the RPC, so the server
     * may as well give up on replying to it.  This method must be called on the
     * server side only.  The server should still call the final "done" callback.
     */
    @Override
    public boolean isCanceled() {
        checkState( CLIENT );
        return canceled;
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
        checkState( SERVER );
    }
}
