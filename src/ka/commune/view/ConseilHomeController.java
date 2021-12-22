package ka.commune.view;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import ka.commune.business.App;
import ka.commune.business.GestionMembreConseil;
import ka.commune.entity.Mandat;
import ka.commune.view.control.PasswordDialog;

public class ConseilHomeController implements Initializable {
	
	
	//@FXML private Button buttonHome;
	@FXML private HBox buttonMembreConseil;
	@FXML private HBox buttonOrdre;
	@FXML private HBox buttonCommission;
	@FXML private Button buttonMembreCommission;
	@FXML private Button buttonListCommission;
	@FXML private Button buttonAddMandat;
	@FXML private Button buttonUpdateMandat;
	@FXML private Button buttonClose;
	@FXML private HBox buttonHome;
	@FXML private Pane menu;
	@FXML private Pane headerPaneMenu;
	@FXML private HBox mandatPane;
	@FXML private VBox managedPane;
	@FXML private FlowPane buttonsPane;
	@FXML private ScrollPane homePane;
	//@FXML private VBox centerPane;
	@FXML private  BorderPane rootStage;
	@FXML private ProgressBar progressBar;
	//@FXML private ImageView imgLoading;
	private Mandat managedMandat=null;
	@FXML private JFXComboBox<Mandat> comboMandat;
	@FXML private TableView<Mandat> tableMandat;
	private TableColumn<Mandat, String> columnDateDebut=new TableColumn<Mandat, String>("تاريخ البداية");
	private TableColumn<Mandat, String> columnDateFin=new TableColumn<Mandat, String>("تاريخ النهاية");
	private TableColumn<Mandat, HBox> columnActionMandat=new TableColumn<Mandat, HBox>();
	@FXML private DatePicker dateDebut;
	@FXML private DatePicker dateFin;
	
	private Vector<HBox> menuButtons=new Vector<HBox>();
	
	private GestionMembreConseil gMembreConseil=new GestionMembreConseil();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		handleDate();
		this.initializeComboBox();
		
		App.rootStage=this.rootStage;
		this.mandatPane.getChildren().remove(managedPane);
		remplirTableMandat();
		menuButtons.add(buttonHome);
		menuButtons.add(buttonCommission);
		menuButtons.add(buttonMembreConseil);
		menuButtons.add(buttonOrdre);
	}
	
	private void initializeComboBox() {
		// TODO Auto-generated method stub
		comboMandat.getItems().clear();
		comboMandat.getItems().addAll(gMembreConseil.findAllMandat());
		if(comboMandat.getItems().size()>0)
		{	for(Mandat m : comboMandat.getItems())
			{
				if(App.getToDayDate().after(m.getDateDebut()) && App.getToDayDate().before(m.getDateFin()) )
				{
					comboMandat.getSelectionModel().select(m);
					App.setMandat(comboMandat.getSelectionModel().getSelectedItem());
					enableButtons();
					return;
				}
			}
			Mandat temp = comboMandat.getItems().get(0);
			for(Mandat m : comboMandat.getItems())
			{
				if(temp.getDateDebut().before(m.getDateDebut()))
					temp= m;
			}
			comboMandat.getSelectionModel().select(temp);
			App.setMandat(comboMandat.getSelectionModel().getSelectedItem());
			enableButtons();
			return;
		}
		disableButtons();
	}
	
	private void disableButtons()
	{
		buttonMembreConseil.setDisable(true);
		buttonCommission.setDisable(true);
		buttonOrdre.setDisable(true);
	}
	
	private void enableButtons()
	{
		buttonMembreConseil.setDisable(false);
		buttonCommission.setDisable(false);
		buttonOrdre.setDisable(false);
	}
	
	

	
	@FXML
	public void buttonHomeClick()
	{
		styleButton(buttonHome);
		rootStage.setCenter(homePane);
	}
	
	public void showManagedPane()
	{
		this.buttonsPane.getChildren().clear();
		this.mandatPane.getChildren().remove(this.managedPane);
		if(this.managedMandat==null)
		{
			this.buttonsPane.getChildren().add(this.buttonAddMandat);
		}
		else
		{
			fillFields();
			this.buttonsPane.getChildren().add(this.buttonUpdateMandat);
		}
		this.mandatPane.getChildren().add(this.managedPane);
		this.buttonsPane.getChildren().add(buttonClose);
	}
	
	private void fillFields() {
		// TODO Auto-generated method stub
		dateDebut.setValue(managedMandat.getDateDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		dateFin.setValue(managedMandat.getDateFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}
	
	@FXML
	private void selectMandat()
	{
		if(!comboMandat.getSelectionModel().getSelectedItem().equals(App.getMandat()))
		{
			App.setMandat(comboMandat.getSelectionModel().getSelectedItem());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("تم");
			alert.setHeaderText("العملية تمت بنجاح");
			alert.showAndWait();
		}
	}

	@FXML
	public void buttonCommissionClick()
	{
		styleButton(buttonCommission);
		try 
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/CommissionView.fxml"));
	       rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@FXML
	public void buttonOrdreClick()
	{
		styleButton(buttonOrdre);
		try 
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/OrdreView.fxml"));
	        rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@FXML
	public void buttonMembreConseilClick()
	{
		// TODO Auto-generated method stub
		styleButton(buttonMembreConseil);
		try 
	  	{
			Parent root ;
			Thread.sleep(20);
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/MembreConseilView.fxml"));
			rootStage.setCenter(root);
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}		
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableMandat()
	{
		ObservableList<Mandat> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableMandat.getColumns().clear();
    	tableMandat.getItems().clear();
    	columnDateDebut.setCellValueFactory(new Callback<CellDataFeatures<Mandat, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Mandat, String> param) {
				// TODO Auto-generated method stub
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
				if (param.getValue().getDateDebut()!= null)
					return new SimpleStringProperty(format1.format(param.getValue().getDateDebut()));
				else return null;
			}
		});
    	columnDateFin.setCellValueFactory(new Callback<CellDataFeatures<Mandat, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Mandat, String> param) {
				// TODO Auto-generated method stub
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
				if (param.getValue().getDateFin()!= null)
					return new SimpleStringProperty(format1.format(param.getValue().getDateFin()));
				else return null;
			}
		});
    	columnActionMandat.setCellValueFactory(new PropertyValueFactory("action"));
    	try {
			for(Mandat m : gMembreConseil.findAllMandat())
			{
				if( m.getDateDebut()!=null || m.getDateFin()!=null )
					temp.add(m);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	for(Mandat m :temp)
		{
			Button delete=new Button("حذف");
			delete.setOnAction(e->{
				PasswordDialog pd = new PasswordDialog();
			    Optional<String> result = pd.showAndWait();
			    result.ifPresent(password -> {
			    	if(password.equals(App.getUser().getPassword()))
			    	{
			    		gMembreConseil.deleteMandat(m);
			    		pd.close();
			    		Alert alert = new Alert(AlertType.INFORMATION);
						alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
						alert.setTitle("تم");
						alert.setHeaderText("العملية تمت بنجاح");
						alert.showAndWait();
						initializeComboBox();
						remplirTableMandat();
			    	}
			    	else {
			    		Alert alert = new Alert(AlertType.ERROR);
						alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
						alert.setTitle("خطأ");
						alert.setHeaderText("كلمة المرور خاطئة! ");
						alert.showAndWait();
			    	}
			    });
			});
			Button update=new Button("تعديل");
			update.setOnAction(e->{
				//tableMembresConseil.getSelectionModel().select(mc);
				this.managedMandat=m;
				showManagedPane();
			});
			m.getAction().getChildren().clear();
			m.getAction().getChildren().addAll(delete,update);
			setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
			setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");
		}
    	tableMandat.setItems(temp);
		tableMandat.getColumns().addAll(columnActionMandat,columnDateDebut,columnDateFin);
	}

	private void handleDate()
	{
		dateDebut.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(dateFin.getValue()!=null)
			{
				if(newValue.isAfter(dateFin.getValue()))
				{
					dateFin.setValue(newValue);
				}
			}
		});
		dateFin.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(dateDebut.getValue()!=null)
			{
				if(newValue.isBefore(dateDebut.getValue()))
				{
					dateFin.setValue(dateDebut.getValue());
				}
			}
		});
	}
	
	@FXML
	private void addMandat()
	{
		if(dateDebut.getValue().toString().equals("") || dateFin.getValue().toString().equals("")  )
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال جميع المعلومات");
			alert.showAndWait();
			return;
		}
		if(dateDebut.getValue().isAfter(dateFin.getValue()))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("تاريخ النهاية يجب أن يكون أكبر من تاريخ البداية !");
			alert.showAndWait();
			return;
		}
		Mandat m= new Mandat();
		m.setDateDebut(Date.from(Instant.from(dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()))));
		m.setDateFin(Date.from(Instant.from(dateFin.getValue().atStartOfDay(ZoneId.systemDefault()))));
		if(gMembreConseil.addMandat(m))
		{
			dateDebut.setValue(null);
			dateFin.setValue(null);
			remplirTableMandat();
			initializeComboBox();
			close();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("هناك تداخل بين هذه الولاية و ولاية أخرى !");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void buttonAddMandatClick() {
		close();
		managedMandat=null;
		this.showManagedPane();
	}
	
	@FXML
	private void updateMandat()
	{
		if(dateDebut.getValue().toString().equals("") || dateFin.getValue().toString().equals("")  )
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال جميع المعلومات");
			alert.showAndWait();
			return;
		}
		if(dateDebut.getValue().isAfter(dateFin.getValue()))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("تاريخ النهاية يجب أن يكون أكبر من تاريخ النهاية !");
			alert.showAndWait();
			return;
		}
		Mandat m= managedMandat;
		m.setDateDebut(Date.from(Instant.from(dateDebut.getValue().atStartOfDay(ZoneId.systemDefault()))));
		m.setDateFin(Date.from(Instant.from(dateFin.getValue().atStartOfDay(ZoneId.systemDefault()))));
		if(gMembreConseil.updateMandat(m))
		{
			dateDebut.setValue(null);
			dateFin.setValue(null);
			remplirTableMandat();
			initializeComboBox();
			close();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("هناك تداخل بين هذه الولاية و ولاية أخرى !");
			alert.showAndWait();
		}
	}
	
	private void setButtonIco(Button btn, String imgSRC, String Color) {
        Image imageTmp = new Image(getClass().getResourceAsStream(imgSRC), 18, 18, true, true);
        btn.setText("");
        btn.setGraphic(new ImageView(imageTmp));
        btn.setStyle("-fx-background-color:" + Color);
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
	
	@FXML
	private void close() {
		// TODO Auto-generated method stub
		//comboDomaine.getSelectionModel().clearSelection();
		dateDebut.setValue(null);
		dateFin.setValue(null);
		mandatPane.getChildren().remove(managedPane);
		mandatPane.setNodeOrientation(NodeOrientation.INHERIT);
	}
	
}
