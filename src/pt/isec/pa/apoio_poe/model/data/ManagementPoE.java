package pt.isec.pa.apoio_poe.model.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ManagementPoE {
    private final ArrayList<Student> students = new ArrayList<>();
    private final ArrayList<Teacher> teachers = new ArrayList<>();
    private final ArrayList<Proposal> proposals = new ArrayList<>();
    private final ArrayList<Application> applications = new ArrayList<>();

    public void newStudents(){
        try {
            FileReader fr = new FileReader(new File("C:\\Users\\serco\\Desktop\\A Minha Universidade\\PA\\Projeto\\src\\pt\\isec\\pa\\apoio_poe\\csvFiles\\students.csv"));
            BufferedReader br = new BufferedReader(fr);
            String delimiter = ",", line = "";
            String[] tempArr;
            while((line = br.readLine()) != null) {
                tempArr = line.split(delimiter);
                Student student = new Student(Long.parseLong(tempArr[0]),tempArr[1],tempArr[2],tempArr[3],tempArr[4],Double.parseDouble(tempArr[5]), Boolean.valueOf(tempArr[6]));
                students.add(student);
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        students.forEach((n) -> System.out.println(n.toString()));
    }
    
    public void deleteStudent(){
        int j=-1;
        long number;
        String studentName;
        Scanner sc = new Scanner(System.in);

            System.out.print("Type student number...");
            studentName = sc.nextInt();
            for(int i=0; i<students.lenght; i++){
            if(students[i].studentNumber==number){
                j=i;
            }
            if(j!=-1){
                for(int i=j; i<students.lenght;i++){
                    students[i-1]=students[i];
                }
                students[students.length]=NULL;
            }
    }
        
    public void addStudent(){
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
    System.out.print("Type student's name...");
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
    
    }
    
        public void newTeachers(){
        try {
            FileReader fr = new FileReader(new File("C:\\Users\\serco\\Desktop\\A Minha Universidade\\PA\\Projeto\\src\\pt\\isec\\pa\\apoio_poe\\csvFiles\\teachers.csv"));
            BufferedReader br = new BufferedReader(fr);
            String delimiter = ",", line = "";
            String[] tempArr;
            while((line = br.readLine()) != null) {
                tempArr = line.split(delimiter);
                Teacher teacher = new Teacher(tempArr[0],tempArr[1]);
                teachers.add(teacher);
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        teacher.forEach((n) -> System.out.println(n.toString()));
    }
    
    public void checkStudents(){
        
        students.forEach((n) -> System.out.println(n.toString()));
        
    }
    
    
}
