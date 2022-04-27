package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;
import java.util.ArrayList;

public class PhaseContext implements Serializable{
    IPhaseState state;
    ManagementPoE management;

    public void serialization() {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/context.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(state);
            out.writeObject(management);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void deserialization() {
        try {
            FileInputStream fileIn = new FileInputStream("src/context.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            state = (IPhaseState) in.readObject();
            management = (ManagementPoE) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }

    }

    public PhaseContext(){
        state = new FirstPhaseState(this, new Phase(1));
        management = new ManagementPoE();
    }

    //package private (by default) --> sendo assim é so usado pelos estados (FSM)
    void changeState(IPhaseState newState){ this.state = newState; }

    //State interface methods
    public PhaseState getState(){ return state.getState(); }

    public boolean nextPhase(){ return state.nextPhase(); }

    public boolean previousPhase(){ return state.previousPhase(); }

    public boolean closePhase(){ return state.closePhase(management.getProposals(), management.getStudent()); }

    //public int getCurrentPhase(){return phase.getCurrentPhase();}

    /************************************************** Students **************************************************/
    public void addStudents(String filename) throws IOException {
        ArrayList<Student> aux = state.addStudents(filename,management.getStudent());
        if(aux!=null)
            management.setStudent(aux);

        //To delete
        state.addTeachers("",management.getTeachers());
        state.addProposals("",management.getProposals(),management.getStudent(),management.getTeachers());
    }

    public String editStudent(long number, String toUpdate,int option) {
        ArrayList<Student> aux = state.editStudent(number,toUpdate, option,management.getStudent());
        if (aux!=null)
            management.setStudent(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public String deleteStudents(long number){
        ArrayList<Student> aux = state.deleteStudents(number,management.getStudent());
        if (aux!=null)
            management.setStudent(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public void showStudents(){ state.showStudents(management.getStudent()); }

    public void exportStudents(String filename) throws IOException { state.exportStudents(filename,management.getStudent()); }

    /************************************************** Teachers **************************************************/
    public void addTeachers(String filename) throws IOException {
        ArrayList<Teacher> aux = state.addTeachers(filename,management.getTeachers());
        if(aux!=null)
            management.setTeachers(aux);
    }

    public String editTeacher(String email, String toUpdate) {
        ArrayList<Teacher> aux = state.editTeacher(email,toUpdate,management.getTeachers());
        if (aux!=null)
            management.setTeachers(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public String deleteTeachers(String email){
        ArrayList<Teacher> aux = state.deleteTeacher(email,management.getTeachers());
        if (aux!=null)
            management.setTeachers(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public void showTeachers(){ state.showTeachers(management.getTeachers()); }

    public void exportTeachers(String filename) throws IOException { state.exportTeacher(filename,management.getTeachers()); }

    /************************************************** Proposals **************************************************/
    public void addProposals(String filename) throws IOException {
        ArrayList<Proposal> aux = state.addProposals(filename,management.getProposals(),management.getStudent(),management.getTeachers());
        if(aux!=null)
            management.setProposals(aux);
    }

    public String editProposals(String id, String toUpdate,int option) {
        ArrayList<Proposal> aux = state.editProposals(id,toUpdate, option,management.getProposals());
        if (aux!=null)
            management.setProposals(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public String deleteProposals(String id){
        ArrayList<Proposal> aux = state.deleteProposals(id,management.getProposals());
        if (aux!=null)
            management.setProposals(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public void showProposals(){ state.showProposals(management.getProposals()); }

    public void exportProposals(String filename) throws IOException { state.exportProposals(filename,management.getProposals()); }


    /************************************************** Applications **************************************************/
    public void addApplications(String filename) throws IOException {ArrayList<Application> aux = state.addApplication(filename,management.getApplications(), management.getProposals(), management.getStudent());
        if(aux!=null)
            management.setApplications(aux);
    }

    public void showApplications(){ state.showApplication(management.getApplications()); }

    public String editApplication(long id, String toUpdate,int option) {
        ArrayList<Application> aux = state.editApplication(id,toUpdate, option,management.getApplications(), management.getProposals());
        if (aux!=null)
            management.setApplications(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public String deleteApplication(Long number){
        ArrayList<Application> aux = state.deleteApplication(number,management.getApplications());
        if (aux!=null)
            management.setApplications(aux);
        else
            return "\nYou have entered wrong data, please confirm again\n";
        return "";
    }

    public void exportApplications(String filename) throws IOException { state.exportApplications(filename,management.getApplications()); }

    public void generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered) { state.generateStudentList(selfProposed,alreadyRegistered,withoutRegistered,management.getProposals(),management.getApplications(), management.getStudent()); }

    public void generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications) {
        state.generateProposalsList(selfProposed, proposeTeacher, withApplications, withoutApplications, management.getProposals(), management.getApplications());
    }

}






