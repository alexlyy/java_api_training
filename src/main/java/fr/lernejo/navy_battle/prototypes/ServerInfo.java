package fr.lernejo.navy_battle.prototypes;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerInfo {
    private final String id;
    private final String url;
    private final String message;

    public ServerInfo(String id, String url, String message) {
        this.id = id;
        this.url = url;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("url", url);
        obj.put("message", message);
        return obj;
    }
    public ServerInfo withURL(String url) {
        return new ServerInfo(
                this.id,
                url,
                this.message
        );
    }

    public static ServerInfo fromJSON(JSONObject object) throws JSONException {
        return new ServerInfo(
                object.getString("id"),
                object.getString("url"),
                object.getString("message")
        );
    }


}
