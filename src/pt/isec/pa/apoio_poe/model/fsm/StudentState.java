package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentState extends PhaseStateAdapter implements Serializable {
    public static final long serialVersionUID=2020129026;

    StudentState(PhaseContext context){ super(context); }

    //State
    @Override
    public void nextPhase() { changeState(new FirstPhaseState(context)); }
    @Override
    public PhaseState getState() { return PhaseState.STUDENT_PHASE; }
    @Override
    public boolean isPhaseClosed(ManagementPoE management){ return management.isStudentPhaseClosed(); }

    //others methods
    @Override
    public String addStudents(String filename, ManagementPoE management) throws IOException {
        filename="src/csvFiles/students.csv";
        BufferedReader br = null;
        StringBuilder warnings = new StringBuilder();
        try {
            FileReader fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String line;
            while((line=br.readLine()) != null) {
                List<String> tempArr = Arrays.asList(line.split(","));

                //Validation of input parameters
                if(tempArr.size()<7)
                    warnings.append("The student with code ").append(tempArr.get(0)).append(", has missing data\n");
                else if(isExistentStudent(Long.parseLong(tempArr.get(0)), management))
                    warnings.append("The student with code ").append(tempArr.get(0)).append(", already exists\n");
                else if(isAInvalidCourseAcronym(tempArr.get(3)))
                    warnings.append("The student with code ").append(tempArr.get(0)).append(", has a wrong course acronym\n");
                else if(isAInvalidIndustryAcronym(tempArr.get(4)))
                    warnings.append("The student with code ").append(tempArr.get(0)).append(", has a wrong industry acronym\n");
                else if(isAInvalidClassification(Double.parseDouble(tempArr.get(5))))
                    warnings.append("The student with code ").append(tempArr.get(0)).append(", has a wrong classification\n");
                else if(isAInvalidBoolean(tempArr.get(6)))
                    warnings.append("The student with code ").append(tempArr.get(0)).append(", has a wrong value to access internships\n");
                else
                    management.addStudent(new Student(Long.parseLong(tempArr.get(0)), tempArr.get(1), tempArr.get(2), tempArr.get(3), tempArr.get(4),Double.parseDouble(tempArr.get(5)), Boolean.parseBoolean(tempArr.get(6))));
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
    public String editStudent(long number, String toUpdate, int option, ManagementPoE management)  {
        if(!isExistentStudent(number,management))
            return "The student with code "+number+", doesn't exists\n";

        for(Student s : management.getStudent())
            if(s.getStudentNumber()==number)
                switch (option){
                    case 1 -> s.setName(toUpdate);
                    case 2 -> {
                        if(isAInvalidCourseAcronym(toUpdate))
                            return "You have entered an invalid course acronym\n";
                        s.setCourseAcronym(toUpdate);
                    }
                    case 3 -> {
                        if(isAInvalidIndustryAcronym(toUpdate))
                            return "You have entered an invalid industry acronym\n";
                        s.setIndustryAcronym(toUpdate);
                    }
                    case 4 -> {
                        if(isAInvalidClassification(Double.parseDouble(toUpdate)))
                            return "You have entered an invalid classification\n";
                        s.setClassification(Double.parseDouble(toUpdate));
                    }
                    case 5 -> {
                        if(isAInvalidBoolean(toUpdate))
                            return "You have entered an invalid value to access internships";
                        s.setAccessInternships(Boolean.parseBoolean(toUpdate));
                    }
                }
        return "";
    }

    @Override
    public String deleteStudents(long number, ManagementPoE management){
        if(!isExistentStudent(number,management))
            return "The student with code "+number+", doesn't exists\n";
        for(Proposal p: management.getProposals())
            if(p.getStudent() != null && p.getStudent().getStudentNumber() == number)
                return "Impossible to delete student due to existent relation\n";
        for(Application a : management.getApplications())
            if(a.getStudent().getStudentNumber()== number)
                return "Impossible to delete student due to existent relation\n";

        management.deleteStudentFromList(number);
        return "";
    }

    @Override
    public List<String> showStudents(ManagementPoE management){
        List<String> list = new ArrayList<>();
        for(Student student: management.getStudent())
            list.add(student.toString());
        return list;
    }

    @Override
    public void exportStudents(String filename, ManagementPoE management) throws IOException {
        FileWriter csvWriter = null;
        try {
            File file = new File(filename);
            if(!file.exists()) {
                csvWriter = new FileWriter(file);

                int count = 0;

                for (Student s : management.getStudent()) {
                    csvWriter.append(String.valueOf(s.getStudentNumber()));
                    csvWriter.append(",");
                    csvWriter.append(s.getName());
                    csvWriter.append(",");
                    csvWriter.append(s.getEmail());
                    csvWriter.append(",");
                    csvWriter.append(s.getCourseAcronym());
                    csvWriter.append(",");
                    csvWriter.append(s.getIndustryAcronym());
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(s.getClassification()));
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(s.isAccessInternships()));
                    count++;
                    if (count < management.getStudent().size())
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
    private static boolean isExistentStudent(long number, ManagementPoE management){
        for(Student s : management.getStudent())
            if(s.getStudentNumber()==number)
                return true;
        return false;
    }

    private static boolean isAInvalidCourseAcronym(String courseAcronym){ return !courseAcronym.equalsIgnoreCase("LEI-PL") && !courseAcronym.equalsIgnoreCase("LEI"); }

    private static boolean isAInvalidIndustryAcronym(String industryAcronym){ return !industryAcronym.equalsIgnoreCase("SI") && !industryAcronym.equalsIgnoreCase("DA") && !industryAcronym.equalsIgnoreCase("RAS"); }

    private static boolean isAInvalidClassification(double classification){ return !(classification >= 0) || !(classification <= 1); }

    private static boolean isAInvalidBoolean(String accessInternships){ return !accessInternships.equalsIgnoreCase("true") && !accessInternships.equalsIgnoreCase("false"); }
}