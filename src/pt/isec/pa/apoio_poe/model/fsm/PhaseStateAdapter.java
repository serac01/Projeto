package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;
import java.util.ArrayList;

abstract class PhaseStateAdapter implements IPhaseState, Serializable{
    protected PhaseContext context;

    protected PhaseStateAdapter(PhaseContext context){ this.context = context; }

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
    public boolean closePhase(ArrayList<Proposal> proposals, ArrayList<Student> students){ return false; }

    @Override
    public void addStudents(String filename, ArrayList<Student> students) throws IOException { }
    @Override
    public void editStudent(long number, String toUpdate, int option, ArrayList<Student> students) { }
    @Override
    public void deleteStudents(long number, ArrayList<Student> students) { }
    @Override
    public void showStudents(ArrayList<Student> students) { }
    @Override
    public void exportStudents(String filename, ArrayList<Student> students) throws IOException { }


    /************************************************** Teachers **************************************************/
    @Override
    public void addTeachers(String filename, ArrayList<Teacher> teachers) throws IOException { }
    @Override
    public void editTeacher(String email, String toUpdate, ArrayList<Teacher> teachers) { }
    @Override
    public void deleteTeacher(String email, ArrayList<Teacher> teachers) { }
    @Override
    public void showTeachers(ArrayList<Teacher> teachers) { }
    @Override
    public void exportTeacher(String filename, ArrayList<Teacher> teachers) throws IOException {  }

    /************************************************** Proposals **************************************************/
    @Override
    public void addProposals(String filename, ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Teacher> teachers) throws IOException { }
    @Override
    public void editProposals(String id, String toUpdate, int option, ArrayList<Proposal> proposals) { }
    @Override
    public void deleteProposals(String id, ArrayList<Proposal> proposals) { }
    @Override
    public void showProposals(ArrayList<Proposal> proposals) { }
    @Override
    public void exportProposals(String filename, ArrayList<Proposal> proposals) throws IOException { }

    /******************************/

    @Override
    public ArrayList<Application> addApplication(String filename, ArrayList<Application> applications, ArrayList<Proposal> proposals, ArrayList<Student> students) throws IOException { return null; }

    @Override
    public ArrayList<Application> editApplication(long number, String id, int option, ArrayList<Application> applications, ArrayList<Proposal> proposals) { return null; }

    @Override
    public ArrayList<Application> deleteApplication(long number, ArrayList<Application> applications){ return null; }

    @Override
    public void showApplication(ArrayList<Application> applications) {}

    @Override
    public void exportApplications(String filename, ArrayList<Application> applications) throws IOException { }

    @Override
    public void generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, ArrayList<Proposal> proposals, ArrayList <Application> applications, ArrayList <Student> students) { }

    @Override
    public void generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications,ArrayList<Proposal> proposals, ArrayList<Application> applications) { }

}
