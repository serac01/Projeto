package pt.isec.pa.apoio_poe.model.data;

public class ProposalSelfProposed extends Proposal{
    private long studentNumber;

    public ProposalSelfProposed(String identification, String title, long studentNumber) {
        super(identification, title); 
        this.studentNumber= studentNumber;
    
    }
    
    
          /*  @Override
    public String toString() {
        return "Proposal{" +
                "identification=" + identification +
                ", title='" + title + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                '}';
    }*/
    
    
    
}
