package pt.isec.pa.apoio_poe.model.fsm;

public class FifthPhaseState extends PhaseStateAdapter {
    public static final long serialVersionUID=2020129026;

    FifthPhaseState(PhaseContext context) { super(context); }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_5; }
}
