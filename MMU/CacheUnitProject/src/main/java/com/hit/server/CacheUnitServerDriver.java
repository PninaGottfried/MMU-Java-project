package main.java.com.hit.server;

import main.java.com.hit.util.CLI;

import java.io.IOException;

public class CacheUnitServerDriver {
    public CacheUnitServerDriver() {
    }

    public static void main(String[] args) throws IOException {
        CLI cli = new CLI(System.in, System.out);
        Server server = new Server();
        cli.addPropertyChangeListener(server);
        new Thread(cli).start();
    }
}
