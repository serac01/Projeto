package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Phase;

public class FourthPhaseState extends PhaseStateAdapter {
    FourthPhaseState(PhaseContext context, Phase phase){
        super(context,phase);
        phase.setCurrentPhase(4);
    }

    @Override
    public boolean previousPhase() {
        changeState(new ThirdPhaseState(context,phase));
        return true;
    }

    @Override
    public boolean nextPhase() {
        changeState(new FifthPhaseState(context,phase));
        return true;
    }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_4; }
}
