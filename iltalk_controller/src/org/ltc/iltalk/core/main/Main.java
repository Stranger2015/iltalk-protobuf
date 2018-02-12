package org.ltc.iltalk.core.main;


import com.google.protobuf.Descriptors;
import com.googlecode.protobuf.pro.duplex.PeerInfo;
import org.ltc.iltalk.core.ILTalkProtocolHandler;

import java.io.IOException;

/**
 *
 */
public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            throw new Error();
        }

        try {
            start(args[0]);
        } catch (IOException | Descriptors.DescriptorValidationException e) {
            e.printStackTrace();
        }
    }

    private static int start( String talkPropsFN ) throws IOException, Descriptors.DescriptorValidationException {

        ILTalkProtocolHandler handler = new ILTalkProtocolHandler(talkPropsFN);
        return handler.mainProc(handler.getSchema("ILTalkProtocol.proto"));
    }
}


