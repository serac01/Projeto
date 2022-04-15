package pt.isec.pa.apoio_poe.model.data;

public class Proposal {
    private String identification, title;

    public Proposal(String identification, String title) {
        this.identification = identification;
        this.title = title;
    }

    public String getIdentification() { return identification; }

    public void setIdentification(String identification) { this.identification = identification; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
    
        @Override
    public String toString() {
        return "Proposal{" +
                "identification=" + identification +
                ", title='" + title + '\'' +
                '}';
    }
}
