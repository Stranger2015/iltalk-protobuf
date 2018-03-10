package grpc;

import org.apache.camel.main.Main;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) {
        Main main = new Main();
        main.addRouteBuilder(new MyRouteBuilder());
        main.run(args);
    }

}

