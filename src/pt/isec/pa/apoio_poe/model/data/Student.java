package pt.isec.pa.apoio_poe.model.data;

public class Student {
    private long studentNumber, classification;
    private String name, email, courseAcronym, industryAcronym;
    private boolean accessInternships;

    public Student(long studentNumber, long classification, String name, String email, String courseAcronym, String industryAcronym, boolean accessInternships) {
        this.studentNumber = studentNumber;
        this.classification = classification;
        this.name = name;
        this.email = email;
        this.courseAcronym = courseAcronym;
        this.industryAcronym = industryAcronym;
        this.accessInternships = accessInternships;
    }

    public long getStudentNumber() { return studentNumber; }
    public void setStudentNumber(long studentNumber) { this.studentNumber = studentNumber; }

    public long getClassification() { return classification; }
    public void setClassification(long classification) { this.classification = classification; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCourseAcronym() { return courseAcronym; }
    public void setCourseAcronym(String courseAcronym) { this.courseAcronym = courseAcronym; }

    public String getIndustryAcronym() { return industryAcronym; }
    public void setIndustryAcronym(String industryAcronym) { this.industryAcronym = industryAcronym; }

    public boolean isAccessInternships() { return accessInternships; }
    public void setAccessInternships(boolean accessInternships) { this.accessInternships = accessInternships; }
}
