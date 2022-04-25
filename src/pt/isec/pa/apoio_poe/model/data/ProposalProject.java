package pt.isec.pa.apoio_poe.model.data;

import java.util.List;

public class ProposalProject extends Proposal{
    private List<String> area;
    private String teacherEmail;
    private long studentNumber;


    public ProposalProject(String identification, String title, List<String> area, String teacherEmail, long studentNumber) {
        super(identification, title, "T2");
        this.area = area;
        this.teacherEmail=teacherEmail;
        this.studentNumber=studentNumber;
    }

    @Override
    public List<String> getArea() { return area; }
    public void setArea(List<String> area) { this.area = area; }

    @Override
    public long getStudentNumber() { return studentNumber; }

    @Override
    public String getTeacherEmail() { return teacherEmail; }

    /*@Override
    public String toString() {
        return "Proposal{" +
                "identification=" + identification +
                ", title='" + title + '\'' +
                ", area='" + area + '\'' +
                '}';
    }*/
}
