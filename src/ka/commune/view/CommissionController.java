package ka.commune.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import ka.commune.business.App;
import ka.commune.business.GestionMembreConseil;
import ka.commune.entity.Commission;
import ka.commune.entity.MembreCommission;
import ka.commune.entity.Membreconseil;
import ka.commune.entity.NiveauScolaire;

public class CommissionController implements Initializable{
	
	@FXML private JFXTextField textDesignationCommission;
	@FXML private JFXComboBox<Commission> textCommission;
	@FXML private JFXComboBox<Membreconseil> textMembreConseil;
	@FXML private TableView<Commission> tableListCommission;
	@FXML private TextField textSearchCommission;
	@FXML private TextField textSearchMembreCommission;
	private TableColumn<Commission, String> columnDesignationCommission=new TableColumn<Commission, String>("اسم اللجنة");
	private TableColumn<Commission, Button> columnActionCommission=new TableColumn<Commission, Button>();
	private TableColumn<Commission, Integer> columnNumeroCommission=new TableColumn<>("الرقم الترتيبي");
	private GestionMembreConseil gMembreConseil;
	
	@FXML private TableView<MembreCommission> tableMembreCommission ;
	private TableColumn<MembreCommission, String> columnMembreConseil=new TableColumn<MembreCommission, String>("عضو المجلس");
	private TableColumn<MembreCommission, String> columnCommission = new TableColumn<MembreCommission, String>("اسم اللجنة");
	private TableColumn<MembreCommission, Integer> columnNumeroMembre=new TableColumn<>("الرقم الترتيبي");
	private TableColumn<MembreCommission,Button > columnAction = new TableColumn<MembreCommission, Button>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		gMembreConseil=new GestionMembreConseil();
		textSearchCommission.textProperty().addListener(tapingSearchCommission);
		textSearchMembreCommission.textProperty().addListener(tapingSearchMembre);
		remplirTableCommission("");
		manageCommission();
		initializeComboBox();
		remplirtableMembreCommission("");
	}
	
	@FXML
	private void getInvitations()
	{
		//TestPdf tp=new TestPdf();
		//tp.genererInvitation(tableListCommission.getItems().get(0));
		
	}
		// Remplissage des tables
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirTableCommission(String critere)
	{
		ObservableList<Commission> temp =FXCollections.observableArrayList();
		temp.clear();
    	tableListCommission.getColumns().clear();
    	tableListCommission.getItems().clear();
		columnNumeroCommission.setCellValueFactory(new PropertyValueFactory("numero"));
    	columnActionCommission.setCellValueFactory(new PropertyValueFactory("delete"));
		columnDesignationCommission.setCellValueFactory(new PropertyValueFactory<Commission,String>("designationCommission"));
		try 
		{
			int i=0;
			for(Commission c :gMembreConseil.findAllCommission())
			{
				if(c.getDesignationCommission()!=null && c.getDesignationCommission().toLowerCase().indexOf(critere)>=0)
				{
					c.setNumero(++i);
					temp.add(c);
				}
			}
			} catch (Exception e) {
			// TODO: handle exception
		}
		for(Commission c : temp)
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
					gMembreConseil.deleteCommission(c);
					remplirTableCommission(textSearchCommission.getText());
					initializeComboBox();
				}
			});
			setButtonIco(c.getDelete(), "/ka/commune/view/resources/img/delete.png", "#d63031");
		}
		tableListCommission.setItems(temp);
		tableListCommission.getColumns().addAll(columnNumeroCommission,columnDesignationCommission,columnActionCommission);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void remplirtableMembreCommission(String critere)
	{
		ObservableList<MembreCommission> temp =FXCollections.observableArrayList();
		temp.clear();
		tableMembreCommission.getColumns().clear();
    	tableMembreCommission.getItems().clear();
		columnNumeroMembre.setCellValueFactory(new PropertyValueFactory("numero"));
    	columnAction.setCellValueFactory(new PropertyValueFactory("delete"));
    	columnMembreConseil.setCellValueFactory(new Callback<CellDataFeatures<MembreCommission, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MembreCommission, String> param) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty(param.getValue().getMembreConseil().getNomArabe()+" "
				+param.getValue().getMembreConseil().getPrenomArabe());
			}
		});
    	columnCommission.setCellValueFactory(new Callback<CellDataFeatures<MembreCommission, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MembreCommission, String> param) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty(param.getValue().getCommission().getDesignationCommission());
			}
		});
    	int i=0;
    	for(Commission c: gMembreConseil.findAllCommission())
    	{
    		for(Membreconseil m: c.getMembreconseils())
    		{
    			if(m.getMandat().getIdMandat()==App.getMandat().getIdMandat())
	    			if((m.getNomArabe()!=null && m.getNomArabe().contains(critere)) || (m.getPrenomArabe()!=null && m.getPrenomArabe().contains(critere))
	    					|| (c.getDesignationCommission()!=null && c.getDesignationCommission().contains(critere)))
	    			{
		    			MembreCommission mc=new MembreCommission();
		    			mc.setCommission(c);
		    			mc.setMembreConseil(m);
		    			mc.setNumero(++i);
		    			temp.add(mc);
	    			}
    		}
    	}
    	for(MembreCommission mc:temp)
    	{
    		mc.getDelete().setOnAction(e->{
    			Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("رسالة التأكد");
				alert.setHeaderText("لإتمام الحذف إضغط على حذف");
				ButtonType buttonYes = new ButtonType("حذف");
				ButtonType buttonNo = new ButtonType("إلغاء", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(buttonYes,buttonNo);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonYes){
					gMembreConseil.removeMembreCommission(mc);
	    			remplirtableMembreCommission(textSearchMembreCommission.getText());
				}
    		});
    		setButtonIco(mc.getDelete(), "/ka/commune/view/resources/img/delete.png", "#d63031");
    	}
    	tableMembreCommission.setItems(temp);
    	tableMembreCommission.getColumns().addAll(columnNumeroMembre,columnMembreConseil,columnCommission,columnAction);
		
	}
	
		// Searching
	
	ChangeListener<String> tapingSearchMembre= (observable, oldValue, newValue) -> {
		// TODO Auto-generated method stub
		remplirtableMembreCommission(newValue);
	};
    
    ChangeListener<String> tapingSearchCommission= (observable, oldValue, newValue) -> {
		// TODO Auto-generated method stub
		remplirTableCommission(newValue);
	};
	
		// Ajout
	@FXML
	private void addCommission()
	{
		if(textDesignationCommission.getText().equals(""))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("يرجى إدخال اسم اللجنة");
			alert.showAndWait();
			return;
		}
		Commission commission=new Commission();
		commission.setDesignationCommission(textDesignationCommission.getText());
	
		if(gMembreConseil.addCommission(commission))
		{
			textDesignationCommission.setText("");
			remplirTableCommission(textSearchCommission.getText());
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("هذه اللجنة موجودة بالفعل");
			alert.showAndWait();
			return;
		}
	}
	@FXML
	private void addMembreCommission()
	{
		if(textMembreConseil.getValue()==null || textCommission.getValue()==null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("يرجى ملء جميع الخانات");
			alert.showAndWait();
			return;
		}
		MembreCommission membreCommission=new MembreCommission();
		membreCommission.setCommission(textCommission.getValue());
		membreCommission.setMembreConseil(textMembreConseil.getValue());
		if(gMembreConseil.addMembreCommission(membreCommission))
		{
			textMembreConseil.getSelectionModel().clearSelection();
			textCommission.getSelectionModel().clearSelection();
			textSearchMembreCommission.setText("");
			remplirtableMembreCommission(textSearchMembreCommission.getText());
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText(membreCommission.getMembreConseil().getNomArabe()+" "+membreCommission.getMembreConseil().getPrenomArabe()
					+" ينتمي مسبقا إلى "+membreCommission.getCommission().getDesignationCommission());
			alert.showAndWait();
			return;
		}
	}
	
	// Update
	private void manageCommission()
	{
		tableListCommission.setEditable(true);
		columnDesignationCommission.setEditable(true);
		columnDesignationCommission.setCellFactory(TextFieldTableCell.forTableColumn());
		columnDesignationCommission.setOnEditCommit(edittedCell -> {
			// TODO Auto-generated method stub
			Commission commission=tableListCommission.getSelectionModel().getSelectedItem();
			if(edittedCell.getNewValue().equals(""))
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("يرجى إدخال اسم اللجنة");
				commission.setDesignationCommission(edittedCell.getOldValue());
				alert.showAndWait();
			}
			else
			{
				commission.setDesignationCommission(edittedCell.getNewValue());

				if(!gMembreConseil.updateCommission(commission))
				{
					commission.setDesignationCommission(edittedCell.getOldValue());
					Alert alert = new Alert(AlertType.ERROR);
					alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
					alert.setTitle("خطأ");
					alert.setHeaderText("هذه اللجنة موجودة بالفعل");
					alert.showAndWait();
				}
			}
			remplirTableCommission(textSearchCommission.getText());
		});
	}
	
	private void initializeComboBox()
	{
		textCommission.getItems().addAll(gMembreConseil.findAllCommission());
		textMembreConseil.getItems().addAll(gMembreConseil.findAllMembreConseil());
	}
	
	private void setButtonIco(Button btn, String imgSRC, String Color) {
        Image imageTmp = new Image(getClass().getResourceAsStream(imgSRC), 18, 18, true, true);
        btn.setText("");
        btn.setGraphic(new ImageView(imageTmp));
        btn.setStyle("-fx-background-color:" + Color);
        btn.getStyleClass().add("cursor_hand");
    }
}
