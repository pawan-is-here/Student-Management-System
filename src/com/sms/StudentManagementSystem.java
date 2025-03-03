package com.sms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class StudentManagementSystem {
    public static void main(String[] args) {
        FileHandler.initializeFiles();
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginFrame().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error initializing application!");
            }
        });
    }

    static class LoginFrame extends JFrame implements ActionListener {
        private JTextField usernameField = new JTextField(15);
        private JPasswordField passwordField = new JPasswordField(15);

        public LoginFrame() {
            setTitle("Student Management System - Login");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.gridy = 0;
            panel.add(new JLabel("Username:"), gbc);
            gbc.gridx = 1;
            panel.add(usernameField, gbc);

            gbc.gridy = 1; gbc.gridx = 0;
            panel.add(new JLabel("Password:"), gbc);
            gbc.gridx = 1;
            panel.add(passwordField, gbc);

            gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2;
            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(this);
            panel.add(loginButton, gbc);

            add(panel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                User user = FileHandler.authenticateUser(username, password);
                if (user != null) {
                    dispose();
                    switch (user.getRole()) {
                        case "Admin":
                            new AdminDashboard().setVisible(true);
                            break;
                        case "Teacher":
                            new TeacherDashboard().setVisible(true);
                            break;
                        case "Student":
                            new StudentDashboard(user.getUsername()).setVisible(true);
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error accessing user data!");
            }
        }
    }
}