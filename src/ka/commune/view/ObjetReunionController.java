package ka.commune.view;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import ka.commune.business.App;
import ka.commune.business.GestionMembreConseil;
import ka.commune.business.PdfGenerator;
import ka.commune.entity.Commission;
import ka.commune.entity.Domaine;
import ka.commune.entity.Objetreunion;
import ka.commune.entity.Sujetsession;

public class ObjetReunionController implements Initializable{

	private static Sujetsession sujetsession=null;
	public static Sujetsession getSujetsession() {
		return sujetsession;
	}

	public static void setSujetsession(Sujetsession sujetsession) {
		ObjetReunionController.sujetsession = sujetsession;
	}

	private Objetreunion managedObjet=null;
	@FXML private BorderPane borderPane;
	@FXML private VBox managePane;
	@FXML private TilePane buttonsPane;
	private GestionMembreConseil gMembreConseil = new GestionMembreConseil();
	@FXML private TableView<Objetreunion> tableObjet;
	@FXML private JFXTextField textObjet;
	@FXML private JFXComboBox<Domaine> comboDomaine;
	private TableColumn<Objetreunion, String> columnDomaine=new TableColumn<Objetreunion, String>("المجال");
	private TableColumn<Objetreunion, String> columnObjet=new TableColumn<Objetreunion, String>("الموضوع");
	private TableColumn<Objetreunion, HBox> columnAction=new TableColumn<Objetreunion, HBox>();
	@FXML private TextField textSearchObjet;
	@FXML private Button buttonClose;
	@FXML private Button buttonAddObjet;
	@FXML private Button buttonUpdateObjet;
	private final TableColumn<Objetreunion, Integer> columnNumero= new TableColumn<>("الرقم الترتيبي");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		textSearchObjet.textProperty().addListener(tapingSearchObjet);
		initializeComboBox();
		borderPane.getChildren().remove(managePane);
		remplirTableObjet("");
	}
	
	@FXML
	private void addObjet() {
		close();
		managedObjet=null;
		this.showManagePane();
	}
	
	private void showManagePane()
	{
		this.buttonsPane.getChildren().clear();
		this.buttonsPane.getChildren().add(buttonClose);
		if(this.managedObjet==null)
		{
			this.buttonsPane.getChildren().add(this.buttonAddObjet);
			this.borderPane.setRight(this.managePane);
		}
		else
		{
			this.borderPane.setRight(this.managePane);
			fillFields();
			this.buttonsPane.getChildren().add(this.buttonUpdateObjet);
		}
	}
	
	private void fillFields() {
		// TODO Auto-generated method stub
		for(Domaine d: comboDomaine.getItems())
			if(d.getIdDomaine()==managedObjet.getDomaine().getIdDomaine())
				comboDomaine.getSelectionModel().select(d);
		textObjet.setText(String.valueOf(managedObjet.getDesignationObjetReunion()));
	}
	@FXML
	private void close() {
		// TODO Auto-generated method stub
		//comboDomaine.getSelectionModel().clearSelection();
		comboDomaine.getSelectionModel().clearSelection();
		textObjet.setText("");
		borderPane.setRight(null);
	}
	
	private void initializeComboBox()
	{
		comboDomaine.getItems().clear();
		comboDomaine.getItems().addAll(gMembreConseil.findAllDomaines());
	}
	
	// Change Listeners
	
	ChangeListener<String> tapingSearchObjet=new ChangeListener<String>()
    {
    	@Override
    	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    		// TODO Auto-generated method stub
    		remplirTableObjet(newValue);
    	}
    };
	
    @FXML
	public void retour()
	{
		try 
  		{
  			Parent root ;
  			Thread.sleep(20);
  			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/OrdreView.fxml"));
  	        App.rootStage.setCenter(root);
  	        sujetsession=null;
  		} catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableObjet(String critere)
	{
		ObservableList<Objetreunion> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableObjet.getColumns().clear();
    	tableObjet.getItems().clear();
		columnNumero.setCellValueFactory(new PropertyValueFactory("numero"));
    	columnDomaine.setCellValueFactory(new Callback<CellDataFeatures<Objetreunion, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Objetreunion, String> param) {
				// TODO Auto-generated method stub
				if (param.getValue().getDomaine()!= null)
				{
					return new SimpleStringProperty(param.getValue().getDomaine().getDesignationDomaine());
				}
				else return null;
			}
		});
    	columnObjet.setCellValueFactory(new PropertyValueFactory<Objetreunion,String>("designationObjetReunion"));
    	columnAction.setCellValueFactory(new PropertyValueFactory("action"));
    	try {
    		int i=0;
			for(Objetreunion o : sujetsession.getObjetreunions())
			{
				if( ( o.getDesignationObjetReunion()!=null && o.getDesignationObjetReunion().toLowerCase().indexOf(critere)>=0)
						|| (o.getDomaine()!=null && o.getDomaine().getDesignationDomaine().toLowerCase().indexOf(critere)>=0 ) )
				{
					o.setNumero(++i);
					temp.add(o);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	for(Objetreunion o :temp)
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
					if(managedObjet!=null && managedObjet.getIdObjetReunion()==o.getIdObjetReunion())
						this.close();
					//gMembreConseil.deleteObjet(o);
					sujetsession.removeObjetreunion(o);
					gMembreConseil.deleteObjet(gMembreConseil.findObjetById(o.getIdObjetReunion()));
					gMembreConseil.updateReunion(sujetsession);
					remplirTableObjet(textSearchObjet.getText().toLowerCase());
				}
			});
			Button update=new Button("تعديل");
			update.setOnAction(e->{
				this.managedObjet=o;
				showManagePane();
			});
			o.getAction().getChildren().clear();
			o.getAction().getChildren().addAll(delete,update);
			setButtonIco(delete, "/ka/commune/view/resources/img/delete.png", "#d63031");
			setButtonIco(update, "/ka/commune/view/resources/img/edit.png", "#74b9ff");
		}
    	tableObjet.setItems(temp);
		tableObjet.getColumns().addAll(columnNumero,columnObjet,columnDomaine,columnAction);
	}
	
	@FXML
	private void addObjetReunion()
	{
		if( comboDomaine.getValue()==null || textObjet.getText().equals("") )
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال جميع المعلومات");
			alert.showAndWait();
			return;
		}
		Objetreunion o=new Objetreunion();
		o.setDomaine(comboDomaine.getValue());
		o.setDesignationObjetReunion(textObjet.getText());
		o.setSujetsession(sujetsession);
		sujetsession.getObjetreunions().add(gMembreConseil.addObjet(o));
		Sujetsession temp=gMembreConseil.updateReunion(sujetsession);
		if(temp!=null)
		{
			sujetsession=temp;
			this.close();
			textSearchObjet.setText("");
			remplirTableObjet("");
		}
	}
	
	@FXML void updateObjetReunion()
	{
		int index=sujetsession.getObjetreunions().indexOf(managedObjet);
		if( comboDomaine.getValue()==null || textObjet.getText().equals("") )
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال جميع المعلومات");
			alert.showAndWait();
			return;
		}
		Objetreunion o=gMembreConseil.findObjetById(managedObjet.getIdObjetReunion());;
		o.setDomaine(comboDomaine.getValue());
		o.setDesignationObjetReunion(textObjet.getText());
		sujetsession.getObjetreunions().set(index,o);
		Sujetsession temp=gMembreConseil.updateReunion(sujetsession);
		if(temp!=null)
		{
			sujetsession=temp;
			remplirTableObjet(textSearchObjet.getText().toLowerCase());
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
