package ka.commune.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ka.commune.business.App;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class SettingsMenuController implements Initializable {

    @FXML private BorderPane rootStage;
    @FXML private HBox buttonAccount;
    @FXML private HBox buttonDataBase;
    @FXML private ScrollPane homePane;

    private Vector<HBox> menuButtons=new Vector<HBox>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.rootStage=this.rootStage;
        menuButtons.add(buttonAccount);
        menuButtons.add(buttonDataBase);
        accountOnClick();
    }

    @FXML
    public void accountOnClick() {
        styleButton(buttonAccount);
        try
        {
            Parent root ;
            root= FXMLLoader.load(getClass().getResource("/ka/commune/view/AccountSettingsView.fxml"));
            rootStage.setCenter(root);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void dataBaseOnClick() {
        styleButton(buttonDataBase);
        try
        {
            Parent root ;
            root= FXMLLoader.load(getClass().getResource("/ka/commune/view/DataBaseSettingsView.fxml"));
            rootStage.setCenter(root);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void styleButton(HBox btn) {
        for(HBox b : menuButtons)
        {
            if(b.equals(btn))
            {
                b.getStyleClass().clear();
                b.getStyleClass().add("active");
            }
            else
            {
                b.getStyleClass().clear();
                b.getStyleClass().add("notActive");
            }
        }
    }
}
