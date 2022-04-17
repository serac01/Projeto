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
        Int number,j=-1;
        String studentName;
        Scanner sc = new Scanner(System.in);
        switch (Input.chooseOption("Choose deletion filter:","By name", "By student number")){
        case 1 ->
            System.out.println("Type student name...");
            studentName = sc.nextLine();
            for(int i=0; i<students.lenght; i++){
            if(students[i].name==studentName){
                j=i;
            }
            if(j!=-1){
                for(int i=j; i<students.lenght;i++){
                    students[i-1]=students[i];
                }
                students[students.length=NULL];
            }
            
        case 2 ->
            System.out.println("Type student number...");
            studentName = sc.nextInt();
            for(int i=0; i<students.lenght; i++){
            if(students[i].studentNumber==number){
                j=i;
            }
            if(j!=-1){
                for(int i=j; i<students.lenght;i++){
                    students[i-1]=students[i];
                }
                students[students.length=NULL];
            }
                
        }
        
        
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
