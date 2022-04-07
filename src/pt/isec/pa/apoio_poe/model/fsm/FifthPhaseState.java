package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Phase;

public class FifthPhaseState extends PhaseStateAdapter {
    FifthPhaseState(PhaseContext context, Phase phase){
        super(context,phase);
        phase.setCurrentPhase(5);
    }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_5; }
}
