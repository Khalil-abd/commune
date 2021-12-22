package ka.commune.view;

import com.jfoenix.controls.JFXProgressBar;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import ka.commune.business.App;
import ka.commune.business.GestionUser;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DataBaseSettingsController implements Initializable {

    private GestionUser gUser=new GestionUser();
    @FXML
    private JFXProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.setProgress(0);
    }

    @FXML
    void exportDataBase() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        selectedDirectory = directoryChooser.showDialog(null);
        if(selectedDirectory==null)
            return;
        SaveService service = new SaveService();
        service.setOnSucceeded(event -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setTitle("تم بنجاح");
            dialog.setHeaderText("العملية تمت بنجاح !");
            dialog.showAndWait();
            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);
        });
        if (!(service.getState().equals(Worker.State.READY))) {
            service.reset();
        }
        progressBar.progressProperty().bind(service.progressProperty());
        try{
            service.start();
        }catch (Exception e){
        }
    }
    File selectedDirectory=null;

    @FXML
    void importDataBase() {
        FileChooser fileChooser=new FileChooser();
        selectedDirectory= fileChooser.showOpenDialog(null);
        if(selectedDirectory==null)
            return;
        LoadService service = new LoadService();
        service.setOnSucceeded(event -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setTitle("تم بنجاح");
            dialog.setHeaderText("العملية تمت بنجاح !");
            dialog.showAndWait();
            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);
        });
        if (!(service.getState().equals(Worker.State.READY))) {
            service.reset();
        }
        progressBar.progressProperty().bind(service.progressProperty());
        service.start();
    }


    class LoadService extends Service<Boolean> {
        @Override
        protected Task<Boolean> createTask() {
            return new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    gUser.importDatabase(selectedDirectory);
                    try{
                        selectedDirectory=null;
                    }catch (Exception e){}
                    return true;
                }
            };
        }
    }

    class SaveService extends Service<Boolean> {
        @Override
        protected Task<Boolean> createTask() {
            return new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    try {
                        gUser.exporterData(selectedDirectory.getPath());
                    }catch (Exception e){ }
                    progressBar.progressProperty().unbind();
                    try{
                        selectedDirectory=null;
                    }catch (Exception e){}
                    return true;
                }
            };
        }
    }
}

