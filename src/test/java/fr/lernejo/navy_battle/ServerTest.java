package fr.lernejo.navy_battle;

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
        new Server().startServer(9876);
        String url = "http://localhost:9876/ping";
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
        assertEquals("Hello", source);
    }
}
