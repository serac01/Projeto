package pt.isec.pa.apoio_poe.model.data;

import java.util.ArrayList;

public class ManagementPoE {
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private ArrayList<Proposal> proposals = new ArrayList<>();

    public ArrayList<Student> getStudent(){ return students; }

    public void setStudent(ArrayList<Student> student){ this.students = student; }

}
