package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class ProposalSelfProposed extends Proposal  implements Serializable {
    private long studentNumber;

    public ProposalSelfProposed(String identification, String title, long studentNumber) {
        super(identification, title, "T3");
        this.studentNumber= studentNumber;
    
    }

    @Override
    public long getStudentNumber() { return studentNumber; }

    /*@Override
    public String toString() {
        return "Proposal{" +
                "identification=" + identification +
                ", title='" + title + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                '}';
    }*/
    
    
    
}
