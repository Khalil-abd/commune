package ka.commune.view.control;

import com.jfoenix.controls.JFXPasswordField;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PasswordDialog extends Dialog<String> {
  private JFXPasswordField passwordField;

  public PasswordDialog() {
    setTitle("تأكيد الحذف");
    setHeaderText("حذف الولاية سيترتب عنه حذف جميع المعلوات المتعلقة بها : أعضاء المجلس و الإجتماعات المنظمة في هذه الولاية.");
    //setContentText("لإتمام الحذف أدخلوا كلمة المرور  واضغطوا على حذف");
    ButtonType passwordButtonType = new ButtonType("حذف", ButtonData.OK_DONE);
    ButtonType passwordButtonCancel = new ButtonType("إلغاء", ButtonData.CANCEL_CLOSE);
    getDialogPane().getButtonTypes().addAll(passwordButtonType, passwordButtonCancel);

    passwordField = new JFXPasswordField();
    passwordField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    passwordField.setPrefWidth(200);
    passwordField.setPromptText("كلمة المرور");
    passwordField.setLabelFloat(true);
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER);
    hBox.getChildren().add(passwordField);
    hBox.setPadding(new Insets(20));

    VBox vbox=new VBox();
    HBox hbox2=new HBox();
    hbox2.setAlignment(Pos.CENTER);
    Label label=new Label("لإتمام الحذف أدخلوا كلمة المرور  واضغطوا على حذف");
    hbox2.getChildren().add(label);
    HBox.setHgrow(hbox2, Priority.ALWAYS);
    vbox.getChildren().add(hbox2);
    vbox.getChildren().add(hBox);
    //HBox.setHgrow(passwordField, Priority.ALWAYS);

    getDialogPane().setContent(vbox);

    Platform.runLater(() -> passwordField.requestFocus());

    setResultConverter(dialogButton -> {
      if (dialogButton == passwordButtonType) {
        return passwordField.getText();
      }
      return null;
    });
  }

  public PasswordField getPasswordField() {
    return passwordField;
  }
}