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
            //Abertura de ficheiro para leitura com FileReader
            FileReader fr = new FileReader("C:\\Users\\serco\\Desktop\\A Minha Universidade\\PA\\Projeto\\src\\pt\\isec\\pa\\apoio_poe\\model\\data\\csvFiles\\eStudents.csv");
            //Adiciona funcionalidades de buffering ao Reader (tem métodos como: readline, read, close)
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                String[] tempArr = line.split(",");
                Student student = new Student(Long.parseLong(tempArr[0]), tempArr[1], tempArr[2], tempArr[3], tempArr[4],Double.parseDouble(tempArr[5]), Boolean.valueOf(tempArr[6]));
                if(isExistentStudent(student.getStudentNumber()) || !isACourseAcronym(student.getCourseAcronym()) || !isAIndustryAcronym(student.getIndustryAcronym()) || isAValidClassification(student.getClassification()))
                    System.out.println("The student with code " + student.getStudentNumber() + ", has invalid data");
                else
                    students.add(student);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }

        //Imprimir os alunos (so para debug)
        students.forEach((n) -> System.out.println(n.toString()));
    }

    public void editStudent(long number)  {
        if(!isExistentStudent(number))
            System.out.println("This student doesn't exist");

        else{
            for(Student s : students)
                if(s.getStudentNumber()==number)
                    System.out.println(s.toString());

            System.out.println("What do you want to edit? ");
            System.out.println("To implement...");
        }
    }

    public void deleteStudent(long number){
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
    }

    /******* Desnecessario *******/
    /*public void addStudent(){
    long sNumber;
    String name,email,courseAcronym,industryAcronym;
    double classification;
    boolean accessInternship;
    Scanner sc = new Scanner(System.in);
    System.out.print("Type student number...");
    sNumber = sc.nextLong();
    for(int i=0; i<students.lenght; i++){
       if(students[i].studentNumber==sNumber){
       System.out.println("Number already exists... Returning to menu");
       return;
       }
    }
    /*Automatic number association
    int highest=0;
    for(int i=0;i<students.lenght;i++){
    if(students[i].studentNumber>= highest)
        highest = students[i].studentNumber;
    }
    sNumber = highest+1;
    System.out.print("Student number :")
    System.out.println(Snumber);
    */
    /*System.out.print("Type student's name...");
    name = sc.nextString();
    System.out.print("Type student's email...");
    email = sc.nextString();
    System.out.print("Type course acronym...");
    courseAcronym = sc.nextString();
    System.out.print("Type industry acronym...");
    industryAcronym = sc.nextString();
    System.out.print("Type classification...");
    classification = sc.nextDouble();
    System.out.print("Type true/false according to student's access to internship...");
    accessInternship = sc.nextBoolean();
    Student newStudent= new Student(sNumber, name, email, courseAcronym, industryAcronym, classification, accessInternships);
    students.add(newStudent);
    
    }*/

    public void showStudents(){ students.forEach((n) -> System.out.println(n.toString())); }

    public boolean isExistentStudent(long number){
        for(Student s : students)
            if(s.getStudentNumber()==number)
                return true;
        return false;
    }

    public boolean isACourseAcronym(String courseAcronym){
        return courseAcronym.equalsIgnoreCase("LEI-PL") || courseAcronym.equalsIgnoreCase("LEI");
    }

    public boolean isAIndustryAcronym(String industryAcronym){
        return industryAcronym.equalsIgnoreCase("SI") || industryAcronym.equalsIgnoreCase("DA") || industryAcronym.equalsIgnoreCase("RAS");
    }

    public boolean isAValidClassification(double classification){
        return classification<0 || classification>1;
    }
    
}
