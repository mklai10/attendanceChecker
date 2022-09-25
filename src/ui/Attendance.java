package ui;

import model.ClassStudents;
import model.Student;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Attendance extends JFrame implements ActionListener {
    GridBagConstraints c = new GridBagConstraints();
    GridBagConstraints scrollingC = new GridBagConstraints();

    JButton classList = new JButton("Class List");
    JButton presentList = new JButton("Present List");
    JButton absentList = new JButton("Absent List");
    JButton presentButton = new JButton("Present");
    JButton absentButton = new JButton("Absent");
    JButton searchButton = new JButton("Search");
    JButton addStudentButton = new JButton("Add Student");
    JButton resetStudentsButton = new JButton("Reset Students");
    JButton resetClassButton = new JButton("Reset Class");
    JButton deleteStudentButton = new JButton("Delete");
    JButton setUnknownButton = new JButton("Unknown");

    ArrayList<JButton> classButtons;
    ArrayList<JButton> absentButtons;
    ArrayList<JButton> presentButtons;

    JPanel classPanel = new JPanel();
    JPanel presentPanel = new JPanel();
    JPanel absentPanel = new JPanel();
    JScrollPane classScrolling = new JScrollPane(classPanel);
    JScrollPane presentScrolling = new JScrollPane(presentPanel);
    JScrollPane absentScrolling = new JScrollPane(absentPanel);
    JPanel studentInfo = new JPanel();
    JPanel basicGUI = new JPanel();

    JLabel studentName;
    JLabel studentStatus;

    private static final String JSON_STORE = "./Data/Class.json";
    ClassStudents classStudents;
    JsonWriter jsonWriter;
    JsonReader jsonReader;

    Student s = null;

    int absentCount = 0;
    int presentCount = 0;
    int classCount = 0;

    public Attendance() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setVisible(true);

        classStudents = new ClassStudents("Class");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        loadProfile();

        initializeHeader();
        initializeStartGui();
        initializeStudentInfo();
        initializeListPanel();
        classButtons = new ArrayList<>();
        absentButtons = new ArrayList<>();
        presentButtons = new ArrayList<>();
        initializeList();

        this.pack();
    }

    public void initializeHeader() {
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.ipady = 100;
        c.fill = GridBagConstraints.BOTH;

        classList.addActionListener(this);
        c.gridx = 0;
        this.add(classList, c);

        presentList.addActionListener(this);
        c.gridx = 1;
        this.add(presentList, c);

        absentList.addActionListener(this);
        c.gridx = 2;
        this.add(absentList, c);
    }

    public void initializeListPanel() {
        c.gridx = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = GridBagConstraints.REMAINDER;
        c.weightx = 0.9;
        c.weighty = 0.9;

        this.add(presentScrolling, c);
        this.add(classScrolling, c);
        this.add(absentScrolling, c);

        classPanel.setLayout(new GridBagLayout());
        presentPanel.setLayout(new GridBagLayout());
        absentPanel.setLayout(new GridBagLayout());

        scrollingC.fill = GridBagConstraints.BOTH;
        scrollingC.weightx = 0.1;
        scrollingC.weighty = 0.1;

        scrollingC.gridx = 0;
        scrollingC.gridy = 0;
        scrollingC.ipady = 0;

        classPanel.setVisible(true);
        presentPanel.setVisible(false);
        absentPanel.setVisible(false);

        classScrolling.setVisible(true);
        presentScrolling.setVisible(false);
        absentScrolling.setVisible(false);
    }

    public void initializeList() {
        if (classStudents.getClassList().size() != 0) {
            int size = classStudents.getClassList().size();
            for (int i = 0; i < size; i++) {
                Student s = classStudents.getClassList().get(i);
                classButtons.add(new JButton(s.getName()));
                classButtons.get(i).addActionListener(this);
                scrollingC.gridy = i;
                classPanel.add(classButtons.get(i), scrollingC);
            }
            classCount = size;
        }

        if (classStudents.getPresent().size() != 0) {
            int size = classStudents.getPresent().size();
            for (int i = 0; i < size; i++) {
                Student s = classStudents.getPresent().get(i);
                presentButtons.add(new JButton(s.getName()));
                presentButtons.get(i).addActionListener(this);
                scrollingC.gridy = i;
                presentPanel.add(presentButtons.get(i), scrollingC);
            }
            presentCount = size;
        }

        if (classStudents.getAbsent().size() != 0) {
            int size = classStudents.getAbsent().size();
            for (int i = 0; i < size; i++) {
                Student s = classStudents.getAbsent().get(i);
                absentButtons.add(new JButton(s.getName()));
                absentButtons.get(i).addActionListener(this);
                scrollingC.gridy = i;
                absentPanel.add(absentButtons.get(i), scrollingC);
            }
            absentCount = size;
        }
    }

    public void initializeStartGui() {
        c.weightx = 0.5;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;

        this.add(basicGUI, c);
        basicGUI.setLayout(new GridBagLayout());

        GridBagConstraints panelC = new GridBagConstraints();

        panelC.fill = GridBagConstraints.BOTH;
        panelC.weightx = 0.1;
        panelC.weighty = 0.1;
        panelC.gridwidth = 1;
        panelC.gridx = 0;
        panelC.gridy = 0;

        searchButton.addActionListener(this);
        basicGUI.add(searchButton, panelC);

        panelC.gridy = 1;

        addStudentButton.addActionListener(this);
        basicGUI.add(addStudentButton, panelC);

        panelC.gridy = 2;

        resetStudentsButton.addActionListener(this);
        basicGUI.add(resetStudentsButton, panelC);

        panelC.gridy = 3;

        resetClassButton.addActionListener(this);
        basicGUI.add(resetClassButton, panelC);

    }

    public void initializeStudentInfo() {
        studentName = new JLabel("Name: ");
        studentStatus = new JLabel("Status: ");


        c.weightx = 0.5;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;

        this.add(studentInfo, c);
        studentInfo.setLayout(new GridBagLayout());

        GridBagConstraints panelC = new GridBagConstraints();

        panelC.insets = new Insets(0,20,0,0);
        panelC.fill = GridBagConstraints.BOTH;
        panelC.weightx = 0.1;
        panelC.weighty = 0.1;

        panelC.gridx = 0;
        panelC.gridy = 0;
        panelC.gridwidth = 2;
        studentInfo.add(studentName, panelC);

        panelC.gridy = 1;
        studentInfo.add(studentStatus, panelC);

        panelC.insets = new Insets(0,0,0,0);

        panelC.gridwidth = 1;
        panelC.gridy = 2;
        presentButton.addActionListener(this);
        studentInfo.add(presentButton, panelC);


        panelC.gridx = 1;
        absentButton.addActionListener(this);
        studentInfo.add(absentButton, panelC);

        panelC.gridx = 0;
        panelC.gridy = 3;
        deleteStudentButton.addActionListener(this);
        studentInfo.add(deleteStudentButton, panelC);

        panelC.gridx = 1;
        setUnknownButton.addActionListener(this);
        studentInfo.add(setUnknownButton, panelC);

        studentInfo.setVisible(false);

    }

    public void selectStudent(String name) {
        studentName.setText("Name: " + name);
        try {
            s = classStudents.getFromClassList(name);
            studentStatus.setText("Status: " + s.getStatus());
        } catch (Exception e) {
            System.out.println("not in classList");
        }

        basicGUI.setVisible(false);
        studentInfo.setVisible(true);
        pack();
    }

    public void resetSelector() {
        studentStatus.setText("Status: ");
        studentName.setText("Name: ");
    }

    public void hideClassList() {
        classPanel.setVisible(false);
        classScrolling.setVisible(false);
    }

    public void hideAbsent() {
        absentScrolling.setVisible(false);
        absentPanel.setVisible(false);
    }

    public void hidePresent() {
        presentScrolling.setVisible(false);
        presentPanel.setVisible(false);
    }

    public void showClassList() {
        classPanel.setVisible(true);
        classScrolling.setVisible(true);
    }

    public void showAbsent() {
        absentScrolling.setVisible(true);
        absentPanel.setVisible(true);
    }

    public void showPresent() {
        presentScrolling.setVisible(true);
        presentPanel.setVisible(true);
    }

    private void clearClassList() {
        for (int i = 0; i < classCount; i++) {
            classPanel.remove(classButtons.get(i));
        }
        classStudents.getClassList().clear();
        classButtons.clear();
        classCount = 0;
    }

    private void clearPresent() {
        for (int i = 0; i < presentCount; i++) {
            presentPanel.remove(presentButtons.get(i));
        }
        classStudents.getPresent().clear();
        presentButtons.clear();
        presentCount = 0;
    }

    private void clearAbsent() {
        for (int i = 0; i < absentCount; i++) {
            absentPanel.remove(absentButtons.get(i));
        }
        classStudents.getAbsent().clear();
        absentButtons.clear();
        absentCount = 0;
    }

    public void loadProfile() {
        try {
            classStudents = jsonReader.read();
//            System.out.println("Loaded " + server.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void saveProfile() {
        try {
            jsonWriter.open();
            jsonWriter.write(classStudents);
            jsonWriter.close();
//            System.out.println("Saved " + server.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == classList) {
            hidePresent();
            hideAbsent();
            showClassList();

            basicGUI.setVisible(true);
            studentInfo.setVisible(false);

            pack();

        } else if (e.getSource() == absentList) {
            hideClassList();
            hidePresent();
            showAbsent();

            basicGUI.setVisible(true);
            studentInfo.setVisible(false);

            pack();

        } else if (e.getSource() == presentList) {
            hideClassList();
            hideAbsent();
            showPresent();

            basicGUI.setVisible(true);
            studentInfo.setVisible(false);

            pack();

        } else if (e.getSource() == addStudentButton) {
            while (true) {
                JTextField name = new JTextField();
                JTextField status = new JTextField();
                Object[] message = {
                        "Name:", name,
                        "Status:", status
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Search", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    Student s = new Student(name.getText(), status.getText());
                    if (name.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Input Is Invalid", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else if (status.getText().equals("")) {
                        s.setStatus("Unknown");
                        try {
                            classStudents.addClassList(s);
                            classButtons.add(new JButton(s.getName()));
                            classButtons.get(classCount).addActionListener(this);
                            scrollingC.gridy = classCount;
                            classPanel.add(classButtons.get(classCount), scrollingC);
                            classCount++;

                            JOptionPane.showMessageDialog(null, "Student Has Been Added");
                            pack();
                            saveProfile();
                            break;
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, "Student Is Already In The Class");
                            break;
                        }
                    } else if (status.getText().toLowerCase().equals("present")) {
                        s.setStatus("Present");
                        try {
                            classStudents.addPresent(s);
                            presentButtons.add(new JButton(s.getName()));
                            presentButtons.get(presentCount).addActionListener(this);
                            scrollingC.gridy = presentCount;
                            presentPanel.add(presentButtons.get(presentCount), scrollingC);
                            presentCount++;

                            classStudents.addClassList(s);
                            classButtons.add(new JButton(s.getName()));
                            classButtons.get(classCount).addActionListener(this);
                            scrollingC.gridy = classCount;
                            classPanel.add(classButtons.get(classCount), scrollingC);
                            classCount++;

                            JOptionPane.showMessageDialog(null, "Student Has Been Added");
                            pack();
                            saveProfile();
                            break;
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null, "Student Is Already In The Class");
                            break;
                        }
                    } else if (status.getText().toLowerCase().equals("absent")) {
                        s.setStatus("Absent");
                        try {
                            classStudents.addAbsent(s);
                            absentButtons.add(new JButton(s.getName()));
                            absentButtons.get(absentCount).addActionListener(this);
                            scrollingC.gridy = absentCount;
                            absentPanel.add(absentButtons.get(absentCount), scrollingC);
                            absentCount++;

                            classStudents.addClassList(s);
                            classButtons.add(new JButton(s.getName()));
                            classButtons.get(classCount).addActionListener(this);
                            scrollingC.gridy = classCount;
                            classPanel.add(classButtons.get(classCount), scrollingC);
                            classCount++;

                            JOptionPane.showMessageDialog(null, "Student Has Been Added");
                            pack();
                            saveProfile();
                            break;
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Student Is Already In The Class");
                            break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Status Is Invalid", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    break;
                }
            }

        } else if (e.getSource() == searchButton) {
            String name = JOptionPane.showInputDialog(null, "Student Name: ");
            if (name != null) {
                try {
                    Student s = classStudents.getFromClassList(name);
                    String status = s.getStatus();
                    JOptionPane.showMessageDialog(null, name + " is " + status.toLowerCase());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "This Student Is Not In The Class", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }

        } else if (e.getSource() == resetStudentsButton) {

            clearPresent();
            clearAbsent();

            int size = classStudents.getClassList().size();
            for (int i = 0; i < size; i++) {
                classStudents.getClassList().get(i).setStatus("Unknown");
            }

            JOptionPane.showMessageDialog(null, "Student Have Been Reset");

            basicGUI.setVisible(false);
            studentInfo.setVisible(true);
            basicGUI.setVisible(true);
            studentInfo.setVisible(false);

            pack();
            saveProfile();


        } else if (e.getSource() == resetClassButton) {

            clearPresent();
            clearAbsent();
            clearClassList();

            JOptionPane.showMessageDialog(null, "Class Has Been Reset");

            basicGUI.setVisible(false);
            studentInfo.setVisible(true);
            basicGUI.setVisible(true);
            studentInfo.setVisible(false);

            saveProfile();
            pack();

        } else if (e.getSource() == absentButton) {
            resetSelector();

            if (s.getStatus().equals("Present")) {
                int index = 0;
                for (int i = 0; i < presentCount; i++) {
                    if (presentButtons.get(i).getText().equals(s.getName())) {
                        index = i;
                    }
                }
                presentPanel.remove(presentButtons.get(index));
                presentButtons.remove(index);
                classStudents.getPresent().remove(index);

                presentCount--;
            }

            try {
                s.setStatus("Absent");

                classStudents.addAbsent(s);
                absentButtons.add(new JButton(s.getName()));
                absentButtons.get(absentCount).addActionListener(this);
                scrollingC.gridy = absentCount;
                absentPanel.add(absentButtons.get(absentCount), scrollingC);

                absentCount++;

            } catch (Exception exception) {
                System.out.println("already there");
            }

            basicGUI.setVisible(true);
            studentInfo.setVisible(false);

            saveProfile();
            pack();

        } else if (e.getSource() == presentButton) {
            resetSelector();

            if (s.getStatus().equals("Absent")) {
                int index = 0;
                for (int i = 0; i < absentCount; i++) {
                    if (absentButtons.get(i).getText().equals(s.getName())) {
                        index = i;
                    }
                }
                absentPanel.remove(absentButtons.get(index));
                absentButtons.remove(index);
                classStudents.getAbsent().remove(index);

                absentCount--;
            }

            try {
                s.setStatus("Present");

                classStudents.addPresent(s);
                presentButtons.add(new JButton(s.getName()));
                presentButtons.get(presentCount).addActionListener(this);
                scrollingC.gridy = presentCount;
                presentPanel.add(presentButtons.get(presentCount), scrollingC);

                presentCount++;

            } catch (Exception exception) {
                System.out.println("already there");
            }

            basicGUI.setVisible(true);
            studentInfo.setVisible(false);

            saveProfile();
            pack();

        } else if (e.getSource() == deleteStudentButton) {
            resetSelector();

            if (s.getStatus().equals("Absent")) {
                int index = 0;
                for (int i = 0; i < absentCount; i++) {
                    if (absentButtons.get(i).getText().equals(s.getName())) {
                        index = i;
                    }
                }
                absentPanel.remove(absentButtons.get(index));
                absentButtons.remove(index);
                classStudents.getAbsent().remove(index);

                absentCount--;
            }
            if (s.getStatus().equals("Present")) {
                int index = 0;
                for (int i = 0; i < presentCount; i++) {
                    if (presentButtons.get(i).getText().equals(s.getName())) {
                        index = i;
                    }
                }
                presentPanel.remove(presentButtons.get(index));
                presentButtons.remove(index);
                classStudents.getPresent().remove(index);

                presentCount--;
            }

            int index = 0;
            for (int i = 0; i < classCount; i++) {
                if (classButtons.get(i).getText().equals(s.getName())) {
                    index = i;
                }
            }
            classPanel.remove(classButtons.get(index));
            classButtons.remove(index);
            classStudents.getClassList().remove(index);

            classCount--;

            basicGUI.setVisible(true);
            studentInfo.setVisible(false);

            JOptionPane.showMessageDialog(null, "Student Has Been Deleted");
            saveProfile();
            pack();

        } else if (e.getSource() == setUnknownButton) {
            if (s.getStatus().equals("Absent")) {
                int index = 0;
                for (int i = 0; i < absentCount; i++) {
                    if (absentButtons.get(i).getText().equals(s.getName())) {
                        index = i;
                    }
                }
                absentPanel.remove(absentButtons.get(index));
                absentButtons.remove(index);
                classStudents.getAbsent().remove(index);

                absentCount--;
            }
            if (s.getStatus().equals("Present")) {
                int index = 0;
                for (int i = 0; i < presentCount; i++) {
                    if (presentButtons.get(i).getText().equals(s.getName())) {
                        index = i;
                    }
                }
                presentPanel.remove(presentButtons.get(index));
                presentButtons.remove(index);
                classStudents.getPresent().remove(index);

                presentCount--;
            }

            s.setStatus("Unknown");

            basicGUI.setVisible(true);
            studentInfo.setVisible(false);

            saveProfile();
            pack();

        } else {
            String name = ((JButton) e.getSource()).getText();
            selectStudent(name);
        }
    }
}
