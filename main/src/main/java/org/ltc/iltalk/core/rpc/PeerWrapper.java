package org.ltc.iltalk.core.rpc;

import com.googlecode.protobuf.pro.duplex.PeerInfo;

import java.io.IOException;
import java.util.Properties;

/**
 *
 */
abstract public class PeerWrapper extends ILTalkRpcController implements IPeerWrapper {

    /**
     *
     */
    protected Properties talkProps;

    /**
     * @return
     */
    public PeerInfo getPeerInfoLocal() {
        return peerInfoLocal;
    }

    /**
     * @return
     */
    public PeerInfo getPeerInfoRemote() {
        return peerInfoRemote;
    }

    abstract public IPeerWrapper getPeer1();

    abstract public IPeerWrapper getPeer2();

    protected PeerInfo peerInfoLocal;
    protected PeerInfo peerInfoRemote;

//    enum Type {
//        CLIENT_SERVER_CONTROLLER,
//        CLIENT_CONTROLLER,
//        SERVER_CONTROLLER,
//    }

    protected Object peer1;
    protected Object peer2;

    public PeerWrapper( State state, Properties talkProps ) throws Exception {
        super(state, talkProps);

        this.talkProps=talkProps;
//        this.type = CLIENT_SERVER;
     //   peer1 = client;
     //   peer1 = server;
//        ILTalkRpcController controller = new ILTalkClientServerController(cltsController, srvsController);
        String hostName1 = talkProps.getProperty("host.name.1", IPeerWrapper.LOCAL_HOST);
        String hostName2 = talkProps.getProperty("host.name.2", IPeerWrapper.LOCAL_HOST);

        String port1 = talkProps.getProperty("host.port.1");
        String port2 = talkProps.getProperty("host.port.2");

        String className1 = talkProps.getProperty("peer.class.name.1", defaultClassName1());
        String className2 = talkProps.getProperty("peer.class.name.2", defaultClassName2());

        launchPeer(className1, hostName1, port1, hostName2, port2, "0");
        launchPeer(className2, hostName2, port2, hostName1, port1, "1");

        peerInfoLocal = null;
        peerInfoRemote = null;
    }

//    public PeerWrapper(String fn, Type type, IPeerWrapper peer1, IPeerWrapper peer2)
//            throws IOException {
//
//        talkProps.load(new BufferedReader(new FileReader(fn)));
//        this.type = Type.WRAPPER;
//        this.peer1 = peer1;
//        this.peer2 = peer2;
//    }

    private String defaultClassName1() {
        return "org.ltc.iltalk.core.rpc.ILTalkClient";
    }

    private String defaultClassName2() {
        return defaultClassName1();
    }

    private IPeerWrapper launchPeer(
            String clazz,
            String hostName,
            String port,

            String remoteHostName,
            String remotePort,
            String isFirstParameterSender
    )
            throws IOException, InterruptedException {
        if (getCurrentState() != State.UNDEFINED){
            String[] command = new String[]{
                    "java", clazz, hostName, port, remoteHostName, remotePort, isFirstParameterSender
            };
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            log.info(String.format("%s exitValue() %d", command, process.exitValue()));

            return new PeerWrapperProxy(null, process);
        } else {
            ////activate

        }

        //todo
        return null;
    }
}
