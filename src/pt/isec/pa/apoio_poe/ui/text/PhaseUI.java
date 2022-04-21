package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.data.Student;
import pt.isec.pa.apoio_poe.model.fsm.PhaseContext;
import pt.isec.pa.apoio_poe.utils.Input;

import java.io.IOException;
import java.util.Scanner;

public class PhaseUI {
    PhaseContext fsm;       //Tem uma maquina de estados
    boolean finish,finishManagement;         //Flag para saber quando acaba o jogo ou parte dele

    public PhaseUI(PhaseContext fsm){
        this.fsm = fsm;
        this.finish = false;
    }

    public void start() throws IOException {
        while (!finish)                     //Enquanto não acabar
            switch (fsm.getState()){        //Recebe a fase atual
                case PHASE_1 -> firstPhaseUI();
                case PHASE_2 -> secondPhaseUI();
                case PHASE_3 -> thirdPhaseUI();
                case PHASE_4 -> fourthPhaseUI();
                case PHASE_5 -> fifthPhaseUI();
            }
    }

    public void firstPhaseUI() throws IOException {
        /** A primeira fase permite alternar entre os modos de gestão de alunos, docentes e propostas de estágios ou projetos.
         * Inserção, consulta, edição e eliminação dos dados referentes a alunos (modo de gestão de alunos)
         * Inserção, consulta, edição e eliminação dos dados referentes a docentes (modo de gestão de docentes)
         * Inserção, consulta, edição e eliminação dos dados referentes a propostas (modo de gestão de propostas)
         * Fechar a fase
         * Avançar para a fase seguinte*/
        finishManagement=false;
        System.out.print("\n1st Phase");
        switch (Input.chooseOption("Choose the option:","Student management","Teacher management",
                "Management proposals for internships or projects","Close phase","Next phase","Quit")){
            case 1 -> management("student");
            case 2 -> management("teacher");
            case 3 -> management("proposals for internships or projects");
            case 4 -> System.out.println("\tTo be implemented!\n");
            case 5 -> fsm.nextPhase();
            default -> finish = true;
        }
    }

    public void management(String name) throws IOException {
        System.out.print("\n\tManagement "+name);
        long studentNumber;
        while(!finishManagement)
            switch (Input.chooseOption("Choose the option:","Insert "+name,"Show "+name,
                    "Edit "+name,"Delete "+name,"Quit")){
                case 1 -> fsm.addStudents(Input.readString("Filename",false));
                case 2 -> fsm.showStudents();
                case 3 -> {
                    Scanner input = new Scanner(System.in);
                    System.out.print("Enter the student number: ");
                    studentNumber = input.nextLong();
                    boolean everythingEdited=false;
                    while(!everythingEdited)
                        switch (Input.chooseOption("Choose the option:","name", "courseAcronym",
                                "industryAcronym", "classification", "accessInternships", "Quit")){
                            case 1 -> fsm.editStudent(studentNumber,"Sergio",1);
                            case 2 -> fsm.editStudent(studentNumber,"LEI-PL",2);
                            case 3 -> fsm.editStudent(studentNumber,"SI",3);
                            case 4 -> fsm.editStudent(studentNumber,"0.59",4);
                            case 5 -> fsm.editStudent(studentNumber,"true",5);
                            default -> everythingEdited=true;
                        }
                }
                case 4 -> System.out.println("\tTo be implemented!\n");
                default -> finishManagement=true;
            }
    }

    public void secondPhaseUI(){
        /** As funcionalidades disponíveis na fase de candidaturas são as seguintes:
         * Inserção, consulta, edição e eliminação de candidaturas
         * Obtenção de listas de alunos (Com autoproposta, Com candidatura já registada, Sem candidatura registada)
         * Obtenção de listas de propostas de projecto/estágio (autopropostas de alunos, propostas de docentes, Propostas com candidaturas, Propostas sem candidatura)
         * Fechar a fase
         * Regresso à fase anterior
         * Avançar para a fase seguinte*/
        System.out.print("\n2nd Phase");
        switch (Input.chooseOption("Choose the option:","Insert applications", "Consult applications",
                "Edit applications", "Delete applications", "Get the list of students","Get lists of project/internship proposals",
                "Close phase","Return to previous phase","Next phase","Quit")){
            case 1, 2, 3, 4, 5, 6, 7 -> System.out.println("\tTo be implemented!\n");
            case 8 -> fsm.previousPhase();
            case 9 -> fsm.nextPhase();
            default -> finish=true;
        }
    }

    public void thirdPhaseUI(){
        /** As funcionalidades disponíveis na fase de atribuição de propostas são as seguintes:
            * Atribuição automática das autopropostas ou propostas de docentes com aluno associado
            * Atribuição automática de uma proposta disponível aos alunos ainda sem atribuições
            * Atribuição manual de propostas disponíveis aos alunos sem atribuição ainda definida.
            * Remoção manual de uma atribuição
            * A gestão manual deverá permitir operações de undo e redo.
            * Obtenção de listas de alunos
            * Obtenção de listas de propostas de projecto estágio
            * Fechar a fase
            * Regresso à fase anterior para consulta de dados ou realizar alterações
            * Avançar para a fase seguinte*/
        System.out.print("\n3rd Phase");
        switch (Input.chooseOption("Choose the option:","Automatically assign self-proposals or proposals from teachers with an associated student",
                "Automatically assign a proposal without assignments","Manual assignment of proposals","Manually removing an assignment",
                "Undo or redo", "Get student lists","Get lists of internship project proposals","Close phase","Return to previous phase","Next phase","Quit")){
            case 1, 2, 3, 4, 5, 6, 7, 8 -> System.out.println("\tTo be implemented!\n");
            case 9 -> fsm.previousPhase();
            case 10 -> fsm.nextPhase();
            default -> finish=true;
        }
    }

    public void fourthPhaseUI(){
        /** As funcionalidades disponíveis na fase de atribuição de orientadores são as seguintes:
            * Associação automática dos docentes
            * Atribuição, consulta, alteração e eliminação de um orientador
            * Undo e redo
            * Obtenção de listas sobre atribuições de orientadores
            * Fechar fase
            * Regresso à fase anterior*/
        System.out.print("\n4th Phase");
        switch (Input.chooseOption("Choose the option:","Automatically associate teachers","Assign advisor",
                "Consult advisor","Change advisor","Delete advisor","Undo or redo","Get lists of advisors assignments",
                "Close phase","Return to previous phase","Quit")){
            case 1, 2, 3, 4, 5, 6, 7 -> System.out.println("\tTo be implemented!\n");
            case 8 -> /*Fechar a fase*/ fsm.nextPhase();
            case 9 -> fsm.previousPhase();
            default -> finish=true;
        }
    }

    public void fifthPhaseUI(){
        /** As funcionalidades disponíveis na fase de consulta são as seguintes:
            * Obter lista de estudantes com propostas
            * Obter lista de estudantes sem propostas
            * Obter lista de propostas disponíveis
            * Obter lista de propostas atribuídas
            * Número de orientações por docente*/
        System.out.print("\n5th Phase");
        switch (Input.chooseOption("Choose the option:","Get list of students with proposals","Get list of students without proposals",
                "Get list of available proposals","Get list of assigned proposals","Number of projects and internships per professor","Quit")){
            case 1, 2, 3, 4, 5 -> System.out.println("\tTo be implemented!\n");
            default -> finish=true;
        }
    }
}
