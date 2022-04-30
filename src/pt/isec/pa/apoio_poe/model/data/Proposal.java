package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;
import java.util.List;

public class Proposal  implements Serializable {
    private final String identification, title, type;

    public Proposal(String identification, String title, String type) {
        this.identification = identification;
        this.title = title;
        this.type = type;
    }

    public String getIdentification() { return identification; }

    public String getTitle() { return title; }

    public String getType() { return type; }

    public Student getStudent() { return null; }

    public List<String> getArea() { return null; }

    public String getHostEntity() { return ""; }

    public Teacher getTeacher() { return null; }
}
