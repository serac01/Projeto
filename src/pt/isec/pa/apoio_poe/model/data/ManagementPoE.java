package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.ArrayList;

public class ManagementPoE implements Serializable {
    private final ArrayList<Student> students = new ArrayList<>();
    private final ArrayList<Teacher> teachers = new ArrayList<>();
    private final ArrayList<Proposal> proposals = new ArrayList<>();
    private final ArrayList<Application> applications = new ArrayList<>();
    boolean isPhase1Closed=false, isPhase2Closed=false, isPhase3Closed=false, isPhase4Closed=false;

    public ArrayList<Student> getStudent(){ return students; }

    public ArrayList<Teacher> getTeachers(){ return teachers; }

    public ArrayList<Proposal> getProposals(){ return proposals; }

    public ArrayList<Application> getApplications() {return applications;}

    public boolean isPhase1Closed() { return isPhase1Closed; }
    public void setPhase1Closed(boolean phase1Closed) { isPhase1Closed = phase1Closed; }

    public boolean isPhase2Closed() { return isPhase2Closed; }
    public void setPhase2Closed(boolean phase2Closed) { isPhase2Closed = phase2Closed; }

    public boolean isPhase3Closed() { return isPhase3Closed; }
    public void setPhase3Closed(boolean phase3Closed) { isPhase3Closed = phase3Closed; }

    public boolean isPhase4Closed() { return isPhase4Closed; }
    public void setPhase4Closed(boolean phase4Closed) { isPhase4Closed = phase4Closed; }
}
