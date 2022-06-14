package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class ProposalProject extends Proposal  implements Serializable {
    public static final long serialVersionUID=2020129026;
    private final List<String> area;


    public ProposalProject(String identification, String title, List<String> area, Teacher teacher, Student student) {
        super(identification, title, "T2",student,teacher);
        this.area = area;
    }

    @Override
    public List<String> getArea() { return area; }
}
