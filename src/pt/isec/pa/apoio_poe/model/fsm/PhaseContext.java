package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;

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
    public void nextPhase(){
        state.nextPhase();
        if(state.getState()==PhaseState.PHASE_2 && management.isPhase2Closed())
            state.closePhase(management.getProposals(), management.getStudent(), management.isPhase1Closed());
        else if(state.getState()==PhaseState.PHASE_3 && management.isPhase3Closed())
            state.closePhase(management.getProposals(), management.getStudent(), management.isPhase1Closed());
        else if(state.getState()==PhaseState.PHASE_4 && management.isPhase4Closed())
            state.closePhase(management.getProposals(), management.getStudent(), management.isPhase1Closed());
    }
    public void previousPhase(){
        state.previousPhase();
        if(state.getState()==PhaseState.PHASE_1 && management.isPhase1Closed())
            state.closePhase(management.getProposals(), management.getStudent(), management.isPhase1Closed());
        else if(state.getState()==PhaseState.PHASE_2 && management.isPhase2Closed())
            state.closePhase(management.getProposals(), management.getStudent(), management.isPhase1Closed());
        else if(state.getState()==PhaseState.PHASE_3 && management.isPhase3Closed())
            state.closePhase(management.getProposals(), management.getStudent(), management.isPhase1Closed());
    }
    public boolean isClosed(){
        if(getState() == PhaseState.PHASE_1)
            management.setPhase1Closed(state.isClosed());
        if(getState() == PhaseState.PHASE_2)
            management.setPhase2Closed(state.isClosed());
        if(getState() == PhaseState.PHASE_3)
            management.setPhase3Closed(state.isClosed());
        if(getState() == PhaseState.PHASE_4)
            management.setPhase4Closed(state.isClosed());
        return state.isClosed();
    }
    public String closePhase(){ return state.closePhase(management.getProposals(), management.getStudent(), management.isPhase1Closed());}

    /************************************************** Students **************************************************/
    public String addStudents(String filename) throws IOException {
        String s = state.addStudents(filename,management.getStudent());
        state.addTeachers("",management.getTeachers());
        state.addProposals("",management.getProposals(),management.getStudent(),management.getTeachers());
        return s;
    }
    public String editStudent(long number, String toUpdate,int option) { return state.editStudent(number,toUpdate, option,management.getStudent()); }
    public String deleteStudents(long number){ return state.deleteStudents(number,management.getStudent()); }
    public String showStudents(){ return state.showStudents(management.getStudent()); }
    public void exportStudents(String filename) throws IOException { state.exportStudents(filename,management.getStudent()); }

    /************************************************** Teachers **************************************************/
    public String addTeachers(String filename) throws IOException { return state.addTeachers(filename,management.getTeachers()); }
    public String editTeacher(String email, String toUpdate) { return state.editTeacher(email,toUpdate,management.getTeachers()); }
    public String deleteTeachers(String email){ return state.deleteTeacher(email,management.getTeachers()); }
    public String showTeachers(){ return state.showTeachers(management.getTeachers()); }
    public void exportTeachers(String filename) throws IOException { state.exportTeacher(filename,management.getTeachers()); }

    /************************************************** Proposals **************************************************/
    public String addProposals(String filename) throws IOException { return state.addProposals(filename,management.getProposals(),management.getStudent(),management.getTeachers()); }
    public String deleteProposals(String id){ return state.deleteProposals(id,management.getProposals()); }
    public String showProposals(){ return state.showProposals(management.getProposals()); }
    public void exportProposals(String filename) throws IOException { state.exportProposals(filename,management.getProposals()); }

    /************************************************** Applications **************************************************/
    public String addApplications(String filename) throws IOException { return state.addApplication(filename,management.getApplications(), management.getProposals(), management.getStudent()); }
    public String editApplication(long id, String toUpdate,int option) { return state.editApplication(id,toUpdate, option,management.getApplications(), management.getProposals()); }
    public String deleteApplication(Long number){ return state.deleteApplication(number,management.getApplications()); }
    public String showApplications(){ return state.showApplication(management.getApplications()); }
    public void exportApplications(String filename) throws IOException { state.exportApplications(filename,management.getApplications()); }
    public String generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered) { return state.generateStudentList(selfProposed,alreadyRegistered,withoutRegistered,management.getProposals(),management.getApplications(), management.getStudent()); }
    public String generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications) { return state.generateProposalsList(selfProposed, proposeTeacher, withApplications, withoutApplications, management.getProposals(), management.getApplications()); }

}






