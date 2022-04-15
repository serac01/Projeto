package pt.isec.pa.apoio_poe;

import pt.isec.pa.apoio_poe.model.fsm.PhaseContext;
import pt.isec.pa.apoio_poe.ui.text.PhaseUI;

public class Main {
    public static void main(String[] args){
        PhaseContext fsm = new PhaseContext();
        PhaseUI ui = new PhaseUI(fsm);
        ui.start();
    }
}
