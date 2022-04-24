package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirstPhaseState extends PhaseStateAdapter {

    FirstPhaseState(PhaseContext context, Phase phase){
        super(context,phase);
        phase.setCurrentPhase(1);
    }

    @Override
    public boolean nextPhase() {
        changeState(new SecondPhaseState(context,phase));
        return true;
    }

    @Override
    public boolean closePhase(ArrayList<Proposal> proposals, ArrayList<Student> students) {
        if(students.isEmpty() || proposals.isEmpty())
            return false;

        List<String> industryAcronym;
        int countSI=0, countDA=0, countRAS=0, studentsSI=0,studentsDA=0,studentsRAS=0;
        //Count the number of proposals
        for(Proposal p : proposals) {
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
        for(Student s : students) {
            if (s.getIndustryAcronym().equalsIgnoreCase("SI"))
                studentsSI++;
            else if (s.getIndustryAcronym().equalsIgnoreCase("DA"))
                studentsDA++;
            else if (s.getIndustryAcronym().equalsIgnoreCase("RAS"))
                studentsRAS++;
        }

        return countSI >= studentsSI && countDA >= studentsDA && countRAS >= studentsRAS;
    }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_1; }

    /************************************************** Students **************************************************/
    public static ArrayList<Student> addStudents(String filename, ArrayList<Student> students) throws IOException {
        BufferedReader br = null;
        filename="src/csvFiles/students.csv";
        try {
            FileReader fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String line;
            while((line=br.readLine()) != null) {
                List<String> tempArr = Arrays.asList(line.split(","));
                //Valida os parametros de entrada
                if(isExistentStudent(Long.parseLong(tempArr.get(0)), students) || !isACourseAcronym(tempArr.get(3)) || !isAIndustryAcronym(tempArr.get(4)) || !isAValidClassification(Double.parseDouble(tempArr.get(5))) || !isAValidBollean(tempArr.get(6)))
                    System.out.println("The student with code " + tempArr.get(0) + ", has invalid or duplicated data");
                else {
                    Student student = new Student(Long.parseLong(tempArr.get(0)), tempArr.get(1), tempArr.get(2), tempArr.get(3), tempArr.get(4),Double.parseDouble(tempArr.get(5)), Boolean.parseBoolean(tempArr.get(6)));
                    students.add(student);
                }
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }
        return students;
    }

    public static ArrayList<Student> editStudent(long number, String toUpdate, int option, ArrayList<Student> students)  {
        if(!isExistentStudent(number,students))
            return null;

        for(Student s : students)
            if(s.getStudentNumber()==number)
                switch (option){
                    case 1 -> {
                        s.setName(toUpdate);
                        return students;
                    }
                    case 2 -> {
                        if(isACourseAcronym(toUpdate)) {
                            s.setCourseAcronym(toUpdate);
                            return students;
                        }
                    }
                    case 3 -> {
                        if(isAIndustryAcronym(toUpdate)) {
                            s.setIndustryAcronym(toUpdate);
                            return students;
                        }
                    }
                    case 4 -> {
                        if(isAValidClassification(Double.parseDouble(toUpdate))) {
                            s.setClassification(Double.parseDouble(toUpdate));
                            return students;
                        }
                    }
                    case 5 -> {
                        if(isAValidBollean(toUpdate)) {
                            s.setAccessInternships(Boolean.parseBoolean(toUpdate));
                            return students;
                        }
                    }
                }
        return null;
    }

    public static ArrayList<Student> deleteStudents(long number, ArrayList<Student> students){
        if(!isExistentStudent(number,students))
            return null;
        students.removeIf(s -> s.getStudentNumber() == number);
        return students;
    }

    public static void showStudents(ArrayList<Student> students){ students.forEach((n) -> System.out.println(n.toString())); }


    /************************************************** Teachers **************************************************/
    public static ArrayList<Teacher> addTeacher(String filename, ArrayList<Teacher> teachers) throws IOException {
        BufferedReader br = null;
        filename="src/csvFiles/teachers.csv";
        try {
            FileReader fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String line;
            while((line=br.readLine()) != null) {
                List<String> tempArr = Arrays.asList(line.split(","));
                //Valida os parametros de entrada
                if(isExistentTeacher(tempArr.get(1), teachers))
                    System.out.println("The teacher " + tempArr.get(0) + ", has an invalid email");
                else {
                    Teacher teacher = new Teacher(tempArr.get(0), tempArr.get(1));
                    teachers.add(teacher);
                }
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }
        return teachers;
    }

    public static ArrayList<Teacher> editTeacher(String email, String toUpdate, ArrayList<Teacher> teachers)  {
        if(!isExistentTeacher(email,teachers))
            return null;

        for(Teacher t : teachers)
            if(email.equalsIgnoreCase(t.getEmail())) {
                t.setName(toUpdate);
                return teachers;
            }
        return null;
    }

    public static ArrayList<Teacher> deleteTeacher(String email, ArrayList<Teacher> teachers){
        if(!isExistentTeacher(email,teachers))
            return null;
        else
            teachers.removeIf(t -> t.getEmail().equalsIgnoreCase(email));
        return teachers;
    }

    public static void showTeachers(ArrayList<Teacher> teachers){ teachers.forEach((n) -> System.out.println(n.toString())); }


    /************************************************** Proposals **************************************************/
    public static ArrayList<Proposal> addProposals(String filename, ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Teacher> teachers) throws IOException {
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
                    if(!isAIndustryAcronymList(area) || isExistentProposal(tempArr.get(1),proposals))
                        System.out.println("The proposal with code " + tempArr.get(1) + ", has invalid or duplicated data");
                    else {
                        ProposalIntership proposalIntership = new ProposalIntership(tempArr.get(1), tempArr.get(3), area, tempArr.get(4));
                        proposals.add(proposalIntership);
                    }
                }
                else if(tempArr.get(0).equalsIgnoreCase("T2")){
                    long number=0;
                    if(tempArr.size()==6)
                        number=Long.parseLong(tempArr.get(5));
                    List<String> area = Arrays.asList(tempArr.get(2).split("\\|"));
                    if(!isAIndustryAcronymList(area) || isExistentProposal(tempArr.get(1),proposals) || !isExistentTeacher(tempArr.get(4),teachers))
                        System.out.println("The proposal with code " + tempArr.get(1) + ", has invalid or duplicated data");
                    else {
                        ProposalProject proposalProject = new ProposalProject(tempArr.get(1), tempArr.get(3), area, tempArr.get(4), number);
                        proposals.add(proposalProject);
                    }
                }
                else if(tempArr.get(0).equalsIgnoreCase("T3")){
                    if(isExistentProposal(tempArr.get(1),proposals) || isARepeatSelfProposedStudent(Long.parseLong(tempArr.get(3)), proposals))
                        System.out.println("The proposal with code " + tempArr.get(1) + ", has invalid or duplicated data");
                    else {
                        ProposalSelfProposed proposalSelfProposed = new ProposalSelfProposed(tempArr.get(1), tempArr.get(2), Long.parseLong(tempArr.get(3)));
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
        return proposals;
    }

    public static ArrayList<Proposal> editProposals(String id, String toUpdate, int option, ArrayList<Proposal> proposals)  {
        if(!isExistentProposal(id,proposals))
            return null;

        for(Proposal p : proposals)
            if(id.equalsIgnoreCase(p.getIdentification()))
                switch (option){
                    case 1 -> {
                        p.setTitle(toUpdate);
                        return proposals;
                    }
                }
        return null;
    }

    public static ArrayList<Proposal> deleteProposals(String id, ArrayList<Proposal> proposals){
        if(!isExistentProposal(id,proposals))
            return null;
        else
            proposals.removeIf(p -> p.getIdentification().equalsIgnoreCase(id));
        return proposals;
    }

    public static void showProposals(ArrayList<Proposal> proposals){ proposals.forEach((n) -> System.out.println(n.toString())); }


    /************************************************** Validations **************************************************/
    private static boolean isExistentStudent(long number, ArrayList<Student> students){
        for(Student s : students)
            if(s.getStudentNumber()==number)
                return true;
        return false;
    }

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

    private static boolean isACourseAcronym(String courseAcronym){ return courseAcronym.equalsIgnoreCase("LEI-PL") || courseAcronym.equalsIgnoreCase("LEI"); }

    private static boolean isAIndustryAcronym(String industryAcronym){ return industryAcronym.equalsIgnoreCase("SI") || industryAcronym.equalsIgnoreCase("DA") || industryAcronym.equalsIgnoreCase("RAS"); }

    private static boolean isAIndustryAcronymList(List<String> industryAcronym){
        int count=0;
        for(String s : industryAcronym)
            if(s.equalsIgnoreCase("SI") || s.equalsIgnoreCase("DA") || s.equalsIgnoreCase("RAS"))
                count++;

        return count == industryAcronym.size();
    }

    private static boolean isAValidClassification(double classification){ return classification>=0 && classification<=1; }

    private static boolean isAValidBollean(String accessInternships){ return accessInternships.equalsIgnoreCase("true") || accessInternships.equalsIgnoreCase("false"); }

    private static boolean isARepeatSelfProposedStudent(long number, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if(number == p.getStudentNumber())
                return true;

        return false;
    }


    private static boolean isProposalIntership(Proposal p){
        if(p.getType().equalsIgnoreCase("T1"))
            return true;
        return false;
    }

    private static boolean isProposalProject(Proposal p){
        if(p.getType().equalsIgnoreCase("T2"))
            return true;
        return false;
    }

    private static boolean isProposalSelfProposed(Proposal p){
        if(p.getType().equalsIgnoreCase("T3"))
            return true;
        return false;
    }


}
