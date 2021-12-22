package ka.commune.view;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.paint.Paint;
import ka.commune.entity.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import ka.commune.business.App;
import ka.commune.business.GestionMembreConseil;
import ka.commune.business.PdfGenerator;

public class MembreConseilController implements Initializable{

	@FXML private HBox OperationPane;
	private GestionMembreConseil gMembreConseil=new GestionMembreConseil();
	//  Membres du Conseils
	
	@FXML private Tab tabMembreConseil;
	@FXML private TableView<Membreconseil> tableMembresConseil ;
	@FXML private TextField textSearchMembre;
	private final TableColumn<Membreconseil, String> columnNomArabe= new TableColumn<>("الإسم العائلي");
	private final TableColumn<Membreconseil, String> columnPrenomArabe= new TableColumn<>("الإسم الشخصي");
	private final TableColumn<Membreconseil, String> columnNom= new TableColumn<>("Nom");
	private final TableColumn<Membreconseil, String> columnPrenom= new TableColumn<>("Prénom");
	private final TableColumn<Membreconseil, String> columnDateNaissance= new TableColumn<>("تاريخ الإزدياد");
	private final TableColumn<Membreconseil, String> columnLieuNaissance=new TableColumn<>("مكان الإزدياد");
	private final TableColumn<Membreconseil, String> columnAdresse=new TableColumn<>("العنوان");
	private final TableColumn<Membreconseil, String> columnProfession=new TableColumn<>("المهنة");
	private final TableColumn<Membreconseil, String> columnCin=new TableColumn<>("البطاقة الوطنية");
	private final TableColumn<Membreconseil, String> columnPartiePolitique= new TableColumn<>("الإنتماء السياسي");
	private final TableColumn<Membreconseil, Integer> columnNumeroCirconscription= new TableColumn<>("رقم الدائرة");
	private final TableColumn<Membreconseil, String> columnDesignationCirconscription=new TableColumn<>("عنوان الدائرة");
	private final TableColumn<Membreconseil, String> columnTelephone=new TableColumn<>("رقم الهاتف");
	private final TableColumn<Membreconseil, String> columnFonction=new TableColumn<>("الصفة داخل المجلس");
	private final TableColumn<Membreconseil, String> columnNiveauScolaire=new TableColumn<>("المستوى الثقافي");
	private final TableColumn<Membreconseil,HBox> columnActionMembre=new TableColumn<>();
	private final TableColumn<Membreconseil, Integer> columnNumeroMembre= new TableColumn<>("الرقم الترتيبي");
	private final TableColumn<Membreconseil, String> columnEmail= new TableColumn<>("البريد الإلكتروني");
	// Fonctions

	@FXML private JFXTextField textFonction;
	@FXML private TextField textSearchFonction;
	@FXML private TableView<Fonction> tableFonction;
	private TableColumn<Fonction, String> columnDesignationFonction=new TableColumn<>("الصفة داخل المجلس");
	private TableColumn<Fonction, Integer> columnNumeroFonction=new TableColumn<>("الرقم الترتيبي");
	private TableColumn<Fonction, HBox> columnActionFonction=new TableColumn<>();


	// Niveau Scolaire

	@FXML private TextField textSearchNiveauScolaire;
	@FXML private JFXTextField textNiveauScolaire;
	@FXML private TableView<NiveauScolaire> tableNiveauScolaire;
	private TableColumn<NiveauScolaire, String> columnNS=new TableColumn<>("المستوى الدراسي");
	private TableColumn<NiveauScolaire, Integer> columnNumeroNS=new TableColumn<>("الرقم الترتيبي");
	private TableColumn<NiveauScolaire, HBox> columnActionNS=new TableColumn<>();

	// Parties Politiques
	
	@FXML private TextField textSearchPartie;
	@FXML private TextField textPartie;
	@FXML private TableView<Partiepolitique> tablePartiePolitique;
	private TableColumn<Partiepolitique, String> columnDesignationPartie=new TableColumn<>("الإنتماء السياسي");
	private TableColumn<Partiepolitique, Button> columnActionPartie=new TableColumn<>();
	private TableColumn<Partiepolitique, Integer> columnNumeroPartie=new TableColumn<>("الرقم الترتيبي");
	

	// Circonscription
	
	@FXML private TextField textSearchCirconscription;
	@FXML private TextField textCirconscription;
	@FXML private TextField textNumeroCirconscription;
	@FXML private TableView<Circonscription> tableCirconscription;
	private TableColumn<Circonscription, Integer> columnNCirconscription=new TableColumn<>("الرقم الترتيبي");
	private TableColumn<Circonscription, String> columnCirconscription=new TableColumn<>("عنوان الدائرة");
	private TableColumn<Circonscription, Integer> columnNumCirconscription=new TableColumn<>("رقم الدائرة");
	private TableColumn<Circonscription, Button> columnActionCirconscription=new TableColumn<>();
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		remplirTableMembre(textSearchMembre.getText().toLowerCase());
		remplirTableCirconscription(textCirconscription.getText().toLowerCase());
		remplirTablePartie(textSearchPartie.getText().toLowerCase());
		remplirTableFonction(textSearchFonction.getText().toLowerCase());
		remplirTableNiveauScolaire(textSearchNiveauScolaire.getText().toLowerCase());
		textSearchFonction.textProperty().addListener(tapingSearchFonction);
		textSearchNiveauScolaire.textProperty().addListener(tapingSearchNiveauScolaire);
		textSearchMembre.textProperty().addListener(tapingSearchMembre);
		textSearchCirconscription.textProperty().addListener(tapingSearchCirconsscription);
		textSearchPartie.textProperty().addListener(tapingSearchPartie);
		OperationPane.getChildren().remove(textSearchCirconscription);
		OperationPane.getChildren().remove(textSearchPartie);
		tablePartiePolitique.setEditable(true);
		manageFonction();
		manageNiveauScolaire();
		manageTablePartie();
		manageCirconscription();
	}

	@FXML public void downloadListMembres()
	{
		if(tableMembresConseil.getItems().size()==0)
		{
			Alert dialog = new Alert(Alert.AlertType.ERROR);
			dialog.setTitle("تخطأ");
			dialog.setHeaderText("لائحة الأعضاء فارغة !");
			dialog.showAndWait();
			return;
		}
		PdfGenerator tp=new PdfGenerator();
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);
		try {
			if(tp.downloadListMembres(tableMembresConseil.getItems(),selectedDirectory.getAbsolutePath()))
			{
				Alert dialog = new Alert(Alert.AlertType.INFORMATION);
				dialog.setTitle("تم بنجاح");
				dialog.setHeaderText("العملية تمت بنجاح !");
				dialog.showAndWait();
			}
			else
			{
				Alert dialog = new Alert(Alert.AlertType.ERROR);
				dialog.setTitle("تخطأ");
				dialog.showAndWait();
			}
		}
		catch(NullPointerException e) {
			return;
		}
	}
	
	// Search Fields Listeners
	
	ChangeListener<String> tapingSearchMembre=new ChangeListener<String>()
    {
    	@Override
    	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    		// TODO Auto-generated method stub
    		remplirTableMembre(newValue);
    	}
    };
    
    ChangeListener<String> tapingSearchPartie=new ChangeListener<String>()
    {
    	@Override
    	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    		// TODO Auto-generated method stub
    		remplirTablePartie(newValue);
    	}
    };
    
    ChangeListener<String> tapingSearchCirconsscription=new ChangeListener<String>()
    {
    	@Override
    	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    		// TODO Auto-generated method stub
    		remplirTableCirconscription(newValue);
    	}
    };
	ChangeListener<String> tapingSearchFonction=new ChangeListener<String>()
	{
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			// TODO Auto-generated method stub
			remplirTableFonction(newValue);
		}
	};
	ChangeListener<String> tapingSearchNiveauScolaire=new ChangeListener<String>()
	{
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			// TODO Auto-generated method stub
			remplirTableNiveauScolaire(newValue);
		}
	};
	
    // All tables
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void remplirTableMembre(String critere)
	{
		ObservableList<Membreconseil> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableMembresConseil.getColumns().clear();
    	tableMembresConseil.getItems().clear();
		columnNomArabe.setCellValueFactory(new PropertyValueFactory<>("nomArabe"));
		columnPrenomArabe.setCellValueFactory(new PropertyValueFactory<>("prenomArabe"));
		columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		columnLieuNaissance.setCellValueFactory(new PropertyValueFactory<>("lieuNaissance"));
		columnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
		columnProfession.setCellValueFactory(new PropertyValueFactory<>("profession"));
		columnCin.setCellValueFactory(new PropertyValueFactory<>("cin"));
		columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		columnNumeroCirconscription.setCellValueFactory(new PropertyValueFactory<>("circonscription.numero"));
		columnDateNaissance.setCellValueFactory(param -> {
			// TODO Auto-generated method stub
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
			if (param.getValue().getDateNaissance()!= null)
				return new SimpleStringProperty(format1.format(param.getValue().getDateNaissance()));
			else return null;
		});
		
		columnPartiePolitique.setCellValueFactory(param -> {
			// TODO Auto-generated method stub
			if (param.getValue().getPartiepolitique()!= null)
				return new SimpleStringProperty(param.getValue().getPartiepolitique().getDesignationPartiePolitique());
			else return null;
		});
		
		columnNumeroCirconscription.setCellValueFactory(param -> {
			// TODO Auto-generated method stub
			if (param.getValue().getCirconscription()!= null)
				return new SimpleIntegerProperty(param.getValue().getCirconscription().getNumero()).asObject();
			return null;
		});
		columnNumeroMembre.setCellValueFactory(new PropertyValueFactory<>("numero"));
		columnDesignationCirconscription.setCellValueFactory(param -> {
			// TODO Auto-generated method stub
			if (param.getValue().getCirconscription()!= null)
				return new SimpleStringProperty(param.getValue().getCirconscription().getDesignationCirconscription());
			else return null;
		});
		
		columnActionMembre.setCellValueFactory(new PropertyValueFactory("actionButtons"));
		columnTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
		columnFonction.setCellValueFactory(new PropertyValueFactory<>("fonction"));
		columnNiveauScolaire.setCellValueFactory(new PropertyValueFactory<>("niveauScolaire"));
		try {
			int i=0;
			for(Membreconseil mc : gMembreConseil.findAllMembreConseil())
			{
				if( mc.getSearchText().toLowerCase().contains(critere.toLowerCase()))
				{
					mc.setNumero(++i);
					temp.add(mc);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		for(Membreconseil mc :temp)
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
				if (result.get() == buttonYes){
					gMembreConseil.deleteMembreConseil(mc);
					remplirTableMembre(textSearchMembre.getText());
				}
			});
			Button update=new Button("تعديل");
			update.setOnAction(e->{
				//tableMembresConseil.getSelectionModel().select(mc);
				ManageMembreConseilController.setMembreConseil(mc);
				updateMembreConseil();
			});
			
			Button membreInfo =new Button();
			membreInfo.setOnAction(e->{
				PdfGenerator pdf =new PdfGenerator();
				DirectoryChooser directoryChooser = new DirectoryChooser();
		        File selectedDirectory = directoryChooser.showDialog(null);
		        List<Membreconseil> list=new Vector<>();
		        list.add(mc);
		        try {
		        	if(pdf.printMembreInformation(list,selectedDirectory.getAbsolutePath()))
		        	{
		        		Alert dialog = new Alert(AlertType.INFORMATION);
		        		dialog.setTitle("تم بنجاح");
						dialog.setHeaderText("العملية تمت بنجاح !");
						dialog.showAndWait();
		        	}
		        	else
		        	{
		        		Alert dialog = new Alert(AlertType.ERROR);
		        		dialog.setTitle("خطأ");
						dialog.setHeaderText("هناك وثيقة بنفس الإسم مفتوحة ببرنامج آخر !");
						dialog.showAndWait();
		        	}
				} catch (Exception e2) {
					// TODO: handle exception
					return;
				}
			});
			mc.getActionButtons().getChildren().clear();
			mc.getActionButtons().getChildren().addAll(delete,update,membreInfo);
			setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
			setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");
			FontAwesomeIconView iconDown = new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD);
			iconDown.setFill(Paint.valueOf("#FFFFFF"));
			setButtonFont(membreInfo, iconDown, "#78e08f");
		}
		
		tableMembresConseil.setItems(temp);
		tableMembresConseil.getColumns().addAll(columnActionMembre,columnNumeroMembre,columnNomArabe,columnPrenomArabe,columnNom,columnPrenom,columnDateNaissance
				,columnLieuNaissance,columnAdresse,columnEmail,columnProfession,columnCin,columnPartiePolitique,columnNumeroCirconscription
				,columnDesignationCirconscription,columnTelephone,columnFonction,columnNiveauScolaire);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTablePartie(String critere)
	{
		ObservableList<Partiepolitique> temp =FXCollections.observableArrayList();
		temp.clear();
    	tablePartiePolitique.getColumns().clear();
    	tablePartiePolitique.getItems().clear();
    	columnActionPartie.setCellValueFactory(new PropertyValueFactory("delete"));
		columnNumeroPartie.setCellValueFactory(new PropertyValueFactory("numero"));
		columnDesignationPartie.setCellValueFactory(new PropertyValueFactory<Partiepolitique,String>("designationPartiePolitique"));
		try {
			int i=0;
			for(Partiepolitique pp :gMembreConseil.findAllPartiePolitique())
			{
				if(pp.getDesignationPartiePolitique().toLowerCase().contains(critere))
				{
					pp.setNumero(++i);
					temp.add(pp);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		for(Partiepolitique pp : temp)
		{
			pp.getDelete().setOnAction(e->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("رسالة التأكد");
				alert.setHeaderText("لإتمام الحذف إضغط على حذف");
				ButtonType buttonYes = new ButtonType("حذف");
				
				ButtonType buttonNo = new ButtonType("إلغاء", ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(buttonYes,buttonNo);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonYes){
					if(!gMembreConseil.deletePartiePolitique(pp))
					{
						Alert dialog = new Alert(AlertType.ERROR);
						dialog.setTitle("خطأ");
						dialog.setHeaderText("لا يمكن حذف هذا الإنتماء لأنه مستعمل !");
						dialog.showAndWait();
					}
					remplirTablePartie(textSearchPartie.getText());
				}
			});
			setButtonIco(pp.getDelete(),"/ka/commune/view/resources/img/delete.png", "#d63031");
		}
		tablePartiePolitique.setItems(temp);
		tablePartiePolitique.getColumns().addAll(columnNumeroPartie,columnDesignationPartie,columnActionPartie);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableFonction(String critere)
	{
		ObservableList<Fonction> temp =FXCollections.observableArrayList();
		temp.clear();
		tableFonction.getColumns().clear();
		tableFonction.getItems().clear();
		columnActionFonction.setCellValueFactory(new PropertyValueFactory("action"));
		columnNumeroFonction.setCellValueFactory(new PropertyValueFactory("numero"));
		columnDesignationFonction.setCellValueFactory(new PropertyValueFactory<Fonction,String>("designation"));
		try {
			int i=0;
			for(Fonction f :gMembreConseil.findAllFonction())
			{
				if(f.getDesignation().toLowerCase().contains(critere.toLowerCase())) {
					f.setNumero(++i);
					temp.add(f);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		for(Fonction f : temp)
		{
			Button delete=new Button();
			delete.setOnAction(e->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("رسالة التأكد");
				alert.setHeaderText("لإتمام الحذف إضغط على حذف");
				ButtonType buttonYes = new ButtonType("حذف");
				ButtonType buttonNo = new ButtonType("إلغاء", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(buttonYes,buttonNo);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonYes){
					if(!gMembreConseil.deleteFocntion(f))
					{
						Alert dialog = new Alert(AlertType.ERROR);
						dialog.setTitle("خطأ");
						dialog.setHeaderText("لا يمكن حذف هذه الوظيفة لأنه مستعملة !");
						dialog.showAndWait();
					}
					remplirTableFonction(textSearchFonction.getText());
				}
			});
			setButtonIco(delete,"/ka/commune/view/resources/img/delete.png", "#d63031");
			f.getAction().getChildren().clear();
			f.getAction().getChildren().add(delete);
		}
		tableFonction.setItems(temp);
		tableFonction.getColumns().addAll(columnNumeroFonction,columnDesignationFonction,columnActionFonction);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableNiveauScolaire(String critere)
	{
		ObservableList<NiveauScolaire> temp =FXCollections.observableArrayList();
		temp.clear();
		tableNiveauScolaire.getColumns().clear();
		tableNiveauScolaire.getItems().clear();
		columnNumeroNS.setCellValueFactory(new PropertyValueFactory("numero"));
		columnActionNS.setCellValueFactory(new PropertyValueFactory("action"));
		columnNS.setCellValueFactory(new PropertyValueFactory<NiveauScolaire,String>("designation"));
		try {
			int i=0;
			for(NiveauScolaire ns :gMembreConseil.findAllNiveauScolaire())
			{
				if(ns.getDesignation().toLowerCase().contains(critere.toLowerCase())) {
					ns.setNumero(++i);
					temp.add(ns);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		for(NiveauScolaire ns : temp)
		{
			Button delete=new Button();
			delete.setOnAction(e->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("رسالة التأكد");
				alert.setHeaderText("لإتمام الحذف إضغط على حذف");
				ButtonType buttonYes = new ButtonType("حذف");
				ButtonType buttonNo = new ButtonType("إلغاء", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(buttonYes,buttonNo);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonYes){
					if(!gMembreConseil.deleteNiveauScolaire(ns)){
						Alert dialog = new Alert(AlertType.ERROR);
						dialog.setTitle("خطأ");
						dialog.setHeaderText("لا يمكن حذف هذا المستوى لأنه مستعمل !");
						dialog.showAndWait();
					}
					remplirTableNiveauScolaire(textSearchNiveauScolaire.getText());
				}
			});
			setButtonIco(delete,"/ka/commune/view/resources/img/delete.png", "#d63031");
			ns.getAction().getChildren().clear();
			ns.getAction().getChildren().add(delete);
		}
		tableNiveauScolaire.setItems(temp);
		tableNiveauScolaire.getColumns().addAll(columnNumeroNS,columnNS,columnActionNS);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableCirconscription(String critere)
	{
		ObservableList<Circonscription> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableCirconscription.getColumns().clear();
    	tableCirconscription.getItems().clear();
		columnNumCirconscription.setCellValueFactory(new PropertyValueFactory<>("numero"));
		columnCirconscription.setCellValueFactory(new PropertyValueFactory<Circonscription,String>("designationCirconscription"));
		columnActionCirconscription.setCellValueFactory(new PropertyValueFactory("delete"));
		try {
			for(Circonscription c :gMembreConseil.findAllCirconscription())
			{
				if(c.getDesignationCirconscription().toLowerCase().contains(critere.toLowerCase())
						|| (c.getNumero()+"").contains(critere.toLowerCase()))
					temp.add(c);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		for(Circonscription c : temp)
		{
			c.getDelete().setOnAction(e->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("رسالة التأكد");
				alert.setHeaderText("لإتمام الحذف إضغط على حذف");
				ButtonType buttonYes = new ButtonType("حذف");
				
				ButtonType buttonNo = new ButtonType("إلغاء", ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(buttonYes,buttonNo);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonYes){
					if(!gMembreConseil.deleteCirconscription(c))
					{
						Alert dialog = new Alert(AlertType.ERROR);
						dialog.setTitle("خطأ");
						dialog.setHeaderText("لا يمكن حذف هذه الدائرة لأنها مستعملة !");
						dialog.showAndWait();
					}
					remplirTableCirconscription(textSearchCirconscription.getText());
				}
				
			});
			setButtonIco(c.getDelete(),"/ka/commune/view/resources/img/delete.png", "#d63031");
		}
		tableCirconscription.setItems(temp);
		tableCirconscription.getColumns().addAll(columnNumCirconscription,columnCirconscription,columnActionCirconscription);
	}
	
	// Adding methodes
	
	@FXML
	public void addMembreConseil()
	{
		try 
		{
			Parent root ;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/ka/commune/view/ManageMembreConseilView.fxml"));
			root = loader.load();
			//root=FXMLLoader.load(getClass().getResource("/ka/commune/view/ManageMembreConseilView.fxml"));
			App.rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@FXML
	public void addPartie()
	{
		if(textPartie.getText().equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("يرجى إدخال اسم الإنتماء السياسي!");
			alert.showAndWait();
			return;
		}
		Partiepolitique partiePolitique=new Partiepolitique();
		partiePolitique.setDesignationPartiePolitique(textPartie.getText());
		try {
			if(gMembreConseil.addPartiePolitiuqe(partiePolitique))
			{
				textPartie.setText("");
				textSearchPartie.setText("");
				remplirTablePartie("");
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("هذا الإنتماء موجود بالفعل!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	
	@FXML
	public void addCirconscription()
	{
		if(textCirconscription.getText().equals("") || textNumeroCirconscription.getText().equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("يرجى إدخال جميع المعلومات!");
			alert.showAndWait();
			return;
		}
		Circonscription circonscription=new Circonscription();
		circonscription.setDesignationCirconscription(textCirconscription.getText());
		circonscription.setNumero(Integer.parseInt(textNumeroCirconscription.getText()));
		try {
			if(gMembreConseil.addCirconscription(circonscription))
			{
				textCirconscription.setText("");
				textNumeroCirconscription.setText("");
				textSearchCirconscription.setText("");
				remplirTableCirconscription("");
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("رقم الدائرة أو عنوانها مستعمل من قبل!");
				alert.showAndWait();
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void addNiveauScolaire() {
		if(textNiveauScolaire.getText().equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("يرجى إدخال المستوى الدراسي!");
			alert.showAndWait();
			return;
		}
		NiveauScolaire niveauScolaire=new NiveauScolaire();
		niveauScolaire.setDesignation(textNiveauScolaire.getText());
		try {
			if(gMembreConseil.addNiveauScolaire(niveauScolaire))
			{
				textNiveauScolaire.setText("");
				textSearchNiveauScolaire.setText("");
				remplirTableNiveauScolaire(textSearchNiveauScolaire.getText().toLowerCase());
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("هذا المستوى الدراسي موجود بالفعل");
				alert.showAndWait();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void addFonction(ActionEvent actionEvent) {
		if(textFonction.getText().equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("يرجى إدخال الصفة!");
			alert.showAndWait();
			return;
		}
		Fonction fonction=new Fonction();
		fonction.setDesignation(textFonction.getText());
		try {
			if(gMembreConseil.addFonction(fonction))
			{
				textFonction.setText("");
				textSearchFonction.setText("");
				remplirTableFonction(textSearchFonction.getText().toLowerCase());
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("هذه الصفة موجودة بالفعل");
				alert.showAndWait();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// Updating methodes
	
	private void manageTablePartie()
	{
		columnDesignationPartie.setCellFactory(TextFieldTableCell.forTableColumn());
		columnDesignationPartie.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Partiepolitique,String>>() {
		@Override
		public void handle(CellEditEvent<Partiepolitique, String> edittedCell) {
			// TODO Auto-generated method stub
			Partiepolitique partiePolitique=tablePartiePolitique.getSelectionModel().getSelectedItem();
			if(edittedCell.getNewValue().toString().equals(""))
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("يرجى إدخال اسم الإنتماء السياسي");
				partiePolitique.setDesignationPartiePolitique(edittedCell.getOldValue().toString());
				//alert.setContentText("Ooops, there was an error!");
				alert.showAndWait();
			}
			else
			{
				partiePolitique.setDesignationPartiePolitique(edittedCell.getNewValue().toString());

				if(!gMembreConseil.updatePartiePolitique(partiePolitique))
				{
					partiePolitique.setDesignationPartiePolitique(edittedCell.getOldValue().toString());
					Alert alert = new Alert(AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					alert.setHeaderText("هذا الإنتماء موجود بالفعل");
					//alert.setContentText("Ooops, there was an error!");
					alert.showAndWait();
				}
			}
			remplirTablePartie(textSearchPartie.getText());
		}
		});
	}

	private void manageFonction()
	{
		columnDesignationFonction.setCellFactory(TextFieldTableCell.forTableColumn());
		columnDesignationFonction.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Fonction,String>>() {
			@Override
			public void handle(CellEditEvent<Fonction, String> edittedCell) {
				// TODO Auto-generated method stub
				Fonction fonction=tableFonction.getSelectionModel().getSelectedItem();
				if(edittedCell.getNewValue().toString().equals(""))
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					alert.setHeaderText("يرجى إدخال الصفة!");
					fonction.setDesignation(edittedCell.getOldValue().toString());
					alert.showAndWait();
				}
				else
				{
					fonction.setDesignation(edittedCell.getNewValue().toString());

					if(!gMembreConseil.updateFonction(fonction))
					{
						fonction.setDesignation(edittedCell.getOldValue().toString());
						Alert alert = new Alert(AlertType.ERROR);
						alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
						alert.setTitle("خطأ");
						alert.setHeaderText("هذه الصفة موجودة بالفعل");
						alert.showAndWait();
					}
				}
				remplirTableFonction(textSearchFonction.getText().toLowerCase());
			}
		});
	}

	private void manageNiveauScolaire()
	{
		columnNS.setCellFactory(TextFieldTableCell.forTableColumn());
		columnNS.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<NiveauScolaire,String>>() {
			@Override
			public void handle(CellEditEvent<NiveauScolaire, String> edittedCell) {
				// TODO Auto-generated method stub
				NiveauScolaire niveauScolaire=tableNiveauScolaire.getSelectionModel().getSelectedItem();
				if(edittedCell.getNewValue().toString().equals(""))
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					alert.setHeaderText("يرجى إدخال الصفة!");
					niveauScolaire.setDesignation(edittedCell.getOldValue().toString());
					alert.showAndWait();
				}
				else
				{
					niveauScolaire.setDesignation(edittedCell.getNewValue().toString());

					if(!gMembreConseil.updateNiveauScolaire(niveauScolaire))
					{
						niveauScolaire.setDesignation(edittedCell.getOldValue().toString());
						Alert alert = new Alert(AlertType.ERROR);
						alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
						alert.setTitle("خطأ");
						alert.setHeaderText("هذه الصفة موجودة بالفعل");
						alert.showAndWait();
					}
				}
				remplirTableNiveauScolaire(textSearchNiveauScolaire.getText().toLowerCase());
			}
		});
	}
	
	private void manageCirconscription()
	{
		tableCirconscription.setEditable(true);
		columnCirconscription.setEditable(true);
		columnNumCirconscription.setEditable(true);
		columnCirconscription.setCellFactory(TextFieldTableCell.forTableColumn());
		columnNumCirconscription.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
			@Override
			public String toString(Integer object) {
				// TODO Auto-generated method stub
				return String.valueOf(object);
			}
			
			@Override
			public Integer fromString(String string) {
				// TODO Auto-generated method stub
				return Integer.parseInt(string);
			}
		}));
		columnCirconscription.setOnEditCommit(e->{
			Circonscription circonscription= tableCirconscription.getSelectionModel().getSelectedItem();
			if(e.getNewValue().toString().equals(""))
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("يرجى إدخال عنوان الدائرة");
				circonscription.setDesignationCirconscription(e.getOldValue().toString());
				//alert.setContentText("Ooops, there was an error!");
				alert.showAndWait();
			}
			else
			{
				circonscription.setDesignationCirconscription(e.getNewValue().toString());
				
				if(!gMembreConseil.updateCirconscription(circonscription))
				{
					circonscription.setDesignationCirconscription(e.getOldValue().toString());
					Alert alert = new Alert(AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					alert.setHeaderText("هذا العنوان مستعمل من قبل");
					//alert.setContentText("Ooops, there was an error!");
					alert.showAndWait();
				}
			}
			textSearchCirconscription.setText("");
			remplirTableCirconscription("");
		});
		
		columnNumCirconscription.setOnEditCommit(e->{
			Circonscription circonscription= tableCirconscription.getSelectionModel().getSelectedItem();
			if(e.getNewValue().toString().equals(""))
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("يرجى إدخال رقم الدائرة");
				circonscription.setNumero(Integer.parseInt(e.getOldValue().toString()));
				//alert.setContentText("Ooops, there was an error!");
				alert.showAndWait();
			}
			else
			{
				circonscription.setNumero(Integer.parseInt(e.getNewValue().toString()));
				if(!gMembreConseil.updateCirconscription(circonscription))
				{
					circonscription.setNumero(Integer.parseInt(e.getOldValue().toString()));
					Alert alert = new Alert(AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					alert.setHeaderText("هذا الرقم مستعمل من قبل");
					//alert.setContentText("Ooops, there was an error!");
					alert.showAndWait();
				}
			}
			remplirTableCirconscription(textSearchCirconscription.getText());
		});
	}
	
	@FXML
	public void updateMembreConseil()
	{
		try 
		{
			Parent root ;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("/ka/commune/view/ManageMembreConseilView.fxml"));
			root = loader.load();
			App.rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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

}
