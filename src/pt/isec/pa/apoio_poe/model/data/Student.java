package pt.isec.pa.apoio_poe.model.data;

import java.io.File;
import java.io.Serializable;
import java.util.Scanner;

public class Student  implements Serializable {
    private long studentNumber;
    private String name, email, courseAcronym, industryAcronym;
    private double classification;
    private boolean accessInternships;

    public Student(long studentNumber, String name, String email, String courseAcronym, String industryAcronym, double classification, boolean accessInternships) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.email = email;
        this.courseAcronym = courseAcronym;
        this.industryAcronym = industryAcronym;
        this.classification = classification;
        this.accessInternships = accessInternships;
    }

    public long getStudentNumber() { return studentNumber; }
    public void setStudentNumber(long studentNumber) { this.studentNumber = studentNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCourseAcronym() { return courseAcronym; }
    public void setCourseAcronym(String courseAcronym) { this.courseAcronym = courseAcronym; }

    public String getIndustryAcronym() { return industryAcronym; }
    public void setIndustryAcronym(String industryAcronym) { this.industryAcronym = industryAcronym; }

    public double getClassification() { return classification; }
    public void setClassification(double classification) { this.classification = classification; }

    public boolean isAccessInternships() { return accessInternships; }
    public void setAccessInternships(boolean accessInternships) { this.accessInternships = accessInternships; }

    @Override
    public String toString() {
        return "Student{" +
                "studentNumber=" + studentNumber +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", courseAcronym='" + courseAcronym + '\'' +
                ", industryAcronym='" + industryAcronym + '\'' +
                ", classification=" + classification +
                ", accessInternships=" + accessInternships +
                '}';
    }
}
