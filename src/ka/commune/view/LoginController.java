package ka.commune.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ka.commune.business.App;
import ka.commune.business.GestionUser;
import org.apache.ibatis.jdbc.ScriptRunner;

public class LoginController implements Initializable {

	@FXML private JFXTextField textLogin;
	@FXML private JFXPasswordField textPassword;
	@FXML private Button buttonLogin;
	@FXML private Button buttonClose;
	@FXML private Label labelMessage;
	@FXML private VBox rootPane;
	private GestionUser gUser;

	private void initializeGestionUser()
	{
		try {
			gUser=new GestionUser();
		}catch (javax.persistence.PersistenceException pe)
		{
			if(pe.getCause().toString().contains("Unknown database"))
			{
				if(GestionUser.createDataBase())
					gUser=new GestionUser();
				else
				{
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					alert.setHeaderText("خطأ أثناء تثبيت قاعدة البيانات!");
					alert.showAndWait();
					System.exit(1);
				}
				return;
			}
			//System.out.println(pe.getCause());
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("خادم قاعدة المعلومات غير مشغل!");
			alert.setContentText("المرجو تشغيل الخادم وإعادة المحاولة!");
			alert.showAndWait();
			System.exit(1);
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initializeGestionUser();
		labelMessage.setVisible(false);
	}
	
	@FXML
	public void close()
	{
		App.stages.close();
		//System.exit(0);
	}
	
	
	@FXML
	public void login() throws IOException
	{
		try 
		{
			App.setUser(gUser.login(textLogin.getText(), textPassword.getText()));
			if(App.getUser()==null){
				labelMessage.setText("اسم المستخدم اوكلمة المرور خاطئة");
				labelMessage.setVisible(true);
				return;
			}
			labelMessage.setVisible(false);
			
			Parent root1 = FXMLLoader.load(getClass().getResource("/ka/commune/view/HomeView.fxml"));
			Scene scene = new Scene(root1);
			App.stages.setScene(scene);
			App.stages.setTitle("Commune");
			App.stages.show();
			App.stages.setResizable(true);
			App.stages.setMinWidth(800);
			App.stages.setMinHeight(600);
			App.stages.setMaximized(true);
			scene.getWindow().onShowingProperty();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			labelMessage.setText("اسم المستخدم اوكلمة المرور خاطئة");
			labelMessage.setVisible(true);
		}
	}

	
}
