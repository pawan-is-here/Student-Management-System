package com.sms;

import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String DATA_DIR = "data/";
    private static final String STUDENT_FILE = DATA_DIR + "students.txt";
    private static final String ATTENDANCE_FILE = DATA_DIR + "attendance.txt";
    private static final String GRADES_FILE = DATA_DIR + "grades.txt";
    private static final String USERS_FILE = DATA_DIR + "users.txt";

    public static void initializeFiles() {
        new File(DATA_DIR).mkdirs();
        createFileIfNotExists(STUDENT_FILE);
        createFileIfNotExists(ATTENDANCE_FILE);
        createFileIfNotExists(GRADES_FILE);
        createFileIfNotExists(USERS_FILE);
    }

    private static void createFileIfNotExists(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            showError("File initialization error");
        }
    }

    // Student operations
    public static void addStudent(Student student) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STUDENT_FILE, true))) {
            bw.write(student.toFileString());
            bw.newLine();
        }
    }

    public static List<Student> getAllStudents() throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 4) {
                    students.add(new Student(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        }
        return students;
    }

    public static Student getStudentById(String id) throws IOException {
        List<Student> students = getAllStudents();
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    // Authentication
    public static User authenticateUser(String username, String password) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                    return new User(parts[0], parts[1], parts[2]);
                }
            }
        }
        return null;
    }

    private static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}