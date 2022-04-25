package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

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

    public boolean closePhase(){ return state.closePhase(management.getProposals(), management.getStudent()); }

    public int getCurrentPhase(){return phase.getCurrentPhase();}

    /************************************************** Students **************************************************/
    public void addStudents(String filename) throws IOException {
        ArrayList<Student> aux;
        aux=FirstPhaseState.addStudents(filename,management.getStudent());
        if(aux!=null)
            management.setStudent(aux);
    }

    public String editStudent(long number, String toUpdate,int option) {
        ArrayList<Student> aux;
        aux=FirstPhaseState.editStudent(number,toUpdate, option,management.getStudent());
        if (aux!=null)
            management.setStudent(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public String deleteStudents(long number){
        ArrayList<Student> aux;
        aux=FirstPhaseState.deleteStudents(number,management.getStudent());
        if (aux!=null)
            management.setStudent(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public void showStudents(){ FirstPhaseState.showStudents(management.getStudent()); }

    public void exportStudents(String filename) throws IOException { FirstPhaseState.exportStudents(filename,management.getStudent()); }

    /************************************************** Teachers **************************************************/
    public void addTeachers(String filename) throws IOException {
        ArrayList<Teacher> aux;
        aux=FirstPhaseState.addTeacher(filename,management.getTeachers());
        if(aux!=null)
            management.setTeachers(aux);
    }

    public String editTeacher(String email, String toUpdate) {
        ArrayList<Teacher> aux;
        aux=FirstPhaseState.editTeacher(email,toUpdate,management.getTeachers());
        if (aux!=null)
            management.setTeachers(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public String deleteTeachers(String email){
        ArrayList<Teacher> aux;
        aux=FirstPhaseState.deleteTeacher(email,management.getTeachers());
        if (aux!=null)
            management.setTeachers(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public void showTeachers(){ FirstPhaseState.showTeachers(management.getTeachers()); }

    public void exportTeachers(String filename) throws IOException { FirstPhaseState.exportTeacher(filename,management.getTeachers()); }

    /************************************************** Proposals **************************************************/
    public void addProposals(String filename) throws IOException {
        ArrayList<Proposal> aux;
        aux=FirstPhaseState.addProposals(filename,management.getProposals(),management.getStudent(),management.getTeachers());
        if(aux!=null)
            management.setProposals(aux);
    }

    public String editProposals(String id, String toUpdate,int option) {
        ArrayList<Proposal> aux;
        aux=FirstPhaseState.editProposals(id,toUpdate, option,management.getProposals());
        if (aux!=null)
            management.setProposals(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public String deleteProposals(String id){
        ArrayList<Proposal> aux;
        aux=FirstPhaseState.deleteProposals(id,management.getProposals());
        if (aux!=null)
            management.setProposals(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public void showProposals(){ FirstPhaseState.showProposals(management.getProposals()); }

    public void exportProposals(String filename) throws IOException { FirstPhaseState.exportProposals(filename,management.getProposals()); }


    /************************************************** Applications **************************************************/
    public void addApplications(String filename) throws IOException {
        addStudents("");
        addTeachers("");
        addProposals("");
        ArrayList<Application> aux;
        aux=SecondPhaseState.addApplication(filename,management.getApplications(), management.getProposals(), management.getStudent());
        if(aux!=null)
            management.setApplications(aux);
    }

    public void showApplications(){ SecondPhaseState.showApplication(management.getApplications()); }

    public String editApplication(long id, String toUpdate,int option) {
        ArrayList<Application> aux;
        aux=SecondPhaseState.editApplication(id,toUpdate, option,management.getApplications(), management.getProposals());
        if (aux!=null)
            management.setApplications(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public String deleteApplication(Long number){
        ArrayList<Application> aux;
        aux=SecondPhaseState.deleteApplication(number,management.getApplications());
        if (aux!=null)
            management.setApplications(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public void exportApplications(String filename) throws IOException { SecondPhaseState.exportApplications(filename,management.getApplications()); }

}






