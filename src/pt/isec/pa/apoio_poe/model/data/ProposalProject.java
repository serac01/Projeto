package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class ProposalProject extends Proposal  implements Serializable {
    private final List<String> area;
    private final Student student;
    private final Teacher teacher;


    public ProposalProject(String identification, String title, List<String> area, Teacher teacher, Student student) {
        super(identification, title, "T2");
        this.area = area;
        this.teacher=teacher;
        this.student=student;
    }

    @Override
    public List<String> getArea() { return area; }

    @Override
    public Student getStudent() { return student; }

    @Override
    public Teacher getTeacher() { return teacher; }
}
