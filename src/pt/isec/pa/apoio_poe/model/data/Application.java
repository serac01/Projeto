package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class Application implements Serializable {
    private Student student;
    private List<String> idProposals;


    public Application(Student student, List<String> idProposals) {
        this.student = student;
        this.idProposals = idProposals;
    }

    public Student getStudentNumber() {
        return student;
    }
    public void setStudent(Student student) { this.student = student; }

    public List<String> getIdProposals() { return idProposals; }
    public void setIdProposals(List<String> idProposals) { this.idProposals = idProposals; }
}
