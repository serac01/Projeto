package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.fsm.*;
import pt.isec.pa.apoio_poe.model.memento.CareTaker;
import java.io.*;
import java.util.List;

public class ManagementPoE {
    private PhaseContext context;
    private final CareTaker careTaker;

    public ManagementPoE() {
        context = new PhaseContext();
        careTaker = new CareTaker(context);
    }

    /************************************************** Undo & Redo **************************************************/
    public boolean hasUndo() { return (careTaker.hasUndo()); }
    public boolean hasRedo() { return (careTaker.hasRedo()); }
    public void undo() { careTaker.undo(); }
    public void redo() { careTaker.redo(); }

    /************************************************** Serialization & Deserialization **************************************************/
    public void serialization(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(context);
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
            context = (PhaseContext) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }

    }

    /************************************************** States **************************************************/
    public void nextPhase(){ context.nextPhase(); }
    public void previousPhase(){ context.previousPhase(); }
    public boolean isPhaseClosed() { return context.isPhaseClosed(); }
    public String closePhase(){ return context.closePhase();}
    public PhaseState getState(){ return context.getState(); }
    public void changeToStudent() { context.changeToStudent(); }
    public void changeToTeacher() { context.changeToTeacher(); }
    public void changeToProposals(){ context.changeToProposals(); }
    public void changeState(IPhaseState newState){ careTaker.save(); context.changeState(newState); }

    /************************************************** Students **************************************************/
    public String addStudents(String filename) throws IOException {  careTaker.save(); return context.addStudents(filename); }
    public String editStudent(long number, String toUpdate,int option){ careTaker.save(); return context.editStudent(number,toUpdate, option); }
    public String deleteStudents(long number){ careTaker.save(); return context.deleteStudents(number); }
    public List<String> showStudents(){ return context.showStudents(); }
    public void exportStudents(String filename) throws IOException { context.exportStudents(filename); }

    /************************************************** Teachers **************************************************/
    public String addTeachers(String filename) throws IOException { careTaker.save(); return context.addTeachers(filename); }
    public String editTeacher(String email, String toUpdate) { careTaker.save(); return context.editTeacher(email,toUpdate); }
    public String deleteTeachers(String email){ careTaker.save(); return context.deleteTeachers(email); }
    public List<String> showTeachers(){ return context.showTeachers(); }
    public void exportTeachers(String filename) throws IOException { context.exportTeachers(filename); }

    /************************************************** Proposals **************************************************/
    public String addProposals(String filename) throws IOException { careTaker.save(); return context.addProposals(filename); }
    public String deleteProposals(String id){ careTaker.save(); return context.deleteProposals(id); }
    public List<String> showProposals(){ return context.showProposals(); }
    public void exportProposals(String filename) throws IOException { context.exportProposals(filename); }

    /************************************************** Applications **************************************************/
    public String addApplications(String filename) throws IOException { careTaker.save(); return context.addApplications(filename); }
    public String editApplication(long id, String toUpdate,int option) { careTaker.save(); return context.editApplication(id,toUpdate, option); }
    public String deleteApplication(Long number){ careTaker.save(); return context.deleteApplication(number); }
    public List<String> showApplications(){ return context.showApplications(); }
    public void exportApplications(String filename) throws IOException { context.exportApplications(filename); }
    public List<String> generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered) { careTaker.save(); return context.generateStudentList(selfProposed,alreadyRegistered,withoutRegistered); }
    public List<String> generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications) { careTaker.save(); return context.generateProposalsList(selfProposed, proposeTeacher, withApplications, withoutApplications); }

    /************************************************** Attribution of proposals **************************************************/
    public String assignAProposalWithoutAssignments(){ careTaker.save(); return context.assignAProposalWithoutAssignments(); }
    public String associateProposalToStudents(String proposal, Long student) { careTaker.save(); return context.associateProposalToStudents(proposal,student); }
    public String removeStudentFromProposal(String proposal){ careTaker.save(); return context.removeStudentFromProposal(proposal); }
    public List<String> generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed) { careTaker.save(); return context.generateListProposalStudents(associatedSelfProposed,alreadyRegistered,proposalAssigned,anyProposalAttributed); }
    public List<String> generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals){ careTaker.save(); return context.generateListProposalPhase3(selfProposed,proposeTeacher,withProposals,withoutProposals); }

    public String assignAdvisor(String proposal, String email){ careTaker.save(); return context.assignAdvisor(proposal,email); }
    public String consultAdvisor(String email) { return context.consultAdvisor(email); }
    public String changeAdvisor(String email, String proposal) { careTaker.save(); return context.changeAdvisor(email,proposal); }
    public String deleteAdvisor(String proposal){ careTaker.save(); return context.deleteAdvisor(proposal); }
    public List<String> generateListAdvisors(boolean op1, boolean op2, boolean op3){ careTaker.save(); return context.generateListAdvisors(op1,op2,op3); }

    public String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5){ careTaker.save(); return context.listPhase5(op1,op2,op3,op4,op5); }
}
