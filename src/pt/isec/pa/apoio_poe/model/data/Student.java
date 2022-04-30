package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Student  implements Serializable {
    private final long studentNumber;
    private final String email;
    private String name, courseAcronym, industryAcronym;
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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public String getCourseAcronym() { return courseAcronym; }
    public void setCourseAcronym(String courseAcronym) { this.courseAcronym = courseAcronym; }

    public String getIndustryAcronym() { return industryAcronym; }
    public void setIndustryAcronym(String industryAcronym) { this.industryAcronym = industryAcronym; }

    public double getClassification() { return classification; }
    public void setClassification(double classification) { this.classification = classification; }

    public boolean isAccessInternships() { return accessInternships; }
    public void setAccessInternships(boolean accessInternships) { this.accessInternships = accessInternships; }
}
