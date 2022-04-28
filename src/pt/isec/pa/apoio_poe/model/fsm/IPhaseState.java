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
    boolean closePhase(ArrayList<Proposal> proposals, ArrayList<Student> student);
    void changeState(IPhaseState newState);
    void setContext(PhaseContext context);

    ArrayList<Student> addStudents(String filename, ArrayList<Student> students) throws IOException;
    ArrayList<Student> editStudent(long number, String toUpdate, int option, ArrayList<Student> students);
    ArrayList<Student> deleteStudents(long number, ArrayList<Student> students);
    void showStudents(ArrayList<Student> students);
    void exportStudents(String filename, ArrayList<Student> students) throws IOException;

    ArrayList<Teacher> addTeachers(String filename, ArrayList<Teacher> teachers) throws IOException;
    ArrayList<Teacher> editTeacher(String email, String toUpdate, ArrayList<Teacher> teachers);
    ArrayList<Teacher> deleteTeacher(String email, ArrayList<Teacher> teachers);
    void showTeachers(ArrayList<Teacher> teachers);
    void exportTeacher(String filename, ArrayList<Teacher> teachers) throws IOException;

    ArrayList<Proposal> addProposals(String filename, ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Teacher> teachers) throws IOException;
    ArrayList<Proposal> editProposals(String id, String toUpdate, int option, ArrayList<Proposal> proposals);
    ArrayList<Proposal> deleteProposals(String id, ArrayList<Proposal> proposals);
    void showProposals(ArrayList<Proposal> proposals);
    void exportProposals(String filename, ArrayList<Proposal> proposals) throws IOException;

    ArrayList<Application> addApplication(String filename, ArrayList<Application> applications, ArrayList<Proposal> proposals, ArrayList<Student> students) throws IOException;
    ArrayList<Application> editApplication(long number, String id, int option, ArrayList<Application> applications, ArrayList<Proposal> proposals);
    ArrayList<Application> deleteApplication(long number, ArrayList<Application> applications);
    void showApplication(ArrayList<Application> applications);
    void exportApplications(String filename, ArrayList<Application> applications) throws IOException;

    void generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, ArrayList<Proposal> proposals, ArrayList <Application> applications, ArrayList <Student> students);
    void generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications,ArrayList<Proposal> proposals, ArrayList<Application> applications);


    PhaseState getState();

}
