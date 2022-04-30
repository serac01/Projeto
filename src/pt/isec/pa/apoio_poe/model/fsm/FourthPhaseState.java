package pt.isec.pa.apoio_poe.model.fsm;

public class FourthPhaseState extends PhaseStateAdapter {
    public static final long serialVersionUID=2020129026;

    FourthPhaseState(PhaseContext context){ super(context); }

    @Override
    public boolean previousPhase() {
        changeState(new ThirdPhaseState(context));
        return true;
    }

    @Override
    public boolean nextPhase() {
        changeState(new FifthPhaseState(context));
        return true;
    }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_4; }
}
