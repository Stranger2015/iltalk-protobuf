package org.ltc.iltalk.core;

import com.googlecode.protobuf.pro.duplex.PeerInfo;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

abstract public class Peer implements IPeer {

    protected static final Map<Class<?>, Logger> loggers = new HashMap<>();

    protected final Logger log = getPeerLogger(getClass());//singletons

    public static Logger getPeerLogger(Class<?> c) {
        Logger log;
        if (!loggers.containsKey(c)) {
            log = getLogger(c);
            loggers.put(c, log);
        } else {
            log = loggers.get(c);
        }

        return log;
    }

    protected Properties talkProps = new Properties();

    public PeerInfo getPeerInfoLocal() {
        return peerInfoLocal;
    }

    public PeerInfo getPeerInfoRemote() {
        return peerInfoRemote;
    }

    public IPeer getPeer1() {
        return peer1;
    }

    public IPeer getPeer2() {
        return peer2;
    }

    protected final PeerInfo peerInfoLocal;
    protected final PeerInfo peerInfoRemote;
    protected IPeer peer1;
    protected IPeer peer2;

    public Peer(String fn) throws IOException, InterruptedException {
        talkProps.load(new BufferedReader(new FileReader(fn)));

        String hostName1 = talkProps.getProperty("host.name.1", IPeer.LOCAL_HOST);
        String hostName2 = talkProps.getProperty("host.name.2", IPeer.LOCAL_HOST);

        String port1 = talkProps.getProperty("host.port.1");
        String port2 = talkProps.getProperty("host.port.2");

        String className1 = talkProps.getProperty("peer.class.name.1", defaultClassName1());
        String className2 = talkProps.getProperty("peer.class.name.2", defaultClassName2());

        peer1 = launchPeerProcesses(className1, hostName1, port1, "1", hostName2, port2);
        peer2 = launchPeerProcesses(className2, hostName2, port2, "0", hostName1, port1);

        peerInfoLocal = null;
        peerInfoRemote = null;
    }

    public String defaultClassName1() {
        return "org.ltc.iltalk.core.ILTalkClient";
    }

    public String defaultClassName2() {
        return defaultClassName1();
    }

    //uswrapper
    private IPeer launchPeerProcesses(
            String clazz,
            String hostName,
            String port,
            String is1,
            String remoteHostName,
            String remotePort
    )
            throws IOException, InterruptedException {

        String[] command = new String[]{
                "java", clazz, hostName, port, is1, remoteHostName, remotePort
        };

        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        log.info(command + " exitValue() " + process.exitValue());
        return new PeerProxy(process);
    }
}
