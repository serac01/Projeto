package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Phase;

public class FirstPhaseState extends PhaseStateAdapter {
    FirstPhaseState(PhaseContext context, Phase phase){
        super(context,phase);
        phase.setCurrentPhase(1);
    }

    @Override
    public boolean nextPhase() {
        changeState(new SecondPhaseState(context,phase));
        return true;
    }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_1; }
}
