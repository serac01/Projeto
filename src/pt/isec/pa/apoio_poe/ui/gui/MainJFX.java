package pt.isec.pa.apoio_poe.ui.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.apoio_poe.model.ManagementPoE;

public class MainJFX extends Application {
    ManagementPoE model;

    @Override
    public void init() throws Exception {
        super.init();
        model = new ManagementPoE();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/pane_home.fxml"));
        Parent root = loader.load();

        PoEPaneController controller = loader.getController();
        controller.init(model);

        Scene scene = new Scene(root);
        scene.setUserData(model);
        stage.setScene(scene);
        stage.setTitle("PoE");
        stage.setResizable(false);
        stage.show();
    }
}
