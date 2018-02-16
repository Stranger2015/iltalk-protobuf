package org.ltc.iltalk.core;

import com.googlecode.protobuf.pro.duplex.PeerInfo;

import java.io.IOException;

abstract public class Peer implements IPeer {


    protected PeerInfo peer_info;
    protected IPeer peer1;
    protected IPeer peer2;

    /**
     * @param hostname
     * @param port
     * @return
     */
    @Override
    abstract public int activate(String hostname, int port) throws IOException;

    @Override
    public PeerInfo getPeerInfo() {
        return peer_info;
    }

    @Override
    public void setPeerInfo(PeerInfo info) {
        this.peer_info = info;
    }
}
