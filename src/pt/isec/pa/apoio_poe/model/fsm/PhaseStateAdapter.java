package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Phase;

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
}
