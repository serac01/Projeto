package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondPhaseState extends PhaseStateAdapter implements Serializable {
    public static final long serialVersionUID=2020129026;
    private boolean isClosed;

    SecondPhaseState(PhaseContext context) {
        super(context);
        isClosed=false;
    }

    //Sate
    @Override
    public boolean previousPhase() {
        changeState(new FirstPhaseState(context));
        return true;
    }
    @Override
    public boolean nextPhase() {
        changeState(new ThirdPhaseState(context));
        return true;
    }
    @Override
    public PhaseState getState() {
        return PhaseState.PHASE_2;
    }
    @Override
    public String closePhase(ArrayList<Proposal> proposals, ArrayList<Student> students, ArrayList<Application> applications, boolean value) {
        if(!value)
            return "The previous phase is still open, so you cannot close this one\n";
        isClosed=true;
        return "";
    }
    @Override
    public boolean isClosed() { return isClosed; }

    //Applications
    @Override
    public String addApplication(String filename, ArrayList<Application> applications, ArrayList<Proposal> proposals, ArrayList<Student> students) {
        filename="src/csvFiles/application.csv";
        StringBuilder warnings = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> tempArr = Arrays.asList(line.split(","));
                List<String> idProposals = new ArrayList<>();
                for (int i = 1; i < tempArr.size(); i++)
                    idProposals.add(tempArr.get(i));
                Student student = null;
                for (Student s : students)
                    if (Long.parseLong(tempArr.get(0)) == s.getStudentNumber())
                        student = s;
                //Valid input parameters
                if (tempArr.size() < 2)
                    warnings.append("the application with the student number ").append(tempArr.get(0)).append(", doesn't have enough data\n");
                else if (hasProposalAlreadyAStudent(idProposals, proposals) || isAProposalAssigned(Long.parseLong(tempArr.get(0)), proposals))
                    warnings.append("The student with code ").append(tempArr.get(0)).append(", chose a proposal that is not available\n");
                else if (student==null)
                    warnings.append("The student with code ").append(tempArr.get(0)).append(", doesn't exist\n");
                else if (isExistentApplication(Long.parseLong(tempArr.get(0)), applications))
                    warnings.append("The application of student with code ").append(tempArr.get(0)).append(", has duplicated data or two or has two iterations\n");
                else
                    applications.add(new Application(student, idProposals));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return warnings.toString();
    }
    @Override
    public String editApplication(long number, String id, int option, ArrayList<Application> applications, ArrayList<Proposal> proposals)  {
        if(!isExistentApplication(number,applications))
            return "The student "+number+", didn't apply or doesn't exist\n";

        for(Application a : applications)
                if(a.getStudentNumber().getStudentNumber()==number)
                    switch (option){
                        case 1 -> {
                            for(Proposal p : proposals) {
                                List<String> aux = a.getIdProposals();
                                if (p.getIdentification().equalsIgnoreCase(id) && !isADuplicateProposal(id,aux) && isAValidProposal(id,proposals) && !hasProposalAlreadyAStudent(aux,proposals)) {
                                    aux.add(id);
                                    a.setIdProposals(aux);
                                }else
                                    return "It's impossible to add this proposal to this application\n";
                            }
                        }
                        case 2 -> {
                            for(Proposal p : proposals) {
                                List<String> aux = a.getIdProposals();
                                if (p.getIdentification().equalsIgnoreCase(id) && aux.size()>1) {
                                    aux.remove(id);
                                    a.setIdProposals(aux);
                                }else
                                    return "It's impossible to remove this proposal to this application\n";
                            }
                        }
                    }
        return "";
    }
    @Override
    public String deleteApplication(long number, ArrayList<Application> applications){
        if(!isExistentApplication(number,applications))
            return "This application doesn't exist\n";
            applications.removeIf(a -> a.getStudentNumber().getStudentNumber() == number);
        return "";
    }
    @Override
    public String showApplication(ArrayList<Application> applications){
        StringBuilder s = new StringBuilder();
        for(Application a : applications){
            s.append(String.format("Applicant number: %d Proposal ID's: ",a.getStudentNumber().getStudentNumber()));
            for(String st: a.getIdProposals())
                s.append(st).append("; ");
            s.append("\n");
        }
        return s.toString();
    }
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

    //Get lists
    @Override
    public String generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, ArrayList<Proposal> proposals, ArrayList <Application> applications, ArrayList <Student> students){
        ArrayList<Long> studentList = new ArrayList<>();
        StringBuilder listStudent = new StringBuilder();
        int count;

        if(selfProposed)
            for(Proposal p : proposals)
                if(p.getType().equalsIgnoreCase("T3") && isExistentStudent(p.getStudent().getStudentNumber(), students))
                    studentList.add(p.getStudent().getStudentNumber());

        if(alreadyRegistered)
            for(Application a : applications)
                if(isExistentStudent(a.getStudentNumber().getStudentNumber(), students))
                    studentList.add(a.getStudentNumber().getStudentNumber());

        if(withoutRegistered)
            for(Student s : students) {
                count=0;
                for (Application a : applications) {
                    if (a.getStudentNumber().getStudentNumber() != s.getStudentNumber())
                        count++;
                    if (count == applications.size() && isExistentStudent(s.getStudentNumber(), students))
                        studentList.add(s.getStudentNumber());
                }
            }

        for(Long l : studentList)
            listStudent.append(l).append("\t");
        return listStudent.toString();
    }
    @Override
    public String generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications,ArrayList<Proposal> proposals, ArrayList<Application> applications){
        ArrayList<String> proposalList = new ArrayList<>();
        StringBuilder listStudent = new StringBuilder();
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
            listStudent.append(s).append(" ");

        return listStudent.toString();
    }

    //Validations
    private static boolean isExistentStudent(long number, ArrayList<Student> students){
        for(Student s : students)
            if(s.getStudentNumber() == number)
                return true;
        return false;
    }
    private static boolean isAProposalAssigned(Long number, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            if (p.getStudent() != null && number == p.getStudent().getStudentNumber())
                return true;
        return false;
    }
    private static boolean isExistentApplication(Long number, ArrayList<Application> applications){
        for(Application a : applications)
            if(number==a.getStudentNumber().getStudentNumber())
                return true;
        return false;
    }
    private static boolean hasProposalAlreadyAStudent(List<String> idProposals, ArrayList<Proposal> proposals){
        for(Proposal p : proposals)
            for(String s : idProposals)
                if (s.equalsIgnoreCase(p.getIdentification()) && (p.getType().equalsIgnoreCase("T3") || p.getStudent()!=null))
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