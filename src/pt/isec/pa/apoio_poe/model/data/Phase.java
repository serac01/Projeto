package pt.isec.pa.apoio_poe.model.data;

import java.io.Serializable;

public class Phase implements Serializable {
    private int currentPhase;

    public Phase(int currentPhase){this.currentPhase=currentPhase;}

    public int getCurrentPhase() { return currentPhase; }

    public void setCurrentPhase(int currentPhase) { this.currentPhase = currentPhase; }

    @Override
    public String toString() { return "Phase: currentPhase=" + currentPhase; }
}
