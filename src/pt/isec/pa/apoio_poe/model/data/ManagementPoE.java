package pt.isec.pa.apoio_poe.model.data;

import java.util.ArrayList;

public class ManagementPoE {
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private ArrayList<Proposal> proposals = new ArrayList<>();
    private ArrayList<Application> applications = new ArrayList<>();


    public ArrayList<Student> getStudent(){ return students; }
    public void setStudent(ArrayList<Student> student){ this.students = student; }

    public ArrayList<Teacher> getTeachers(){ return teachers; }
    public void setTeachers(ArrayList<Teacher> teachers){ this.teachers = teachers; }

    public ArrayList<Proposal> getProposals(){ return proposals; }
    public void setProposals(ArrayList<Proposal> proposals){ this.proposals = proposals; }

    public ArrayList<Application> getApplications() {return applications;}
    public void setApplications(ArrayList<Application> applications) {this.applications = applications;}
}
