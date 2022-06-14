package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class Application implements Serializable {
    public static final long serialVersionUID=2020129026;
    private Student student;
    private List<String> idProposals;


    public Application(Student student, List<String> idProposals) {
        this.student = student;
        this.idProposals = idProposals;
    }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public List<String> getIdProposals() { return idProposals; }
    public void setIdProposals(List<String> idProposals) { this.idProposals = idProposals; }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("Applicant number: %d Proposal ID's: ",student.getStudentNumber()));
        for(String st: idProposals)
            s.append(st).append("; ");
        return s.toString();
    }
}
