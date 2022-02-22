package org.ltc.iltalk.protobuf.compiler;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor.Syntax;
import com.google.protobuf.compiler.PluginProtos;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest.*;

/**
 * protoc --proto_path=IMPORT_PATH --cpp_out=DST_DIR --java_out=DST_DIR --python_out=DST_DIR path/to/file.proto
 * <p>
 * IMPORT_PATH specifies a directory in which to look for .proto files when resolving import directives.
 * If omitted, the current directory is used. Multiple import directories can be specified
 * by passing the --proto_path option multiple times; they will be searched in order.
 * -I=IMPORT_PATH can be used as a short form of --proto_path.
 * You can provide one or more output directives:
 * --cpp_out generates C++ code in DST_DIR. See the C++ generated code reference for more.
 * --java_out generates Java code in DST_DIR. See the Java generated code reference for more.
 * --python_out generates Python code in DST_DIR. See the Python generated code reference for more.
 * As an extra convenience, if the DST_DIR ends in .zip or .jar, the compiler will write the output to
 * a single ZIP-format archive file with the given name.
 * .jar outputs will also be given a manifest file as required by the Java JAR specification.
 * Note that if the output archive already exists, it will be overwritten;
 * the compiler is not smart enough to add files to an existing archive.
 * You must provide one or more .proto files as input.
 * Multiple .proto files can be specified at once.
 * Although the files are named relative to the current directory, each file must reside in one of the
 * IMPORT_PATHs, so that the compiler can determine its canonical name.
 */
public class ProtocGenGrpcLogtalk {
    public static final String ID = "protoc-gen-grpc-logtalk";
    private static final String defaultTaskID = "protoc-gen-logtalk";
    private static String taskID;
    private static String defaultID;
    private static FileDescriptor currentFile;

    private static CodeGeneratorRequest request;
    private static CodeGeneratorResponse response;

    /**
     * if __name__ == '__main__':
     * # Read request message from stdin
     *--------------------------------
     *
     *  data = sys.stdin.read()
     * # Parse request
     *-------------------------------
     * request = plugin.CodeGeneratorRequest()
     * request.ParseFromString(data)
     *
     * # Create response
     * ------------------------------
     * response = plugin.CodeGeneratorResponse()
     *
     *------------------------------
     * # Generate code
     * generate_code(request, response)
     * <p>
     * # Serialise response message
     *-----------------------------
     *
     * output = response.SerializeToString()
     *
     * # Write to stdout
     * -----------------------------
     * sys.stdout.write(output)
     *
     * @param argv
     */
    public static void main(String[] argv) throws IOException {
        Console console = System.console();
        console.printf("\nCustom PROTOC plugin ID=%s started", ID);
        if (defaultTaskID.equals(defaultID)) {
            console.printf("\nas a default plugin %s\n", defaultID);
        }
        request = getDefaultInstance();
        response = CodeGeneratorResponse.getDefaultInstance();
        try {
            InputStream stdin = System.in;
            PrintStream stdout = System.out;
            int bytesAvail = stdin.available();
            if (stdin.markSupported()) {
                stdin.mark(bytesAvail);
            }
            byte[] data = new byte[bytesAvail];
            int rc = stdin.read(data);
            switch(rc){
                case 0:
                case -1:
                default: if(rc == data.length){

                }
            }
            if (stdin.markSupported()) {
                stdin.reset();
            }
        } catch( Exception exception ) {
            exception.getStackTrace();
        }
        response.
        currentFile = PluginProtos.getDescriptor();
        Syntax syntax = currentFile.getSyntax();
        currentFile.getOptions();

    }

    public static
    CodeGeneratorRequest getRequest () {
        return request;
    }

    public static
    void setRequest ( CodeGeneratorRequest request ) {
        ProtocGenGrpcLogtalk.request = request;
    }
}
