package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondPhaseState extends PhaseStateAdapter implements Serializable {
    public static final long serialVersionUID=2020129026;

    SecondPhaseState(PhaseContext context) { super(context); }

    //Sate
    @Override
    public void previousPhase() {
        changeState(new FirstPhaseState(context));
    }
    @Override
    public void nextPhase() {
        changeState(new ThirdPhaseState(context));
    }
    @Override
    public PhaseState getState() {
        return PhaseState.PHASE_2;
    }
    @Override
    public String closePhase(ManagementPoE management) {
        if(!management.isPhase1Closed())
            return "The previous phase is still open, so you cannot close this one\n";
        management.setPhase2Closed(true);
        nextPhase();
        return "";
    }
    @Override
    public boolean isPhaseClosed(ManagementPoE management){ return management.isPhase2Closed(); }

    //Applications
    @Override
    public String addApplication(String filename, ManagementPoE management) {
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
                for (Student s : management.getStudent())
                    if (Long.parseLong(tempArr.get(0)) == s.getStudentNumber())
                        student = s;
                //Valid input parameters
                if (tempArr.size() < 2)
                    warnings.append("the application with the student number ").append(tempArr.get(0)).append(", doesn't have enough data\n");
                else if (hasProposalAlreadyAStudent(idProposals, management) || isAProposalAssigned(Long.parseLong(tempArr.get(0)), management))
                    warnings.append("The student with code ").append(tempArr.get(0)).append(", chose a proposal that is not available\n");
                else if (student==null)
                    warnings.append("The student with code ").append(tempArr.get(0)).append(", doesn't exist\n");
                else if (isExistentApplication(Long.parseLong(tempArr.get(0)), management))
                    warnings.append("The application of student with code ").append(tempArr.get(0)).append(", has duplicated data or two or has two iterations\n");
                else {
                    if(!student.isAccessInternships())
                        isAInvalidProposalForStudent(idProposals, management);
                    management.addApplication(new Application(student, idProposals));
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return warnings.toString();
    }
    @Override
    public String editApplication(long number, String id, int option, ManagementPoE management)  {
        boolean changed=false;
        if(!isExistentApplication(number,management))
            return "The student "+number+", didn't apply or doesn't exist\n";

        for(Application a : management.getApplications())
                if(a.getStudent().getStudentNumber()==number)
                    switch (option){
                        case 1 -> {
                            List<String> aux = a.getIdProposals();
                            for(Proposal p : management.getProposals())
                                if (p.getIdentification().equalsIgnoreCase(id) && !isADuplicateProposal(id,aux) && isAValidProposal(id,management) && !hasProposalAlreadyAStudent(aux,management)) {
                                    aux.add(id);
                                    a.setIdProposals(aux);
                                    changed=true;
                                }
                            if(!changed)
                                return "It's impossible to remove this proposal to this application\n";
                        }
                        case 2 -> {
                            List<String> aux = a.getIdProposals();
                            for(String s : aux)
                                if (s.equalsIgnoreCase(id) && aux.size()>1) {
                                    aux.remove(id);
                                    a.setIdProposals(aux);
                                    changed=true;
                                }
                            if(!changed)
                                return "It's impossible to remove this proposal to this application\n";
                        }
                    }
        return "";
    }
    @Override
    public String deleteApplication(long number, ManagementPoE management){
        if(!isExistentApplication(number,management))
            return "This application doesn't exist\n";
        management.deleteApplicationFromList(number);
        return "";
    }
    @Override
    public List<String> showApplication(ManagementPoE management){
        List<String> list = new ArrayList<>();
        for(Application a : management.getApplications())
            list.add(a.toString());
        return list;
    }
    @Override
    public void exportApplications(String filename, ManagementPoE management) throws IOException {
        FileWriter csvWriter = null;
        try {
            File file = new File(filename);
            if(!file.exists()) {
                csvWriter = new FileWriter(file);
                int count = 0;
                for (Application a : management.getApplications()) {
                    csvWriter.append(String.valueOf(a.getStudent()));
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
                    if (count < management.getApplications().size())
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
    public List<String> generateStudentList(boolean selfProposed, boolean alreadyRegistered, boolean withoutRegistered, ManagementPoE management){
        ArrayList<String> studentList = new ArrayList<>();
        int count;

        if(selfProposed)
            for(Proposal p : management.getProposals())
                if(p.getType().equalsIgnoreCase("T3") && isExistentStudent(p.getStudent().getStudentNumber(), management))
                    studentList.add(String.valueOf(p.getStudent().getStudentNumber()));

        if(alreadyRegistered)
            for(Application a : management.getApplications())
                if(isExistentStudent(a.getStudent().getStudentNumber(), management))
                    studentList.add(String.valueOf(a.getStudent().getStudentNumber()));

        if(withoutRegistered)
            for(Student s : management.getStudent()) {
                count=0;
                for (Application a : management.getApplications()) {
                    if (a.getStudent().getStudentNumber() != s.getStudentNumber())
                        count++;
                    if (count == management.getApplications().size() && isExistentStudent(s.getStudentNumber(), management))
                        studentList.add(String.valueOf(s.getStudentNumber()));
                }
            }
        return studentList;
    }
    @Override
    public List<String> generateProposalsList(boolean selfProposed, boolean proposeTeacher, boolean withApplications, boolean withoutApplications, ManagementPoE management){
        ArrayList<String> proposalList = new ArrayList<>();
        boolean exist = false;

        if(selfProposed)
            for(Proposal p : management.getProposals())
                if(p.getType().equalsIgnoreCase("T3"))
                    proposalList.add(p.getIdentification());

        if(proposeTeacher)
            for(Proposal p : management.getProposals())
                if(p.getType().equalsIgnoreCase("T2"))
                    proposalList.add(p.getIdentification());

        if(withApplications)
            for(Proposal p : management.getProposals())
                for(Application a : management.getApplications())
                    for (String s : a.getIdProposals())
                        if(s.equalsIgnoreCase(p.getIdentification()))
                            proposalList.add(p.getIdentification());

        if(withoutApplications) {
            List<String> aux = new ArrayList<>();
            for (Application a : management.getApplications())
                aux.addAll(a.getIdProposals());

            for (Proposal p : management.getProposals()) {
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

        return proposalList;
    }

    //Validations
    private static boolean isExistentStudent(long number, ManagementPoE management){
        for(Student s : management.getStudent())
            if(s.getStudentNumber() == number)
                return true;
        return false;
    }
    private static boolean isAProposalAssigned(Long number, ManagementPoE management){
        for(Proposal p : management.getProposals())
            if (p.getStudent() != null && number == p.getStudent().getStudentNumber())
                return true;
        return false;
    }
    private static boolean isExistentApplication(Long number, ManagementPoE management){
        for(Application a : management.getApplications())
            if(number==a.getStudent().getStudentNumber())
                return true;
        return false;
    }
    private static boolean hasProposalAlreadyAStudent(List<String> idProposals, ManagementPoE management){
        for(Proposal p : management.getProposals())
            for(String s : idProposals)
                if (s.equalsIgnoreCase(p.getIdentification()) && (p.getType().equalsIgnoreCase("T3") || p.getStudent()!=null))
                    return true;
        return false;
    }
    private static boolean isAValidProposal(String id, ManagementPoE management){
        for(Proposal p : management.getProposals())
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
    private static void isAInvalidProposalForStudent(List<String> idProposals, ManagementPoE management){
        for(Proposal p : management.getProposals())
            idProposals.removeIf(s -> s.equalsIgnoreCase(p.getIdentification()) && p.getType().equalsIgnoreCase("T1"));
    }
}