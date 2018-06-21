package org.ltc.iltalk.lang.model.protobuf.compiler;


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
public class CustomProtocPlugin extends LogtalkProtocPlugin {

public static final String ID = "compiler.id";
    PrintStream requestStream = System.out;

    InputStream responseStream = System.in;

    public static void main( String[] argv ){

        CustomProtocPlugin plugin = new CustomProtocPlugin();
        System.out.printf("\nCustom proto compiler started ID=%s\n","n/a");

        requestStream.

    }

}
