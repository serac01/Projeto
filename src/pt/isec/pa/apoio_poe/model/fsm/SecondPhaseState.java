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
                else if(isExistentApplication(Long.parseLong(tempArr.get(0)),applications))
                    System.out.println("The application of student with code " + tempArr.get(0) + ", has duplicated data or two or has two iterations");
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

    public static ArrayList<Application> editApplication(long number, String id, int option, ArrayList<Application> applications)  {
        if(!isExistentApplication(number,applications))
            return null;

        for(Application a : applications)
            if(a.getStudentNumber()==number)
                switch (option){
                    case 1 -> {
                        System.out.println("Temporario -> Add Proposal to list");
                        for(String s : a.getIdProposals())
                            if(s.equalsIgnoreCase(id))
                                return null;
                        List<String> aux = a.getIdProposals();
                        aux.add(id);
                        a.setIdProposals(aux);
                        return applications;
                    }
                    case 2 -> {
                        System.out.println("Temporario -> Edit Proposal from list/Change Proposal ID");
                        for(String s : a.getIdProposals())
                            if(s.equalsIgnoreCase(id))
                                s = id;
                        return applications;

                    }
                    case 3 -> {
                        System.out.println("Temporario -> Remove Proposal from list");
                        for(String s : a.getIdProposals())
                            if(s.equalsIgnoreCase(id)){
                                List<String> aux = a.getIdProposals();
                                aux.remove(s);
                                a.setIdProposals(aux);
                            }
                        return applications;

                    }
                }
        return null;
    }

    public static ArrayList<Application> deleteApplication(long number, ArrayList<Application> applications){
        if(!isExistentApplication(number,applications))
            return null;
        applications.removeIf(a -> a.getStudentNumber() == number);
        return applications;
    }

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

    private static boolean isExistentApplication(Long number, ArrayList<Application> applications){
        for(Application a : applications)
            if(number==a.getStudentNumber())
                return true;
        return false;
    }

}