package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.fsm.PhaseContext;
import pt.isec.pa.apoio_poe.utils.Input;

public class PhaseUI {
    PhaseContext fsm;
    boolean finish=false;

    public PhaseUI(PhaseContext fsm){ this.fsm = fsm; }

    public void start(){
        int op;
        while (!finish)
            switch (fsm.getState()){
                case PHASE_1 -> firstPhaseUI();
                case PHASE_2 -> secondPhaseUI();
            }
    }

    public void firstPhaseUI(){
        int op;
        System.out.println("1st Phase");
        op = Input.chooseOption("Phase","next","Quit");
        switch (op){
            case 1 -> fsm.nextPhase();
            case 2 -> finish=true;
        }
    }

    public void secondPhaseUI(){
        int op;
        System.out.println("2st Phase");
        op = Input.chooseOption("Phase","next","previous","Quit");
        switch (op){
            case 1 -> fsm.nextPhase();
            case 2 -> fsm.previousPhase();
            case 3 -> finish=true;
        }
    }
}
