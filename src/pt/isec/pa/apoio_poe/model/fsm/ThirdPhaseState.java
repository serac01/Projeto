package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ThirdPhaseState extends PhaseStateAdapter {
    public static final long serialVersionUID=2020129026;

    ThirdPhaseState(PhaseContext context){ super(context);  }

    @Override
    public void previousPhase() {
        changeState(new SecondPhaseState(context));
    }
    @Override
    public void nextPhase() {
        changeState(new FourthPhaseState(context));
    }
    @Override
    public PhaseState getState() { return PhaseState.PHASE_3; }
    @Override
    public String closePhase(DataPoE data) {
        int counter=0;
        for(Application a : data.getApplications())
            for(Proposal p : data.getProposals())
                if(p.getStudent()!=null)
                    if(p.getStudent().getStudentNumber() == a.getStudent().getStudentNumber())
                        counter++;
        if(counter != data.getApplications().size())
            return "There are still students who applied and still don't have a proposal assigned\n";
        data.setPhase3Closed(true);
        nextPhase();
        return "";
    }
    @Override
    public boolean isPhaseClosed(DataPoE data){ return data.isPhase3Closed(); }

    @Override
    public String assignAProposalWithoutAssignments(DataPoE data){
        ArrayList<Proposal> proposalsWithoutAssignments = new ArrayList<>();
        ArrayList<Student> studentsWithoutAssignments = new ArrayList<>(data.getStudent());
        double classification;
        String proposalId="", currentProposalId="";
        int repeatedClassification=0,repeatedProposal=0;

        //Available proposals
        for(Proposal p : data.getProposals())
            if(p.getStudent()==null)
                proposalsWithoutAssignments.add(p);

        //Available students
        for(Student s : data.getStudent())
            for(Proposal p : data.getProposals())
                if(p.getStudent()!=null && p.getStudent().getStudentNumber() == s.getStudentNumber())
                    studentsWithoutAssignments.remove(s);

        //Sort available students by their classifications
        //studentsWithoutAssignments.sort((o1, o2) -> Double.compare(o2.getClassification(), o1.getClassification()));

        System.out.println("\n\nFree Proposals:");
        for(Proposal p : proposalsWithoutAssignments)
            System.out.println(p.toString());
        System.out.println("\n\nFree Students:");
        for(Student s : studentsWithoutAssignments)
            System.out.println(s.toString());
        System.out.println("\n\nApplications:");
        for(Application a : data.getApplications())
            System.out.println(a.toString());

        //student, proposal
        classification = studentsWithoutAssignments.get(0).getClassification();
        for(Application a : data.getApplications())
            if(a.getStudent().getStudentNumber() ==  studentsWithoutAssignments.get(0).getStudentNumber()) {
                proposalId=a.getIdProposals().get(0);
                break;
            }
        for(Student s : studentsWithoutAssignments) {
            for(Application a : data.getApplications())
                if(a.getStudent().getStudentNumber() == s.getStudentNumber()) {
                    currentProposalId = a.getIdProposals().get(0);
                    break;
                }
            if (classification == s.getClassification() && proposalId.equalsIgnoreCase(currentProposalId)) {
                repeatedClassification++;
                proposalId=currentProposalId;
            }
            else {
                if(repeatedClassification==1) {
                    for (Proposal p : proposalsWithoutAssignments)
                        if (p.getIdentification().equalsIgnoreCase(currentProposalId))
                            p.setStudent(s);
                }else{
                    System.out.println("\n\nExistem "+repeatedClassification+" estudantes com a clasificação "+classification+" repetidos e proposata: "+currentProposalId);
                    repeatedClassification = 1;
                    classification = s.getClassification();
                }
            }
        }
        System.out.println("Existem "+repeatedClassification+" estudantes com a clasificação "+classification+" repetidos e proposata: "+currentProposalId);
        return "";
    }

    @Override
    public String associateProposalToStudents(String proposal, Long student, DataPoE data){
        if(!isAValidProposal(proposal,data))
            return "This proposal doesn't exist\n";
        if(!isExistentStudent(student,data))
            return "This student doesn't exist\n";
        if(isAProposalAssigned(proposal,data))
            return "This proposal is already associated to another student\n";

        for(Proposal p : data.getProposals())
            if(proposal.equalsIgnoreCase(p.getIdentification()))
                for(Student s : data.getStudent())
                    if(s.getStudentNumber() == student)
                        p.setStudent(s);
        return "";
    }

    @Override
    public  String removeStudentFromProposal(String proposal, DataPoE data){
        if(!isAValidProposal(proposal,data))
            return "This proposal doesn't exist\n";
        if(!isAProposalAssigned(proposal,data))
            return "This proposal doesn't yet have a student assigned\n";
        if(!isAValidProposalToRemove(proposal,data))
            return "Cannot remove student from this proposal\n";

        for(Proposal p : data.getProposals())
            if(p.getIdentification().equalsIgnoreCase(proposal))
                p.setStudent(null);

        return "";
    }

    @Override
    public List<String> generateListProposalStudents(boolean associatedSelfProposed, boolean alreadyRegistered, boolean proposalAssigned, boolean anyProposalAttributed, DataPoE data){
        ArrayList<String> listStudents = new ArrayList<>();

        if(associatedSelfProposed) {
            for (Proposal p : data.getProposals())
                if (p.getType().equalsIgnoreCase("T3"))
                    listStudents.add(p.getStudent().toString());
        }else if(alreadyRegistered)
            for (Application a : data.getApplications())
                listStudents.add(a.getStudent().toString());
        else if(proposalAssigned) {
            for (Proposal p : data.getProposals())
                if (p.getStudent()!=null)
                    listStudents.add(p.getStudent().toString());
        }else if(anyProposalAttributed) {
            for(Student s : data.getStudent())
                listStudents.add(s.toString());
            for (Proposal p : data.getProposals())
                if (p.getStudent() != null)
                    listStudents.remove(p.getStudent().toString());
        }
        return listStudents;
    }

    @Override
    public List<String> generateListProposalPhase3(boolean selfProposed, boolean proposeTeacher, boolean withProposals, boolean withoutProposals, DataPoE data){
        ArrayList<String> proposalList = new ArrayList<>();
        if(selfProposed)
            for(Proposal p : data.getProposals())
                if(p.getType().equalsIgnoreCase("T3"))
                    proposalList.add(p.toString());

        if(proposeTeacher)
            for(Proposal p : data.getProposals())
                if(p.getType().equalsIgnoreCase("T2"))
                    proposalList.add(p.toString());

        if(withProposals)
            for (Proposal p : data.getProposals())
                if (p.getStudent()!=null)
                    proposalList.add(p.toString());

        if(withoutProposals) {
            for(Proposal p : data.getProposals())
                proposalList.add(p.toString());
            for (Proposal p : data.getProposals())
                if (p.getStudent() != null)
                    proposalList.remove(p.toString());
        }
        return proposalList;
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
    private static boolean isExistentStudent(long number, DataPoE data){
        for(Student s : data.getStudent())
            if(s.getStudentNumber() == number)
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
                if(p.getStudent()!=null)
                    return true;
        return false;
    }
    private static boolean isAValidProposalToRemove(String id, DataPoE data){
        for(Proposal p : data.getProposals())
            if (p.getIdentification().equalsIgnoreCase(id))
                if(p.getType().equalsIgnoreCase("T3") || (p.getType().equalsIgnoreCase("T2") &&p.getStudent()!=null))
                    return false;
        return true;
    }
}
