package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Application;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FourthPhaseState extends PhaseStateAdapter {
    public static final long serialVersionUID=2020129026;
    private boolean isClosed;

    FourthPhaseState(PhaseContext context){ super(context); }

    @Override
    public boolean previousPhase() {
        changeState(new ThirdPhaseState(context));
        return true;
    }
    @Override
    public boolean nextPhase() {
        changeState(new FifthPhaseState(context));
        return true;
    }
    @Override
    public PhaseState getState() { return PhaseState.PHASE_4; }
    @Override
    public String closePhase(ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Application> applications, boolean value) {
        isClosed=true;
        return "";
    }
    @Override
    public boolean isClosed() { return isClosed; }

    @Override
    public String assignAdvisor(String proposal, String email, ArrayList<Proposal> proposals, ArrayList<Teacher> teachers){
        if(!isAValidProposal(proposal,proposals))
            return "This proposal doesn't exist\n";
        if(!isExistentTeacher(email,teachers))
            return "This advisor doesn't exist\n";
        if(isAProposalAssigned(proposal,proposals))
            return "This proposal is already associated to another advisor\n";

        for(Proposal p : proposals)
            if(proposal.equalsIgnoreCase(p.getIdentification()))
                for(Teacher t : teachers)
                    if(t.getEmail().equalsIgnoreCase(email))
                        p.setTeacher(t);

        return "";
    }

    @Override
    public String consultAdvisor(String email, ArrayList<Teacher> teachers){
        if(!isExistentTeacher(email,teachers))
            return "This advisor doesn't exist\n";

        for(Teacher t : teachers)
            if(t.getEmail().equalsIgnoreCase(email))
                return String.format("Teacher name: %-40s Teacher email: %-50s \n",t.getName(),t.getEmail());
        return "";
    }

    @Override
    public String changeAdvisor(String email, String proposal, ArrayList<Teacher> teachers, ArrayList<Proposal> proposals){
        if(!isExistentTeacher(email,teachers))
            return "This advisor doesn't exist\n";
        if(!isAValidProposal(proposal,proposals))
            return "This proposal doesn't exist\n";

        for(Proposal p : proposals)
            if(p.getIdentification().equalsIgnoreCase(proposal))
                for(Teacher t : teachers)
                    if(t.getEmail().equalsIgnoreCase(email))
                        p.setTeacher(t);
        return "";
    }

    @Override
    public String deleteAdvisor(String proposal, ArrayList<Proposal> proposals){
        if(!isAValidProposal(proposal,proposals))
            return "This proposal doesn't exist\n";
        if(!isAProposalAssigned(proposal,proposals))
            return "This proposal is not associated with anyone yet\n";

        for(Proposal p : proposals)
            if(p.getIdentification().equalsIgnoreCase(proposal))
                p.setTeacher(null);
        return "";
    }

    @Override
    public String generateListAdvisors(boolean op1, boolean op2, boolean op3, ArrayList<Teacher> teachers, ArrayList<Student> students, ArrayList<Proposal> proposals){
        ArrayList<Student> listOfStudents = new ArrayList<>();
        if(op1) {
            for (Proposal p : proposals)
                if (p.getStudent() != null && p.getTeacher() != null)
                    for (Student s : students)
                        if (p.getStudent().getStudentNumber() == s.getStudentNumber())
                            listOfStudents.add(s);
            return StudentState.showStudents(listOfStudents);
        }
        else if(op2) {
            for (Proposal p : proposals)
                if (p.getStudent() != null && p.getTeacher() == null)
                    for (Student s : students)
                        if (p.getStudent().getStudentNumber() == s.getStudentNumber())
                            listOfStudents.add(s);
            return StudentState.showStudents(listOfStudents);
        }
        else if(op3) {
            StringBuilder phrase = new StringBuilder();
            phrase.append("On average there are ").append((float) proposals.size() / teachers.size()).append(" proposals per teacher\n");
            int counter;
            for(Teacher t : teachers) {
                counter=0;
                for (Proposal p : proposals)
                    if (p.getTeacher() != null && p.getTeacher().getEmail().equalsIgnoreCase(t.getEmail()))
                        counter++;
                phrase.append("The teacher ").append(t.getName()).append(" has ").append(counter).append(" assigned proposals\n");
            }
            return phrase.toString();
        }
        return "";
    }

    @Override
    public void exportProposals(String filename, ArrayList<Proposal> proposals) throws IOException {
        FileWriter csvWriter = null;
        try {
            File file = new File(filename);
            if(!file.exists()) {
                csvWriter = new FileWriter(file);
                int count = 0;

                for (Proposal p : proposals) {
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
                    if (count < proposals.size())
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
    private static boolean isExistentTeacher(String email, ArrayList<Teacher> teachers){
        for(Teacher t : teachers)
            if(t.getEmail().equalsIgnoreCase(email))
                return true;
        return false;
    }
    private static boolean isAValidProposal(String id, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if(id.equalsIgnoreCase(p.getIdentification()))
                return true;
        return false;
    }
    private static boolean isAProposalAssigned(String id, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if (p.getIdentification().equalsIgnoreCase(id))
                if(p.getTeacher() != null)
                    return true;
        return false;
    }

}
