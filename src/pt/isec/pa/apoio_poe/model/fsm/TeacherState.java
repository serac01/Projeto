package pt.isec.pa.apoio_poe.model.fsm;

import pt.isec.pa.apoio_poe.model.data.Teacher;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherState {
    public static void addTeachers(String filename, ArrayList<Teacher> teachers) throws IOException {
        BufferedReader br = null;
        filename="src/csvFiles/teachers.csv";
        try {
            FileReader fr = new FileReader(filename);
            br = new BufferedReader(fr);
            String line;
            while((line=br.readLine()) != null) {
                List<String> tempArr = Arrays.asList(line.split(","));
                //Valida os parametros de entrada
                if(isExistentTeacher(tempArr.get(1), teachers))
                    System.out.println("The teacher " + tempArr.get(0) + ", has an invalid email");
                else {
                    Teacher teacher = new Teacher(tempArr.get(0), tempArr.get(1));
                    teachers.add(teacher);
                }
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }finally {
            if (br != null)
                br.close();
        }
    }

    public static void editTeacher(String email, String toUpdate, ArrayList<Teacher> teachers)  {
        if(!isExistentTeacher(email,teachers))
            return;

        for(Teacher t : teachers)
            if(email.equalsIgnoreCase(t.getEmail())) {
                t.setName(toUpdate);
            }
    }

    public static void deleteTeacher(String email, ArrayList<Teacher> teachers){
        if(!isExistentTeacher(email,teachers))
            return;
        teachers.removeIf(t -> t.getEmail().equalsIgnoreCase(email));
    }

    public static void showTeachers(ArrayList<Teacher> teachers){ teachers.forEach((n) -> System.out.println(n.toString())); }

    public static void exportTeacher(String filename, ArrayList<Teacher> teachers) throws IOException {
        filename = "src/csvFiles/exportTeachers.csv";
        FileWriter csvWriter = null;
        try {
            File file = new File(filename);
            if(!file.exists()) {
                csvWriter = new FileWriter(file);
                int count = 0;
                for (Teacher t : teachers) {
                    csvWriter.append(t.getName());
                    csvWriter.append(",");
                    csvWriter.append(t.getEmail());
                    count++;
                    if (count < teachers.size())
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
    private static boolean isExistentTeacher(String email, ArrayList<Teacher> teachers){
        for(Teacher t : teachers)
            if(email.equalsIgnoreCase(t.getEmail()))
                return true;
        return false;
    }
}