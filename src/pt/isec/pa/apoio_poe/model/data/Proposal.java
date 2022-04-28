package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class Proposal  implements Serializable {
    private String identification, title, type;

    public Proposal(String identification, String title, String type) {
        this.identification = identification;
        this.title = title;
        this.type = type;
    }

    public String getIdentification() { return identification; }
    public void setIdentification(String identification) { this.identification = identification; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Student getStudent() { return new Student(0,"","","","",0,false); }

    public List<String> getArea() { return null; }

    public String getHostEntity() { return ""; }

    public Teacher getTeacher() { return null; }
    
    @Override
    public String toString() {
        return "Proposal{" +
                "identification=" + identification +
                ", title='" + title + '\'' +
                '}';
    }
}
