package com.sms;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class StudentDashboard extends JFrame {
    private String studentId;

    public StudentDashboard(String studentId) {
        this.studentId = studentId;
        setTitle("Student Dashboard - " + studentId);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        
        try {
            Student student = FileHandler.getStudentById(studentId);
            if (student != null) {
                infoArea.setText("ID: " + student.getId() + "\n"
                    + "Name: " + student.getName() + "\n"
                    + "Course: " + student.getCourse() + "\n"
                    + "Semester: " + student.getSemester());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading student data!");
        }
        
        panel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        add(panel);
    }
}