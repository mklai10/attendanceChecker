package model;

import org.json.JSONObject;
import persistence.Writable;

public class Student implements Writable {
    private final String name;
    private String status;

    public Student(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("status", status);
        return json;
    }
}
