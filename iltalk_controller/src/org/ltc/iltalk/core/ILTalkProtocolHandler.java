package org.ltc.iltalk.core;

import com.googlecode.protobuf.pro.duplex.PeerInfo;

import java.io.IOException;

public class ILTalkProtocolHandler extends PeerWrapper {


//    private final IPeerWrapper peer1;
//    private final IPeerWrapper peer2;

//    protected final Properties talkProps = new Properties();

    /**
     * @param fn
     * @throws IOException
     * @throws InterruptedException
     */
    public ILTalkProtocolHandler(String fn) {
        super(fn);

    }

    /**
     * @return
     */
    @Override
    public PeerInfo getPeerInfo() {
        return null;
    }

    /**
     * @param peerInfo1
     * @param peerInfo2
     * @param isFirstHostSender
     * @return
     */
    @Override
    public int activate(PeerInfo peerInfo1, PeerInfo peerInfo2, boolean isFirstHostSender)



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



