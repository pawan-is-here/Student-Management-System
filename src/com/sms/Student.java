package com.sms;

public class Student {
    private String id;
    private String name;
    private String course;
    private String semester;

    public Student(String id, String name, String course, String semester) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.semester = semester;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCourse() { return course; }
    public String getSemester() { return semester; }

    public String toFileString() {
        return String.join(", ", id, name, course, semester);
    }
}