package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FirstPhaseState extends PhaseStateAdapter implements Serializable {
    public static final long serialVersionUID=2020129026;
    private boolean isClosed;

    FirstPhaseState(PhaseContext context){
        super(context);
        this.isClosed = false;
    }

    //State
    @Override
    public boolean nextPhase() {
        changeState(new SecondPhaseState(context));
        return true;
    }
    @Override
    public boolean isClosed() { return isClosed; }
    @Override
    public PhaseState getState() { return PhaseState.PHASE_1; }
    @Override
    public String closePhase(ArrayList<Proposal> proposals, ArrayList<Student> students, boolean value) {
        if(students.isEmpty() || proposals.isEmpty())
            return "You still don't have the necessary data to be able to close the phase\n";

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
            this.isClosed=true;
        else
            return "Unable to close this phase\n";
        return "";
    }

    //Students
    @Override
    public String addStudents(String filename, ArrayList<Student> students) throws IOException { return StudentState.addStudents(filename, students); }
    @Override
    public String editStudent(long number, String toUpdate, int option, ArrayList<Student> students)  { return StudentState.editStudent(number, toUpdate, option, students); }
    @Override
    public String deleteStudents(long number, ArrayList<Student> students){ return StudentState.deleteStudents(number,students); }
    @Override
    public String showStudents(ArrayList<Student> students){ return StudentState.showStudents(students); }
    @Override
    public void exportStudents(String filename, ArrayList<Student> students) throws IOException { StudentState.exportStudents(filename, students); }

    //Teachers
    @Override
    public String addTeachers(String filename, ArrayList<Teacher> teachers) throws IOException { return TeacherState.addTeachers(filename,teachers); }
    @Override
    public String editTeacher(String email, String toUpdate, ArrayList<Teacher> teachers) { return TeacherState.editTeacher(email,toUpdate,teachers); }
    @Override
    public String deleteTeacher(String email, ArrayList<Teacher> teachers) { return TeacherState.deleteTeacher(email,teachers); }
    @Override
    public String showTeachers(ArrayList<Teacher> teachers) { return TeacherState.showTeachers(teachers); }
    @Override
    public void exportTeacher(String filename, ArrayList<Teacher> teachers) throws IOException { TeacherState.exportTeacher(filename,teachers); }

    //Proposals
    @Override
    public String addProposals(String filename, ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Teacher> teachers) throws IOException { return ProposalsState.addProposals(filename,proposals,students,teachers); }
    @Override
    public String deleteProposals(String id, ArrayList<Proposal> proposals) { return ProposalsState.deleteProposals(id, proposals); }
    @Override
    public String showProposals(ArrayList<Proposal> proposals) { return ProposalsState.showProposals(proposals); }
    @Override
    public void exportProposals(String filename, ArrayList<Proposal> proposals) throws IOException { ProposalsState.exportProposals(filename, proposals); }
}