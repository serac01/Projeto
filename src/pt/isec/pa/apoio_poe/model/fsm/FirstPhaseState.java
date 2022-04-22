package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Phase;
import pt.isec.pa.apoio_poe.model.data.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirstPhaseState extends PhaseStateAdapter {

    FirstPhaseState(PhaseContext context, Phase phase){
        super(context,phase);
        phase.setCurrentPhase(1);
    }

    @Override
    public boolean nextPhase() {
        changeState(new SecondPhaseState(context,phase));
        return true;
    }

    @Override
    public PhaseState getState() { return PhaseState.PHASE_1; }

    public static ArrayList<Student> addStudents(String filename, ArrayList<Student> students) throws IOException {
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
        return students;
    }

    public static ArrayList<Student> editStudent(long number, String toUpdate, int option, ArrayList<Student> students)  {
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

    /*public void deleteStudent(long number){
        int j=-1;

            for(int i=0; i<students.lenght; i++){
            if(students[i].studentNumber==number){
                j=i;
            }}
            if(j!=-1){
                for(int i=j; i<students.lenght;i++){
                    students[i-1]=students[i];
                }
                students[students.length]=NULL;
            }
    }*/

    public static void showStudents(ArrayList<Student> students){ students.forEach((n) -> System.out.println(n.toString())); }

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
