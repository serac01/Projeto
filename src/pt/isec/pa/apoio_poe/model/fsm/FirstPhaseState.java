package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FirstPhaseState extends PhaseStateAdapter implements Serializable {
    private boolean isClosed;
    public static final long serialVersionUID=2020129026;

    FirstPhaseState(PhaseContext context){
        super(context);
        isClosed=false;
    }

    @Override
    public boolean nextPhase() {
        changeState(new SecondPhaseState(context));
        return true;
    }

    public boolean isClosed() { return isClosed; }

    @Override
    public boolean closePhase(ArrayList<Proposal> proposals, ArrayList<Student> students) {
        if(isClosed)
            return true;

        if(students.isEmpty() || proposals.isEmpty())
            return false;

        List<String> industryAcronym;
        int countSI=0, countDA=0, countRAS=0, studentsSI=0,studentsDA=0,studentsRAS=0;
        //Count the number of proposals
        for(Proposal p : proposals) {
            if(p.getType().equalsIgnoreCase("T1") || p.getType().equalsIgnoreCase("T2")){
                industryAcronym = p.getArea();
                for (String l : industryAcronym)
                    if (l.equalsIgnoreCase("SI"))
                        countSI++;
                    else if (l.equalsIgnoreCase("DA"))
                        countDA++;
                    else if (l.equalsIgnoreCase("RAS"))
                        countRAS++;
            }
        }

        //Count the number os persons per area
        for(Student s : students) {
            if (s.getIndustryAcronym().equalsIgnoreCase("SI"))
                studentsSI++;
            else if (s.getIndustryAcronym().equalsIgnoreCase("DA"))
                studentsDA++;
            else if (s.getIndustryAcronym().equalsIgnoreCase("RAS"))
                studentsRAS++;
        }

        if(countSI >= studentsSI && countDA >= studentsDA && countRAS >= studentsRAS)
            isClosed=true;
        return isClosed;
    }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_1; }

    @Override
    public void addStudents(String filename, ArrayList<Student> students) throws IOException { StudentState.addStudents(filename, students); }
    @Override
    public void editStudent(long number, String toUpdate, int option, ArrayList<Student> students)  { StudentState.editStudent(number, toUpdate, option, students); }
    @Override
    public void deleteStudents(long number, ArrayList<Student> students){ StudentState.deleteStudents(number,students); }
    @Override
    public void showStudents(ArrayList<Student> students){ StudentState.showStudents(students); }
    @Override
    public void exportStudents(String filename, ArrayList<Student> students) throws IOException { StudentState.exportStudents(filename, students); }

    @Override
    public void addTeachers(String filename, ArrayList<Teacher> teachers) throws IOException { TeacherState.addTeachers(filename,teachers); }
    @Override
    public void editTeacher(String email, String toUpdate, ArrayList<Teacher> teachers) { TeacherState.editTeacher(email,toUpdate,teachers); }
    @Override
    public void deleteTeacher(String email, ArrayList<Teacher> teachers) { TeacherState.deleteTeacher(email,teachers); }
    @Override
    public void showTeachers(ArrayList<Teacher> teachers) { TeacherState.showTeachers(teachers); }
    @Override
    public void exportTeacher(String filename, ArrayList<Teacher> teachers) throws IOException { TeacherState.exportTeacher(filename,teachers); }

    @Override
    public void addProposals(String filename, ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Teacher> teachers) throws IOException { ProposalsState.addProposals(filename,proposals,students,teachers); }
    @Override
    public void editProposals(String id, String toUpdate, int option, ArrayList<Proposal> proposals) { ProposalsState.editProposals(id, toUpdate, option, proposals); }
    @Override
    public void deleteProposals(String id, ArrayList<Proposal> proposals) { ProposalsState.deleteProposals(id, proposals); }
    @Override
    public void showProposals(ArrayList<Proposal> proposals) { ProposalsState.showProposals(proposals); }
    @Override
    public void exportProposals(String filename, ArrayList<Proposal> proposals) throws IOException { ProposalsState.exportProposals(filename, proposals); }
}