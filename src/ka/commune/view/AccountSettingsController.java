package ka.commune.view;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import ka.commune.business.App;
import ka.commune.business.GestionUser;
import ka.commune.entity.User;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccountSettingsController implements Initializable {

    @FXML private JFXCheckBox checkUser;
    @FXML private JFXTextField textUser;
    @FXML private JFXTextField textOldPassword;
    @FXML private JFXTextField textNewPassword;

    private GestionUser gUser=new GestionUser();
    private User user;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user= App.getUser();
        textUser.setText(user.getLogin());
        textUser.setEditable(false);
        checkUser.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                textUser.setEditable(newValue);
                if(!newValue)
                    textUser.setText(user.getLogin());
            }
        });
    }

    @FXML
    public void updateAccount() {
        if(checkUser.isSelected())
        {
            if(textUser.getText().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("خطأ");
                alert.setHeaderText("المرجو إدخال إسم المستخدم");
                alert.showAndWait();
                return;
            }
            user.setLogin(textUser.getText());
        }
        if(!textOldPassword.getText().equals("")){
            if(!textOldPassword.getText().equals(user.getPassword()))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("خطأ");
                alert.setHeaderText("كلمة المرور الحالية خاطئة");
                alert.showAndWait();
                return;
            }
            if(textNewPassword.getText().equals("") || textNewPassword.getText().equals(user.getPassword()))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.setTitle("خطأ");
                alert.setHeaderText("المرجو إدخال كلمة مرور جديدة");
                alert.showAndWait();
                return;
            }
            user.setPassword(textNewPassword.getText());
        }
        if(gUser.updateUser(user))
        {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setTitle("تم بنجاح");
            dialog.setHeaderText("العملية تمت بنجاح !");
            dialog.showAndWait();
            clearFields();
        }
    }

    private void clearFields() {
        checkUser.setSelected(false);
        textUser.setText(user.getLogin());
        textOldPassword.setText("");
        textNewPassword.setText("");
    }

}
