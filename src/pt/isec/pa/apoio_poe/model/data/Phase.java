package pt.isec.pa.apoio_poe.model.data;

public class Phase {
    private int currentPhase;
    private ManagementPoE management;

    public Phase(int currentPhase){this.currentPhase=currentPhase;}

    public int getCurrentPhase() { return currentPhase; }

    public void setCurrentPhase(int currentPhase) { this.currentPhase = currentPhase; }

    @Override
    public String toString() { return "Phase: currentPhase=" + currentPhase; }
}
