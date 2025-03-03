package com.sms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class AdminDashboard extends JFrame {
    private DefaultTableModel studentModel;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Students Tab
        JPanel studentsPanel = new JPanel(new BorderLayout());
        studentModel = new DefaultTableModel(new Object[]{"ID", "Name", "Course", "Semester"}, 0);
        JTable studentsTable = new JTable(studentModel);
        
        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.addActionListener(e -> showAddStudentDialog());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addStudentBtn);
        
        studentsPanel.add(new JScrollPane(studentsTable), BorderLayout.CENTER);
        studentsPanel.add(buttonPanel, BorderLayout.SOUTH);
        loadStudentData();
        
        tabbedPane.addTab("Students", studentsPanel);
        add(tabbedPane);
    }

    private void showAddStudentDialog() {
        JDialog dialog = new JDialog(this, "Add Student", true);
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField semesterField = new JTextField();
        
        panel.add(new JLabel("Student ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Course:"));
        panel.add(courseField);
        panel.add(new JLabel("Semester:"));
        panel.add(semesterField);
        
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            try {
                Student student = new Student(
                    idField.getText(),
                    nameField.getText(),
                    courseField.getText(),
                    semesterField.getText()
                );
                FileHandler.addStudent(student);
                loadStudentData();
                dialog.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialog, "Error saving student!");
            }
        });
        
        panel.add(saveBtn);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void loadStudentData() {
        try {
            studentModel.setRowCount(0);
            List<Student> students = FileHandler.getAllStudents();
            for (Student student : students) {
                studentModel.addRow(new Object[]{
                    student.getId(),
                    student.getName(),
                    student.getCourse(),
                    student.getSemester()
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading students!");
        }
    }
}