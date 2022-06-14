package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class Proposal  implements Serializable {
    public static final long serialVersionUID=2020129026;
    private final String identification, title, type;
    private Student student;
    private Teacher teacher;

    public Proposal(String identification, String title, String type, Student student, Teacher teacher) {
        this.identification = identification;
        this.title = title;
        this.type = type;
        this.student=student;
        this.teacher=teacher;
    }

    public String getIdentification() { return identification; }

    public String getTitle() { return title; }

    public String getType() { return type; }

    public List<String> getArea() { return null; }

    public String getHostEntity() { return ""; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    @Override
    public String toString() {
        if(type.equalsIgnoreCase("T1") && student!=null && teacher!=null)
            return String.format("Proposal ID: %-5s Type: [%s]Internship   Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s Host Entity: %-30s",identification,type,title,getArea(),student.getEmail(),student.getStudentNumber(),teacher.getName(),teacher.getEmail(),getHostEntity());
        else if(type.equalsIgnoreCase("T1") && student!=null && teacher==null)
            return String.format("Proposal ID: %-5s Type: [%s]Internship   Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s Host Entity: %-30s",identification,type,title,getArea(),student.getName(),student.getStudentNumber(),"empty","empty",getHostEntity());
        else if(type.equalsIgnoreCase("T1") && student==null && teacher!=null)
            return String.format("Proposal ID: %-5s Type: [%s]Internship   Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10s Assigned Teacher: %-30s with Email: %-30s Host Entity: %-30s",identification,type,title,getArea(),"empty","empty",teacher.getName(),teacher.getEmail(),getHostEntity());
        else if(type.equalsIgnoreCase("T1") && student==null && teacher==null)
            return String.format("Proposal ID: %-5s Type: [%s]Internship   Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10s Assigned Teacher: %-30s with Email: %-30s Host Entity: %-30s",identification,type,title,getArea(),"empty","empty","empty","empty",getHostEntity());

        else if(type.equalsIgnoreCase("T2") && student!=null && teacher!=null)
            return String.format("Proposal ID: %-5s Type: [%s]Project      Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s",identification,type,title,getArea(),student.getName(),student.getStudentNumber(),teacher.getName(),teacher.getEmail());
        else if(type.equalsIgnoreCase("T2") && student==null && teacher!=null)
            return String.format("Proposal ID: %-5s Type: [%s]Project      Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10s Assigned Teacher: %-30s with Email: %-30s",identification,type,title,getArea(),"empty","empty",teacher.getName(),teacher.getEmail());
        else if(type.equalsIgnoreCase("T2") && student!=null && teacher==null)
            return String.format("Proposal ID: %-5s Type: [%s]Project      Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s",identification,type,title,getArea(),student.getName(),student.getStudentNumber(),"empty","empty");
        else if(type.equalsIgnoreCase("T2") && student==null && teacher==null)
            return String.format("Proposal ID: %-5s Type: [%s]Project      Title: %-50s Area: %-13s Assigned Student: %-30s with Number: %-10s Assigned Teacher: %-30s with Email: %-30s",identification,type,title,getArea(),"empty","empty","empty","empty");

        else if(type.equalsIgnoreCase("T3") && student!=null && teacher!=null)
            return String.format("Proposal ID: %-5s Type: [%s]SelfProposed Title: %-50s                     Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s",identification,type,title,student.getName(),student.getStudentNumber(),teacher.getName(),teacher.getEmail());
        else if(type.equalsIgnoreCase("T3") && student!=null && teacher==null)
            return String.format("Proposal ID: %-5s Type: [%s]SelfProposed Title: %-50s                     Assigned Student: %-30s with Number: %-10d Assigned Teacher: %-30s with Email: %-30s",identification,type,title,student.getName(),student.getStudentNumber(),"empty","empty");
        return "";
    }
}
