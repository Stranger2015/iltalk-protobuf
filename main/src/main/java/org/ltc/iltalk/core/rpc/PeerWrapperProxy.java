package org.ltc.iltalk.core.rpc;

import com.googlecode.protobuf.pro.duplex.PeerInfo;

import java.io.IOException;

/**
 * Remote
 */
public class PeerWrapperProxy extends PeerWrapper {
    private final Process process;
    //   private final int pid;

    /**
     * @param process
     * @param fn
     */
    public PeerWrapperProxy(String fn, Process process) throws IOException, InterruptedException {
        super(fn);
        //this.pid = process.;//fixme pid
        this.process = process;
    }

    /**
     * @return
     */
    @Override
    public PeerInfo getPeerInfo() {
        return null;
    }


    public int activate(PeerInfo peerInfo1, PeerInfo peerInfo2, boolean isFirstHostSender) {

        return 0;
    }

    public Process getProcess() {
        return process;
    }

    @Override
    public IPeerWrapper getPeer1() {
        return null;
    }

    @Override
    public IPeerWrapper getPeer2() {
        return null;
    }
}
