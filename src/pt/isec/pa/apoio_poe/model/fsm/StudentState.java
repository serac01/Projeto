package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentState {
    public static void addStudents(String filename, ArrayList<Student> students) throws IOException {
        BufferedReader br = null;
        filename="src/csvFiles/eStudents.csv";
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
    }

    public static void editStudent(long number, String toUpdate, int option, ArrayList<Student> students)  {
        if(!isExistentStudent(number,students))
            return;

        for(Student s : students)
            if(s.getStudentNumber()==number)
                switch (option){
                    case 1 -> {
                        s.setName(toUpdate);
                    }
                    case 2 -> {
                        if(isACourseAcronym(toUpdate)) {
                            s.setCourseAcronym(toUpdate);
                        }
                    }
                    case 3 -> {
                        if(isAIndustryAcronym(toUpdate)) {
                            s.setIndustryAcronym(toUpdate);
                        }
                    }
                    case 4 -> {
                        if(isAValidClassification(Double.parseDouble(toUpdate))) {
                            s.setClassification(Double.parseDouble(toUpdate));
                        }
                    }
                    case 5 -> {
                        if(isAValidBollean(toUpdate)) {
                            s.setAccessInternships(Boolean.parseBoolean(toUpdate));
                        }
                    }
                }
    }

    public static void deleteStudents(long number, ArrayList<Student> students){
        if(!isExistentStudent(number,students))
            return;

        students.removeIf(s -> s.getStudentNumber() == number);
    }

    public static void showStudents(ArrayList<Student> students){ students.forEach((n) -> System.out.println(n.toString())); }

    public static void exportStudents(String filename, ArrayList<Student> students) throws IOException {
        filename = "src/csvFiles/exportStudent.csv";
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

    private static boolean isACourseAcronym(String courseAcronym){ return courseAcronym.equalsIgnoreCase("LEI-PL") || courseAcronym.equalsIgnoreCase("LEI"); }

    private static boolean isAIndustryAcronym(String industryAcronym){ return industryAcronym.equalsIgnoreCase("SI") || industryAcronym.equalsIgnoreCase("DA") || industryAcronym.equalsIgnoreCase("RAS"); }

    private static boolean isAValidClassification(double classification){ return classification>=0 && classification<=1; }

    private static boolean isAValidBollean(String accessInternships){ return accessInternships.equalsIgnoreCase("true") || accessInternships.equalsIgnoreCase("false"); }
}