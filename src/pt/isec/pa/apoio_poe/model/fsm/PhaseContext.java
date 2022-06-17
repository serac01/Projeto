package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.ManagementPoE;
import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.memento.IMemento;
import pt.isec.pa.apoio_poe.model.memento.IOriginator;
import pt.isec.pa.apoio_poe.model.memento.MementoPoE;

import java.io.*;
import java.util.List;

public class PhaseContext implements Serializable, IOriginator {
    public static final long serialVersionUID=2020129026;
    IPhaseState state;
    DataPoE data;

    public PhaseContext(){
        state = new FirstPhaseState(this);
        data = new DataPoE();
    }

    /************************************************** Serialization & Deserialization **************************************************/
    public void serialization(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(state);
            out.writeObject(data);
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
            data = (DataPoE) in.readObject();
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
    public boolean isPhaseClosed() { return state.isPhaseClosed(data); }
    public String closePhase(){ return state.closePhase(data);}
    public PhaseState getState(){ return state.getState(); }
    /************************************************** Students **************************************************/
    public String addStudents(String filename) throws IOException {  return state.addStudents(filename,data); }
    public String editStudent(long number, String toUpdate,int option) { return state.editStudent(number,toUpdate, option,data); }
    public String deleteStudents(long number){ return state.deleteStudents(number,data); }
    public List<String> showStudents(){ return state.showStudents(data); }
    public void exportStudents(String filename) throws IOException { state.exportStudents(filename,data); }

    /************************************************** Teachers **************************************************/
    public String addTeachers(String filename) throws IOException { return state.addTeachers(filename,data); }
    public String editTeacher(String email, String toUpdate) { return state.editTeacher(email,toUpdate,data); }
    public String deleteTeachers(String email){ return state.deleteTeacher(email,data); }
    public List<String> showTeachers(){ return state.showTeachers(data); }
    public void exportTeachers(String filename) throws IOException { state.exportTeacher(filename,data); }

    /************************************************** Proposals **************************************************/
    public String addProposals(String filename) throws IOException { return state.addProposals(filename,data); }
    public String deleteProposals(String id){ return state.deleteProposals(id,data); }
    public List<String> showProposals(){ return state.showProposals(data); }
    public void exportProposals(String filename) throws IOException { state.exportProposals(filename,data); }

    /************************************************** Applications **************************************************/
    public String addApplications(String filename) throws IOException { return state.addApplication(filename,data); }
    public String editApplication(long id, String toUpdate,int option) { return state.editApplication(id,toUpdate, option,data); }
    public String deleteApplication(Long number){ return state.deleteApplication(number,data); }
    public List<String> showApplications(){ return state.showApplication(data); }
    public void exportApplications(String filename) throws IOException { state.exportApplications(filename,data); }
    public List<String> generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered) { return state.generateStudentList(selfProposed,alreadyRegistered,withoutRegistered,data); }
    public List<String> generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications) { return state.generateProposalsList(selfProposed, proposeTeacher, withApplications, withoutApplications, data); }

    /************************************************** Attribution of proposals **************************************************/
    public String assignAProposalWithoutAssignments(){ return state.assignAProposalWithoutAssignments(data); }
    public String associateProposalToStudents(String proposal, Long student) { return state.associateProposalToStudents(proposal,student,data); }
    public String removeStudentFromProposal(String proposal){ return state.removeStudentFromProposal(proposal,data); }
    public List<String> generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed) { return state.generateListProposalStudents(associatedSelfProposed,alreadyRegistered,proposalAssigned,anyProposalAttributed,data); }
    public List<String> generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals){ return state.generateListProposalPhase3(selfProposed,proposeTeacher,withProposals,withoutProposals,data); }

    public String assignAdvisor(String proposal, String email){ return state.assignAdvisor(proposal,email,data); }
    public String consultAdvisor(String email) { return state.consultAdvisor(email,data); }
    public String changeAdvisor(String email, String proposal) { return state.changeAdvisor(email,proposal,data); }
    public String deleteAdvisor(String proposal){ return state.deleteAdvisor(proposal,data); }
    public List<String> generateListAdvisors(boolean op1, boolean op2, boolean op3){ return state.generateListAdvisors(op1,op2,op3,data); }

    public String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5){ return state.listPhase5(op1,op2,op3,op4,op5,data); }

    @Override
    public IMemento save() {
        return new MementoPoE(this);
    }

    @Override
    public void restore(IMemento memento) {
        Object obj = memento.getSnapshot();
        if (obj instanceof PhaseContext m) {
            this.state = m.state;
            this.data = m.data;
        }
    }
}






