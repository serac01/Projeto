package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondPhaseState extends PhaseStateAdapter {
    SecondPhaseState(PhaseContext context, Phase phase) {
        super(context, phase);
        phase.setCurrentPhase(2);
    }

    @Override
    public boolean previousPhase() {
        changeState(new FirstPhaseState(context, phase));
        return true;
    }

    @Override
    public boolean nextPhase() {
        changeState(new ThirdPhaseState(context, phase));
        return true;
    }

    @Override
    public PhaseState getState() {
        return PhaseState.PHASE_2;
    }


    /************************************************** Applications **************************************************/
    public static ArrayList<Application> addApplication(String filename, ArrayList<Application> applications, ArrayList<Proposal> proposals) throws IOException {
        BufferedReader br = null;
        filename="src/csvFiles/eApplication.csv";
        try {
            FileReader fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String line;
            while((line=br.readLine()) != null) {
                List<String> tempArr = Arrays.asList(line.split(","));
                List<String> idApplications = new ArrayList<>();
                for (int i=1; i<tempArr.size(); i++)
                    idApplications.add(tempArr.get(i));
                //Valida os parametros de entrada
                if(tempArr.size()<2 || isAProposalSelfProposed(idApplications, proposals) || isAProposalAssigned(Long.parseLong(tempArr.get(0)), proposals))
                    System.out.println("The student with code " + tempArr.get(0) + ", entered wrong data");
                else {
                    Application application = new Application(Long.parseLong(tempArr.get(0)), idApplications);
                    applications.add(application);
                }
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }
        return applications;
    }

    /*public static ArrayList<Student> editApplication(long number, String toUpdate, int option, ArrayList<Student> students)  {
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

    public static ArrayList<Student> deleteApplication(long number, ArrayList<Student> students){
        if(!isExistentStudent(number,students))
            return null;
        students.removeIf(s -> s.getStudentNumber() == number);
        return students;
    }*/

    public static void showApplication(ArrayList<Application> applications){ applications.forEach((n) -> System.out.println(n.toString())); }


    /************************************************** Validations **************************************************/
    private static boolean isAProposalAssigned(long number, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if (number == p.getStudentNumber())
                return true;

        return false;
    }

    private static boolean isAProposalSelfProposed(List<String> selfProposed, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            for(String s : selfProposed)
                if (s.equalsIgnoreCase(p.getIdentification()) && p.getType().equalsIgnoreCase("T3"))
                    return true;

        return false;
    }

}