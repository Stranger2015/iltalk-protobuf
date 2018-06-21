package org.ltc.iltalk.core.rpc;

import com.googlecode.protobuf.pro.duplex.PeerInfo;

import java.lang.reflect.InvocationTargetException;

import static java.lang.Class.forName;

/**
 *
 */
public interface IPeerWrapper {

    String LOCAL_HOST = "127.0.0.1";
    String MAIN_PROTO_NAME = "ILTalkProtocol";

    /**
     * @param args
     */
    static void main(String[] args) {
        int rc = 0;
        boolean isSenderHost = true;
        if (args.length < 5) {
            System.err.println(
                    "usage: <peer class> <peer1 hostname> <peer1 port> " +
                            "<peer2 hostname> <peer2 port> isSenderHost(<\"true\", \"f;sr\">, ");
            rc = -1;

        } else {
            isSenderHost = args.length == 5 || args[5].equals("true");
        }
        try {
            Class<IPeerWrapper> peerClass = (Class<IPeerWrapper>) forName(args[0]);
            IPeerWrapper peer = peerClass.getConstructor().newInstance();

//            rc = peerClass.activate(
//                    createPeerInfo(args[1], args[2]),
//                    createPeerInfo(args[3], args[4]),
//                    isFirstHostSender
//            );
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            rc = createRc(e , -1);
           //throw new Error(e);
        }
        Runtime.getRuntime().halt(rc);
    }

    static int createRc(Exception e, int rc ) {
           return --rc;
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


//    /**
//     * @param peerInfo1
//     * @param peerInfo2
//     * @param isFirstHostSender
//     * @return
//     */
//    int activate(PeerInfo peerInfo1, PeerInfo peerInfo2, boolean isFirstHostSender)
//            throws IOException;
}

