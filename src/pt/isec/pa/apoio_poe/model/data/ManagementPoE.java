package pt.isec.pa.apoio_poe.model.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ManagementPoE {
    private final ArrayList<Student> students = new ArrayList<>();
    private final ArrayList<Teacher> teachers = new ArrayList<>();
    private final ArrayList<Proposal> proposals = new ArrayList<>();
    private BufferedReader br = null;

    public void addStudents() throws IOException {
        try {
            FileReader fr = new FileReader("C:\\Users\\serco\\Desktop\\A Minha Universidade\\PA\\Projeto\\src\\pt\\isec\\pa\\apoio_poe\\model\\data\\csvFiles\\eStudents.csv");
            br = new BufferedReader(fr);
            String line;

            while((line = br.readLine()) != null) {
                String[] tempArr = line.split(",");
                Student student = new Student(Long.parseLong(tempArr[0]), tempArr[1], tempArr[2], tempArr[3], tempArr[4],Double.parseDouble(tempArr[5]), Boolean.valueOf(tempArr[6]));

                //Valida os parametros de entrada
                if(isExistentStudent(student.getStudentNumber()) || !isACourseAcronym(student.getCourseAcronym()) || !isAIndustryAcronym(student.getIndustryAcronym()) || !isAValidClassification(student.getClassification()))
                    System.out.println("The student with code " + student.getStudentNumber() + ", has invalid or duplicated data");
                else
                    students.add(student);
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }
    }

    public void editStudent(long number, String toUpdate, int option)  {
        if(!isExistentStudent(number))
            System.out.println("This student doesn't exist");

        for(Student s : students)
            if(s.getStudentNumber()==number)
                switch (option){
                    case 1 -> s.setName(toUpdate);
                    case 2 -> {
                        if(isACourseAcronym(toUpdate))
                            s.setCourseAcronym(toUpdate);
                    }
                    case 3 -> {
                        if(isAIndustryAcronym(toUpdate))
                            s.setIndustryAcronym(toUpdate);
                    }
                    case 4 -> {
                        if(isAValidClassification(Double.parseDouble(toUpdate)))
                            s.setClassification(Double.parseDouble(toUpdate));
                    }
                    case 5 -> s.setAccessInternships(Boolean.valueOf(toUpdate));
                }
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

    public void showStudents(){ students.forEach((n) -> System.out.println(n.toString())); }


    //Booleans
    public boolean isExistentStudent(long number){
        for(Student s : students)
            if(s.getStudentNumber()==number)
                return true;
        return false;
    }

    public boolean isACourseAcronym(String courseAcronym){ return courseAcronym.equalsIgnoreCase("LEI-PL") || courseAcronym.equalsIgnoreCase("LEI"); }

    public boolean isAIndustryAcronym(String industryAcronym){ return industryAcronym.equalsIgnoreCase("SI") || industryAcronym.equalsIgnoreCase("DA") || industryAcronym.equalsIgnoreCase("RAS"); }

    public boolean isAValidClassification(double classification){
        return classification>0 || classification<1;
    }
}
