package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FirstPhaseState extends PhaseStateAdapter implements Serializable {
    public static final long serialVersionUID=2020129026;

    FirstPhaseState(PhaseContext context){ super(context); }

    //State
    @Override
    public void nextPhase() { changeState(new SecondPhaseState(context)); }
    @Override
    public PhaseState getState() { return PhaseState.PHASE_1; }
    @Override
    public String closePhase(DataPoE data) {
        List<String> industryAcronym;
        int countSI=0, countDA=0, countRAS=0, studentsSI=0,studentsDA=0,studentsRAS=0;

        if(data.getStudent().isEmpty() || data.getProposals().isEmpty())
            return "You still don't have the necessary data to be able to close the phase\n";

        //Count the number of proposals
        for(Proposal p : data.getProposals()) {
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
        for(Student s : data.getStudent()) {
            if (s.getIndustryAcronym().equalsIgnoreCase("SI"))
                studentsSI++;
            else if (s.getIndustryAcronym().equalsIgnoreCase("DA"))
                studentsDA++;
            else if (s.getIndustryAcronym().equalsIgnoreCase("RAS"))
                studentsRAS++;
        }

        if(countSI >= studentsSI && countDA >= studentsDA && countRAS >= studentsRAS) {
            data.setPhase1Closed(true);
            data.setStudentPhaseClosed(true);
            data.setTeacherPhaseClosed(true);
            data.setProposalPhaseClosed(true);
        }
        else
            return "Unable to close this phase\n";
        return "";
    }
    @Override
    public void changeToStudent() { changeState(new StudentState(context)); }
    @Override
    public void changeToTeacher() { changeState(new TeacherState(context)); }
    @Override
    public void changeToProposal() { changeState(new ProposalsState(context)); }
    @Override
    public boolean isPhaseClosed(DataPoE data){ return data.isPhase1Closed(); }

}