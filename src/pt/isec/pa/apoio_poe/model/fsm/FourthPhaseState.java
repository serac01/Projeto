package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FourthPhaseState extends PhaseStateAdapter {
    public static final long serialVersionUID=2020129026;

    FourthPhaseState(PhaseContext context){ super(context); }

    @Override
    public void previousPhase() {
        changeState(new ThirdPhaseState(context));
    }
    @Override
    public void nextPhase() {
        changeState(new FifthPhaseState(context));
    }
    @Override
    public PhaseState getState() { return PhaseState.PHASE_4; }
    @Override
    public String closePhase(DataPoE data) {
        data.setPhase4Closed(true);
        return "";
    }
    @Override
    public boolean isPhaseClosed(DataPoE data){ return data.isPhase4Closed(); }

    @Override
    public String assignAdvisor(String proposal, String email, DataPoE data){
        if(!isAValidProposal(proposal,data))
            return "This proposal doesn't exist\n";
        if(!isExistentTeacher(email,data))
            return "This advisor doesn't exist\n";
        if(isAProposalAssigned(proposal,data))
            return "This proposal is already associated to another advisor\n";

        for(Proposal p : data.getProposals())
            if(proposal.equalsIgnoreCase(p.getIdentification()))
                for(Teacher t : data.getTeachers())
                    if(t.getEmail().equalsIgnoreCase(email))
                        p.setTeacher(t);

        return "";
    }

    @Override
    public String consultAdvisor(String email, DataPoE data){
        if(!isExistentTeacher(email,data))
            return "This advisor doesn't exist\n";

        for(Teacher t : data.getTeachers())
            if(t.getEmail().equalsIgnoreCase(email))
                return String.format("Teacher name: %-40s Teacher email: %-50s \n",t.getName(),t.getEmail());
        return "";
    }

    @Override
    public String changeAdvisor(String email, String proposal, DataPoE data){
        if(!isExistentTeacher(email,data))
            return "This advisor doesn't exist\n";
        if(!isAValidProposal(proposal,data))
            return "This proposal doesn't exist\n";

        for(Proposal p : data.getProposals())
            if(p.getIdentification().equalsIgnoreCase(proposal))
                for(Teacher t : data.getTeachers())
                    if(t.getEmail().equalsIgnoreCase(email))
                        p.setTeacher(t);
        return "";
    }

    @Override
    public String deleteAdvisor(String proposal, DataPoE data){
        if(!isAValidProposal(proposal,data))
            return "This proposal doesn't exist\n";
        if(!isAProposalAssigned(proposal,data))
            return "This proposal is not associated with anyone yet\n";

        for(Proposal p : data.getProposals())
            if(p.getIdentification().equalsIgnoreCase(proposal))
                p.setTeacher(null);
        return "";
    }

    @Override
    public List<String> generateListAdvisors(boolean op1, boolean op2, boolean op3, DataPoE data){
        ArrayList<String> listOfStudents = new ArrayList<>();
        if(op1) {
            for (Proposal p : data.getProposals())
                if (p.getStudent() != null && p.getTeacher() != null)
                    for (Student s : data.getStudent())
                        if (p.getStudent().getStudentNumber() == s.getStudentNumber())
                            listOfStudents.add(s.toString());
            return listOfStudents;
        }
        else if(op2) {
            for (Proposal p : data.getProposals())
                if (p.getStudent() != null && p.getTeacher() == null)
                    for (Student s : data.getStudent())
                        if (p.getStudent().getStudentNumber() == s.getStudentNumber())
                            listOfStudents.add(s.toString());
            return listOfStudents;
        }
        else if(op3) {
            StringBuilder phrase = new StringBuilder();
            int counter;

            listOfStudents.add(String.valueOf(phrase.append("On average there are ").append((float) data.getProposals().size() / data.getTeachers().size()).append(" proposals per teacher")));

            for(Teacher t : data.getTeachers()) {
                phrase.setLength(0);
                counter=0;
                for (Proposal p : data.getProposals())
                    if (p.getTeacher() != null && p.getTeacher().getEmail().equalsIgnoreCase(t.getEmail()))
                        counter++;
                listOfStudents.add(String.valueOf(phrase.append("The teacher ").append(t.getName()).append(" has ").append(counter).append(" assigned proposals")));
            }
            return listOfStudents;
        }
        return listOfStudents;
    }

    @Override
    public void exportProposals(String filename, DataPoE data) throws IOException {
        FileWriter csvWriter = null;
        try {
            File file = new File(filename);
            if(!file.exists()) {
                csvWriter = new FileWriter(file);
                int count = 0;

                for (Proposal p : data.getProposals()) {
                    csvWriter.append(p.getType());
                    csvWriter.append(",");
                    csvWriter.append(p.getIdentification());
                    csvWriter.append(",");
                    if(p.getType().equalsIgnoreCase("T1")){
                        csvWriter.append(getStringIndustryAcronym(p.getArea()));
                        csvWriter.append(",");
                        csvWriter.append(p.getTitle());
                        csvWriter.append(",");
                        csvWriter.append(p.getHostEntity());
                        if(p.getStudent()!=null){
                            csvWriter.append(",");
                            csvWriter.append(String.valueOf(p.getStudent().getStudentNumber()));
                        }
                        if(p.getTeacher()!=null){
                            csvWriter.append(",");
                            csvWriter.append(String.valueOf(p.getTeacher().getEmail()));
                        }
                    }else if(p.getType().equalsIgnoreCase("T2")){
                        csvWriter.append(getStringIndustryAcronym(p.getArea()));
                        csvWriter.append(",");
                        csvWriter.append(p.getTitle());
                        csvWriter.append(",");
                        csvWriter.append(p.getTeacher().getEmail());
                        if(p.getStudent()!=null){
                            csvWriter.append(",");
                            csvWriter.append(String.valueOf(p.getStudent().getStudentNumber()));
                        }
                    }else if(p.getType().equalsIgnoreCase("T3")){
                        csvWriter.append(p.getTitle());
                        csvWriter.append(",");
                        csvWriter.append(String.valueOf(p.getStudent().getStudentNumber()));
                        if(p.getTeacher()!=null){
                            csvWriter.append(",");
                            csvWriter.append(String.valueOf(p.getTeacher().getEmail()));
                        }
                    }
                    count++;
                    if (count < data.getProposals().size())
                        csvWriter.append("\n");
                }
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (csvWriter != null)
                csvWriter.close();
        }
    }

    private String getStringIndustryAcronym(List<String> area){
        StringBuilder listIndustryAcronym = new StringBuilder(5);
        int countIndustryAcronym=0;
        for(String s : area) {
            listIndustryAcronym.append(s);
            countIndustryAcronym++;
            if(countIndustryAcronym < area.size())
                listIndustryAcronym.append("|");
        }
        return listIndustryAcronym.toString();
    }

    //Validations
    private static boolean isExistentTeacher(String email, DataPoE data){
        for(Teacher t : data.getTeachers())
            if(t.getEmail().equalsIgnoreCase(email))
                return true;
        return false;
    }
    private static boolean isAValidProposal(String id, DataPoE data){
        for(Proposal p : data.getProposals())
            if(id.equalsIgnoreCase(p.getIdentification()))
                return true;
        return false;
    }
    private static boolean isAProposalAssigned(String id, DataPoE data){
        for(Proposal p : data.getProposals())
            if (p.getIdentification().equalsIgnoreCase(id))
                if(p.getTeacher() != null)
                    return true;
        return false;
    }

}
