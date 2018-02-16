package org.ltc.iltalk.core;

import com.googlecode.protobuf.pro.duplex.PeerInfo;

import java.io.IOException;

import static java.lang.Class.forName;

public interface IPeer {

    String LOCAL_HOST = "127.0.0.1";

    static void main(String[] args) {
        int rc;
        if (args.length < 3) {
            System.err.println("usage: <peer class> <peer hostname> <peer port>");
            // System.exit(-1);
            rc = -1;
        }
        try {
            Class<IPeer> peerClass = (Class<IPeer>) forName(args[0]);
            IPeer peer = peerClass.newInstance();
            rc = peer.activate(args[1], Integer.parseInt(args[2]));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            rc = -2;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            rc = -3;
        } catch (InstantiationException e) {
            e.printStackTrace();
            rc = -4;
        }

        System.exit(rc);
    }

    /**
     * @param hostname
     * @param port
     * @return
     */
    int activate(String hostname, int port) throws IOException;

    /**
     * @return
     */
    PeerInfo getPeerInfo();

    /**
     * @param info
     */
    void setPeerInfo(PeerInfo info);

}
