package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ManagementPoE;
import pt.isec.pa.apoio_poe.model.data.Phase;
import pt.isec.pa.apoio_poe.model.data.ManagementPoE;

public class PhaseContext {
    IPhaseState state;
    Phase phase;
    ManagementPoE management;

    public PhaseContext(){
        phase = new Phase(1);
        state = new FirstPhaseState(this, phase);
        management = new ManagementPoE();
    }

    //package private (by default) --> sendo assim é so usado pelos estados (FSM)
    void changeState(IPhaseState newState){ this.state = newState; }

    //State interface methods
    public PhaseState getState(){ return state.getState(); }

    public boolean nextPhase(){ return state.nextPhase(); }

    public boolean previousPhase(){ return state.previousPhase(); }

    public void newStudents(){ management.newStudents(); }

    //Get data
    public int getCurrentPhase(){return phase.getCurrentPhase();}
}
