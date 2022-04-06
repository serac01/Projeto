package pt.isec.pa.apoio_poe.model.fsm;

public interface IPhaseState {
    boolean nextPhase();
    boolean previousPhase();

    PhaseState getState();
}
