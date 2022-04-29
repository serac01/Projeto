package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProposalsState {
    public static void addProposals(String filename, ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Teacher> teachers) throws IOException {
        BufferedReader br = null;
        filename="src/csvFiles/proposals.csv";
        try {
            FileReader fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String line;
            while((line=br.readLine()) != null) {
                List<String> tempArr = Arrays.asList(line.split(","));
                if(tempArr.get(0).equalsIgnoreCase("T1")){
                    List<String> area = Arrays.asList(tempArr.get(2).split("\\|"));
                    if(isAIndustryAcronymList(area) || isExistentProposal(tempArr.get(1),proposals))
                        System.out.println("The proposal with code " + tempArr.get(1) + ", has invalid or duplicated data");
                    else {
                        ProposalIntership proposalIntership = new ProposalIntership(tempArr.get(1), tempArr.get(3), area, tempArr.get(4));
                        proposals.add(proposalIntership);
                    }
                }
                else if(tempArr.get(0).equalsIgnoreCase("T2")){
                    Student stud=null;
                    if(tempArr.size()==6)
                        for(Student s : students)
                            if(s.getStudentNumber() == Long.parseLong(tempArr.get(5)))
                                stud=s;
                    List<String> area = Arrays.asList(tempArr.get(2).split("\\|"));
                    if(isAIndustryAcronymList(area) || isExistentProposal(tempArr.get(1),proposals) || !isExistentTeacher(tempArr.get(4),teachers))
                        System.out.println("The proposal with code " + tempArr.get(1) + ", has invalid or duplicated data");
                    else {
                        Teacher t=null;
                        for(Teacher teacher : teachers)
                            if(teacher.getEmail().equalsIgnoreCase(tempArr.get(4)))
                                t=teacher;
                        ProposalProject proposalProject = new ProposalProject(tempArr.get(1), tempArr.get(3), area, t, stud);
                        proposals.add(proposalProject);
                    }
                }
                else if(tempArr.get(0).equalsIgnoreCase("T3")){
                    if(isExistentProposal(tempArr.get(1),proposals) || isARepeatSelfProposedStudent(Long.parseLong(tempArr.get(3)), proposals))
                        System.out.println("The proposal with code " + tempArr.get(1) + ", has invalid or duplicated data");
                    else {
                        Student stud=null;
                        for(Student s : students)
                            if(s.getStudentNumber() == Long.parseLong(tempArr.get(3)))
                                stud=s;
                        ProposalSelfProposed proposalSelfProposed = new ProposalSelfProposed(tempArr.get(1), tempArr.get(2), stud);
                        proposals.add(proposalSelfProposed);
                    }
                }
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }
    }

    public static void editProposals(String id, String toUpdate, int option, ArrayList<Proposal> proposals)  {
        if(!isExistentProposal(id,proposals))
            return;

        for(Proposal p : proposals)
            if(id.equalsIgnoreCase(p.getIdentification()))
                switch (option){
                    case 1 -> p.setTitle(toUpdate);
                }
    }

    public static void deleteProposals(String id, ArrayList<Proposal> proposals){
        if(!isExistentProposal(id,proposals))
            return;

        proposals.removeIf(p -> p.getIdentification().equalsIgnoreCase(id));
    }

    public static void showProposals(ArrayList<Proposal> proposals){ proposals.forEach((n) -> System.out.println(n.toString())); }

    public static void exportProposals(String filename, ArrayList<Proposal> proposals) throws IOException {
        filename = "src/csvFiles/exportProposals.csv";
        FileWriter csvWriter = null;
        try {
            File file = new File(filename);
            if(!file.exists()) {
                csvWriter = new FileWriter(file);

                int count = 0;

                for (Proposal p : proposals) {
                    if(p.getType().equalsIgnoreCase("T1")){
                        StringBuilder stringBuilder = new StringBuilder(5);
                        int countAreaT1=0;
                        for(String s : p.getArea()) {
                            stringBuilder.append(s);
                            countAreaT1++;
                            if(countAreaT1 < p.getArea().size())
                                stringBuilder.append("|");
                        }
                        csvWriter.append(stringBuilder);
                        csvWriter.append(",");
                        csvWriter.append(p.getTitle());
                        csvWriter.append(",");
                        csvWriter.append(p.getHostEntity());
                    }else if(p.getType().equalsIgnoreCase("T2")){
                        csvWriter.append(p.getType());
                        csvWriter.append(",");
                        csvWriter.append(p.getIdentification());
                        csvWriter.append(",");
                        StringBuilder stringBuilder = new StringBuilder(5);
                        int countAreaT2=0;
                        for(String s : p.getArea()) {
                            stringBuilder.append(s);
                            countAreaT2++;
                            if(countAreaT2 < p.getArea().size())
                                stringBuilder.append("|");
                        }
                        csvWriter.append(stringBuilder);
                        csvWriter.append(",");
                        csvWriter.append(p.getTitle());
                        csvWriter.append(",");
                        csvWriter.append(p.getTeacher().getEmail());
                        if(p.getStudent()!=null){
                            csvWriter.append(",");
                            csvWriter.append(String.valueOf(p.getStudent().getStudentNumber()));
                        }
                    }else if(p.getType().equalsIgnoreCase("T3")){
                        csvWriter.append(p.getType());
                        csvWriter.append(",");
                        csvWriter.append(p.getIdentification());
                        csvWriter.append(",");
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

    //Validations
    private static boolean isExistentTeacher(String email, ArrayList<Teacher> teachers){
        for(Teacher t : teachers)
            if(email.equalsIgnoreCase(t.getEmail()))
                return true;
        return false;
    }

    private static boolean isExistentProposal(String id, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if(id.equalsIgnoreCase(p.getIdentification()))
                return true;
        return false;
    }

    private static boolean isAIndustryAcronymList(List<String> industryAcronym){
        int count=0;
        for(String s : industryAcronym)
            if(s.equalsIgnoreCase("SI") || s.equalsIgnoreCase("DA") || s.equalsIgnoreCase("RAS"))
                count++;

        return count != industryAcronym.size();
    }

    private static boolean isARepeatSelfProposedStudent(long number, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if(p.getStudent() != null)
                if(number == p.getStudent().getStudentNumber())
                    return true;

        return false;
    }
}