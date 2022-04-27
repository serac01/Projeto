package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class ProposalIntership extends Proposal implements Serializable {
    private String hostEntity;
    private List<String> area;

    public ProposalIntership(String identification, String title, List<String> area, String hostEntity) {
        super(identification, title, "T1");
        this.area = area;
        this.hostEntity = hostEntity;
    }

    @Override
    public List<String> getArea() { return area; }
    public void setArea(List<String> area) { this.area = area; }

    @Override
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
