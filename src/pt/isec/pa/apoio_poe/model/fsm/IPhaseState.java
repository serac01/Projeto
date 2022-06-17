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
    boolean isPhaseClosed(DataPoE data);
    String closePhase(DataPoE data);
    PhaseState getState();

    String addStudents(String filename, DataPoE data) throws IOException;
    String editStudent(long number, String toUpdate, int option, DataPoE data);
    String deleteStudents(long number, DataPoE data);
    List<String> showStudents(DataPoE data);
    void exportStudents(String filename, DataPoE data) throws IOException;

    String addTeachers(String filename, DataPoE data) throws IOException;
    String editTeacher(String email, String toUpdate, DataPoE data);
    String deleteTeacher(String email, DataPoE data);
    List<String> showTeachers(DataPoE data);
    void exportTeacher(String filename, DataPoE data) throws IOException;

    String addProposals(String filename, DataPoE data) throws IOException;
    String deleteProposals(String id, DataPoE data);
    List<String> showProposals(DataPoE data);
    void exportProposals(String filename, DataPoE data) throws IOException;

    String addApplication(String filename, DataPoE data) throws IOException;
    String editApplication(long number, String id, int option, DataPoE data);
    String deleteApplication(long number, DataPoE data);
    List<String> showApplication(DataPoE data);
    void exportApplications(String filename, DataPoE data) throws IOException;
    List<String> generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, DataPoE data);
    List<String> generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications,DataPoE data);

    String assignAProposalWithoutAssignments(DataPoE data);
    String associateProposalToStudents(String proposal, Long student, DataPoE data);
    String removeStudentFromProposal(String proposal, DataPoE data);
    List<String> generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed, DataPoE data);
    List<String> generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals, DataPoE data);

    String assignAdvisor(String proposal, String email, DataPoE data);
    String consultAdvisor(String email, DataPoE data);
    String changeAdvisor(String email, String proposal, DataPoE data);
    String deleteAdvisor(String proposal, DataPoE data);
    List<String> generateListAdvisors(boolean op1, boolean op2, boolean op3, DataPoE data);

    String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5, DataPoE data);

}
