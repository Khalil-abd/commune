package ka.commune.view;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import ka.commune.business.App;
import ka.commune.business.GestionServicesSociaux;
import ka.commune.entity.ActiviteBS;

public class ActiviteBSController implements Initializable{

	private ActiviteBS managedActivite;
	private GestionServicesSociaux gServices;
	
	@FXML TableView<ActiviteBS> tableActivite;
	private final TableColumn<ActiviteBS, Integer> columnNumero=new TableColumn<>("الرقم الترتيبي");
	private final TableColumn<ActiviteBS, String> columnSujet=new TableColumn<>("الموضوع");
	private final TableColumn<ActiviteBS, String> columnDate=new TableColumn<>("التاريخ");
	private final TableColumn<ActiviteBS, String> columnJointures=new TableColumn<>("مرفقات");
	private final TableColumn<ActiviteBS, HBox> columnActionActivite=new TableColumn<>();
	
	@FXML private TextField textSearchActivite;
	@FXML private Button buttonAddActivite;
	@FXML private Button buttonUpdateActivite;
	@FXML Button buttonClose;
	@FXML private TilePane buttonsPane;
	@FXML private VBox managePane;
	@FXML private BorderPane borderPane;
	@FXML private JFXTextArea textJointures;
	@FXML JFXDatePicker date;
	@FXML JFXTextField textSujet;
	@FXML private ComboBox<String> comboYear;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.borderPane.setRight(null);
		initializeComboYear();
		comboYear.valueProperty().addListener(selectingYear);
		gServices=new GestionServicesSociaux();
		textSearchActivite.textProperty().addListener(tapingSearchActivite);
		remplirTableActivite(textSearchActivite.getText());
	}

	private void initializeComboYear()
	{
		comboYear.getItems().clear();
		comboYear.getItems().addAll(App.getListYears());
		for(String year: comboYear.getItems())
		{
			if(year.equals(App.getCurrentYear()+""))
				comboYear.getSelectionModel().select(year);
		}
	}

	ChangeListener<String> selectingYear= (observable, oldValue, newValue) -> {
		// TODO Auto-generated method stub
		remplirTableActivite(newValue.toLowerCase());
	};
	
	@FXML
	private void addActivite() {
		close();
		managedActivite=null;
		this.showManagePane();
	}
	
	private void showManagePane()
	{
		this.buttonsPane.getChildren().clear();
		this.buttonsPane.getChildren().add(buttonClose);
		if(this.managedActivite==null)
		{
			this.buttonsPane.getChildren().add(this.buttonAddActivite);
			this.borderPane.setRight(this.managePane);
			
		}
		else
		{
			this.borderPane.setRight(this.managePane);
			fillFields();
			this.buttonsPane.getChildren().add(this.buttonUpdateActivite);
		}
	}
	
	@FXML
	private void close() {
		// TODO Auto-generated method stub
		
		textSujet.setText("");
		textJointures.setText("");
		date.setValue(null);
		borderPane.setRight(null);
	}
	
	private void fillFields() {
		// TODO Auto-generated method stub
		textSujet.setText(String.valueOf(managedActivite.getSujet()));
		textJointures.setText(managedActivite.getJointures());
		date.setValue(managedActivite.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}
	
	@FXML
	public void consultSujetReunion()
	{
		try 
		{
			Parent root ;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/ka/commune/view/ObjetActiviteBSView.fxml"));
			root = loader.load();
			App.rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// search Listener
	
	ChangeListener<String> tapingSearchActivite= (observable, oldValue, newValue) -> {
		// TODO Auto-generated method stub
		remplirTableActivite(newValue);
	};
    
    // remplir Table 
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableActivite(String critere)
	{
		ObservableList<ActiviteBS> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableActivite.getColumns().clear();
    	tableActivite.getItems().clear();
		columnNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
    	columnSujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
		columnJointures.setCellValueFactory(new PropertyValueFactory<>("jointures"));
    	
    	columnDate.setCellValueFactory(param -> {
			// TODO Auto-generated method stub
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
			if (param.getValue().getDate()!= null)
				return new SimpleStringProperty(format1.format(param.getValue().getDate()));
			else return null;
		});
		columnActionActivite.setPrefWidth(125);
    	columnActionActivite.setCellValueFactory(new PropertyValueFactory("action"));
    	try {
			for(ActiviteBS ss : gServices.findAllActiviteBS())
			{
				if( ss.getSearchText().toLowerCase().contains(critere.toLowerCase())
				&& ss.getDate().toString().contains(comboYear.getSelectionModel().getSelectedItem()))
					temp.add(ss);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	int i=0;
    	for(ActiviteBS ss :temp)
		{
			ss.setNumero(++i);
			Button delete=new Button("حذف");
			delete.setOnAction(e->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("رسالة التأكد");
				alert.setHeaderText("لإتمام الحذف إضغط على حذف");
				ButtonType buttonYes = new ButtonType("حذف");
				
				ButtonType buttonNo = new ButtonType("إلغاء", ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(buttonYes,buttonNo);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == buttonYes){
					if(managedActivite!=null && managedActivite.getIdActiviteBS()==ss.getIdActiviteBS())
						this.close();
					gServices.deleteActiviteBS(ss);
					remplirTableActivite(textSearchActivite.getText().toLowerCase());
				}
			});
			Button update=new Button("تعديل");
			update.setOnAction(e->{
				this.managedActivite=ss;
				showManagePane();
			});
			Button consult= new Button("consulter");
			consult.setOnAction(e->{
				ObjetActiviteBSController.setActivite(ss);
				consultSujetReunion();
			});
			ss.getAction().getChildren().clear();
			ss.getAction().getChildren().addAll(delete,update,consult);
			setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
			setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");
			setButtonIco(consult, "/ka/commune/view/resources/img/watch.png", "#2d3436");
		}
    	tableActivite.setItems(temp);
		tableActivite.getColumns().addAll(columnActionActivite,columnNumero,columnSujet,columnDate,columnJointures);
	}
   
    // Add activite
    
    @FXML
	private void addNewActivite()
	{
		if( textSujet.getText().equals("")|| date.getValue()==null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال جميع المعلومات");
			alert.showAndWait();
			return;
		}
		
		ActiviteBS activiteBS=new ActiviteBS();
		activiteBS.setSujet(textSujet.getText());
		activiteBS.setDate(Date.from(Instant.from(date.getValue().atStartOfDay(ZoneId.systemDefault()))));
		activiteBS.setJointures(textJointures.getText());
		
		if(gServices.addActiviteBS(activiteBS))
		{
			this.close();
			textSearchActivite.setText("");
			remplirTableActivite("");
		}
	}
    
    // update Activite
    
    @FXML void updateActivite()
	{
		if(textSujet.getText().equals("") || textJointures.getText().equals("") || date.getValue()==null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال جميع المعلومات");
			alert.showAndWait();
			return;
		}
		ActiviteBS activiteBS =managedActivite;
		activiteBS.setSujet(textSujet.getText());
		activiteBS.setDate(Date.from(Instant.from(date.getValue().atStartOfDay(ZoneId.systemDefault()))));
		activiteBS.setJointures(textJointures.getText());
		if(gServices.updateActiviteBS(activiteBS))
		{
			remplirTableActivite(textSearchActivite.getText().toLowerCase());
			this.close();
		}
	}
    
	private void setButtonIco(Button btn, String imgSRC, String Color) {
		Image imageTmp = new Image(getClass().getResourceAsStream(imgSRC), 18, 18, true, true);
		btn.setText("");
		btn.setGraphic(new ImageView(imageTmp));
		btn.setStyle("-fx-background-color:" + Color);
		btn.getStyleClass().add("cursor_hand");
	}

}
