package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Phase;
import pt.isec.pa.apoio_poe.model.data.Student;

import java.util.ArrayList;

public class ThirdPhaseState extends PhaseStateAdapter {
    public static final long serialVersionUID=2020129026;

    ThirdPhaseState(PhaseContext context){ super(context);  }

    @Override
    public boolean previousPhase() {
        changeState(new SecondPhaseState(context));
        return true;
    }

    @Override
    public boolean nextPhase() {
        changeState(new FourthPhaseState(context));
        return true;
    }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_3; }
    
    public void setProposalToStudent(ArrayList<Student> students){
        ArrayList<Student> studentsToAccessInternships = null;
                
        for(Student s : students)
            if(s.isAccessInternships()) {
                assert false;
                studentsToAccessInternships.add(s);
            }

        assert false;
        for(Student s : studentsToAccessInternships)
            System.out.println(s.toString());
    }
}
