package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Phase;

public class SecondPhaseState extends PhaseStateAdapter {
    SecondPhaseState(PhaseContext context, Phase phase){
        super(context,phase);
        phase.setCurrentPhase(2);
    }

    @Override
    public boolean previousPhase() {
        changeState(new FirstPhaseState(context,phase));
        return true;
    }

    @Override
    public boolean nextPhase() {
        changeState(new ThirdPhaseState(context,phase));
        return true;
    }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_2; }
}
