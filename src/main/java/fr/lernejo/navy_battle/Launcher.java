package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.prototypes.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.concurrent.Executors;

public class Launcher {

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Usage: Launcher [port] {server_url}");
                System.exit(-1);
            }

            int serverPort = Integer.parseInt(args[0]);
            System.out.println("Starting to listen on port " + serverPort);

            new Server().startServer(serverPort, args.length > 1 ? args[1] : null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
