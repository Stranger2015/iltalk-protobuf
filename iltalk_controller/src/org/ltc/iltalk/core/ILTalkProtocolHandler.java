package org.ltc.iltalk.core;

import com.googlecode.protobuf.pro.duplex.PeerInfo;

import java.io.IOException;

public class LTalkProtocolHandler extends Peer {


//    private final IPeer peer1;
//    private final IPeer peer2;

//    protected final Properties talkProps = new Properties();

    public ILTalkProtocolHandler(String fn) throws IOException, InterruptedException {
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
    public int activate(PeerInfo peerInfo1, PeerInfo peerInfo2, boolean isFirstHostSender) throws IOException {
        return 0;
    }
}
}



