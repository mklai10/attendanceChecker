package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class ClassStudents implements Writable {
    private final String name;
    private final List<Student> classList;
    private final List<Student> present;
    private final List<Student> absent;

    public ClassStudents(String name) {
        this.name = name;
        classList = new ArrayList<>();
        present = new ArrayList<>();
        absent = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Student> getClassList() {
        return classList;
    }

    public List<Student> getAbsent() {
        return absent;
    }

    public List<Student> getPresent() {
        return present;
    }

    public Student getFromClassList(String name) throws Exception {
        boolean inStudent = false;
        Student student = null;
        for (Student s : classList) {
            if (s.getName().equals(name)) {
                student = s;
                inStudent = true;
                break;
            }
        }
        if (inStudent) {
            return student;
        } else {
            throw new Exception();
        }
    }

    public void addClassList(Student student) throws Exception{
        String name = student.getName();
        boolean inStudent = false;
        for (Student s : classList) {
            if (s.getName().equals(name)) {
                inStudent = true;
                break;
            }
        }
        if (!inStudent) {
            classList.add(student);
        } else {
            throw new Exception();
        }
    }

    public void addPresent(Student student) throws Exception{
        String name = student.getName();
        boolean inStudent = false;
        for (Student s : present) {
            if (s.getName().equals(name)) {
                inStudent = true;
                break;
            }
        }
        if (!inStudent) {
            present.add(student);
        } else {
            throw new Exception();
        }
    }

    public void addAbsent(Student student) throws Exception{
        String name = student.getName();
        boolean inStudent = false;
        for (Student s : absent) {
            if (s.getName().equals(name)) {
                inStudent = true;
                break;
            }
        }
        if (!inStudent) {
            absent.add(student);
        } else {
            throw new Exception();
        }
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("ClassList", classListToJson());
        json.put("Present", presentToJson());
        json.put("Absent", absentToJson());
        return json;
    }

    private JSONArray classListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Student p : classList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    private JSONArray presentToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Student p : present) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    private JSONArray absentToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Student p : absent) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
