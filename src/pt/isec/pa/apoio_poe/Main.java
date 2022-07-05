package pt.isec.pa.apoio_poe;

import javafx.application.Application;
import pt.isec.pa.apoio_poe.ui.gui.MainJFX;
import pt.isec.pa.apoio_poe.model.ManagementPoE;
import pt.isec.pa.apoio_poe.ui.text.PhaseUI;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        /*ManagementPoE fsm = new ManagementPoE();
        PhaseUI ui = new PhaseUI(fsm);
        ui.start();*/

        Application.launch(MainJFX.class,args);
    }
}
