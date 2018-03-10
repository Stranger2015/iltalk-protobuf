package org.ltc.iltalk.core;

import com.googlecode.protobuf.pro.duplex.PeerInfo;

import java.io.IOException;

import static java.lang.Class.forName;

/**
 *
 */
public interface IPeerWrapper {

    String LOCAL_HOST = "127.0.0.1";

    /**
     * @param args
     */
    static void main(String[] args) {
        int rc;
        boolean isFirstHostSender = true;
        if (args.length < 5) {
            System.err.println(
                    "usage: <peer class> <peer1 hostname> <peer1 port> " +
                            "<peer2 hostname> <peer2 port> IsFirstHostSender(<\"1\", \"0\">, ");
            rc = -1;

        } else {
            isFirstHostSender = args.length == 5 || args[5].equals("1");
        }
        try {
            Class<IPeerWrapper> peerClass = (Class<IPeerWrapper>) forName(args[0]);
            IPeerWrapper peer = peerClass.newInstance();
            rc = peer.activate(
                    createPeerInfo(args[1], args[2]),
                    createPeerInfo(args[3], args[4]),
                    isFirstHostSender
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            rc = -2;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            rc = -3;
        } catch (InstantiationException e) {
            e.printStackTrace();
            rc = -4;
        } catch (IOException e) {
            e.printStackTrace();
            rc = -5;
        }
        Runtime.getRuntime().exit(rc);
    }

    /**
     * @param host
     * @param port
     * @return
     */
    static PeerInfo createPeerInfo(String host, String port) {
        return createPeerInfo(host, port, null);
    }

    /**
     * @param host
     * @param port
     * @param pid
     * @return
     */
    static PeerInfo createPeerInfo(String host, String port, String pid) {

        return new PeerInfo(host, Integer.parseInt(port), pid);
    }

    /**
     * @return
     */
    PeerInfo getPeerInfo();


    /**
     * @param peerInfo1
     * @param peerInfo2
     * @param isFirstHostSender
     * @return
     */
    int activate(PeerInfo peerInfo1, PeerInfo peerInfo2, boolean isFirstHostSender)
            throws IOException;
}

