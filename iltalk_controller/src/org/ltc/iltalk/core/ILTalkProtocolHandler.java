package org.ltc.iltalk.core;

import com.googlecode.protobuf.pro.duplex.PeerInfo;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

public class ILTalkProtocolHandler extends Peer {
    public final Logger log = getLogger(getClass());//singletins

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



