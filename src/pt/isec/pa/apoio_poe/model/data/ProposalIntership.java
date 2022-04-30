package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class ProposalIntership extends Proposal implements Serializable {
    private final String hostEntity;
    private final List<String> area;

    public ProposalIntership(String identification, String title, List<String> area, String hostEntity) {
        super(identification, title, "T1");
        this.area = area;
        this.hostEntity = hostEntity;
    }

    @Override
    public List<String> getArea() { return area; }

    @Override
    public String getHostEntity() { return hostEntity; }
}
