package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProposalsState extends PhaseStateAdapter implements Serializable {
    public static final long serialVersionUID=2020129026;

    ProposalsState(PhaseContext context){ super(context); }

    //State
    @Override
    public void nextPhase() { changeState(new FirstPhaseState(context)); }
    @Override
    public PhaseState getState() { return PhaseState.PROPOSAL_PHASE; }
    @Override
    public boolean isPhaseClosed(ManagementPoE management){ return management.isProposalPhaseClosed(); }

    //others methods
    public String addProposals(String filename, ManagementPoE management) throws IOException {
        filename="src/csvFiles/proposals.csv";
        BufferedReader br = null;
        StringBuilder warnings = new StringBuilder();
        try {
            FileReader fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String line;
            while((line=br.readLine()) != null) {
                List<String> tempArr = Arrays.asList(line.split(","));

                //For all T1 proposals
                if(tempArr.get(0).equalsIgnoreCase("T1")){
                    List<String> area = Arrays.asList(tempArr.get(2).split("\\|"));
                    if(isExistentProposal(tempArr.get(1),management))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", already exists\n");
                    else if(isAInvalidIndustryAcronymList(area))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", has a invalid industry acronym\n");
                    else
                        management.addProposal(new ProposalIntership(tempArr.get(1), tempArr.get(3), area, tempArr.get(4),null,null));
                }

                //For all T2 proposals
                else if(tempArr.get(0).equalsIgnoreCase("T2")){
                    //Get the student if exists
                    Student auxStudent = null;
                    if(tempArr.size()==6)
                        for(Student s : management.getStudent())
                            if(s.getStudentNumber() == Long.parseLong(tempArr.get(5)))
                                auxStudent=s;
                    //Get the teacher
                    Teacher auxTeacher = null;
                    for(Teacher teacher : management.getTeachers())
                        if(teacher.getEmail().equalsIgnoreCase(tempArr.get(4)))
                            auxTeacher=teacher;

                    List<String> area = Arrays.asList(tempArr.get(2).split("\\|"));
                    if(isExistentProposal(tempArr.get(1),management))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", already exists\n");
                    else if(isAInvalidIndustryAcronymList(area))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", has invalid industry acronym\n");
                    else if(auxTeacher==null)
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", has a invalid teacher\n");
                    else
                        management.addProposal(new ProposalProject(tempArr.get(1), tempArr.get(3), area, auxTeacher, auxStudent));
                }

                //For all T3 proposals
                else if(tempArr.get(0).equalsIgnoreCase("T3")){
                    if(isExistentProposal(tempArr.get(1),management))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", already exists\n");
                    else if(isARepeatStudent(Long.parseLong(tempArr.get(3)), management))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", has a invalid student, this student already has an associated proposal\n");
                    else {
                        Student auxStudent=null;
                        for(Student s : management.getStudent())
                            if(s.getStudentNumber() == Long.parseLong(tempArr.get(3)))
                                auxStudent=s;
                        management.addProposal(new ProposalSelfProposed(tempArr.get(1), tempArr.get(2), auxStudent,null));
                    }
                }
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }
        return warnings.toString();
    }

    public String deleteProposals(String id, ManagementPoE management){
        if(!isExistentProposal(id,management))
            return "The proposal with code "+id+", doesn't exists\n";
        for(Application a: management.getApplications())
            for(String s: a.getIdProposals())
                if(s.equalsIgnoreCase(id))
                    return "Impossible to delete Proposal due to existing relation\n";
        management.deleteProposalFromList(id);
        return "";
    }

    public List<String> showProposals(ManagementPoE management){
        List<String> list = new ArrayList<>();
        for (Proposal p: management.getProposals())
            list.add(p.toString());
        return list;
    }

    public void exportProposals(String filename, ManagementPoE management) throws IOException {
        FileWriter csvWriter = null;
        try {
            File file = new File(filename);
            if(!file.exists()) {
                csvWriter = new FileWriter(file);
                int count = 0;

                for (Proposal p : management.getProposals()) {
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
                    }
                    count++;
                    if (count < management.getProposals().size())
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
    private static boolean isExistentProposal(String id, ManagementPoE management){
        for(Proposal p : management.getProposals())
            if(id.equalsIgnoreCase(p.getIdentification()))
                return true;
        return false;
    }

    private static boolean isAInvalidIndustryAcronymList(List<String> industryAcronym){
        int count=0;
        for(String s : industryAcronym)
            if(s.equalsIgnoreCase("SI") || s.equalsIgnoreCase("DA") || s.equalsIgnoreCase("RAS"))
                count++;

        return count != industryAcronym.size();
    }

    private static boolean isARepeatStudent(long number, ManagementPoE management){
        for(Proposal p : management.getProposals())
            if(p.getStudent() != null)
                if(number == p.getStudent().getStudentNumber())
                    return true;
        return false;
    }
}