package pt.isec.pa.apoio_poe.model.data;

import java.util.ArrayList;

public class University {
    private final ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }
}
