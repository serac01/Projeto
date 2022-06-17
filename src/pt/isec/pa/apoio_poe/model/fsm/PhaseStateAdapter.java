package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import pt.isec.pa.apoio_poe.model.memento.IMemento;
import pt.isec.pa.apoio_poe.model.memento.IOriginator;
import pt.isec.pa.apoio_poe.model.memento.MementoPoE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

abstract class PhaseStateAdapter implements IPhaseState, Serializable {
    protected PhaseContext context;
    public static final long serialVersionUID=2020129026;

    protected PhaseStateAdapter(PhaseContext context){ this.context = context; }

    //States
    @Override
    public void changeState(IPhaseState newState){ context.changeState(newState); }
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
    public String closePhase(DataPoE data){ return null; }
    @Override
    public boolean isPhaseClosed(DataPoE data){ return false; }

    //Students
    @Override
    public String addStudents(String filename, DataPoE data) throws IOException { return null; }
    @Override
    public String editStudent(long number, String toUpdate, int option, DataPoE data) { return null; }
    @Override
    public String deleteStudents(long number, DataPoE data) { return null; }
    @Override
    public List<String> showStudents(DataPoE data) { return null; }
    @Override
    public void exportStudents(String filename, DataPoE data) throws IOException { }

    //Teachers
    @Override
    public String addTeachers(String filename, DataPoE data) throws IOException { return null; }
    @Override
    public String editTeacher(String email, String toUpdate, DataPoE data) { return null; }
    @Override
    public String deleteTeacher(String email, DataPoE data) { return null; }
    @Override
    public List<String> showTeachers(DataPoE data) { return null; }
    @Override
    public void exportTeacher(String filename, DataPoE data) throws IOException {  }

    //Proposals
    @Override
    public String addProposals(String filename, DataPoE data) throws IOException { return null; }
    @Override
    public String deleteProposals(String id, DataPoE data) { return null; }
    @Override
    public List<String> showProposals(DataPoE data) { return null; }
    @Override
    public void exportProposals(String filename, DataPoE data) throws IOException { }

    //Applications
    @Override
    public String addApplication(String filename, DataPoE data) throws IOException { return null; }
    @Override
    public String editApplication(long number, String id, int option, DataPoE data) { return null; }
    @Override
    public String deleteApplication(long number, DataPoE data){ return null; }
    @Override
    public List<String> showApplication(DataPoE data) { return null; }
    @Override
    public void exportApplications(String filename, DataPoE data) throws IOException {}
    @Override
    public List<String> generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, DataPoE data) { return null; }
    @Override
    public List<String> generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications, DataPoE data) { return null; }

    @Override
    public String assignAProposalWithoutAssignments(DataPoE data){ return null; }
    @Override
    public String associateProposalToStudents(String proposal, Long student, DataPoE data) { return null; }
    @Override
    public  String removeStudentFromProposal(String proposal, DataPoE data){ return null; }
    @Override
    public List<String> generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed, DataPoE data) { return null; }
    @Override
    public List<String> generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals, DataPoE data){ return null; }

    @Override
    public String assignAdvisor(String proposal, String email, DataPoE data){ return null; }
    @Override
    public String consultAdvisor(String email, DataPoE data){ return null; }
    @Override
    public String changeAdvisor(String email, String proposal, DataPoE data) { return null; }
    @Override
    public String deleteAdvisor(String proposal, DataPoE data){ return null; }
    @Override
    public List<String> generateListAdvisors(boolean op1, boolean op2, boolean op3, DataPoE data){ return null; }

    @Override
    public String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5, DataPoE DataPoE){ return null; }
}
