package pt.isec.pa.apoio_poe.model.data;

public class ProposalSelfProposed extends Proposal{
    int studentNumber;
    public ProposalSelfProposed(String identification, String title, int studentNumber,) { 
        super(identification, title); 
        this.studentNumber= studentNumber;
    
    }
    
    
            @Override
    public String toString() {
        return "Proposal{" +
                "identification=" + identification +
                ", title='" + title + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                '}';
    }
    
    
    
}
