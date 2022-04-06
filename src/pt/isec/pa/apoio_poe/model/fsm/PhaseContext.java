package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Phase;

public class PhaseContext {
    IPhaseState state;
    Phase phase;

    public PhaseContext(){
        phase = new Phase(1);
        state = new FirstPhaseState(this, phase);
    }

    //package private (by default) --> sendo assim é so usado pelos estados (FSM)
    void changeState(IPhaseState newState){ this.state = newState; }

    //State interface methods
    public PhaseState getState(){ return state.getState(); }

    public boolean nextPhase(){ return state.nextPhase(); }

    public boolean previousPhase(){ return state.previousPhase(); }

    //Get data
    public int getCurrentPhase(){return phase.getCurrentPhase();}
}
