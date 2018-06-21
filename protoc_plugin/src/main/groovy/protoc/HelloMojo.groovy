//
// Generated from archetype; please customize.
//

package protoc

/**
 * Example Maven2 Groovy Mojo.
 *
 * @goal hello
 */
class HelloMojo
        extends GroovyMojo {
    /**
     * The hello message to display.
     *
     * @parameter expression="${message}" default-value="Hello World"
     */
    String message

    void execute() {
        println "${message}"
    }
}
