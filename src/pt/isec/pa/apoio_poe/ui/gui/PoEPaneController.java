package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import pt.isec.pa.apoio_poe.model.ManagementPoE;
import pt.isec.pa.apoio_poe.utils.Input;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PoEPaneController {
    ManagementPoE model;
    @FXML
    private VBox root;
    @FXML
    private ListView lvShowStudents, lvShowTeachers, lvShowProposal, lvShowApplications, lvShowPhase3, lvShowPhase4;

    public void init(ManagementPoE model) { this.model = model; }

    /************************************** Home & States **************************************/
    @FXML
    public void onLoadState() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\serco\\Desktop\\A Minha Universidade\\PA\\projeto"));
        int response = fileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            model.serialization(file.getAbsolutePath());
        }
        onUpdateWindow();
    }
    @FXML
    public void onStart() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/pane_phase_1.fxml"));
        VBox pane = loader.load();
        PoEPaneController controller = loader.getController();
        controller.init(model);
        root.getChildren().setAll(pane);
    }
    @FXML
    public void onQuit() { Platform.exit(); }
    @FXML
    public void onClosePhase() { model.closePhase(); }
    @FXML
    public void onNextPhase() throws IOException {
        model.nextPhase();
        update();
    }
    @FXML
    public void onPreviousPhase() throws IOException {
        model.previousPhase();
        update();
    }
    private void update() throws IOException {
        switch (model.getState()){
            case PHASE_1 -> onStart();
            case STUDENT_PHASE -> onStudentManagement();
            case TEACHER_PHASE -> onTeacherManagement();
            case PROPOSAL_PHASE -> onProposalsManagement();
            case PHASE_2 -> onPhase2Management();
            case PHASE_3 -> onPhase3Management();
            case PHASE_4 -> onPhase4Management();
            case PHASE_5 -> onPhase5Management();
        }
    }
    public void onUpdateWindow() throws IOException {
        switch (model.getState()){
            case PHASE_1 -> onStart();
            case STUDENT_PHASE -> showStudentsOnScreen();
            case TEACHER_PHASE -> showTeachersOnScreen();
            case PROPOSAL_PHASE -> showProposalsOnScreen();
            case PHASE_2 -> showApplicationsOnScreen();
        }
    }

    /************************************** Toolbar **************************************/
    @FXML
    public void onExportState() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if(selectedDirectory != null) {
            Dialog<Object> dialog = new Dialog<>();
            TextField tfSaveState = new TextField();dialog.setTitle("Save the State");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            tfSaveState.setPromptText("Filename");
            dialogPane.setContent(new VBox(8, tfSaveState));
            dialog.setResultConverter((ButtonType button) -> {
                if (button == ButtonType.OK) {
                    if(tfSaveState.getText().trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                        alert.showAndWait();
                    }else
                        model.serialization(selectedDirectory.getAbsolutePath() + "\\" + tfSaveState.getText());
                }
                return null;
            });
            dialog.showAndWait();
        }
    }
    @FXML
    public void onUndo() throws IOException {
        if (model.hasUndo()) {
            model.undo();
            onUpdateWindow();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No undo available at the moment", ButtonType.OK);
            alert.showAndWait();
        }
    }
    @FXML
    public void onRedo() throws IOException {
        if (model.hasRedo()) {
            model.redo();
            onUpdateWindow();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No redo available at the moment", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /************************************** First Phase **************************************/
    @FXML
    public void onStudentManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/pane_phase_student.fxml"));
        VBox pane = loader.load();
        PoEPaneController controller = loader.getController();
        controller.init(model);
        root.getChildren().setAll(pane);
        model.changeToStudent();
    }
    @FXML
    public void onTeacherManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/pane_phase_teacher.fxml"));
        VBox pane = loader.load();
        PoEPaneController controller = loader.getController();
        controller.init(model);
        root.getChildren().setAll(pane);
        model.changeToTeacher();
    }
    @FXML
    public void onProposalsManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/pane_phase_proposal.fxml"));
        VBox pane = loader.load();
        PoEPaneController controller = loader.getController();
        controller.init(model);
        root.getChildren().setAll(pane);
        model.changeToProposals();
    }
    @FXML
    public void onPhase2Management() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/pane_phase_2.fxml"));
        VBox pane = loader.load();
        PoEPaneController controller = loader.getController();
        controller.init(model);
        root.getChildren().setAll(pane);
    }
    @FXML
    public void onPhase3Management() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/pane_phase_3.fxml"));
        VBox pane = loader.load();
        PoEPaneController controller = loader.getController();
        controller.init(model);
        root.getChildren().setAll(pane);
    }
    @FXML
    public void onPhase4Management() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/pane_phase_4.fxml"));
        VBox pane = loader.load();
        PoEPaneController controller = loader.getController();
        controller.init(model);
        root.getChildren().setAll(pane);
    }
    @FXML
    public void onPhase5Management() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/pane_phase_5.fxml"));
        VBox pane = loader.load();
        PoEPaneController controller = loader.getController();
        controller.init(model);
        root.getChildren().setAll(pane);
    }

    /************************************** Students Phase **************************************/
    @FXML
    public void onInsertStudent() throws IOException {
        String x = "";
        /*JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\serco\\Desktop\\A Minha Universidade\\PA\\projeto"));
        int response = fileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION)*/
        x = model.addStudents("");//fileChooser.getSelectedFile().getAbsolutePath()

        if(!x.equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
            alert.showAndWait();
        }
        showStudentsOnScreen();
    }
    @FXML
    public void onEditStudent() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfStudentNumber = new TextField();
        TextField tfNewEdit = new TextField();
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(Arrays.asList("Name","Course acronym","Industry acronym","Classification","Access internships")));
        dialog.setTitle("Edit Student");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfStudentNumber.setPromptText("Student number");
        tfNewEdit.setPromptText("Change here");
        comboBox.getSelectionModel().selectFirst();
        dialogPane.setContent(new VBox(8, tfStudentNumber, comboBox, tfNewEdit));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfStudentNumber.getText().trim().isEmpty() || tfNewEdit.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    if (comboBox.getValue().equalsIgnoreCase("Name"))
                        x = model.editStudent(Long.parseLong(tfStudentNumber.getText()), tfNewEdit.getText(), 1);
                    else if (comboBox.getValue().equalsIgnoreCase("Course acronym"))
                        x = model.editStudent(Long.parseLong(tfStudentNumber.getText()), tfNewEdit.getText(), 2);
                    else if (comboBox.getValue().equalsIgnoreCase("Industry acronym"))
                        x = model.editStudent(Long.parseLong(tfStudentNumber.getText()), tfNewEdit.getText(), 3);
                    else if (comboBox.getValue().equalsIgnoreCase("Classification"))
                        x = model.editStudent(Long.parseLong(tfStudentNumber.getText()), tfNewEdit.getText(), 4);
                    else if (comboBox.getValue().equalsIgnoreCase("Access internships"))
                        x = model.editStudent(Long.parseLong(tfStudentNumber.getText()), tfNewEdit.getText(), 5);
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
        showStudentsOnScreen();
    }
    @FXML
    public void onDeleteStudent() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfStudentNumber = new TextField();dialog.setTitle("Delete Student");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfStudentNumber.setPromptText("Student number");
        dialogPane.setContent(new VBox(8, tfStudentNumber));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfStudentNumber.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.deleteStudents(Long.parseLong(tfStudentNumber.getText()));
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
        showStudentsOnScreen();
    }
    @FXML
    public void onExportStudent() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if(selectedDirectory != null) {
            Dialog<Object> dialog = new Dialog<>();
            TextField tfStudentNumber = new TextField();dialog.setTitle("Export Student");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            tfStudentNumber.setPromptText("Filename");
            dialogPane.setContent(new VBox(8, tfStudentNumber));
            dialog.setResultConverter((ButtonType button) -> {
                if (button == ButtonType.OK) {
                    if(tfStudentNumber.getText().trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                        alert.showAndWait();
                    }else {
                        try {
                            model.exportStudents(selectedDirectory.getAbsolutePath() + "\\" + tfStudentNumber.getText() + ".csv");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            });
            dialog.showAndWait();
        }
    }
    public void showStudentsOnScreen() {
        if(model.showStudents().isEmpty())
            return;
        lvShowStudents.getItems().clear();
        for(String s : model.showStudents())
            lvShowStudents.getItems().add(s);
    }

    /************************************** Teachers Phase **************************************/
    @FXML
    public void onInsertTeachers() throws IOException {
        String x = "";
        /*JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\serco\\Desktop\\A Minha Universidade\\PA\\projeto"));
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)*/
        x = model.addTeachers("");//fileChooser.getSelectedFile().getAbsolutePath()

        if(!x.equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
            alert.showAndWait();
        }
        showTeachersOnScreen();
    }
    @FXML
    public void onEditTeachers() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfTeachersEmail = new TextField();
        TextField tfNewName = new TextField();
        dialog.setTitle("Edit Teacher");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfTeachersEmail.setPromptText("Teacher's email");
        tfNewName.setPromptText("New name");
        dialogPane.setContent(new VBox(8, tfTeachersEmail, tfNewName));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfTeachersEmail.getText().trim().isEmpty() || tfNewName.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.editTeacher(tfTeachersEmail.getText(), tfNewName.getText());
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
        showTeachersOnScreen();
    }
    @FXML
    public void onDeleteTeachers() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfTeachersEmail = new TextField();dialog.setTitle("Delete Teachers");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfTeachersEmail.setPromptText("Teacher's email");
        dialogPane.setContent(new VBox(8, tfTeachersEmail));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfTeachersEmail.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.deleteTeachers(tfTeachersEmail.getText());
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
        showTeachersOnScreen();
    }
    @FXML
    public void onExportTeachers() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if(selectedDirectory != null) {
            Dialog<Object> dialog = new Dialog<>();
            TextField tfTeacherFile = new TextField();dialog.setTitle("Export Teachers");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            tfTeacherFile.setPromptText("Filename");
            dialogPane.setContent(new VBox(8, tfTeacherFile));
            dialog.setResultConverter((ButtonType button) -> {
                if (button == ButtonType.OK) {
                    if(tfTeacherFile.getText().trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                        alert.showAndWait();
                    }else {
                        try {
                            model.exportTeachers(selectedDirectory.getAbsolutePath() + "\\" + tfTeacherFile.getText() + ".csv");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            });
            dialog.showAndWait();
        }
    }
    public void showTeachersOnScreen() {
        if(model.showTeachers().isEmpty())
            return;
        lvShowTeachers.getItems().clear();
        for(String s : model.showTeachers())
            lvShowTeachers.getItems().add(s);
    }

    /************************************** Proposal Phase **************************************/
    @FXML
    public void onInsertProposal() throws IOException {
        String x = "";
        /*JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\serco\\Desktop\\A Minha Universidade\\PA\\projeto"));
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)*/
        x = model.addProposals("");//fileChooser.getSelectedFile().getAbsolutePath()

        if(!x.equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
            alert.showAndWait();
        }
        showProposalsOnScreen();
    }
    @FXML
    public void onDeleteProposal() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfProposal = new TextField();dialog.setTitle("Delete Proposal");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfProposal.setPromptText("Proposal ID");
        dialogPane.setContent(new VBox(8, tfProposal));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfProposal.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.deleteProposals(tfProposal.getText());
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
        showProposalsOnScreen();
    }
    @FXML
    public void onExportProposal() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if(selectedDirectory != null) {
            Dialog<Object> dialog = new Dialog<>();
            TextField tfProposal = new TextField();dialog.setTitle("Export Proposals");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            tfProposal.setPromptText("Filename");
            dialogPane.setContent(new VBox(8, tfProposal));
            dialog.setResultConverter((ButtonType button) -> {
                if (button == ButtonType.OK) {
                    if(tfProposal.getText().trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                        alert.showAndWait();
                    }else {
                        try {
                            model.exportProposals(selectedDirectory.getAbsolutePath() + "\\" + tfProposal.getText() + ".csv");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            });
            dialog.showAndWait();
        }
    }
    public void showProposalsOnScreen() {
        if(model.showProposals().isEmpty())
            return;
        lvShowProposal.getItems().clear();
        for(String s : model.showProposals())
            lvShowProposal.getItems().add(s);
    }

    /************************************** Phase 2 **************************************/
    @FXML
    public void onInsertApplication() throws IOException {
        String x = "";
        /*JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\serco\\Desktop\\A Minha Universidade\\PA\\projeto"));
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)*/
        x = model.addApplications("");//fileChooser.getSelectedFile().getAbsolutePath()

        if(!x.equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
            alert.showAndWait();
        }
        showApplicationsOnScreen();
    }
    @FXML
    public void onEditApplication() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfStudent = new TextField();
        TextField tfId = new TextField();
        dialog.setTitle("Edit Application");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfId.setPromptText("Proposal id");
        tfStudent.setPromptText("Proposal id");
        dialogPane.setContent(new VBox(8, tfId));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfId.getText().trim().isEmpty() || tfStudent.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.editApplication(Long.parseLong(tfStudent.getText()), tfId.getText(),1);
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
        showApplicationsOnScreen();
    }
    @FXML
    public void onDeleteApplication() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfApp = new TextField();dialog.setTitle("Delete Application");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfApp.setPromptText("Student number");
        dialogPane.setContent(new VBox(8, tfApp));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfApp.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.deleteApplication(Long.parseLong(tfApp.getText()));
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
        showApplicationsOnScreen();
    }
    @FXML
    public void onExportApplication() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if(selectedDirectory != null) {
            Dialog<Object> dialog = new Dialog<>();
            TextField tfApp = new TextField();dialog.setTitle("Export Applications");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            tfApp.setPromptText("Filename");
            dialogPane.setContent(new VBox(8, tfApp));
            dialog.setResultConverter((ButtonType button) -> {
                if (button == ButtonType.OK) {
                    if(tfApp.getText().trim().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                        alert.showAndWait();
                    }else {
                        try {
                            model.exportApplications(selectedDirectory.getAbsolutePath() + "\\" + tfApp.getText() + ".csv");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            });
            dialog.showAndWait();
        }
    }
    public void showApplicationsOnScreen() {
        if(model.showApplications().isEmpty())
            return;
        lvShowApplications.getItems().clear();
        for(String s : model.showApplications())
            lvShowApplications.getItems().add(s);
    }

    /************************************** Phase 3 **************************************/
    @FXML
    public void onAssignProposal() { model.assignAProposalWithoutAssignments(); }
    @FXML
    public void onManualAssign() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfStudent = new TextField();
        TextField tfId = new TextField();
        dialog.setTitle("Manual assign");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfId.setPromptText("Proposal id");
        tfStudent.setPromptText("Student number");
        dialogPane.setContent(new VBox(8, tfId, tfStudent));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfId.getText().trim().isEmpty() || tfStudent.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.associateProposalToStudents(tfId.getText(),Long.parseLong(tfStudent.getText()));
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
    }
    @FXML
    public void onManualRemove() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfStudent = new TextField();
        TextField tfId = new TextField();
        dialog.setTitle("Manual remove");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfId.setPromptText("Proposal id");
        tfStudent.setPromptText("Student number");
        dialogPane.setContent(new VBox(8, tfId, tfStudent));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfId.getText().trim().isEmpty() || tfStudent.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.removeStudentFromProposal(tfId.getText());
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    /************************************** Phase 4 **************************************/
    @FXML
    public void onAssignAdvisor() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfTeacher = new TextField();
        TextField tfProposal = new TextField();
        dialog.setTitle("Assign Advisor");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfTeacher.setPromptText("Teacher id");
        tfProposal.setPromptText("Proposal id");
        dialogPane.setContent(new VBox(8, tfTeacher, tfProposal));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfProposal.getText().trim().isEmpty() || tfTeacher.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.assignAdvisor(tfProposal.getText(), tfTeacher.getText());
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
    }
    @FXML
    public void onConsultAdvisor() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfTeacher = new TextField();dialog.setTitle("Consult Advisor");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfTeacher.setPromptText("consult advisor");
        dialogPane.setContent(new VBox(8, tfTeacher));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfTeacher.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.consultAdvisor(tfTeacher.getText());
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
    }
    @FXML
    public void onDeleteAdvisor() {
        Dialog<Object> dialog = new Dialog<>();
        TextField tfTeacher = new TextField();dialog.setTitle("Delete Advisor");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        tfTeacher.setPromptText("proposal id");
        dialogPane.setContent(new VBox(8, tfTeacher));
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                String x="";
                if(tfTeacher.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Fill in all spaces", ButtonType.OK);
                    alert.showAndWait();
                }else {
                    x = model.deleteAdvisor(tfTeacher.getText());
                    if (!x.equalsIgnoreCase("")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, x, ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
    }
}