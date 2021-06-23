package fr.lernejo.navy_battle;

import java.io.IOException;

public class Launcher {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(-1);
        }
        int port = Integer.parseInt(args[0]);
        try {
            new Server().startServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}