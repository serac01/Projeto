package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondPhaseState extends PhaseStateAdapter{
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

    @Override
    public ArrayList<Application> addApplication(String filename, ArrayList<Application> applications, ArrayList<Proposal> proposals, ArrayList<Student> students) throws IOException {
        BufferedReader br = null;
        filename="src/csvFiles/application.csv";
        try {
            FileReader fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String line;
            while((line=br.readLine()) != null) {
                List<String> tempArr = Arrays.asList(line.split(","));
                List<String> idProposals = new ArrayList<>();
                for (int i=1; i<tempArr.size(); i++)
                    idProposals.add(tempArr.get(i));
                //Valida os parametros de entrada
                if(tempArr.size()<2 || hasProposalAlreadyAStudent(idProposals, proposals) || isAProposalAssigned(Long.parseLong(tempArr.get(0)), proposals) || !isExistentStudent(Long.parseLong(tempArr.get(0)), students))
                    System.out.println("The student with code " + tempArr.get(0) + ", entered wrong data");
                else if(isExistentApplication(Long.parseLong(tempArr.get(0)),applications))
                    System.out.println("The application of student with code " + tempArr.get(0) + ", has duplicated data or two or has two iterations");
                else {
                    Application application = new Application(Long.parseLong(tempArr.get(0)), idProposals);
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

    @Override
    public ArrayList<Application> editApplication(long number, String id, int option, ArrayList<Application> applications, ArrayList<Proposal> proposals)  {
        if(!isExistentApplication(number,applications))
            return null;

        for(Application a : applications)
            if(a.getStudentNumber()==number)
                switch (option){
                    case 1 -> {
                        for(Proposal p : proposals) {
                            List<String> aux = a.getIdProposals();
                            if (p.getIdentification().equalsIgnoreCase(id) && !isADuplicateProposal(id,aux) && isAValidProposal(id,proposals) && !hasProposalAlreadyAStudent(aux,proposals)) {
                                aux.add(id);
                                a.setIdProposals(aux);
                                return applications;
                            }
                        }
                    }
                    case 2 -> {
                        for(Proposal p : proposals) {
                            List<String> aux = a.getIdProposals();
                            if (p.getIdentification().equalsIgnoreCase(id) && aux.size()>1) {
                                aux.remove(id);
                                a.setIdProposals(aux);
                                return applications;
                            }
                        }
                    }
                }
        return null;
    }

    @Override
    public ArrayList<Application> deleteApplication(long number, ArrayList<Application> applications){
        if(!isExistentApplication(number,applications))
            return null;
        applications.removeIf(a -> a.getStudentNumber() == number);
        return applications;
    }

    @Override
    public void showApplication(ArrayList<Application> applications){ applications.forEach((n) -> System.out.println(n.toString())); }

    @Override
    public void exportApplications(String filename, ArrayList<Application> applications) throws IOException {
        filename = "src/csvFiles/exportApplications.csv";
        FileWriter csvWriter = null;
        try {
            File file = new File(filename);
            if(!file.exists()) {
                csvWriter = new FileWriter(file);
                int count = 0;
                for (Application a : applications) {
                    csvWriter.append(String.valueOf(a.getStudentNumber()));
                    csvWriter.append(",");
                    StringBuilder stringBuilder = new StringBuilder(20);
                    int countIdProposals=0;
                    for(String s : a.getIdProposals()) {
                        stringBuilder.append(s);
                        countIdProposals++;
                        if(countIdProposals < a.getIdProposals().size())
                            stringBuilder.append(",");
                    }
                    csvWriter.append(stringBuilder);
                    if (count < applications.size())
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

    /************************************************** Obter lista de alunos **************************************************/
    @Override
    public void generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, ArrayList<Proposal> proposals, ArrayList <Application> applications, ArrayList <Student> students){
        ArrayList<Long> studentList = new ArrayList<Long>();
        int count=0;

        if(selfProposed)
            for(Proposal p : proposals)
                if(p.getType().equalsIgnoreCase("T3") && !isExistentStudent(p.getStudentNumber(),studentList))
                    studentList.add(p.getStudentNumber());

        if(alreadyRegistered)
            for(Application a : applications)
                if(!isExistentStudent(a.getStudentNumber(),studentList))
                    studentList.add(a.getStudentNumber());

        if(withoutRegistered)
            for(Student s : students) {
                count=0;
                for (Application a : applications) {
                    if (a.getStudentNumber() != s.getStudentNumber())
                        count++;
                    if (count == applications.size() && !isExistentStudent(s.getStudentNumber(),studentList))
                        studentList.add(s.getStudentNumber());
                }
            }

        for(Long l : studentList)
            System.out.print(l+" ");
    }

    @Override
    public void generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications,ArrayList<Proposal> proposals, ArrayList<Application> applications){
        ArrayList<String> proposalList = new ArrayList<>();
        boolean exist = false;

        if(selfProposed)
            for(Proposal p : proposals)
                if(p.getType().equalsIgnoreCase("T3"))
                    proposalList.add(p.getIdentification());

        if(proposeTeacher)
            for(Proposal p : proposals)
                if(p.getType().equalsIgnoreCase("T2"))
                    proposalList.add(p.getIdentification());

        if(withApplications)
            for(Proposal p : proposals)
                for(Application a : applications)
                    for (String s : a.getIdProposals())
                        if(s.equalsIgnoreCase(p.getIdentification()))
                            proposalList.add(p.getIdentification());

        if(withoutApplications) {
            List<String> aux = new ArrayList<>();
            for (Application a : applications)
                aux.addAll(a.getIdProposals());

            for (Proposal p : proposals) {
                for (String s : aux)
                    if (s.equalsIgnoreCase(p.getIdentification())) {
                        exist = true;
                        break;
                    }

                if(!exist)
                    proposalList.add(p.getIdentification());
                exist=false;
            }
        }

        for(String s : proposalList)
            System.out.print(s+" ");
    }

    /************************************************** Validations **************************************************/
    private static boolean isExistentStudent(long number, ArrayList<Long> students){
        for(Long s : students)
            if(s == number)
                return true;
        return false;
    }

    private static boolean isAProposalAssigned(Long number, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if (number == p.getStudentNumber())
                return true;
        return false;
    }

    private static boolean isExistentApplication(Long number, ArrayList<Application> applications){
        for(Application a : applications)
            if(number==a.getStudentNumber())
                return true;
        return false;
    }

    private static boolean hasProposalAlreadyAStudent(List<String> idProposals, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            for(String s : idProposals)
                if (s.equalsIgnoreCase(p.getIdentification()) && (p.getType().equalsIgnoreCase("T3") || p.getStudentNumber()!=0))
                    return true;
        return false;
    }

    private static boolean isExistentStudent(Long number, ArrayList<Student> students){
        for(Student s : students)
            if(number==s.getStudentNumber())
                return true;
        return false;
    }

    private static boolean isAValidProposal(String id, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if(id.equalsIgnoreCase(p.getIdentification()))
                return true;
        return false;
    }

    private static boolean isADuplicateProposal(String id, List<String> idProposals){
        for(String s : idProposals)
            if (s.equalsIgnoreCase(id))
                return true;
        return false;
    }
}