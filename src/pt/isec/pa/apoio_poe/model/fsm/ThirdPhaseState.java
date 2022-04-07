package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Phase;

public class ThirdPhaseState extends PhaseStateAdapter {
    ThirdPhaseState(PhaseContext context, Phase phase){
        super(context,phase);
        phase.setCurrentPhase(3);
    }

    @Override
    public boolean previousPhase() {
        changeState(new SecondPhaseState(context,phase));
        return true;
    }

    @Override
    public boolean nextPhase() {
        changeState(new FourthPhaseState(context,phase));
        return true;
    }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_3; }
}
