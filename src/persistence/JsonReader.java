package persistence;

import model.Student;
import model.ClassStudents;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Bank from JSON data stored in file
// code gotten from JsonSerializationDemo
public class JsonReader {
    private final String source;

    // Effects: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Bank from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ClassStudents read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseClass(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Bank from JSON object and returns it
    private ClassStudents parseClass(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ClassStudents c = new ClassStudents(name);
        addsClassList(c, jsonObject);
        addsPresent(c, jsonObject);
        addsAbsent(c, jsonObject);
        return c;
    }

    private void addsClassList(ClassStudents c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("ClassList");
        for (Object json : jsonArray) {
            JSONObject nextClassList = (JSONObject) json;
            addClassList(c, nextClassList);
        }
    }

    private void addsPresent(ClassStudents c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Present");
        for (Object json : jsonArray) {
            JSONObject nextPresent = (JSONObject) json;
            addPresent(c, nextPresent);
        }
    }

    private void addsAbsent(ClassStudents c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Absent");
        for (Object json : jsonArray) {
            JSONObject nextAbsent = (JSONObject) json;
            addAbsent(c, nextAbsent);
        }
    }

    private void addClassList(ClassStudents c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String status = jsonObject.getString("status");
        Student student = new Student(name, status);
        student.setStatus(status);
        try {
            c.addClassList(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPresent(ClassStudents c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String status = jsonObject.getString("status");
        Student student = new Student(name, status);
        student.setStatus(status);
        try {
            c.addPresent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addAbsent(ClassStudents c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String status = jsonObject.getString("status");
        Student student = new Student(name, status);
        student.setStatus(status);
        try {
            c.addAbsent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
