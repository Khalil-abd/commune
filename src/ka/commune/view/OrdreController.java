package ka.commune.view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import ka.commune.business.App;
import ka.commune.business.GestionMembreConseil;
import ka.commune.business.PdfGenerator;
import ka.commune.entity.*;

import java.io.File;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdreController implements Initializable{
	
	private final GestionMembreConseil gMembreConseil = new GestionMembreConseil();
	// Reunion
	@FXML private BorderPane borderPane;
	@FXML private VBox managePane;
	@FXML private JFXComboBox<Designationsujetsession> comboTitre;
	@FXML private JFXComboBox<Session> comboSession;
	@FXML private JFXComboBox<String> comboMonth;
	@FXML private JFXTextField textYear;
	@FXML JFXDatePicker date;
	@FXML private JFXTimePicker time;
	@FXML private TableView<Sujetsession> tableReunion;
	@FXML private Button buttonAddReunion;
	@FXML private Button buttonUpdateReunion;
	@FXML private TilePane buttonsPane;
	@FXML private TextField textSearchReunion;
	private Sujetsession managedReunion=null;
	@FXML Button buttonClose;
	
	private final TableColumn<Sujetsession, String> columnTitreReunion= new TableColumn<>("الموضوع");
	private final TableColumn<Sujetsession, String> columnTypeSession= new TableColumn<>("الدورة");
	private final TableColumn<Sujetsession, String> columnDate= new TableColumn<>("التاريخ");
	private final TableColumn<Sujetsession, String> columnMonth= new TableColumn<>("الشهر");
	private final TableColumn<Sujetsession, String> columnYear= new TableColumn<>("السنة");
	private final TableColumn<Sujetsession, String> columnTime= new TableColumn<>("الوقت");
	private final TableColumn<Sujetsession, Integer> columnNumeroReunion= new TableColumn<>("الرقم الترتيبي");
	private final TableColumn<Sujetsession, HBox> columnActionReunion= new TableColumn<>();
	
	// Titre
	@FXML private JFXTextField textTitre;
	@FXML private TableView<Designationsujetsession> tableTitre;
	private final TableColumn<Designationsujetsession, String> columnTitre= new TableColumn<>("الموضوع");
	private final TableColumn<Designationsujetsession, HBox> columnActionTitre= new TableColumn<>();
	private final TableColumn<Designationsujetsession, Integer> columnNumeroTitre= new TableColumn<>("الرقم الترتيبي");
	@FXML private TextField textSearchTitre;
	// Session
	@FXML private JFXTextField textSession;
	@FXML private TableView<Session> tableSession;
	private final TableColumn<Session, String> columnSession= new TableColumn<>("نوع الدورة");
	private final TableColumn<Session, HBox> columnActionSession= new TableColumn<>();
	private final TableColumn<Session, Integer> columnNumeroSession= new TableColumn<>("الرقم الترتيبي");
	@FXML private TextField textSearchSession;
	
	//Domaine
	@FXML private JFXTextField textDomaine;
	@FXML private TableView<Domaine> tableDomaine;
	private final TableColumn<Domaine, String> columnDomaine= new TableColumn<>("اسم المجال");
	private final TableColumn<Domaine, Integer> columnNumeroDomaine= new TableColumn<>("الرقم الترتيبي");
	private final TableColumn<Domaine, HBox> columnActionDomaine= new TableColumn<>();
	@FXML private TextField textSearchDomaine;

	@FXML
	private ComboBox<String> comboYear;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.initializeComboBox();
		this.borderPane.setRight(null);
		initializeComboYear();
		comboYear.valueProperty().addListener(selectingYear);
		this.textSearchDomaine.textProperty().addListener(tapingSearchDomaine);
		this.textSearchReunion.textProperty().addListener(tapingSearchReunion);
		this.textSearchSession.textProperty().addListener(tapingSearchSession);
		this.textSearchTitre.textProperty().addListener(tapingSearchTitre);
		this.date.valueProperty().addListener((observable, oldValue, newValue) -> {
			// TODO Auto-generated method stub
			if(newValue!= null)
			{
				comboMonth.getSelectionModel().select(newValue.getMonthValue()-1);
				textYear.setText(String.valueOf(newValue.getYear()));
			}
			else{
				comboMonth.getSelectionModel().clearSelection();
				textYear.setText("");
			}

		});
		this.remplirTableReunion("");
		this.remplirTableTitre("");
		this.remplirTableSession("");
		this.remplirTableDomaine("");
		this.manageTableDomaine();
		this.manageTableSession();
		this.manageTableTitre();
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
		remplirTableReunion(newValue.toLowerCase());
	};
	
	@FXML
	private void addOrdre() {
		close();
		managedReunion=null;
		this.showManagePane();
	}
	
	private void showManagePane()
	{
		this.buttonsPane.getChildren().clear();
		this.buttonsPane.getChildren().add(buttonClose);
		if(this.managedReunion==null)
		{
			this.buttonsPane.getChildren().add(this.buttonAddReunion);
			this.borderPane.setRight(this.managePane);
			
		}
		else
		{
			this.borderPane.setRight(this.managePane);
			fillFields();
			this.buttonsPane.getChildren().add(this.buttonUpdateReunion);
		}
	}
	
	private void fillFields() {
		// TODO Auto-generated method stub
		for(Designationsujetsession d: comboTitre.getItems())
			if(d.getIdDesignation()==managedReunion.getDesignationsujetsession().getIdDesignation())
				comboTitre.getSelectionModel().select(d);
		for(Session s:comboSession.getItems())
			if(s.getIdSession()==managedReunion.getSession().getIdSession())
				comboSession.getSelectionModel().select(s);
		for(String m : comboMonth.getItems())
			if(m.toLowerCase().equals(managedReunion.getMois().toLowerCase()))
				comboMonth.getSelectionModel().select(m);
		textYear.setText(String.valueOf(managedReunion.getAnnee()));
		time.setValue(managedReunion.getHeure().toLocalTime());
		date.setValue(managedReunion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}
	@FXML
	private void close() {
		// TODO Auto-generated method stub
		//comboDomaine.getSelectionModel().clearSelection();
		
		comboMonth.getSelectionModel().clearSelection();
		comboTitre.getSelectionModel().clearSelection();
		comboSession.getSelectionModel().clearSelection();
		textYear.setText("");
		date.setValue(null);
		time.setValue(null);
		borderPane.setRight(null);
	}
	
	private void initializeComboBox()
	{
		comboSession.getItems().clear();
		comboTitre.getItems().clear();
		comboMonth.getItems().clear();
		comboSession.getItems().addAll(gMembreConseil.findAllSessions());
		comboTitre.getItems().addAll(gMembreConseil.findAllTitres());
		comboMonth.getItems().addAll("يناير","فبراير","مارس","أبريل","ماي","يونيو","يوليوز","غشت","شتنبر","أكتوبر","نونبر","دجنبر");
	}
	
	// Change Listeners
	
	ChangeListener<String> tapingSearchReunion= (observable, oldValue, newValue) -> {
		// TODO Auto-generated method stub
		remplirTableReunion(newValue.toLowerCase());
	};
    
    ChangeListener<String> tapingSearchTitre= (observable, oldValue, newValue) -> {
		// TODO Auto-generated method stub
		remplirTableTitre(newValue.toLowerCase());
	};
    
    ChangeListener<String> tapingSearchSession= (observable, oldValue, newValue) -> {
		// TODO Auto-generated method stub
		remplirTableSession(newValue.toLowerCase());
	};
    
    ChangeListener<String> tapingSearchDomaine= (observable, oldValue, newValue) -> {
		// TODO Auto-generated method stub
		remplirTableDomaine(newValue.toLowerCase());
	};
	
    @FXML
	public void consultSujetReunion()
	{
		try 
		{
			Parent root ;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/ka/commune/view/ObjetReunionView.fxml"));
			root = loader.load();
			App.rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
    
	// Remplissage des tables
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableReunion(String critere)
	{
		ObservableList<Sujetsession> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableReunion.getColumns().clear();
    	tableReunion.getItems().clear();
    	columnTypeSession.setCellValueFactory(param -> {
			// TODO Auto-generated method stub
			if (param.getValue().getSession()!= null)
			{
				return new SimpleStringProperty(param.getValue().getSession().getDesignationSession());
			}
			else return null;
		});
    	columnTitreReunion.setCellValueFactory(param -> {
			// TODO Auto-generated method stub
			if (param.getValue().getDesignationsujetsession()!= null)
				return new SimpleStringProperty(param.getValue().getDesignationsujetsession().getDesignation());
			else return null;
		});
    	columnTime.setCellValueFactory(param -> {
			// TODO Auto-generated method stub
			if (param.getValue().getHeure()!= null)
				return new SimpleStringProperty(param.getValue().getHeure().toString());
			else return null;
		});
    	columnMonth.setCellValueFactory(new PropertyValueFactory<>("mois"));
		columnNumeroReunion.setCellValueFactory(new PropertyValueFactory<>("numero"));
    	columnYear.setCellValueFactory(param -> {
			// TODO Auto-generated method stub
			if (param.getValue().getAnnee()>0)
				return new SimpleStringProperty(String.valueOf(param.getValue().getAnnee()));
			else return null;
		});
    	columnDate.setCellValueFactory(param -> {
			// TODO Auto-generated method stub
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
			if (param.getValue().getDate()!= null)
				return new SimpleStringProperty(format1.format(param.getValue().getDate()));
			else return null;
		});
		columnActionReunion.setPrefWidth(156);
    	columnActionReunion.setCellValueFactory(new PropertyValueFactory("action"));
    	try {
    		int i=0;
			for(Sujetsession ss : gMembreConseil.findAllReunions())
			{
				if(  ss.getSearchText().toLowerCase().contains(critere.toLowerCase())
				&& (ss.getAnnee()+"").equals(comboYear.getSelectionModel().getSelectedItem()))
				{
					ss.setNumero(++i);
					temp.add(ss);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	for(Sujetsession ss :temp)
		{
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
				if (result.isPresent() && (result.get() == buttonYes)){
					if(managedReunion!=null && managedReunion.getIdSujetSession()==ss.getIdSujetSession())
						this.close();
					gMembreConseil.deleteReunion(ss);
					remplirTableReunion(textSearchReunion.getText().toLowerCase());
				}
			});
			Button update=new Button("تعديل");
			update.setOnAction(e->{
				//tableMembresConseil.getSelectionModel().select(mc);
				this.managedReunion=ss;
				showManagePane();
			});
			Button consult= new Button("consulter");
			consult.setOnAction(e->{
				ObjetReunionController.setSujetsession(ss);
				consultSujetReunion();
			});
			Button invite=new Button("دعوة");
			invite.setOnAction(e->{
				getInvitations(ss);
			});
			ss.getAction().getChildren().clear();
			ss.getAction().getChildren().addAll(delete,update,consult,invite);
			setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
			setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");

			FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ENVELOPE);
			icon.setFill(Paint.valueOf("#000"));
			setButtonFont(invite, icon, "#F8EFBA");
			setButtonIco(consult, "/ka/commune/view/resources/img/watch.png", "#2d3436");
		}
    	tableReunion.setItems(temp);
		tableReunion.getColumns().addAll(columnActionReunion,columnNumeroReunion,columnTitreReunion,columnTypeSession,columnDate,columnMonth,columnYear,columnTime);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableTitre(String critere)
	{
		ObservableList<Designationsujetsession> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableTitre.getColumns().clear();
    	tableTitre.getItems().clear();
    	columnTitre.setCellValueFactory(new PropertyValueFactory<>("designation"));
		columnNumeroTitre.setCellValueFactory(new PropertyValueFactory("numero"));
    	columnActionTitre.setCellValueFactory(new PropertyValueFactory("action"));
    	try {
    		int i=0;
			for(Designationsujetsession d : gMembreConseil.findAllTitres())
				if(d.getDesignation().toLowerCase().contains(critere))
				{
					d.setNumero(++i);
					temp.add(d);
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	for(Designationsujetsession d :temp)
		{
			Button delete=new Button("حذف");
			delete.setOnAction(e->{
				gMembreConseil.deleteTitre(d);
				remplirTableTitre(textSearchTitre.getText().toLowerCase());
			});
			d.getAction().getChildren().clear();
			d.getAction().getChildren().addAll(delete);
			setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
		}
    	tableTitre.setItems(temp);
		tableTitre.getColumns().addAll(columnNumeroTitre,columnTitre,columnActionTitre);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableSession(String critere)
	{
		ObservableList<Session> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableSession.getColumns().clear();
    	tableSession.getItems().clear();
		columnNumeroSession.setCellValueFactory(new PropertyValueFactory("numero"));
    	columnSession.setCellValueFactory(new PropertyValueFactory<>("designationSession"));
    	columnActionSession.setCellValueFactory(new PropertyValueFactory("action"));
    	try {
    		int i=0;
			for(Session s : gMembreConseil.findAllSessions())
				if( s.getDesignationSession().toLowerCase().contains(critere))
				{
					s.setNumero(++i);
					temp.add(s);
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	for(Session s :temp)
		{
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
				if (result.isPresent() && (result.get() == buttonYes)){
					gMembreConseil.deleteSession(s);
					remplirTableSession(textSearchSession.getText().toLowerCase());
				}
			});
			s.getAction().getChildren().clear();
			s.getAction().getChildren().addAll(delete);
			setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
		}
    	tableSession.setItems(temp);
		tableSession.getColumns().addAll(columnNumeroSession,columnSession,columnActionSession);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableDomaine(String critere)
	{
		ObservableList<Domaine> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableDomaine.getColumns().clear();
    	tableDomaine.getItems().clear();
		columnNumeroDomaine.setCellValueFactory(new PropertyValueFactory("numero"));
    	columnDomaine.setCellValueFactory(new PropertyValueFactory<>("designationDomaine"));
    	columnActionDomaine.setCellValueFactory(new PropertyValueFactory("action"));
    	try {
    		int i=0;
			for(Domaine d : gMembreConseil.findAllDomaines())
				if( d.getDesignationDomaine().toLowerCase().contains(critere))
				{
					d.setNumero(++i);
					temp.add(d);
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	for(Domaine d :temp)
		{
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
				if (result.isPresent() && buttonYes == result.get()){
					gMembreConseil.deleteDomaine(d);
					remplirTableDomaine(textSearchDomaine.getText().toLowerCase());
				}
			});
			d.getAction().getChildren().clear();
			d.getAction().getChildren().addAll(delete);
			setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
		}
    	tableDomaine.setItems(temp);
		tableDomaine.getColumns().addAll(columnNumeroDomaine,columnDomaine,columnActionDomaine);
	}

	// Add
	
	@FXML
	private void addReunion()
	{
		if (ReunionIsUncomplete()) return;
		Sujetsession sujetSession=new Sujetsession();
		sujetSession.setMandat(App.getMandat());
		sujetSession.setSession(comboSession.getValue());
		initiateSujetSession(sujetSession);
		if(gMembreConseil.addReunion(sujetSession))
		{
			this.close();
			textSearchReunion.setText("");
			remplirTableReunion("");
		}
	}
	@FXML
	private void addTitre()
	{
		if(textTitre.getText().equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال الموضوع");
			alert.showAndWait();
			return;
		}
		Designationsujetsession d= new Designationsujetsession();
		d.setDesignation(textTitre.getText());
		if(gMembreConseil.addTitre(d))
		{
			textTitre.setText("");
			textSearchTitre.setText("");
			remplirTableTitre("");
			initializeComboBox();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("هذا الموضوع موجود بالفعل");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void addSession()
	{
		if(textSession.getText().equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال نوع الدورة");
			alert.showAndWait();
			return;
		}
		Session s= new Session();
		s.setDesignationSession(textSession.getText());
		if(gMembreConseil.addSession(s))
		{
			textSession.setText("");
			textSearchSession.setText("");
			remplirTableSession("");
			initializeComboBox();
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("هذه الدورة موجودة بالفعل");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void addDomaine()
	{
		if(textDomaine.getText().equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال اسم المجال");
			alert.showAndWait();
			return;
		}
		Domaine d= new Domaine();
		d.setDesignationDomaine(textDomaine.getText());
		if(gMembreConseil.addDomaine(d))
		{
			textDomaine.setText("");
			textSearchDomaine.setText("");
			remplirTableDomaine("");
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("هذا المجال موجود بالفعل");
			alert.showAndWait();
		}
	}
	
	// Update
	
	@FXML void updateReunion()
	{
		if (ReunionIsUncomplete()) return;
		Sujetsession sujetSession=managedReunion;
		initiateSujetSession(sujetSession);
		sujetSession.setSession(comboSession.getValue());
		if(gMembreConseil.updateReunion(sujetSession)!=null)
		{
			remplirTableReunion(textSearchReunion.getText().toLowerCase());
			this.close();
		}
	}

	private void initiateSujetSession(Sujetsession sujetSession) {
		sujetSession.setAnnee(Integer.parseInt(textYear.getText()));
		sujetSession.setDate(Date.from(Instant.from(date.getValue().atStartOfDay(ZoneId.systemDefault()))));
		sujetSession.setDesignationsujetsession(comboTitre.getValue());
		sujetSession.setHeure(Time.valueOf(time.getValue()));
		sujetSession.setMois(comboMonth.getValue());
	}

	private boolean ReunionIsUncomplete() {
		if(comboSession.getValue()==null || comboMonth.getValue()==null
				|| textYear.getText().equals("") || comboTitre.getValue()==null || time.getValue()==null || date.getValue()==null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال جميع المعلومات");
			alert.showAndWait();
			return true;
		}
		return false;
	}

	private void manageTableTitre()
	{
		columnTitre.setCellFactory(TextFieldTableCell.forTableColumn());
		columnTitre.setOnEditCommit(edittedCell -> {
			// TODO Auto-generated method stub
			Designationsujetsession titre=tableTitre.getSelectionModel().getSelectedItem();
			if(edittedCell.getNewValue().equals(""))
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("المرجو إدخال الموضوع");
				titre.setDesignation(edittedCell.getOldValue());
				alert.showAndWait();
			}
			else
			{
				titre.setDesignation(edittedCell.getNewValue());

				if(!gMembreConseil.updateTitre(titre))
				{
					titre.setDesignation(edittedCell.getOldValue());
					Alert alert = new Alert(AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					alert.setHeaderText("هذا الموضوع موجود بالفعل");
					alert.showAndWait();
				}
			}
			remplirTableTitre(textSearchTitre.getText().toLowerCase());
			initializeComboBox();
			remplirTableReunion(textSearchReunion.getText().toLowerCase());
		});
	}
	
	private void manageTableSession()
	{
		columnSession.setCellFactory(TextFieldTableCell.forTableColumn());
		columnSession.setOnEditCommit(edittedCell -> {
			// TODO Auto-generated method stub
			Session session=tableSession.getSelectionModel().getSelectedItem();
			if(edittedCell.getNewValue().equals(""))
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("المرجو إدخال نوع الدورة");
				session.setDesignationSession(edittedCell.getOldValue());
				alert.showAndWait();
			}
			else
			{
				session.setDesignationSession(edittedCell.getNewValue());

				if(!gMembreConseil.updateSession(session))
				{
					session.setDesignationSession(edittedCell.getOldValue());
					Alert alert = new Alert(AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					alert.setHeaderText("هذه الدورة موجودة بالفعل");
					alert.showAndWait();
				}
			}
			remplirTableSession(textSearchSession.getText().toLowerCase());
			initializeComboBox();
			remplirTableReunion(textSearchReunion.getText().toLowerCase());
		});
	}
	
	private void manageTableDomaine()
	{
		columnDomaine.setCellFactory(TextFieldTableCell.forTableColumn());
		columnDomaine.setOnEditCommit(edittedCell -> {
			// TODO Auto-generated method stub
			Domaine domaine=tableDomaine.getSelectionModel().getSelectedItem();
			if(edittedCell.getNewValue().equals(""))
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("المرجو إدخال اسم المجال");
				domaine.setDesignationDomaine(edittedCell.getOldValue());
				alert.showAndWait();
			}
			else
			{
				domaine.setDesignationDomaine(edittedCell.getNewValue());

				if(!gMembreConseil.updateDomaine(domaine))
				{
					domaine.setDesignationDomaine(edittedCell.getOldValue());
					Alert alert = new Alert(AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					alert.setHeaderText("هذا المجال موجود بالفعل");
					alert.showAndWait();
				}
			}
			remplirTableDomaine(textSearchSession.getText().toLowerCase());
		});
	}
	
	private void setButtonIco(Button btn, String imgSRC, String Color) {
        Image imageTmp = new Image(getClass().getResourceAsStream(imgSRC), 18, 18, true, true);
        btn.setText("");
        btn.setGraphic(new ImageView(imageTmp));
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }

	private void setButtonFont(Button btn, GlyphIcon f, String Color) {
		f.setSize("16");
		btn.setText("");
		btn.setGraphic(f);
		btn.setAlignment(Pos.CENTER);
		btn.setStyle("-fx-background-color:" + Color);
		btn.getStyleClass().add("cursor_hand");
	}

	private void getInvitations(Sujetsession sujetsession)
	{
		PdfGenerator tp=new PdfGenerator();
		List<Commission> listCommissions=gMembreConseil.findAllCommission();
		Commission com=new Commission();
		com.setDesignationCommission("جميع الأعضاء");
		com.setMembreconseils(gMembreConseil.findAllMembreConseil());
		listCommissions.add(com);
		ChoiceDialog<Commission> cDial = new ChoiceDialog<>(com,listCommissions);
		cDial.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		cDial.setTitle("اختيار المدعوين");
		cDial.setHeaderText("الأعضاء المدعوين : ");
		Optional<Commission> selection = cDial.showAndWait();
		selection.ifPresent(str -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			//directoryChooser.setInitialDirectory(new File("src"));
			File selectedDirectory = directoryChooser.showDialog(null);
			try {
				if(tp.genererInvitation(sujetsession, selection.get(),selectedDirectory.getAbsolutePath()))
				{
					Alert dialog = new Alert(AlertType.INFORMATION);
					dialog.setTitle("تم بنجاح");
					dialog.setHeaderText("العملية تمت بنجاح !");
					dialog.showAndWait();
				}
				else
				{
					Alert dialog = new Alert(AlertType.ERROR);
					dialog.setTitle("تخطأ");
					dialog.setHeaderText("لا يوجد أي عضو في اللائحة المختارة !");
					dialog.showAndWait();
				}
			}
			catch(NullPointerException e) {
				return;
			}

		});

	}
	
}