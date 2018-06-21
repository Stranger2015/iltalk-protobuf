package org.ltc.iltalk.core.rpc;

import com.googlecode.protobuf.pro.duplex.PeerInfo;

import java.io.IOException;
import java.util.Properties;

public class ILTalkProtocolHandler extends PeerWrapper {


//    private final IPeerWrapper peer1;
//    private final IPeerWrapper peer2;

//    protected final Properties talkProps = new Properties();

    /**
     *
     * @param state
     * @param properties
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public ILTalkProtocolHandler(State state, Properties properties) throws Exception {
        super(state, properties);

    }

    /**
     * @return
     */
    @Override
    public PeerInfo getPeerInfo() {
        return null;
    }

//    @Override
//    public int activate(PeerInfo peerInfo1, PeerInfo peerInfo2, boolean isFirstHostSender) {
//
//        return 0;
//    }

    @Override
    public IPeerWrapper getPeer1() {
        return null;
    }

    @Override
    public IPeerWrapper getPeer2() {
        return null;
    }
}



