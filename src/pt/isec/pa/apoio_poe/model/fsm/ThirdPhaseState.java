package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThirdPhaseState extends PhaseStateAdapter {
    public static final long serialVersionUID=2020129026;
    private boolean isClosed;

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
    @Override
    public String closePhase(ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Application> applications, boolean value) {
        int counter=0;
        for(Application a : applications)
            for(Proposal p : proposals)
                if(p.getStudent()!=null)
                    if(p.getStudent().getStudentNumber() == a.getStudentNumber().getStudentNumber())
                        counter++;
        if(counter != applications.size())
            return "There are still students who applied and still don't have a proposal assigned\n";
        isClosed=true;
        return "";
    }
    @Override
    public boolean isClosed() { return isClosed; }

    @Override
    public String assignAProposalWithoutAssignments(ArrayList<Application> applications, ArrayList<Student> students, ArrayList<Proposal> proposals){
        //Extract the students

        ArrayList<Student> studentsOrder = new ArrayList<>();
        for(Application a : applications)
            for(Student s : students)
                if(s.getStudentNumber()==a.getStudentNumber().getStudentNumber())
                    studentsOrder.add(s);

        //Sort students by classification
        for (int i = 0; i < studentsOrder.size(); i++)
            for (int j = i+1; j < studentsOrder.size(); j++)
                if(studentsOrder.get(i).getClassification() > studentsOrder.get(j).getClassification()) {
                    Student temp = studentsOrder.get(i);
                    studentsOrder.set(i,studentsOrder.get(j));
                    studentsOrder.set(j,temp);
                }
        Collections.reverse(studentsOrder);

        int counter;
        List<String> listProposals;
        for(Student s : studentsOrder)
            for(Application a : applications)
                if(s.getStudentNumber() == a.getStudentNumber().getStudentNumber()) {
                    counter=0;
                    listProposals = a.getIdProposals();
                        for(Proposal p : proposals)
                            if (p.getIdentification().equalsIgnoreCase(listProposals.get(counter)))
                                if (p.getStudent() == null)
                                    p.setStudent(s);
                                else
                                    counter++;
                }

        return "";
    }

    @Override
    public String associateProposalToStudents(String proposal, Long student, ArrayList<Proposal> proposals, ArrayList<Student> students){
        if(!isAValidProposal(proposal,proposals))
            return "This proposal doesn't exist\n";
        if(!isExistentStudent(student,students))
            return "This student doesn't exist\n";
        if(isAProposalAssigned(proposal,proposals))
            return "This proposal is already associated to another student\n";

        for(Proposal p : proposals)
            if(proposal.equalsIgnoreCase(p.getIdentification()))
                for(Student s : students)
                    if(s.getStudentNumber() == student)
                        p.setStudent(s);
        return "";
    }

    @Override
    public  String removeStudentFromProposal(String proposal, ArrayList<Proposal> proposals){
        if(!isAValidProposal(proposal,proposals))
            return "This proposal doesn't exist\n";
        if(!isAProposalAssigned(proposal,proposals))
            return "This proposal doesn't yet have a student assigned\n";
        if(!isAValidProposalToRemove(proposal,proposals))
            return "Cannot remove student from this proposal\n";

        for(Proposal p : proposals)
            if(p.getIdentification().equalsIgnoreCase(proposal))
                p.setStudent(null);

        return "";
    }

    @Override
    public String generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed, ArrayList<Student> students, ArrayList<Proposal> proposals){
        ArrayList<Student> listStudents = new ArrayList<>();

        if(associatedSelfProposed) {
            for (Proposal p : proposals)
                if (p.getType().equalsIgnoreCase("T3"))
                    listStudents.add(p.getStudent());
        }else if(alreadyRegistered)
            return "";
        else if(proposalAssigned) {
            for (Proposal p : proposals)
                if (p.getStudent()!=null)
                    listStudents.add(p.getStudent());
        }else if(anyProposalAttributed) {
            listStudents.addAll(students);
            for (Proposal p : proposals)
                if (p.getStudent() != null)
                    listStudents.remove(p.getStudent());
        }

        return StudentState.showStudents(listStudents);
    }

    @Override
    public String generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals, ArrayList<Proposal> proposals){
        ArrayList<Proposal> proposalList = new ArrayList<>();

        if(selfProposed)
            for(Proposal p : proposals)
                if(p.getType().equalsIgnoreCase("T3"))
                    proposalList.add(p);

        if(proposeTeacher)
            for(Proposal p : proposals)
                if(p.getType().equalsIgnoreCase("T2"))
                    proposalList.add(p);

        if(withProposals)
            for (Proposal p : proposals)
                if (p.getStudent()!=null)
                    proposalList.add(p);

        if(withoutProposals) {
            proposalList.addAll(proposals);
            for (Proposal p : proposals)
                if (p.getStudent() != null)
                    proposalList.remove(p);
        }

        return ProposalsState.showProposals(proposalList);
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
    private static boolean isExistentStudent(long number, ArrayList<Student> students){
        for(Student s : students)
            if(s.getStudentNumber() == number)
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
                if(p.getStudent()!=null)
                    return true;
        return false;
    }
    private static boolean isAValidProposalToRemove(String id, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if (p.getIdentification().equalsIgnoreCase(id))
                if(p.getType().equalsIgnoreCase("T3") || (p.getType().equalsIgnoreCase("T2") &&p.getStudent()!=null))
                    return false;
        return true;
    }
}
