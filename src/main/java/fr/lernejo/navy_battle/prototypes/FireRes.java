package fr.lernejo.navy_battle.prototypes;

import fr.lernejo.navy_battle.RequestHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public enum FireRes {
    HIT("hit"),
    MISS("miss"),
    SUNK("sunk");

    private final String apiString;

    FireRes(String res) {
        this.apiString = res;
    }

    public static FireRes fromAPI(String value) {
        var res = Arrays.stream(FireRes.values()).filter(f -> f.apiString.equals(value)).findFirst();

        if (res.isEmpty())
            throw new RuntimeException("Invalid value!");

        return res.get();
    }

    public String toAPI() {
        return apiString;
    }
}
