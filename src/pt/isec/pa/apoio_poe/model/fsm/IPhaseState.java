package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import java.util.ArrayList;

public interface IPhaseState {
    boolean nextPhase();
    boolean previousPhase();
    boolean closePhase(ArrayList<Proposal> proposals, ArrayList<Student> student);

    PhaseState getState();

}
