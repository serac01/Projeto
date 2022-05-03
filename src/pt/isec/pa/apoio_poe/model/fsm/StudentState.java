package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentState implements Serializable{
    public static final long serialVersionUID=2020129026;

    public static String addStudents(String filename, ArrayList<Student> students) throws IOException {
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
                else if(isExistentStudent(Long.parseLong(tempArr.get(0)), students))
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
                    students.add(new Student(Long.parseLong(tempArr.get(0)), tempArr.get(1), tempArr.get(2), tempArr.get(3), tempArr.get(4),Double.parseDouble(tempArr.get(5)), Boolean.parseBoolean(tempArr.get(6))));
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }
        return warnings.toString();
    }

    public static String editStudent(long number, String toUpdate, int option, ArrayList<Student> students)  {
        if(!isExistentStudent(number,students))
            return "The student with code "+number+", doesn't exists\n";

        for(Student s : students)
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

    public static String deleteStudents(long number, ArrayList<Student> students, ArrayList<Proposal> proposals, ArrayList<Application> applications){
        if(!isExistentStudent(number,students))
            return "The student with code "+number+", doesn't exists\n";
        for(Proposal p: proposals)
            if(p.getStudent().getStudentNumber()== number)
                return "Impossible to delete student due to existent relation\n";
        for(Application a : applications)
            if(a.getStudentNumber().getStudentNumber()== number)
                return "Impossible to delete student due to existent relation\n";

        students.removeIf(s -> s.getStudentNumber() == number);
        return "";
    }

    public static String showStudents(ArrayList<Student> students){
        StringBuilder s = new StringBuilder();
        for(Student student: students)
            s.append(String.format("Student number: %-10d Student name: %-30s Email: %s Course acronym: %-6s Industry acronym: %-7s Classification: %.6f Access to Internship : %b \n",student.getStudentNumber(),student.getName(),student.getEmail(),student.getCourseAcronym(),student.getIndustryAcronym(),student.getClassification(),student.isAccessInternships()));
        return s.toString();
    }

    public static void exportStudents(String filename, ArrayList<Student> students) throws IOException {
        FileWriter csvWriter = null;
        try {
            File file = new File(filename);
            if(!file.exists()) {
                csvWriter = new FileWriter(file);

                int count = 0;

                for (Student s : students) {
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
                    if (count < students.size())
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
    private static boolean isExistentStudent(long number, ArrayList<Student> students){
        for(Student s : students)
            if(s.getStudentNumber()==number)
                return true;
        return false;
    }

    private static boolean isAInvalidCourseAcronym(String courseAcronym){ return !courseAcronym.equalsIgnoreCase("LEI-PL") && !courseAcronym.equalsIgnoreCase("LEI"); }

    private static boolean isAInvalidIndustryAcronym(String industryAcronym){ return !industryAcronym.equalsIgnoreCase("SI") && !industryAcronym.equalsIgnoreCase("DA") && !industryAcronym.equalsIgnoreCase("RAS"); }

    private static boolean isAInvalidClassification(double classification){ return !(classification >= 0) || !(classification <= 1); }

    private static boolean isAInvalidBoolean(String accessInternships){ return !accessInternships.equalsIgnoreCase("true") && !accessInternships.equalsIgnoreCase("false"); }
}