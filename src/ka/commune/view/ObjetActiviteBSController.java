package ka.commune.view;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ka.commune.business.App;
import ka.commune.business.GestionServicesSociaux;
import ka.commune.entity.ActiviteBS;
import ka.commune.entity.ObjetActiviteBS;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ObjetActiviteBSController implements Initializable{
	
	private static ActiviteBS activite;
	private GestionServicesSociaux gServices;
	
	@FXML TableView<ObjetActiviteBS> tableObjet;
	@FXML JFXTextField textDesignation;
	@FXML TextField textSearchObjet;
	private final TableColumn<ObjetActiviteBS, String> columnDesignation = new TableColumn<>("الموضوع");
	private final TableColumn<ObjetActiviteBS,Button > columnAction = new TableColumn<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		gServices=new GestionServicesSociaux();
		textSearchObjet.textProperty().addListener(tapingSearchObjet);
		manageCommission();
		remplirTableObjet(textSearchObjet.getText());
	}
	
	 ChangeListener<String> tapingSearchObjet= (observable, oldValue, newValue) -> {
		 // TODO Auto-generated method stub
		 remplirTableObjet(newValue);
	 };
	    
	    @FXML
		public void retour()
		{
			try 
	  		{
	  			Parent root ;
	  			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/ActiviteBSView.fxml"));
	  			activite=null;
	  	        App.rootStage.setCenter(root);
	  		} catch (Exception e) {
	  			// TODO: handle exception
	  			e.printStackTrace();
	  		}
		}
	
	@FXML
	private void addObjet()
	{
		if(textDesignation.getText().equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("يرجى إدخال اسم الموضوع !");
			alert.showAndWait();
			return;
		}
		ObjetActiviteBS objetActiviteBS=new ObjetActiviteBS();
		objetActiviteBS.setActiviteb(activite);
		objetActiviteBS.setDesignation(textDesignation.getText());
		objetActiviteBS.setActiviteb(activite);
		activite.addObjetactiviteb(objetActiviteBS);
		if(gServices.updateActiviteBS(activite))
		{
			textDesignation.setText("");
			remplirTableObjet(textSearchObjet.getText());
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			//alert.setHeaderText("هذه اللجنة موجودة بالفعل");
			alert.showAndWait();
			
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableObjet(String critere)
	{
		ObservableList<ObjetActiviteBS> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableObjet.getColumns().clear();
    	tableObjet.getItems().clear();
    	columnAction.setCellValueFactory(new PropertyValueFactory("delete"));
		columnDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
		try 
		{
			for(ObjetActiviteBS o :activite.getObjetactivitebs())
			{
				if(o.getDesignation()!=null && o.getDesignation().toLowerCase().contains(critere))
					temp.add(o);
			}
			} catch (Exception e) {
			// TODO: handle exception
		}
		for(ObjetActiviteBS o : temp)
		{
			o.getDelete().setOnAction(e->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("رسالة التأكد");
				alert.setHeaderText("لإتمام الحذف إضغط على حذف");
				ButtonType buttonYes = new ButtonType("حذف");
				ButtonType buttonNo = new ButtonType("إلغاء", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(buttonYes,buttonNo);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && (result.get() == buttonYes)){
					gServices.deleteObjet(activite.removeObjetactiviteb(o));
					gServices.updateActiviteBS(activite);
					remplirTableObjet(textSearchObjet.getText());
				}
			});
			setButtonIco(o.getDelete());
		}
		tableObjet.setItems(temp);
		tableObjet.getColumns().addAll(columnDesignation,columnAction);
	}
	
	
	// Update
	private void manageCommission()
	{
		tableObjet.setEditable(true);
		columnDesignation.setEditable(true);
		columnDesignation.setCellFactory(TextFieldTableCell.forTableColumn());
		columnDesignation.setOnEditCommit(edittedCell -> {
			// TODO Auto-generated method stub
			ObjetActiviteBS objetActiviteBS=tableObjet.getSelectionModel().getSelectedItem();
			int index=activite.getObjetactivitebs().indexOf(objetActiviteBS);
			if(edittedCell.getNewValue().equals(""))
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("يرجى إدخال الموضوع");
				objetActiviteBS.setDesignation(edittedCell.getOldValue());
				alert.showAndWait();
			}
			else
			{
				objetActiviteBS.setDesignation(edittedCell.getNewValue());
				objetActiviteBS=gServices.updateObjet(objetActiviteBS);
				if(objetActiviteBS!=null)
					activite.getObjetactivitebs().set(index,objetActiviteBS);
				if(!gServices.updateActiviteBS(activite))
				{
					objetActiviteBS.setDesignation(edittedCell.getOldValue());
					Alert alert = new Alert(AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					//alert.setHeaderText("هذه اللجنة موجودة بالفعل");
					alert.showAndWait();
				}
			}
			remplirTableObjet(textSearchObjet.getText());
		});
	}
	
	
	private void setButtonIco(Button btn) {
        Image imageTmp = new Image(getClass().getResourceAsStream("/ka/commune/view/resources/img/delete.png"), 18, 18, true, true);
        btn.setText("");
        btn.setGraphic(new ImageView(imageTmp));
        btn.setStyle("-fx-background-color:" + "#d63031");
        btn.getStyleClass().add("cursor_hand");
    }
	
	// Getters and Setter

	public static void setActivite(ActiviteBS activite) {
		ObjetActiviteBSController.activite = activite;
	}

	

}
