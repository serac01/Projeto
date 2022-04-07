package pt.isec.pa.apoio_poe.model.data;

public class ProposalProject extends Proposal{
    private String area;

    public ProposalProject(String identification, String title, String area) {
        super(identification, title);
        this.area = area;
    }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
}