package fr.lernejo.navy_battle;

import org.json.JSONObject;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URI;


public abstract class AbstractServer {

    private final HttpClient client = HttpClient.newHttpClient();

    public JSONObject sendPOSTRequest(String url, JSONObject obj) throws IOException, InterruptedException {
        HttpRequest requetePost = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(obj.toString()))
                .build();

        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }

    public JSONObject sendGETRequest(String url) throws IOException, InterruptedException {
        HttpRequest requeteGET = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Accept", "application/json")
                .GET()
                .build();

        var response = client.send(requeteGET, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }

}
