package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class Proposal  implements Serializable {
    private final String identification, title, type;
    private Student student;
    private Teacher teacher;

    public Proposal(String identification, String title, String type, Student student, Teacher teacher) {
        this.identification = identification;
        this.title = title;
        this.type = type;
        this.student=student;
        this.teacher=teacher;
    }

    public String getIdentification() { return identification; }

    public String getTitle() { return title; }

    public String getType() { return type; }

    public List<String> getArea() { return null; }

    public String getHostEntity() { return ""; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
}
