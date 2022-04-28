package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class ProposalProject extends Proposal  implements Serializable {
    private List<String> area;
    private Student student;
    private Teacher teacher;


    public ProposalProject(String identification, String title, List<String> area, Teacher teacher, Student student) {
        super(identification, title, "T2");
        this.area = area;
        this.teacher=teacher;
        this.student=student;
    }

    @Override
    public List<String> getArea() { return area; }
    public void setArea(List<String> area) { this.area = area; }

    @Override
    public Student getStudent() { return student; }

    @Override
    public Teacher getTeacher() { return teacher; }

    /*@Override
    public String toString() {
        return "Proposal{" +
                "identification=" + identification +
                ", title='" + title + '\'' +
                ", area='" + area + '\'' +
                '}';
    }*/
}
