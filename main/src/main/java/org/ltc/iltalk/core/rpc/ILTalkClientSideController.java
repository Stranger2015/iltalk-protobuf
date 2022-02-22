package org.ltc.iltalk.core.rpc;

import com.google.protobuf.RpcCallback;
import com.googlecode.protobuf.pro.duplex.PeerInfo;

import java.util.Properties;

import static org.ltc.iltalk.core.rpc.ILTalkRpcController.State.CLIENT;

/**
 *
 */
public class ILTalkClientSideController extends ILTalkRpcController {

//    private final PeerInfo clientInfo1;
//    private final PeerInfo serverInfo1;
//
//    private final PeerInfo clientInfo2;
//    private final PeerInfo serverInfo2;
//    private final PeerInfo dbServerInfo;
//    private final PeerInfo dbClientInfo;

//    private RpcClientChannel channel;

    private ILTalkClient rpcClient;
   // private ILTalkClient dbRpcClient;

    public boolean isDynamicSchemaMode;

    /**
     * @param talkProps Properties
     */
    public ILTalkClientSideController( Properties talkProps ) {

        super( CLIENT, talkProps );

        //String clientHostNameKey1 = "host.name.1";
        //String clientHostNameKey2 = "host.name.2";
//
//        String serverHostNameKey1 = "host.name.1";
//        String serverHostNameKey2 = "host.name.2";
//
//        String clientPortKey1 = "host.port.1";
//        String clientPortKey2 = "host.port.2";
//        String serverPortKey1 = "server.port.1";
//        String serverPortKey2 = "server.port.2";
//
//        String dbServerHostNameKey = "database.host.name";
//        String dbClientHostNameKey = "database.host.name";
//        String dbServerPortKey = "database.server.port";//fixme
//        String dbClientPortKey = "database.host.port";
//
//        String lang1 = talkProps.getProperty("lang.1", "Java");
//        String lang2 = talkProps.getProperty("lang.2", "Logtalk");
//        String mode = talkProps.getProperty("iltalk.protocol.mode", "dynamic");

//        if (mode.equalsIgnoreCase("dynamic")) {
//            this.isDynamicSchemaMode = true;
//        }
//
//        clientInfo1 = createPeerInfo(clientHostNameKey1, clientPortKey1);
//        clientInfo2 = createPeerInfo(clientHostNameKey2, clientPortKey2);
//
//        serverInfo1 = createPeerInfo(serverHostNameKey1, serverPortKey1);
//        serverInfo2 = createPeerInfo(serverHostNameKey2, serverPortKey2);

//        dbServerInfo = createPeerInfo(dbServerHostNameKey, dbServerPortKey);
//        dbClientInfo = createPeerInfo(dbClientHostNameKey, dbClientPortKey);
//
//        channel = new RpcClientChannel() {
//        }
//
//        PeerInfo clientInfo1;
        rpcClient = new ILTalkClient(clientInfo1, serverInfo1, dbClientInfo, dbServerInfo);
//        dbRpcClient = new ILTalkClient(clientInfo2, serverInfo2, dbClientInfo, dbServerInfo);

//        client1.activate(clientInfo1, serverInfo1, dbServerInfo, dbClientInfo, true);
//        client2.activate(clientInfo2, serverInfo2, dbServerInfo, dbClientInfo, false);
    }

//    private PeerInfo createPeerInfo(String hostKey, String portKey) {
//        String host = this.talkProps.getProperty(hostKey, LOCAL_HOST);
//        int port = Integer.parseInt(talkProps.getProperty(portKey));
//
//        return new PeerInfo(host, port);
//    }

//    private static boolean isUptodate(String fn) {
//        File file = new File(fn + ".proto");
//        return file.exists() && file.isFile();// && file.lastModified() == getCompiled(fn);
//    }

//    private static long getCompiled(String fn) {
//       return database.getSchemaCompiledDete(fn);
//    }

    public ILTalkClient getRpcClient() {
        return rpcClient;
    }

//    public ILTalkClient getDbRpcClient() {
//        return dbRpcClient;
//    }

    /**
     * Loading host-side controller
     *
     * @param args
     */
    public static void main(String[] args) {
        IPeerWrapper.main(args);
    }
//        ILTalkClientSideController controller = null;
//        InputStream stream = null;
//
//        try {
//            Properties props = new Properties();
//            stream = controller.getClass().getClassLoader().getResourceAsStream("iltalk.properties");
//            // load a properties file
//            props.load(stream);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            } finally {
//                if (stream != null) {
//                    try {
//                        stream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();`
//                    }
//                }
//            }

//        if (controller.isDynamicSchemaMode) {
//            DynamicSchema.Builder schemaBuilder = DynamicSchema.newBuilder();
//            // schemaBuilder.setName("ILTalkProtocol.proto");
//
//            MessageDefinition msgDef = MessageDefinition.newBuilder(IPeerWrapper.MAIN_PROTO_NAME) // message ILTalk
////                    .addField("required", "int32", "id", 1)        // required int32 id = 1
////                    .addField("required", "string", "name", 2)    // required string name = 2
////                    .addField("optional", "string", "email", 3)    // optional string email = 3
//                    .build();
//
//            schemaBuilder.addMessageDefinition(msgDef);
//            DynamicSchema schema = null;
//            try {
//                schema = schemaBuilder.build();
//            } catch (Descriptors.DescriptorValidationException e) {
//                e.printStackTrace();
//                throw new Error("Can't build", e);
//            }
////             Create dynamic message from schema
////                FileDescriptorProto fdp=getDefaultInstance(), Builder.schemaBuilder, true);
////                DynamicMessage.getDefaultInstance(Descriptors.FileDescriptor.buildFrom(
////            DynamicMessage.Builder msgBuilder = schema.newMessageBuilder(
////            Descriptors.Descriptor msgDesc = msgBuilder.getDescriptorForType();
//
//
////        DuplexProtocol.OobMessage talkMessage = DuplexProtocol.OobMessage.getDefaultInstance();
////        rpcClient.sendOobMessage(schema);
//
//            //FileInputStream fin = new FileInputStream("iltalk.desc");
//            //Descriptors.Descriptor desc=Descriptors.
//            //Descriptors.FileDescriptor fd  =  Descriptors.FileDescriptor..buildFrom(fin).parseFrom(fin);
//            //Descriptors.Descriptor md = set.getFile(0).getMessageType(0);
//            //Descriptors.Descriptor
//        }
//    }

    /**
     * Resets the RpcController to its initial state so that it may be reused in
     * a new call.  This can be called from the host side only.  It must not
     * be called while an RPC is in progress.
     */
    @Override
    public void reset() {
        super.reset();
    }

    /**
     * After a call has finished, returns true if the call failed.  The possible
     * reasons for failure depend on the RPC implementation.  {@code failed()}
     * most only be called on the host side, and must not be called before a
     * call has finished.
     */
    @Override
    public boolean failed() {
        return super.failed();
    }

    /**
     * If {@code failed()} is {@code true}, returns a human-readable description
     * of the error.
     */
    @Override
    public String errorText() {
        return super.errorText();
    }

    /**
     * Advises the RPC system that the caller desires that the RPC call be
     * canceled.  The RPC system may cancel it immediately, may wait awhile and
     * then cancel it, or may not even cancel the call at all.  If the call is
     * canceled, the "done" callback will still be called and the RpcController
     * will indicate that the call failed at that time.
     */
    @Override
    public void startCancel() {
        super.startCancel();
    }

    /**
     * Causes {@code failed()} to return true on the host side.  {@code reason}
     * will be incorporated into the message returned by {@code errorText()}.
     * If you find you need to return machine-readable information about
     * failures, you should incorporate it into your response protocol buffer
     * and should NOT call {@code setFailed()}.
     *
     * @param reason
     */
    @Override
    public void setFailed(String reason) {
        super.setFailed(reason);
    }

    /**
     * If {@code true}, indicates that the host canceled the RPC, so the server
     * may as well give up on replying to it.  This method must be called on the
     * server side only.  The server should still call the final "done" callback.
     */
    @Override
    public boolean isCanceled() {
        return super.isCanceled();
    }

    /**
     * Asks that the given callback be called when the RPC is canceled.  The
     * parameter passed to the callback will always be {@code null}.  The
     * callback will always be called exactly once.  If the RPC completes without
     * being canceled, the callback will be called after completion.  If the RPC
     * has already been canceled when NotifyOnCancel() is called, the callback
     * will be called immediately.
     *
     * <p>{@code notifyOnCancel()} must be called no more than once per request.
     * It must be called on the server side only.
     *
     * @param callback
     */
    @Override
    public void notifyOnCancel(RpcCallback<Object> callback) {
            super.notifyOnCancel(callback);
    }

//    public PeerInfo getDbClientInfo() {
//        return dbClientInfo;
//    }
}