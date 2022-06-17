package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.DataPoE;
import pt.isec.pa.apoio_poe.model.data.Proposal;
import pt.isec.pa.apoio_poe.model.data.Teacher;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherState extends PhaseStateAdapter implements Serializable {
    public static final long serialVersionUID=2020129026;

    TeacherState(PhaseContext context){ super(context); }

    //State
    @Override
    public void nextPhase() { changeState(new FirstPhaseState(context)); }
    @Override
    public PhaseState getState() { return PhaseState.TEACHER_PHASE; }
    @Override
    public boolean isPhaseClosed(DataPoE data){ return data.isTeacherPhaseClosed(); }

    //others methods
    @Override
    public String addTeachers(String filename, DataPoE data) throws IOException {
        filename="src/csvFiles/teachers.csv";
        BufferedReader br = null;
        StringBuilder warnings = new StringBuilder();
        try {
            FileReader fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String line;
            while((line=br.readLine()) != null) {
                List<String> tempArr = Arrays.asList(line.split(","));
                if(isExistentTeacher(tempArr.get(1), data))
                    warnings.append("The teacher ").append(tempArr.get(0)).append(", has an invalid email");
                else
                    data.addTeacher(new Teacher(tempArr.get(0), tempArr.get(1)));
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }
        return warnings.toString();
    }

    @Override
    public String editTeacher(String email, String toUpdate, DataPoE data)  {
        if(!isExistentTeacher(email,data))
            return "The teacher with email "+email+", doesn't exists\n";

        for(Teacher t : data.getTeachers())
            if(email.equalsIgnoreCase(t.getEmail())) {
                t.setName(toUpdate);
                break;
            }
        return "";
    }

    @Override
    public String deleteTeacher(String email, DataPoE data){
        if(!isExistentTeacher(email,data))
            return "The teacher with email "+email+", doesn't exists\n";
        for(Proposal p : data.getProposals())
            if(p.getTeacher()!=null && p.getTeacher().getEmail().equalsIgnoreCase(email))
                return "Impossible to remove teacher due to existing relation";
        data.deleteTeacherFromList(email);
        return "";
    }

    @Override
    public List<String> showTeachers(DataPoE data){
        List<String> list = new ArrayList<>();
        for (Teacher t: data.getTeachers())
            list.add(t.toString());
        return list;
    }

    @Override
    public void exportTeacher(String filename, DataPoE data) throws IOException {
        FileWriter csvWriter = null;
        try {
            File file = new File(filename);
            if(!file.exists()) {
                csvWriter = new FileWriter(file);
                int count = 0;
                for (Teacher t : data.getTeachers()) {
                    csvWriter.append(t.getName());
                    csvWriter.append(",");
                    csvWriter.append(t.getEmail());
                    count++;
                    if (count < data.getTeachers().size())
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
    private static boolean isExistentTeacher(String email, DataPoE data){
        for(Teacher t : data.getTeachers())
            if(email.equalsIgnoreCase(t.getEmail()))
                return true;
        return false;
    }
}