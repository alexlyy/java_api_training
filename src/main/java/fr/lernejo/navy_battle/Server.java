package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.prototypes.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server extends AbstractServer {
    private final Option<GameMap> localMap = new Option<>();
    private final Option<GameMap> remoteMap = new Option<>();
    private final Option<ServerInfo> localServer = new Option<>();
    private final Option<ServerInfo> remoteServer = new Option<>();

    public void startServer(int port, String connectURL) throws IOException {
        localServer.set(new ServerInfo(
                UUID.randomUUID().toString(),
                "http://localhost:" + port,
                "OK"
        ));
        if (connectURL != null)
            new Thread(() -> this.requestStart(connectURL)).start();
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", this::handlePing);
        server.createContext("/api/game/start", s -> startGame(new RequestHandler(s)));
        server.createContext("/api/game/fire", s -> HandleFire(new RequestHandler(s)));
        server.start();
    }

    public void startGame(RequestHandler handler) throws IOException {
        try {
            remoteServer.set(ServerInfo.fromJSON(handler.getJSONObject()));
            localMap.set(new GameMap(true));
            remoteMap.set(new GameMap(false));
            System.out.println("Will fight against " + remoteServer.get().getUrl());
            handler.sendJSON(202, localServer.get().toJSON());
            fire();
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }
    public void requestStart(String server) {
        try {
            localMap.set(new GameMap(true));remoteMap.set(new GameMap(false));
            var response = sendPOSTRequest(server + "/api/game/start", this.localServer.get().toJSON());
            this.remoteServer.set(ServerInfo.fromJSON(response).withURL(server));
            System.out.println("Will fight against " + remoteServer.get().getUrl());

        } catch (Exception e) {
            e.printStackTrace();System.err.println("Failed to start game!");
        }
    }

    public void HandleFire(RequestHandler handler) throws IOException {
        try {
            String cell = handler.getQueryParameter("cell");
            var pos = new Coordinates(cell);var res = localMap.get().hit(pos);var response = new JSONObject();
            response.put("consequence", res.toAPI());response.put("shipLeft", localMap.get().hasShipLeft());
            handler.sendJSON(200, response);
            if (localMap.get().hasShipLeft()) {
                fire();
            }
        } catch (Exception e) {
            e.printStackTrace();handler.sendString(400, e.getMessage());
        }
    }

    public void fire() throws IOException, InterruptedException {
        Coordinates coordinates = remoteMap.get().getNextPlaceToHit();
        var response =
                sendGETRequest(remoteServer.get().getUrl() + "/api/game/fire?cell=" + coordinates.toString());
        if (!response.getBoolean("shipLeft")) {
            return;
        }
        var result = FireRes.fromAPI(response.getString("consequence"));
        if (result != FireRes.MISS)
            remoteMap.get().setCell(coordinates, Cell.SUCCESSFUL_FIRE);
        else
            remoteMap.get().setCell(coordinates, Cell.MISSED_FIRE);
    }

    private void handlePing(HttpExchange exchange) throws IOException {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        {
            try (OutputStream os = exchange.getResponseBody()) {

                os.write(body.getBytes());
            }
        }
    }
}
