package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.ManagementPoE;
import pt.isec.pa.apoio_poe.model.data.Phase;
import pt.isec.pa.apoio_poe.model.data.Student;

import java.io.IOException;
import java.util.ArrayList;

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

    //Get data
    public void addStudents(String filename) throws IOException {
        ArrayList<Student> aux;
        aux=FirstPhaseState.addStudents(filename,management.getStudent());
        if(aux!=null)
            management.setStudent(aux);
    }

    public String editStudent(long number, String toUpdate, int option) {
        ArrayList<Student> aux;
        aux=FirstPhaseState.editStudent(number,toUpdate,option,management.getStudent());
        if (aux!=null)
            management.setStudent(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public void showStudents(){ FirstPhaseState.showStudents(management.getStudent()); }

    public int getCurrentPhase(){return phase.getCurrentPhase();}
}
