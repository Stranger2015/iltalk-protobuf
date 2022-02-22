package org.ltc.iltalk.protobuf.compiler;


import org.ltc.iltalk.lang.model.protobuf.compiler.LogtalkProtocPlugin;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * //<MessageType>
 * //    <Name>my-protobuf-messages</Name>
 * //    <Module>protobuf</Module>
 * //    <ProtoPath>proto-archive;/mnt/shared/protofiles</ProtoPath>
 * //    <ProtoFile>proto-archive/person.proto</ProtoFile>
 * //    <Type>MyNamespace.Message</Type>
 * //</MessageType>
 */
public
class CustomProtoc extends LogtalkProtocPlugin {

    public static final String ID = "compiler.id";

    private final PrintStream requestStream;
    private final InputStream responseStream;


    public
    CustomProtoc ( PrintStream requestStream, InputStream responseStream ) {
        this.requestStream = requestStream;
        this.responseStream = responseStream;
    }

    public static
    void main ( String[] argv ) {
        System.out.printf("\nCustom proto compiler started ID=%s\n", "n/a");
        CustomProtoc cpp = new CustomProtoc(System.out, System.in);
    }

    public
    PrintStream getRequestStream () {
        return requestStream;
    }

    public
    InputStream getResponseStream () {
        return responseStream;
    }
}
