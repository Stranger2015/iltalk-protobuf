package org.ltc.iltalk.core;

import com.googlecode.protobuf.pro.duplex.PeerInfo;

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
    public PeerWrapperProxy(String fn, Process process) {
        super(fn, process);
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


    @Override
    public int activate(PeerInfo peerInfo1, PeerInfo peerInfo2, boolean isFirstHostSender) {

        return 0;
    }

    public Process getProcess() {
        return process;
    }
}
