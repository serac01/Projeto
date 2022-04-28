package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class ProposalSelfProposed extends Proposal  implements Serializable {
    private Student student;

    public ProposalSelfProposed(String identification, String title, Student student) {
        super(identification, title, "T3");
        this.student = student;
    
    }

    @Override
    public Student getStudent() { return student; }

    /*@Override
    public String toString() {
        return "Proposal{" +
                "identification=" + identification +
                ", title='" + title + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                '}';
    }*/
    
    
    
}
