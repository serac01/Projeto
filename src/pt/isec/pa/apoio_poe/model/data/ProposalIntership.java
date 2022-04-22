package pt.isec.pa.apoio_poe.model.data;

import java.util.List;

public class ProposalIntership extends Proposal{
    private String hostEntity;
    private List<String> area;

    public ProposalIntership(String identification, String title, List<String> area, String hostEntity) {
        super(identification, title);
        this.area = area;
        this.hostEntity = hostEntity;
    }

    public List<String> getArea() { return area; }
    public void setArea(List<String> area) { this.area = area; }

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
