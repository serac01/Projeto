package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;
import java.util.ArrayList;

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
    public boolean nextPhase() { return false; }
    @Override
    public void setContext(PhaseContext context) { this.context=context;  }
    @Override
    public boolean previousPhase() { return false; }
    @Override
    public boolean isClosed(){ return false; }
    @Override
    public String closePhase(ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Application> applications, boolean value){ return null; }

    //Students
    @Override
    public String addStudents(String filename, ArrayList<Student> students) throws IOException { return null; }
    @Override
    public String editStudent(long number, String toUpdate, int option, ArrayList<Student> students) { return null; }
    @Override
    public String deleteStudents(long number, ArrayList<Student> students, ArrayList<Proposal> proposals, ArrayList<Application> applications) { return null; }
    @Override
    public String showStudents(ArrayList<Student> students) { return null; }
    @Override
    public void exportStudents(String filename, ArrayList<Student> students) throws IOException { }

    //Teachers
    @Override
    public String addTeachers(String filename, ArrayList<Teacher> teachers) throws IOException { return null; }
    @Override
    public String editTeacher(String email, String toUpdate, ArrayList<Teacher> teachers) { return null; }
    @Override
    public String deleteTeacher(String email, ArrayList<Teacher> teachers, ArrayList<Proposal> proposals) { return null; }
    @Override
    public String showTeachers(ArrayList<Teacher> teachers) { return null; }
    @Override
    public void exportTeacher(String filename, ArrayList<Teacher> teachers) throws IOException {  }

    //Proposals
    @Override
    public String addProposals(String filename, ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Teacher> teachers) throws IOException { return null; }
    @Override
    public String deleteProposals(String id, ArrayList<Proposal> proposals, ArrayList<Application> applications) { return null; }
    @Override
    public String showProposals(ArrayList<Proposal> proposals) { return null; }
    @Override
    public void exportProposals(String filename, ArrayList<Proposal> proposals) throws IOException { }

    //Applications
    @Override
    public String addApplication(String filename, ArrayList<Application> applications, ArrayList<Proposal> proposals, ArrayList<Student> students) throws IOException { return null; }
    @Override
    public String editApplication(long number, String id, int option, ArrayList<Application> applications, ArrayList<Proposal> proposals) { return null; }
    @Override
    public String deleteApplication(long number, ArrayList<Application> applications){ return null; }
    @Override
    public String showApplication(ArrayList<Application> applications) { return null; }
    @Override
    public void exportApplications(String filename, ArrayList<Application> applications) throws IOException {}
    @Override
    public String generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, ArrayList<Proposal> proposals, ArrayList <Application> applications, ArrayList <Student> students) { return null; }
    @Override
    public String generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications,ArrayList<Proposal> proposals, ArrayList<Application> applications) { return null; }

    @Override
    public String assignAProposalWithoutAssignments(ArrayList<Application> applications, ArrayList<Student> students, ArrayList<Proposal> proposals){ return null; }
    @Override
    public String associateProposalToStudents(String proposal, Long student, ArrayList<Proposal> proposals, ArrayList<Student> students) { return null; }
    @Override
    public  String removeStudentFromProposal(String proposal, ArrayList<Proposal> proposals){ return null; }
    @Override
    public String generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed, ArrayList<Student> students, ArrayList<Proposal> proposals) { return null; }
    @Override
    public String generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals, ArrayList<Proposal> proposals){ return null; }

    @Override
    public String assignAdvisor(String proposal, String email, ArrayList<Proposal> proposals, ArrayList<Teacher> teachers){ return null; }
    @Override
    public String consultAdvisor(String email, ArrayList<Teacher> teachers){ return null; }
    @Override
    public String changeAdvisor(String email, String proposal, ArrayList<Teacher> teachers, ArrayList<Proposal> proposals) { return null; }
    @Override
    public String deleteAdvisor(String proposal, ArrayList<Proposal> proposals){ return null; }
    @Override
    public String generateListAdvisors(boolean op1, boolean op2, boolean op3, ArrayList<Teacher> teachers, ArrayList<Student> students, ArrayList<Proposal> proposals){ return null; }

    @Override
    public String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5, ArrayList<Teacher> teachers, ArrayList<Student> students, ArrayList<Proposal> proposals){ return null; }
}
