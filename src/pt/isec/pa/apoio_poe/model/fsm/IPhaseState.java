package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Application;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IPhaseState {
    boolean nextPhase();
    boolean previousPhase();
    String closePhase(ArrayList<Proposal> proposals, ArrayList<Student> student, ArrayList<Application> applications, boolean value);
    boolean isClosed();
    void changeState(IPhaseState newState);
    void setContext(PhaseContext context);

    String addStudents(String filename, ArrayList<Student> students) throws IOException;
    String editStudent(long number, String toUpdate, int option, ArrayList<Student> students);
    String deleteStudents(long number, ArrayList<Student> students, ArrayList<Proposal> proposals, ArrayList<Application> applications);
    String showStudents(ArrayList<Student> students);
    void exportStudents(String filename, ArrayList<Student> students) throws IOException;

    String addTeachers(String filename, ArrayList<Teacher> teachers) throws IOException;
    String editTeacher(String email, String toUpdate, ArrayList<Teacher> teachers);
    String deleteTeacher(String email, ArrayList<Teacher> teachers, ArrayList<Proposal> proposals);
    String showTeachers(ArrayList<Teacher> teachers);
    void exportTeacher(String filename, ArrayList<Teacher> teachers) throws IOException;

    String addProposals(String filename, ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Teacher> teachers) throws IOException;
    String deleteProposals(String id, ArrayList<Proposal> proposals, ArrayList<Application> applications);
    String showProposals(ArrayList<Proposal> proposals);
    void exportProposals(String filename, ArrayList<Proposal> proposals) throws IOException;

    String addApplication(String filename, ArrayList<Application> applications, ArrayList<Proposal> proposals, ArrayList<Student> students) throws IOException;
    String editApplication(long number, String id, int option, ArrayList<Application> applications, ArrayList<Proposal> proposals);
    String deleteApplication(long number, ArrayList<Application> applications);
    String showApplication(ArrayList<Application> applications);
    void exportApplications(String filename, ArrayList<Application> applications) throws IOException;
    String generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, ArrayList<Proposal> proposals, ArrayList <Application> applications, ArrayList <Student> students);
    String generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications,ArrayList<Proposal> proposals, ArrayList<Application> applications);

    String assignAProposalWithoutAssignments(ArrayList<Application> applications, ArrayList<Student> students, ArrayList<Proposal> proposals);
    String associateProposalToStudents(String proposal, Long student, ArrayList<Proposal> proposals, ArrayList<Student> students);
    String removeStudentFromProposal(String proposal, ArrayList<Proposal> proposals);
    String generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed, ArrayList<Student> students, ArrayList<Proposal> proposals);
    String generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals, ArrayList<Proposal> proposals);

    String assignAdvisor(String proposal, String email, ArrayList<Proposal> proposals, ArrayList<Teacher> teachers);
    String consultAdvisor(String email, ArrayList<Teacher> teachers);
    String changeAdvisor(String email, String proposal, ArrayList<Teacher> teachers, ArrayList<Proposal> proposals);
    String deleteAdvisor(String proposal, ArrayList<Proposal> proposals);
    String generateListAdvisors(boolean op1, boolean op2, boolean op3, ArrayList<Teacher> teachers, ArrayList<Student> students, ArrayList<Proposal> proposals);

    String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5, ArrayList<Teacher> teachers, ArrayList<Student> students, ArrayList<Proposal> proposals);

    PhaseState getState();
}
