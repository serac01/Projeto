package pt.isec.pa.apoio_poe.model.data;

public class ProposalIntership extends Proposal{
    private String area, hostEntity;

    public ProposalIntership(String identification, String title, String area, String hostEntity) {
        super(identification, title);
        this.area = area;
        this.hostEntity = hostEntity;
    }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getHostEntity() { return hostEntity; }
    public void setHostEntity(String hostEntity) { this.hostEntity = hostEntity; }

    /*@Override
    public String toString() {
        return "Proposal{" +
                "identification=" + identification +
                ", title='" + title + '\'' +
                ", area='" + area + '\'' +
                ", hostEntity'" + hostEntity + '\'' +
                '}';
    }*/
}
