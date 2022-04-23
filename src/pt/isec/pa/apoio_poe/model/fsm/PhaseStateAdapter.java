package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Phase;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;

import java.util.ArrayList;

abstract class PhaseStateAdapter implements IPhaseState {
    protected Phase phase;
    protected PhaseContext context;

    protected PhaseStateAdapter(PhaseContext context, Phase phase){
        this.context = context;
        this.phase = phase;
    }

    protected void changeState(IPhaseState newState){
        context.changeState(newState);
    }

    @Override
    public boolean nextPhase() { return false; }

    @Override
    public boolean previousPhase() { return false; }

    @Override
    public boolean closePhase(ArrayList<Proposal> proposals, ArrayList<Student> students){ return false; }
}
