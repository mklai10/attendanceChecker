package persistence;

import org.json.JSONObject;

public interface Writable {
    // Code gotten from JsonSerializationDemo
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
