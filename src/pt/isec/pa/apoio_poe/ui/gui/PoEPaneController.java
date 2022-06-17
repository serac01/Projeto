package pt.isec.pa.apoio_poe.ui.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import pt.isec.pa.apoio_poe.model.ManagementPoE;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class PoEPaneController {
    ManagementPoE model;
    @FXML
    private VBox root;
    @FXML
    private ListView lvShowStudents;
    ObservableList observableList = FXCollections.observableArrayList();

    public void init(ManagementPoE model) { this.model = model; }

    /************************************** Home **************************************/
    @FXML
    public void onLoadState() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:\\Users\\serco\\Desktop\\A Minha Universidade\\PA\\projeto"));
        int response = fileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            model.serialization(file.getAbsolutePath());
        }
        update();
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
    public void onNextPhase() { model.nextPhase(); }

    private void update() throws IOException {
        switch (model.getState()){
            case PHASE_1 -> onStart();
            case STUDENT_PHASE -> onStudentManagement();
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
    public void onTeacherManagement() throws IOException { model.addStudents(""); model.showStudents().forEach(System.out::println);; }

    @FXML
    public void onProposalsManagement() { model.changeToProposals(); }


    /************************************** First Phase **************************************/
    @FXML
    public void onInsertStudent() throws IOException {
        model.addStudents("");
        showStudentsOnScreen();
    }

    @FXML
    public void onEditStudent() throws IOException {
        model.editStudent(2019999999,"",1);
    }

    @FXML
    public void onDeleteStudent() throws IOException {
        model.deleteStudents(2019999999);
    }

    @FXML
    public void onExportStudent() throws IOException {
        model.exportStudents("");
    }

    public void showStudentsOnScreen() {
        observableList.setAll(model.showStudents());
        lvShowStudents.setItems(observableList);

    }

}

