package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.ArrayList;

public class ManagementPoE implements Serializable {
    public static final long serialVersionUID=2020129026;
    private final ArrayList<Student> students = new ArrayList<>();
    private final ArrayList<Teacher> teachers = new ArrayList<>();
    private final ArrayList<Proposal> proposals = new ArrayList<>();
    private final ArrayList<Application> applications = new ArrayList<>();
    boolean isPhase1Closed=false, isStudentPhaseClosed=false, isTeacherPhaseClosed=false, isProposalPhaseClosed=false, isPhase2Closed=false, isPhase3Closed=false, isPhase4Closed=false;

    public ArrayList<Student> getStudent(){ return students; }
    public ArrayList<Teacher> getTeachers(){ return teachers; }
    public ArrayList<Proposal> getProposals(){ return proposals; }
    public ArrayList<Application> getApplications() {return applications;}

    public void addStudent(Student student){ students.add(student); }
    public void addTeacher(Teacher teacher){ teachers.add(teacher); }
    public void addProposal(Proposal proposal){ proposals.add(proposal); }
    public void addApplication(Application application){ applications.add(application); }

    public void deleteStudentFromList(long number){ students.removeIf(s -> s.getStudentNumber() == number); }
    public void deleteTeacherFromList(String email){ teachers.removeIf(t -> t.getEmail().equalsIgnoreCase(email)); }
    public void deleteProposalFromList(String id){ proposals.removeIf(p -> p.getIdentification().equalsIgnoreCase(id)); }
    public void deleteApplicationFromList(long number){ applications.removeIf(a -> a.getStudent().getStudentNumber() == number); }

    public static String showListOfStudents(ArrayList<Student> listStudents){
        StringBuilder s = new StringBuilder();
        for(Student student: listStudents)
            s.append(String.format("Student number: %-10d Student name: %-30s Email: %s Course acronym: %-6s Industry acronym: %-7s Classification: %.6f Access to Internship : %b \n",student.getStudentNumber(),student.getName(),student.getEmail(),student.getCourseAcronym(),student.getIndustryAcronym(),student.getClassification(),student.isAccessInternships()));
        return s.toString();
    }
    public static String showListOfProposals(ArrayList<Proposal> listProposals){
        StringBuilder s = new StringBuilder();
        for (Proposal p: listProposals)
            if(p.getType().equalsIgnoreCase("T1") && p.getStudent()!=null && p.getTeacher()!=null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]Internship   Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s Host Entity: %-30s \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),p.getStudent().getName(),p.getStudent().getStudentNumber(),p.getTeacher().getName(),p.getTeacher().getEmail(),p.getHostEntity()));
            else if(p.getType().equalsIgnoreCase("T1") && p.getStudent()!=null && p.getTeacher()==null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]Internship   Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s Host Entity: %-30s \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),p.getStudent().getName(),p.getStudent().getStudentNumber(),"empty","empty",p.getHostEntity()));
            else if(p.getType().equalsIgnoreCase("T1") && p.getStudent()==null && p.getTeacher()!=null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]Internship   Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10s Assigned Teacher: %-30s with Email: %-30s Host Entity: %-30s \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),"empty","empty",p.getTeacher().getName(),p.getTeacher().getEmail(),p.getHostEntity()));
            else if(p.getType().equalsIgnoreCase("T1") && p.getStudent()==null && p.getTeacher()==null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]Internship   Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10s Assigned Teacher: %-30s with Email: %-30s Host Entity: %-30s \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),"empty","empty","empty","empty",p.getHostEntity()));
            else if(p.getType().equalsIgnoreCase("T2") && p.getStudent()!=null && p.getTeacher()!=null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]Project      Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s  \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),p.getStudent().getName(),p.getStudent().getStudentNumber(),p.getTeacher().getName(),p.getTeacher().getEmail()));
            else if(p.getType().equalsIgnoreCase("T2") && p.getStudent()==null && p.getTeacher()!=null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]Project      Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10s Assigned Teacher: %-30s with Email: %-30s  \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),"empty","empty",p.getTeacher().getName(),p.getTeacher().getEmail()));
            else if(p.getType().equalsIgnoreCase("T2") && p.getStudent()!=null && p.getTeacher()==null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]Project      Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s  \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),p.getStudent().getName(),p.getStudent().getStudentNumber(),"empty","empty"));
            else if(p.getType().equalsIgnoreCase("T2") && p.getStudent()==null && p.getTeacher()==null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]Project      Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10s Assigned Teacher: %-30s with Email: %-30s  \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),"empty","empty","empty","empty"));
            else if(p.getType().equalsIgnoreCase("T3") && p.getTeacher()!=null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]SelfProposed Title: %-50s                     Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s  \n",p.getIdentification(),p.getType(),p.getTitle(),p.getStudent().getName(),p.getStudent().getStudentNumber(),p.getTeacher().getName(),p.getTeacher().getEmail()));
            else if(p.getType().equalsIgnoreCase("T3") && p.getTeacher()==null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]SelfProposed Title: %-50s                     Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s  \n",p.getIdentification(),p.getType(),p.getTitle(),p.getStudent().getName(),p.getStudent().getStudentNumber(),"empty","empty"));
        return s.toString();
    }

    public boolean isPhase1Closed() { return isPhase1Closed; }
    public boolean isPhase2Closed() { return isPhase2Closed; }
    public boolean isPhase3Closed() { return isPhase3Closed; }
    public boolean isPhase4Closed() { return isPhase4Closed; }
    public boolean isStudentPhaseClosed() { return isStudentPhaseClosed; }
    public boolean isTeacherPhaseClosed() { return isTeacherPhaseClosed; }
    public boolean isProposalPhaseClosed() { return isProposalPhaseClosed; }

    public void setPhase1Closed(boolean phase1Closed) { this.isPhase1Closed = phase1Closed; }
    public void setPhase2Closed(boolean phase2Closed) { this.isPhase2Closed = phase2Closed; }
    public void setPhase3Closed(boolean phase3Closed) { this.isPhase3Closed = phase3Closed; }
    public void setPhase4Closed(boolean phase4Closed) { this.isPhase4Closed = phase4Closed; }
    public void setStudentPhaseClosed(boolean studentPhaseClosed) { this.isStudentPhaseClosed = studentPhaseClosed; }
    public void setTeacherPhaseClosed(boolean teacherPhaseClosed) { this.isTeacherPhaseClosed = teacherPhaseClosed; }
    public void setProposalPhaseClosed(boolean proposalPhaseClosed) { this.isProposalPhaseClosed = proposalPhaseClosed; }
}
