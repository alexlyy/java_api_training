package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.prototypes.ServerInfo;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {
    @Test
    public void ServerTest() throws IOException {
        new Server().startServer(8888,null);
        String url = "http://localhost:8888/ping";
        String source ="";
        URL oracle = new URL(url);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            source +=inputLine;
        in.close();
        assertEquals("OK", source);
    }
    @Test
    void testIt() {
        var one = new ServerInfo("id", "url", "message");
        assertEquals("id", one.getId());
        assertEquals("url", one.getUrl());
        assertEquals("message", one.getMessage());
    }

    @Test
    void testJSON() {
        JSONObject in = new JSONObject("{\"id\": \"my_id\", \"url\":\"my_url\",\"message\":\"my_message\"}");
        var srv = ServerInfo.fromJSON(in);
        assertEquals("my_id", srv.getId());
        assertEquals("my_url", srv.getUrl());
        assertEquals("my_message", srv.getMessage());

        assertEquals(srv.toJSON().toString(), in.toString());
    }
}
