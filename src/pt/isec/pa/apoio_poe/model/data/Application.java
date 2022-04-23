package pt.isec.pa.apoio_poe.model.data;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private long studentNumber;
    private List<String> idProposals;


    public Application(long studentNumber, List<String> idProposals) {
        this.studentNumber = studentNumber;
        this.idProposals = idProposals;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public List<String> getIdProposals() {
        return idProposals;
    }

    public void setIdProposals(List<String> idProposals) { this.idProposals = idProposals; }

    @Override
    public String toString() {
        System.out.print("Number: "+studentNumber);
        for (String s : idProposals)
            System.out.print(" "+s);
        return "";
    }
}
