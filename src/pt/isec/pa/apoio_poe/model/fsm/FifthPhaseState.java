package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.DataPoE;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.data.Teacher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FifthPhaseState extends PhaseStateAdapter {
    public static final long serialVersionUID=2020129026;

    FifthPhaseState(PhaseContext context) { super(context); }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_5; }

    @Override
    public String listPhase5(boolean op1, boolean op2, boolean op3, boolean op4, boolean op5, DataPoE data){
       ArrayList<Student> listStudents = new ArrayList<>();
        ArrayList<Proposal> listProposals = new ArrayList<>();
        if(op1){
            for (Proposal p : data.getProposals())
                if (p.getStudent() != null)
                    listStudents.add(p.getStudent());
            return DataPoE.showListOfStudents(listStudents);
        }else if(op2) {
            for (Proposal p : data.getProposals())
                if (p.getStudent() != null)
                    listStudents.add(p.getStudent());
            return DataPoE.showListOfStudents(listStudents);
        }else if(op3) {
            for (Proposal p : data.getProposals())
                if (p.getStudent() != null)
                    listProposals.add(p);
            return DataPoE.showListOfProposals(listProposals);
        }else if(op4){
            listProposals.addAll(data.getProposals());
            for (Proposal p : data.getProposals())
                if (p.getStudent() != null)
                    listProposals.remove(p);
            return DataPoE.showListOfProposals(listProposals);
        }
        else if(op5){
            StringBuilder phrase = new StringBuilder();
            phrase.append("On average there are ").append((float) data.getProposals().size() / data.getTeachers().size()).append(" proposals per teacher\n");
            int counter;
            for(Teacher t : data.getTeachers()) {
                counter=0;
                for (Proposal p : data.getProposals())
                    if (p.getTeacher() != null && p.getTeacher().getEmail().equalsIgnoreCase(t.getEmail()))
                        counter++;
                phrase.append("The teacher ").append(t.getName()).append(" has ").append(counter).append(" assigned proposals\n");
            }
            return phrase.toString();
        }
        return "";
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
}
