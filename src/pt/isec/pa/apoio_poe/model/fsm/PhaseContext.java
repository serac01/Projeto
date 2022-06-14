package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import java.io.*;
import java.util.List;

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
    public void nextPhase(){ state.nextPhase(); }
    public void previousPhase(){ state.previousPhase(); }
    public void changeToStudent() { state.changeToStudent(); }
    public void changeToTeacher() { state.changeToTeacher(); }
    public void changeToProposals() { state.changeToProposal(); }
    public boolean isPhaseClosed() { return state.isPhaseClosed(management); }
    public String closePhase(){ return state.closePhase(management);}
    public PhaseState getState(){ return state.getState(); }
    /************************************************** Students **************************************************/
    public String addStudents(String filename) throws IOException {  return state.addStudents(filename,management); }
    public String editStudent(long number, String toUpdate,int option) { return state.editStudent(number,toUpdate, option,management); }
    public String deleteStudents(long number){ return state.deleteStudents(number,management); }
    public List<String> showStudents(){ return state.showStudents(management); }
    public void exportStudents(String filename) throws IOException { state.exportStudents(filename,management); }

    /************************************************** Teachers **************************************************/
    public String addTeachers(String filename) throws IOException { return state.addTeachers(filename,management); }
    public String editTeacher(String email, String toUpdate) { return state.editTeacher(email,toUpdate,management); }
    public String deleteTeachers(String email){ return state.deleteTeacher(email,management); }
    public List<String> showTeachers(){ return state.showTeachers(management); }
    public void exportTeachers(String filename) throws IOException { state.exportTeacher(filename,management); }

    /************************************************** Proposals **************************************************/
    public String addProposals(String filename) throws IOException { return state.addProposals(filename,management); }
    public String deleteProposals(String id){ return state.deleteProposals(id,management); }
    public List<String> showProposals(){ return state.showProposals(management); }
    public void exportProposals(String filename) throws IOException { state.exportProposals(filename,management); }

    /************************************************** Applications **************************************************/
    public String addApplications(String filename) throws IOException { return state.addApplication(filename,management); }
    public String editApplication(long id, String toUpdate,int option) { return state.editApplication(id,toUpdate, option,management); }
    public String deleteApplication(Long number){ return state.deleteApplication(number,management); }
    public List<String> showApplications(){ return state.showApplication(management); }
    public void exportApplications(String filename) throws IOException { state.exportApplications(filename,management); }
    public List<String> generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered) { return state.generateStudentList(selfProposed,alreadyRegistered,withoutRegistered,management); }
    public List<String> generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications) { return state.generateProposalsList(selfProposed, proposeTeacher, withApplications, withoutApplications, management); }

    /************************************************** Attribution of proposals **************************************************/
    public String assignAProposalWithoutAssignments(){ return state.assignAProposalWithoutAssignments(management); }
    public String associateProposalToStudents(String proposal, Long student) { return state.associateProposalToStudents(proposal,student,management); }
    public String removeStudentFromProposal(String proposal){ return state.removeStudentFromProposal(proposal,management); }
    public List<String> generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed) { return state.generateListProposalStudents(associatedSelfProposed,alreadyRegistered,proposalAssigned,anyProposalAttributed,management); }
    public List<String> generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals){ return state.generateListProposalPhase3(selfProposed,proposeTeacher,withProposals,withoutProposals,management); }

    public String assignAdvisor(String proposal, String email){ return state.assignAdvisor(proposal,email,management); }
    public String consultAdvisor(String email) { return state.consultAdvisor(email,management); }
    public String changeAdvisor(String email, String proposal) { return state.changeAdvisor(email,proposal,management); }
    public String deleteAdvisor(String proposal){ return state.deleteAdvisor(proposal,management); }
    public List<String> generateListAdvisors(boolean op1, boolean op2, boolean op3){ return state.generateListAdvisors(op1,op2,op3,management); }

    public String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5){ return state.listPhase5(op1,op2,op3,op4,op5,management); }


}






