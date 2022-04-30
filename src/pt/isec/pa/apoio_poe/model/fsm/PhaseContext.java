package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;
import java.util.ArrayList;

public class PhaseContext implements Serializable{
    public static final long serialVersionUID=2020129026;
    IPhaseState state;
    ManagementPoE management;

    public PhaseContext(){
        state = new FirstPhaseState(this);
        management = new ManagementPoE();
    }

    /************************************************** Serialization & Deserialization **************************************************/
    public void serialization(String filename) {
        filename = "src/context.bin";
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(state);
            out.writeObject(management);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    public void deserialization(String filename) {
        filename = "src/context.bin";
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            state = (IPhaseState) in.readObject();
            state.setContext(this);
            changeState(state);
            management = (ManagementPoE) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }

    }

    /************************************************** States **************************************************/
    public void changeState(IPhaseState newState){ this.state = newState; }
    public PhaseState getState(){ return state.getState(); }
    public void nextPhase(){ state.nextPhase(); }
    public void previousPhase(){ state.previousPhase(); }
    public boolean isClosed(){ return state.isClosed(); }
    public String closePhase(){ return state.closePhase(management.getProposals(), management.getStudent()); }

    /************************************************** Students **************************************************/
    public String addStudents(String filename) throws IOException {
        String s = state.addStudents(filename,management.getStudent());
        state.addTeachers("",management.getTeachers());
        state.addProposals("",management.getProposals(),management.getStudent(),management.getTeachers());
        return s;
    }
    public String editStudent(long number, String toUpdate,int option) { return state.editStudent(number,toUpdate, option,management.getStudent()); }
    public String deleteStudents(long number){ return state.deleteStudents(number,management.getStudent()); }
    public void showStudents(){ state.showStudents(management.getStudent()); }
    public void exportStudents(String filename) throws IOException { state.exportStudents(filename,management.getStudent()); }

    /************************************************** Teachers **************************************************/
    public String addTeachers(String filename) throws IOException { return state.addTeachers(filename,management.getTeachers()); }
    public String editTeacher(String email, String toUpdate) { return state.editTeacher(email,toUpdate,management.getTeachers()); }
    public String deleteTeachers(String email){ return state.deleteTeacher(email,management.getTeachers()); }
    public void showTeachers(){ state.showTeachers(management.getTeachers()); }
    public void exportTeachers(String filename) throws IOException { state.exportTeacher(filename,management.getTeachers()); }

    /************************************************** Proposals **************************************************/
    public String addProposals(String filename) throws IOException { return state.addProposals(filename,management.getProposals(),management.getStudent(),management.getTeachers()); }
    public String deleteProposals(String id){ return state.deleteProposals(id,management.getProposals()); }
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
    public void generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications) { state.generateProposalsList(selfProposed, proposeTeacher, withApplications, withoutApplications, management.getProposals(), management.getApplications()); }

}






