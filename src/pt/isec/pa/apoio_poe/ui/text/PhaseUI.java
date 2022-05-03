package pt.isec.pa.apoio_poe.ui.text;

import pt.isec.pa.apoio_poe.model.fsm.PhaseContext;
import pt.isec.pa.apoio_poe.utils.Input;
import java.io.IOException;

public class PhaseUI {
    PhaseContext fsm;
    boolean finish;

    public PhaseUI(PhaseContext fsm){
        this.fsm = fsm;
        this.finish = false;
    }

    public void start() throws IOException {
        switch (Input.chooseOption("Choose the option:","load a state","Quit")) {
            case 1 -> fsm.deserialization(Input.readString("Filename ",false));
        }
        while (!finish)
            switch (fsm.getState()){
                case PHASE_1 -> firstPhaseUI();
                case PHASE_2 -> secondPhaseUI();
                case PHASE_3 -> thirdPhaseUI();
                case PHASE_4 -> fourthPhaseUI();
                case PHASE_5 -> fifthPhaseUI();
            }
    }

    public void firstPhaseUI() throws IOException {
        System.out.print("\n1st Phase");
        if(fsm.isClosed())
            switch (Input.chooseOption("Choose the option:","Student management","Teacher management",
                    "Management proposals for internships or projects","Save the sate","Next phase","Quit")){
                case 1 -> management("student");
                case 2 -> management("teacher");
                case 3 -> management("proposals for internships or projects");
                case 4 -> fsm.serialization(Input.readString("Filename ",false));
                case 5 -> fsm.nextPhase();
                default -> finish = true;
            }
        else
            switch (Input.chooseOption("Choose the option:","Student management","Teacher management",
                    "Management proposals for internships or projects","Close phase","Next phase","Save the state","Quit")){
                case 1 -> management("student");
                case 2 -> management("teacher");
                case 3 -> management("proposals for internships or projects");
                case 4 -> System.out.println(fsm.closePhase());
                case 5 -> fsm.nextPhase();
                case 6 -> fsm.serialization(Input.readString("Filename ",false));
                default -> finish = true;
            }
    }

    public void management(String name) throws IOException {
        System.out.print("\n\tManagement "+name);
        boolean finishManagement=false;
        long studentNumber;
        while(!finishManagement)
            if(fsm.isClosed())
                switch (Input.chooseOption("Choose the option:","Show "+name,"Export "+name,"Quit")){
                    case 1 ->  {
                        if(name.equalsIgnoreCase("student"))
                            System.out.println(fsm.showStudents());
                        else if(name.equalsIgnoreCase("teacher"))
                            System.out.println(fsm.showTeachers());
                        else if(name.equalsIgnoreCase("proposals for internships or projects"))
                            System.out.println(fsm.showProposals());
                    }
                    case 2 -> {
                        if(name.equalsIgnoreCase("student"))
                            fsm.exportStudents(Input.readString("Filename ",false));
                        else if(name.equalsIgnoreCase("teacher"))
                            fsm.exportTeachers(Input.readString("Filename ",false));
                        else if(name.equalsIgnoreCase("proposals for internships or projects"))
                            fsm.exportProposals(Input.readString("Filename ",false));
                    }
                    default -> finishManagement=true;
                }
            else
                switch (Input.chooseOption("Choose the option:","Insert "+name,"Show "+name,
                        "Edit "+name,"Delete "+name,"Export "+name,"Quit")){
                    case 1 -> {
                        if(name.equalsIgnoreCase("student"))
                            System.out.println(fsm.addStudents(Input.readString("Filename ",false)));
                        else if(name.equalsIgnoreCase("teacher"))
                            System.out.println(fsm.addTeachers(Input.readString("Filename ",false)));
                        else if(name.equalsIgnoreCase("proposals for internships or projects"))
                            System.out.println(fsm.addProposals(Input.readString("Filename ",false)));
                    }
                    case 2 ->  {
                        if(name.equalsIgnoreCase("student"))
                            System.out.println(fsm.showStudents());
                        else if(name.equalsIgnoreCase("teacher"))
                            System.out.println(fsm.showTeachers());
                        else if(name.equalsIgnoreCase("proposals for internships or projects"))
                            System.out.println(fsm.showProposals());
                    }
                    case 3 -> {
                        if(name.equalsIgnoreCase("student")){
                            System.out.println("Enter student number");
                            studentNumber = (long) Input.readNumber(" ");
                            boolean everythingEdited=false;
                            while(!everythingEdited)
                                switch (Input.chooseOption("Choose the option:","Edit name", "Edit course acronym",
                                        "Edit industry acronym", "Edit classification", "Edit access internships", "Quit")){
                                    case 1 -> System.out.print(fsm.editStudent(studentNumber,Input.readString("New name ",false),1));
                                    case 2 -> System.out.print(fsm.editStudent(studentNumber,Input.readString("New course acronym ",true),2));
                                    case 3 -> System.out.print(fsm.editStudent(studentNumber,Input.readString("New industry acronym ",true),3));
                                    case 4 -> System.out.print(fsm.editStudent(studentNumber,Input.readString("New classification",true),4));
                                    case 5 -> System.out.print(fsm.editStudent(studentNumber,Input.readString("Access internships [true/false] ",true),5));
                                    default -> everythingEdited=true;
                                }
                        }else if(name.equalsIgnoreCase("teacher")){
                            System.out.println("Enter teacher email");
                            String email = Input.readString("Email ",true);
                            boolean everythingEdited=false;
                            while(!everythingEdited)
                                if (Input.chooseOption("Choose the option:", "Edit name", "Quit") == 1)
                                    System.out.print(fsm.editTeacher(email, Input.readString("New name ", false)));
                                else
                                    everythingEdited = true;
                        }
                    }
                    case 4 -> {
                        if(name.equalsIgnoreCase("student"))
                            System.out.println(fsm.deleteStudents((long) Input.readNumber("Enter student number ")));
                        else if(name.equalsIgnoreCase("teacher"))
                            System.out.println(fsm.deleteTeachers(Input.readString("Enter teacher email ",false)));
                        else if(name.equalsIgnoreCase("proposals for internships or projects"))
                            System.out.println(fsm.deleteProposals(Input.readString("Enter proposal id ",false)));
                    }
                    case 5 -> {
                        if(name.equalsIgnoreCase("student"))
                            fsm.exportStudents(Input.readString("Filename ",false));
                        else if(name.equalsIgnoreCase("teacher"))
                            fsm.exportTeachers(Input.readString("Filename ",false));
                        else if(name.equalsIgnoreCase("proposals for internships or projects"))
                            fsm.exportProposals(Input.readString("Filename ",false));
                    }
                    default -> finishManagement=true;
                }
    }

    public void secondPhaseUI() throws IOException {
        System.out.print("\n2nd Phase");
        if(fsm.isClosed())
            switch (Input.chooseOption("Choose the option:","Consult applications", "Export applications",
                    "Get the list of students","Get lists of project/internship proposals",
                    "Return to previous phase","Next phase","Save the sate","Quit")){
                case 1 -> System.out.println(fsm.showApplications());
                case 2 -> fsm.exportApplications(Input.readString("Filename ",false));
                case 3 -> {
                    boolean allChosen=false;
                    while(!allChosen)
                        switch (Input.chooseOption("Choose the option:","Self-proposal", "With already registered application", "Without registered application", "Quit")){
                            case 1 -> System.out.println(fsm.generateStudentList(true,false,false));
                            case 2 -> System.out.println(fsm.generateStudentList(false,true,false));
                            case 3 -> System.out.println(fsm.generateStudentList(false,false,true));
                            default -> allChosen=true;
                        }
                }
                case 4 -> {
                    boolean allChosen=false, selfProposed=false,proposeTeacher=false,withApplications=false,withoutApplications=false;
                    while(!allChosen) {
                        switch (Input.chooseOption("Choose the option:", "self-proposals from students",
                                "proposals from teachers", "Proposals with applications",
                                "Proposals without applications", "Generate the list", "Quit")) {
                            case 1 -> selfProposed=Input.changeBoolean(selfProposed);
                            case 2 -> proposeTeacher=Input.changeBoolean(proposeTeacher);
                            case 3 -> withApplications=Input.changeBoolean(withApplications);
                            case 4 -> withoutApplications=Input.changeBoolean(withoutApplications);
                            case 5 -> System.out.println(fsm.generateProposalsList(selfProposed, proposeTeacher, withApplications, withoutApplications));
                            default -> allChosen = true;
                        }
                    }
                }
                case 5 -> fsm.previousPhase();
                case 6 -> fsm.nextPhase();
                case 7 -> fsm.serialization(Input.readString("Filename ",false));
                default -> finish=true;
            }
        else
            switch (Input.chooseOption("Choose the option:","Insert applications", "Consult applications",
                    "Edit applications", "Delete applications", "Export applications", "Get the list of students","Get lists of project/internship proposals",
                    "Close phase","Return to previous phase","Next phase","Save the sate","Quit")){
                case 1 -> System.out.println(fsm.addApplications(Input.readString("Filename ",false)));
                case 2 -> System.out.println(fsm.showApplications());
                case 3 -> {
                    long studentNumber = (long) Input.readNumber("Enter student number ");
                    boolean everythingEdited=false;
                    while(!everythingEdited)
                        switch (Input.chooseOption("Choose the option:","New proposal", "Delete proposal",  "Quit")){
                            case 1 -> System.out.print(fsm.editApplication(studentNumber,Input.readString("Proposal id ",false),1));
                            case 2 -> System.out.print(fsm.editApplication(studentNumber,Input.readString("Proposal id ",false),2));
                            default -> everythingEdited=true;
                        }
                }
                case 4 -> {
                    long studentNumber = (long) Input.readNumber("Enter student number ");
                    System.out.print(fsm.deleteApplication(studentNumber));}
                case 5 -> fsm.exportApplications(Input.readString("Filename ",false));
                case 6 -> {
                    boolean allChosen=false;
                    while(!allChosen)
                        switch (Input.chooseOption("Choose the option:","Self-proposal", "With already registered application", "Without registered application", "Quit")){
                            case 1 -> System.out.println(fsm.generateStudentList(true,false,false));
                            case 2 -> System.out.println(fsm.generateStudentList(false,true,false));
                            case 3 -> System.out.println(fsm.generateStudentList(false,false,true));
                            default -> allChosen=true;
                        }
                }
                case 7 -> {
                    boolean allChosen=false, selfProposed=false,proposeTeacher=false,withApplications=false,withoutApplications=false;
                    while(!allChosen) {
                        switch (Input.chooseOption("Choose the option:", "self-proposals from students",
                                "proposals from teachers", "Proposals with applications",
                                "Proposals without applications", "Generate the list", "Quit")) {
                            case 1 -> selfProposed=Input.changeBoolean(selfProposed);
                            case 2 -> proposeTeacher=Input.changeBoolean(proposeTeacher);
                            case 3 -> withApplications=Input.changeBoolean(withApplications);
                            case 4 -> withoutApplications=Input.changeBoolean(withoutApplications);
                            case 5 -> System.out.println(fsm.generateProposalsList(selfProposed, proposeTeacher, withApplications, withoutApplications));
                            default -> allChosen = true;
                        }
                    }
                }
                case 8 -> System.out.println(fsm.closePhase());
                case 9 -> fsm.previousPhase();
                case 10 -> fsm.nextPhase();
                case 11 -> fsm.serialization(Input.readString("Filename ",false));
                default -> finish=true;
            }
    }

    public void thirdPhaseUI() throws IOException {
        System.out.print("\n3rd Phase");
        if(fsm.isClosed())
            switch (Input.chooseOption("Choose the option:",
                    "Get student lists","Get lists of internship project proposals","Save the state","Export data","Return to previous phase","Next phase","Quit")){
                case 1 -> {
                    boolean allChosen=false;
                    while(!allChosen)
                        switch (Input.chooseOption("Choose the option:","Have an associated self-proposal",
                                "Have an application already registered","Have a proposal assigned", "Don't have any proposal attributed", "Quit")){
                            case 1 -> System.out.println(fsm.generateListProposalStudents(true,false,false,false));
                            case 2 -> System.out.println(fsm.generateListProposalStudents(false,true,false,false));
                            case 3 -> System.out.println(fsm.generateListProposalStudents(false,false,true,false));
                            case 4 -> System.out.println(fsm.generateListProposalStudents(false,false,false,true));
                            default -> allChosen=true;
                        }
                }
                case 2 -> {
                    boolean allChosen=false, selfProposed=false,proposeTeacher=false,withProposals=false,withoutProposals=false;
                    while(!allChosen) {
                        switch (Input.chooseOption("Choose the option:", "Student self-proposals",
                                "Teachers proposals", "Available proposals", "Assigned proposals", "Generate the list", "Quit")) {
                            case 1 -> selfProposed=Input.changeBoolean(selfProposed);
                            case 2 -> proposeTeacher=Input.changeBoolean(proposeTeacher);
                            case 3 -> withProposals=Input.changeBoolean(withProposals);
                            case 4 -> withoutProposals=Input.changeBoolean(withoutProposals);
                            case 5 -> System.out.println(fsm.generateListProposalPhase3(selfProposed, proposeTeacher, withProposals, withoutProposals));
                            default -> allChosen = true;
                        }
                    }
                }
                case 3 -> fsm.serialization(Input.readString("Filename ",false));
                case 4 -> fsm.exportProposals(Input.readString("Filename ",false));
                case 5 -> fsm.previousPhase();
                case 6 -> fsm.nextPhase();
                default -> finish=true;
            }
        else
            switch (Input.chooseOption("Choose the option:","Automatically assign self-proposals or proposals from teachers with an associated student",
                    "Automatically assign a proposal without assignments","Manual assignment of proposals","Manually removing an assignment",
                    "Get student lists","Get lists of internship project proposals","Save the state","Export data","Close phase","Return to previous phase","Next phase","Quit")){
                case 1 -> System.out.println(); //It is already done in the way the project was structured
                case 2 -> System.out.println(fsm.assignAProposalWithoutAssignments());
                case 3 -> System.out.println(fsm.associateProposalToStudents(Input.readString("Proposal id: ",true),(long) Input.readNumber("Enter student number ")));
                case 4 -> System.out.println(fsm.removeStudentFromProposal(Input.readString("Proposal id: ",true)));
                case 5 -> {
                    boolean allChosen=false;
                    while(!allChosen)
                        switch (Input.chooseOption("Choose the option:","Have an associated self-proposal",
                                "Have an application already registered","Have a proposal assigned", "Don't have any proposal attributed", "Quit")){
                            case 1 -> System.out.println(fsm.generateListProposalStudents(true,false,false,false));
                            case 2 -> System.out.println(fsm.generateListProposalStudents(false,true,false,false));
                            case 3 -> System.out.println(fsm.generateListProposalStudents(false,false,true,false));
                            case 4 -> System.out.println(fsm.generateListProposalStudents(false,false,false,true));
                            default -> allChosen=true;
                        }
                }
                case 6 -> {
                    boolean allChosen=false, selfProposed=false,proposeTeacher=false,withProposals=false,withoutProposals=false;
                    while(!allChosen) {
                        switch (Input.chooseOption("Choose the option:", "Student self-proposals",
                                "Teachers proposals", "Available proposals", "Assigned proposals", "Generate the list", "Quit")) {
                            case 1 -> selfProposed=Input.changeBoolean(selfProposed);
                            case 2 -> proposeTeacher=Input.changeBoolean(proposeTeacher);
                            case 3 -> withProposals=Input.changeBoolean(withProposals);
                            case 4 -> withoutProposals=Input.changeBoolean(withoutProposals);
                            case 5 -> System.out.println(fsm.generateListProposalPhase3(selfProposed, proposeTeacher, withProposals, withoutProposals));
                            default -> allChosen = true;
                        }
                    }
                }
                case 7 -> fsm.serialization(Input.readString("Filename ",false));
                case 8 -> fsm.exportProposals(Input.readString("Filename ",false));
                case 9 -> System.out.println(fsm.closePhase());
                case 10 -> fsm.previousPhase();
                case 11 -> fsm.nextPhase();
                default -> finish=true;
            }
    }

    public void fourthPhaseUI() throws IOException {
        System.out.print("\n4th Phase");
        if(fsm.isClosed())
            switch (Input.chooseOption("Choose the option:", "Consult advisor","See some data about the advisors assignments",
                    "Save the state", "Export the data", "Next phase","Return to previous phase","Quit")){
                case 1 -> System.out.println(fsm.consultAdvisor(Input.readString("Teacher id: ",true)));
                case 2 -> {
                    boolean allChosen=false;
                    while(!allChosen)
                        switch (Input.chooseOption("Choose the option:","list of students with a proposal and assigned advisor",
                                "List of students with an assigned proposal but without an associated supervisor",
                                "Number of orientations per professor, on average, minimum, maximum, and per specified professor", "Quit")){
                            case 1 -> System.out.println(fsm.generateListAdvisors(true,false,false));
                            case 2 -> System.out.println(fsm.generateListAdvisors(false,true,false));
                            case 3 -> System.out.println(fsm.generateListAdvisors(false,false,true));
                            default -> allChosen=true;
                        }
                }
                case 3 -> fsm.serialization(Input.readString("Filename ",false));
                case 4 -> fsm.exportProposals(Input.readString("Filename ",false));
                case 5 -> fsm.nextPhase();
                case 6 -> fsm.previousPhase();
                default -> finish=true;
            }
        else
            switch (Input.chooseOption("Choose the option:","Automatically associate teachers","Assign advisor",
                    "Consult advisor","Change advisor","Delete advisor","See some data about the advisors assignments","Save the state",
                    "Export the data","Close phase","Return to previous phase","Next phase","Quit")){
                case 1 -> System.out.println(); //Done
                case 2 -> System.out.println(fsm.assignAdvisor(Input.readString("Proposal id: ",true), Input.readString("Teacher id: ",true)));
                case 3 -> System.out.println(fsm.consultAdvisor(Input.readString("Teacher id: ",true)));
                case 4 -> System.out.println(fsm.changeAdvisor(Input.readString("Teacher id: ",true),Input.readString("Proposal id: ",true)));
                case 5 -> System.out.println(fsm.deleteAdvisor(Input.readString("Proposal id: ",false)));
                case 6 -> {
                    boolean allChosen=false;
                    while(!allChosen)
                        switch (Input.chooseOption("Choose the option:","list of students with a proposal and assigned advisor",
                                "List of students with an assigned proposal but without an associated supervisor",
                                "Number of orientations per professor, on average, minimum, maximum, and per specified professor", "Quit")){
                            case 1 -> System.out.println(fsm.generateListAdvisors(true,false,false));
                            case 2 -> System.out.println(fsm.generateListAdvisors(false,true,false));
                            case 3 -> System.out.println(fsm.generateListAdvisors(false,false,true));
                            default -> allChosen=true;
                        }
                }
                case 7 -> fsm.serialization(Input.readString("Filename ",false));
                case 8 -> fsm.exportProposals(Input.readString("Filename ",false));
                case 9 -> fsm.closePhase();
                case 10 -> fsm.previousPhase();
                case 11 -> fsm.nextPhase();
                default -> finish=true;
            }
    }

    public void fifthPhaseUI() throws IOException {
        System.out.print("\n5th Phase");
        switch (Input.chooseOption("Choose the option:","Get list of students with proposals","Get list of students without proposals",
                "Get list of assigned proposals","Get list of available proposals","Number of projects and internships per professor","Export the data","Quit")){
            case 1 -> System.out.println(fsm.listPhase5(true,false,false,false,false));
            case 2 -> System.out.println(fsm.listPhase5(false,true,false,false,false));
            case 3 -> System.out.println(fsm.listPhase5(false,false,true,false,false));
            case 4 -> System.out.println(fsm.listPhase5(false,false,false,true,false));
            case 5 -> System.out.println(fsm.listPhase5(false,false,false,false,true));
            case 6 -> fsm.exportProposals(Input.readString("Filename ",false));
            default -> finish=true;
        }
    }
}
