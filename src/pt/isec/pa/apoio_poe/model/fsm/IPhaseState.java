package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;
import java.util.List;

public interface IPhaseState {
    void nextPhase();
    void previousPhase();
    void changeToStudent();
    void changeToTeacher();
    void changeToProposal();
    void changeState(IPhaseState newState);
    void setContext(PhaseContext context);
    boolean isPhaseClosed(ManagementPoE management);
    String closePhase(ManagementPoE management);
    PhaseState getState();

    String addStudents(String filename, ManagementPoE management) throws IOException;
    String editStudent(long number, String toUpdate, int option, ManagementPoE management);
    String deleteStudents(long number, ManagementPoE management);
    List<String> showStudents(ManagementPoE management);
    void exportStudents(String filename, ManagementPoE management) throws IOException;

    String addTeachers(String filename, ManagementPoE management) throws IOException;
    String editTeacher(String email, String toUpdate, ManagementPoE management);
    String deleteTeacher(String email, ManagementPoE management);
    List<String> showTeachers(ManagementPoE management);
    void exportTeacher(String filename, ManagementPoE management) throws IOException;

    String addProposals(String filename, ManagementPoE management) throws IOException;
    String deleteProposals(String id, ManagementPoE management);
    List<String> showProposals(ManagementPoE management);
    void exportProposals(String filename, ManagementPoE management) throws IOException;

    String addApplication(String filename, ManagementPoE management) throws IOException;
    String editApplication(long number, String id, int option, ManagementPoE management);
    String deleteApplication(long number, ManagementPoE management);
    List<String> showApplication(ManagementPoE management);
    void exportApplications(String filename, ManagementPoE management) throws IOException;
    List<String> generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, ManagementPoE management);
    List<String> generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications,ManagementPoE management);

    String assignAProposalWithoutAssignments(ManagementPoE management);
    String associateProposalToStudents(String proposal, Long student, ManagementPoE management);
    String removeStudentFromProposal(String proposal, ManagementPoE management);
    List<String> generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed, ManagementPoE management);
    List<String> generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals, ManagementPoE management);

    String assignAdvisor(String proposal, String email, ManagementPoE management);
    String consultAdvisor(String email, ManagementPoE management);
    String changeAdvisor(String email, String proposal, ManagementPoE management);
    String deleteAdvisor(String proposal, ManagementPoE management);
    List<String> generateListAdvisors(boolean op1, boolean op2, boolean op3, ManagementPoE management);

    String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5, ManagementPoE management);

}
