package pt.isec.pa.apoio_poe;

import javafx.application.Application;
import pt.isec.pa.apoio_poe.ui.gui.MainJFX;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //ManagementPoE fsm = new ManagementPoE();
        //PhaseUI ui = new PhaseUI(fsm);
        //ui.start();

        Application.launch(MainJFX.class,args);
    }
}
