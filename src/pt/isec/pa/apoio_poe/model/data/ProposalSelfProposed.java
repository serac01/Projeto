package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class ProposalSelfProposed extends Proposal  implements Serializable {
    public static final long serialVersionUID=2020129026;

    public ProposalSelfProposed(String identification, String title, Student student, Teacher teacher) {
        super(identification, title, "T3",student,teacher);
    }
}
