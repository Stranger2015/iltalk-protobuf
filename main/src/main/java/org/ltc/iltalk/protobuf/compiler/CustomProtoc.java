package org.ltc.iltalk.protobuf.compiler;


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
public class CustomProtoc extends LogtalkProtocPlugin {

public static final String ID = "compiler.id";
    private static PrintStream requestStream;

    public static void main( String[] argv ){
        System.out.printf("\nCustom proto compiler started ID=%s\n","n/a");
        requestStream = System.out;
        responseStream = System.in;
    }

}
