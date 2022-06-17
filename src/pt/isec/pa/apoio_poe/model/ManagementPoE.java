package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.fsm.IPhaseState;
import pt.isec.pa.apoio_poe.model.fsm.PhaseContext;
import pt.isec.pa.apoio_poe.model.fsm.PhaseState;
import pt.isec.pa.apoio_poe.model.memento.CareTaker;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.List;

public class ManagementPoE {
    private final PhaseContext context;
    private final CareTaker careTaker;
    PropertyChangeSupport pcs;

    public ManagementPoE() {
        context = new PhaseContext();
        careTaker = new CareTaker(context);
        pcs = new PropertyChangeSupport(this);
    }


    public boolean hasUndo() {
        return (careTaker.hasUndo());
    }
    public boolean hasRedo() {
        return (careTaker.hasRedo());
    }
    public void undo() {
        careTaker.undo();
    }
    public void redo() { careTaker.redo(); }

    public void serialization(String filename) {
        context.serialization(filename);
    }
    public void deserialization(String filename) {
        context.deserialization(filename);
    }
    public void nextPhase(){ careTaker.save(); context.nextPhase(); }
    public void previousPhase(){ careTaker.save(); context.previousPhase(); }
    public boolean isPhaseClosed() { return context.isPhaseClosed(); }
    public String closePhase(){ careTaker.save(); return context.closePhase();}
    public PhaseState getState(){ return context.getState(); }
    public void changeToStudent() { careTaker.save(); context.changeToStudent(); }
    public void changeToTeacher() { careTaker.save(); context.changeToTeacher(); }
    public void changeToProposals() { careTaker.save(); context.changeToProposals(); }
    public void changeState(IPhaseState newState){ context.changeState(newState); }

    /************************************************** Students **************************************************/
    public String addStudents(String filename) throws IOException {  careTaker.save(); return context.addStudents(filename); }
    public String editStudent(long number, String toUpdate,int option) { careTaker.save(); return context.editStudent(number,toUpdate, option); }
    public String deleteStudents(long number){ careTaker.save(); return context.deleteStudents(number); }
    public List<String> showStudents(){ return context.showStudents(); }
    public void exportStudents(String filename) throws IOException { context.exportStudents(filename); }

    /************************************************** Teachers **************************************************/
    public String addTeachers(String filename) throws IOException { return context.addTeachers(filename); }
    public String editTeacher(String email, String toUpdate) { return context.editTeacher(email,toUpdate); }
    public String deleteTeachers(String email){ return context.deleteTeachers(email); }
    public List<String> showTeachers(){ return context.showTeachers(); }
    public void exportTeachers(String filename) throws IOException { context.exportTeachers(filename); }

    /************************************************** Proposals **************************************************/
    public String addProposals(String filename) throws IOException { return context.addProposals(filename); }
    public String deleteProposals(String id){ return context.deleteProposals(id); }
    public List<String> showProposals(){ return context.showProposals(); }
    public void exportProposals(String filename) throws IOException { context.exportProposals(filename); }

    /************************************************** Applications **************************************************/
    public String addApplications(String filename) throws IOException { return context.addApplications(filename); }
    public String editApplication(long id, String toUpdate,int option) { return context.editApplication(id,toUpdate, option); }
    public String deleteApplication(Long number){ return context.deleteApplication(number); }
    public List<String> showApplications(){ return context.showApplications(); }
    public void exportApplications(String filename) throws IOException { context.exportApplications(filename); }
    public List<String> generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered) { return context.generateStudentList(selfProposed,alreadyRegistered,withoutRegistered); }
    public List<String> generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications) { return context.generateProposalsList(selfProposed, proposeTeacher, withApplications, withoutApplications); }

    /************************************************** Attribution of proposals **************************************************/
    public String assignAProposalWithoutAssignments(){ return context.assignAProposalWithoutAssignments(); }
    public String associateProposalToStudents(String proposal, Long student) { return context.associateProposalToStudents(proposal,student); }
    public String removeStudentFromProposal(String proposal){ return context.removeStudentFromProposal(proposal); }
    public List<String> generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed) { return context.generateListProposalStudents(associatedSelfProposed,alreadyRegistered,proposalAssigned,anyProposalAttributed); }
    public List<String> generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals){ return context.generateListProposalPhase3(selfProposed,proposeTeacher,withProposals,withoutProposals); }

    public String assignAdvisor(String proposal, String email){ return context.assignAdvisor(proposal,email); }
    public String consultAdvisor(String email) { return context.consultAdvisor(email); }
    public String changeAdvisor(String email, String proposal) { return context.changeAdvisor(email,proposal); }
    public String deleteAdvisor(String proposal){ return context.deleteAdvisor(proposal); }
    public List<String> generateListAdvisors(boolean op1, boolean op2, boolean op3){ return context.generateListAdvisors(op1,op2,op3); }

    public String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5){ return context.listPhase5(op1,op2,op3,op4,op5); }



















    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

}
