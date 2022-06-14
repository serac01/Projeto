package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

abstract class PhaseStateAdapter implements IPhaseState, Serializable{
    protected PhaseContext context;
    public static final long serialVersionUID=2020129026;

    protected PhaseStateAdapter(PhaseContext context){ this.context = context; }

    //States
    @Override
    public void changeState(IPhaseState newState){
        context.changeState(newState);
    }
    @Override
    public void nextPhase() { }
    @Override
    public void previousPhase() { }
    @Override
    public void changeToStudent() { }
    @Override
    public void changeToTeacher() { }
    @Override
    public void changeToProposal() { }
    @Override
    public void setContext(PhaseContext context) { this.context=context;  }
    @Override
    public String closePhase(ManagementPoE management){ return null; }
    @Override
    public boolean isPhaseClosed(ManagementPoE management){ return false; }

    //Students
    @Override
    public String addStudents(String filename, ManagementPoE management) throws IOException { return null; }
    @Override
    public String editStudent(long number, String toUpdate, int option, ManagementPoE management) { return null; }
    @Override
    public String deleteStudents(long number, ManagementPoE management) { return null; }
    @Override
    public List<String> showStudents(ManagementPoE management) { return null; }
    @Override
    public void exportStudents(String filename, ManagementPoE management) throws IOException { }

    //Teachers
    @Override
    public String addTeachers(String filename, ManagementPoE management) throws IOException { return null; }
    @Override
    public String editTeacher(String email, String toUpdate, ManagementPoE management) { return null; }
    @Override
    public String deleteTeacher(String email, ManagementPoE management) { return null; }
    @Override
    public List<String> showTeachers(ManagementPoE management) { return null; }
    @Override
    public void exportTeacher(String filename, ManagementPoE management) throws IOException {  }

    //Proposals
    @Override
    public String addProposals(String filename, ManagementPoE management) throws IOException { return null; }
    @Override
    public String deleteProposals(String id, ManagementPoE management) { return null; }
    @Override
    public List<String> showProposals(ManagementPoE management) { return null; }
    @Override
    public void exportProposals(String filename, ManagementPoE management) throws IOException { }

    //Applications
    @Override
    public String addApplication(String filename, ManagementPoE management) throws IOException { return null; }
    @Override
    public String editApplication(long number, String id, int option, ManagementPoE management) { return null; }
    @Override
    public String deleteApplication(long number, ManagementPoE management){ return null; }
    @Override
    public List<String> showApplication(ManagementPoE management) { return null; }
    @Override
    public void exportApplications(String filename, ManagementPoE management) throws IOException {}
    @Override
    public List<String> generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, ManagementPoE management) { return null; }
    @Override
    public List<String> generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications, ManagementPoE management) { return null; }

    @Override
    public String assignAProposalWithoutAssignments(ManagementPoE management){ return null; }
    @Override
    public String associateProposalToStudents(String proposal, Long student, ManagementPoE management) { return null; }
    @Override
    public  String removeStudentFromProposal(String proposal, ManagementPoE management){ return null; }
    @Override
    public List<String> generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed, ManagementPoE management) { return null; }
    @Override
    public List<String> generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals, ManagementPoE management){ return null; }

    @Override
    public String assignAdvisor(String proposal, String email, ManagementPoE management){ return null; }
    @Override
    public String consultAdvisor(String email, ManagementPoE management){ return null; }
    @Override
    public String changeAdvisor(String email, String proposal, ManagementPoE management) { return null; }
    @Override
    public String deleteAdvisor(String proposal, ManagementPoE management){ return null; }
    @Override
    public List<String> generateListAdvisors(boolean op1, boolean op2, boolean op3, ManagementPoE management){ return null; }

    @Override
    public String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5, ManagementPoE managementPoE){ return null; }
}
