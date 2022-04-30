package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProposalsState implements Serializable{
    public static final long serialVersionUID=2020129026;

    public static String addProposals(String filename, ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Teacher> teachers) throws IOException {
        BufferedReader br = null;
        filename="src/csvFiles/proposals.csv";
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
                    if(isExistentProposal(tempArr.get(1),proposals))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", already exists\n");
                    else if(isAInvalidIndustryAcronymList(area))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", has a invalid industry acronym\n");
                    else
                        proposals.add(new ProposalIntership(tempArr.get(1), tempArr.get(3), area, tempArr.get(4)));
                }

                //For all T2 proposals
                else if(tempArr.get(0).equalsIgnoreCase("T2")){
                    //Get the student if exists
                    Student auxStudent = null;
                    if(tempArr.size()==6)
                        for(Student s : students)
                            if(s.getStudentNumber() == Long.parseLong(tempArr.get(5)))
                                auxStudent=s;
                    //Get the teacher
                    Teacher auxTeacher = null;
                    for(Teacher teacher : teachers)
                        if(teacher.getEmail().equalsIgnoreCase(tempArr.get(4)))
                            auxTeacher=teacher;

                    List<String> area = Arrays.asList(tempArr.get(2).split("\\|"));
                    if(isExistentProposal(tempArr.get(1),proposals))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", already exists\n");
                    else if(isAInvalidIndustryAcronymList(area))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", has invalid industry acronym\n");
                    else if(auxTeacher==null)
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", has a invalid teacher\n");
                    else
                        proposals.add(new ProposalProject(tempArr.get(1), tempArr.get(3), area, auxTeacher, auxStudent));
                }

                //For all T3 proposals
                else if(tempArr.get(0).equalsIgnoreCase("T3")){
                    if(isExistentProposal(tempArr.get(1),proposals))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", already exists\n");
                    else if(isARepeatStudent(Long.parseLong(tempArr.get(3)), proposals))
                        warnings.append("The proposal with code ").append(tempArr.get(1)).append(", has a invalid student, this student already has an associated proposal\n");
                    else {
                        Student auxStudent=null;
                        for(Student s : students)
                            if(s.getStudentNumber() == Long.parseLong(tempArr.get(3)))
                                auxStudent=s;
                        proposals.add(new ProposalSelfProposed(tempArr.get(1), tempArr.get(2), auxStudent));
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

    public static String deleteProposals(String id, ArrayList<Proposal> proposals){
        if(!isExistentProposal(id,proposals))
            return "The proposal with code "+id+", doesn't exists\n";

        proposals.removeIf(p -> p.getIdentification().equalsIgnoreCase(id));
        return "";
    }

    public static String showProposals(ArrayList<Proposal> proposals){
        StringBuilder s = new StringBuilder();
        for (Proposal p:proposals)
            if(p.getType().equalsIgnoreCase("T1"))
                s.append(String.format("Proposal ID: %-5s Type: [%s]Internship   Title: %-50s Area: %-13s Host Entity: %-30s \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),p.getHostEntity()));
            else if(p.getType().equalsIgnoreCase("T2") && p.getStudent()!=null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]Project      Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s  \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),p.getStudent().getName(),p.getStudent().getStudentNumber(),p.getTeacher().getName(),p.getTeacher().getEmail()));
            else if(p.getType().equalsIgnoreCase("T2") && p.getStudent()==null)
                s.append(String.format("Proposal ID: %-5s Type: [%s]Project      Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10s Assigned Teacher: %-30s with Email: %-30s  \n",p.getIdentification(),p.getType(),p.getTitle(),p.getArea(),"empty","empty",p.getTeacher().getName(),p.getTeacher().getEmail()));
            else if(p.getType().equalsIgnoreCase("T3"))
                s.append(String.format("Proposal ID: %-5s Type: [%s]SelfProposed Title: %-50s                     Assigned Student: %-30s with Number: %-10s  \n",p.getIdentification(),p.getType(),p.getTitle(),"empty","empty"));
        return s.toString();
    }

    public static void exportProposals(String filename, ArrayList<Proposal> proposals) throws IOException {
        filename = "src/csvFiles/exportProposals.csv";
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

    private static String getStringIndustryAcronym(List<String> area){
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
    private static boolean isExistentProposal(String id, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
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

    private static boolean isARepeatStudent(long number, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if(p.getStudent() != null)
                if(number == p.getStudent().getStudentNumber())
                    return true;
        return false;
    }
}