package ka.commune.view;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import ka.commune.business.App;
import ka.commune.business.GestionMembreConseil;
import ka.commune.entity.*;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageMembreConseilController implements Initializable{



	@FXML private JFXTextField textNom;
	@FXML private JFXTextField textNomArabe;
	@FXML private JFXTextField textPrenom;
	@FXML private JFXTextField textPrenomArabe;
	@FXML private JFXTextField textTelephone;


	/*
	<?import ka.commune.view.control.NumberField?>
	<?import ka.commune.view.control.AlphabetField?>
	<?import ka.commune.view.control.ArabicAlphabetField?>
	 */

	//@FXML private AlphabetField textNom;
	//@FXML private ArabicAlphabetField textNomArabe;
	//@FXML private AlphabetField textPrenom;
	//@FXML private ArabicAlphabetField textPrenomArabe;
	@FXML private JFXTextField textEmail;
	@FXML private JFXComboBox<Fonction> comboFonction;
	@FXML private JFXComboBox<NiveauScolaire> comboNiveauScolaire;
	@FXML private JFXDatePicker textDateNaissance;
	@FXML private JFXTextField textLieuNaissance;
	@FXML private JFXTextField textCin;
	@FXML private JFXTextField textAdresse;
	@FXML private JFXTextField textProfession;
	@FXML private JFXComboBox<Partiepolitique> textPartiePolitique;
	@FXML private JFXComboBox<Circonscription> textCirconscription;
	//@FXML private NumberField textTelephone;
	@FXML private Button buttonAdd;
	@FXML private Button buttonUpdate;
	@FXML protected Button buttonRetour;
	@FXML private HBox buttonsPane;
	private static Membreconseil membreConseil;
	private GestionMembreConseil gMembreConseil;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		gMembreConseil=new GestionMembreConseil();
		initializeComboBox();
		if(membreConseil !=null)
		{
			buttonsPane.getChildren().remove(buttonAdd);
			fillFields();
		}
		else {
			buttonsPane.getChildren().remove(buttonUpdate);
		}
		textNomArabe.requestFocus();
	}
	
	@FXML
	public void update()
	{
		if(checkIfComplete())
		{
			initMembreConseil(membreConseil);
			if(gMembreConseil.updateMembreConseil(membreConseil))
			{
				this.clearAllFields();
				retour();
			}else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("رقم الهاتف أو رقم البطاقة الوطنية مستعمل من قبل");
				alert.showAndWait();
			}
		}else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال جميع المعلومات");
			alert.showAndWait();
		}

	}

	private boolean checkIfComplete()
	{
		if(textCirconscription.getValue()==null)
			return false;
		if(textDateNaissance.getValue()==null)
			return false;
		if(comboFonction.getValue()==null)
			return false;
		if(comboNiveauScolaire.getValue()==null)
			return false;
		if(textLieuNaissance.getText().equals(""))
			return false;
		if(textNom.getText().equals(""))
			return false;
		if(textNomArabe.getText().equals(""))
			return false;
		if(textPrenom.getText().equals(""))
			return false;
		if(textPrenomArabe.getText().equals(""))
			return false;
		if(textPartiePolitique.getValue()==null)
			return false;
		if(textProfession.getText().equals(""))
			return false;
		if(textAdresse.getText().equals(""))
			return false;
		if(textCin.getText().equals(""))
			return false;
		if(textTelephone.getText().equals(""))
			return false;
		return true;
	}

	private void initMembreConseil(Membreconseil membreConseil) {
		try {
			membreConseil.setCirconscription(textCirconscription.getValue());
			membreConseil.setDateNaissance((textDateNaissance.getValue())!=null ?
					Date.from(Instant.from(textDateNaissance.getValue().atStartOfDay(ZoneId.systemDefault()))):null);
			membreConseil.setFonction(comboFonction.getValue());
			membreConseil.setLieuNaissance(textLieuNaissance.getText());
			membreConseil.setNiveauScolaire(comboNiveauScolaire.getValue());
			membreConseil.setNom(textNom.getText());
			membreConseil.setNomArabe(textNomArabe.getText());
			membreConseil.setPartiepolitique(textPartiePolitique.getValue());
			membreConseil.setPrenom(textPrenom.getText());
			membreConseil.setPrenomArabe(textPrenomArabe.getText());
			membreConseil.setProfession(textProfession.getText());
			membreConseil.setAdresse(textAdresse.getText());
			membreConseil.setCin(textCin.getText());
			membreConseil.setMandat(App.getMandat());
			membreConseil.setTelephone(textTelephone.getText());
			membreConseil.setEmail(textEmail.getText());
		}catch (Exception e){}
	}

	@FXML
	public void add()
	{
		membreConseil=new Membreconseil();
		if(checkIfComplete())
		{
			initMembreConseil(membreConseil);
			if(gMembreConseil.addMembreConseil(membreConseil))
			{
				this.clearAllFields();
				retour();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
				alert.setTitle("خطأ");
				alert.setHeaderText("رقم الهاتف أو رقم البطاقة الوطنية مستعمل من قبل");
				alert.showAndWait();
			}
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			alert.setTitle("خطأ");
			alert.setHeaderText("المرجو إدخال جميع المعلومات");
			alert.showAndWait();
		}
	}
	
	private void clearAllFields() {
		// TODO Auto-generated method stub
		
	}

	@FXML
	public void retour()
	{
		try 
  		{
  			Parent root ;
  			Thread.sleep(20);
  			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/MembreConseilView.fxml"));
  	        App.rootStage.setCenter(root);
  	        membreConseil=null;
  		} catch (Exception e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
	}

	public static Membreconseil getMembreConseil() {
		return membreConseil;
	}

	public static void setMembreConseil(Membreconseil membreConseil) {
		ManageMembreConseilController.membreConseil = membreConseil;
	}
	
	
	private void fillFields()
	{
		if(membreConseil.getEmail()==null || membreConseil.getEmail().equals(""))
			textEmail.setText("");
		else
			textEmail.setText(membreConseil.getEmail());
		textAdresse.setText((!membreConseil.getAdresse().equals(null))?membreConseil.getAdresse():"");
		textCin.setText(membreConseil.getCin()!=null?membreConseil.getCin():"");
		if(membreConseil.getCirconscription()!=null)
		{
			for(Circonscription c : textCirconscription.getItems())
				if(c.getDesignationCirconscription().equals(membreConseil.getCirconscription().getDesignationCirconscription()))
				{
					textCirconscription.getSelectionModel().select(c);
					break;
				}
		}
		if(membreConseil.getFonction()!=null)
		{
			for(Fonction f : comboFonction.getItems())
				if(f.getIdFonction()==membreConseil.getFonction().getIdFonction())
				{
					comboFonction.getSelectionModel().select(f);
					break;
				}
		}
		if(membreConseil.getNiveauScolaire()!=null)
		{
			for(NiveauScolaire ns : comboNiveauScolaire.getItems())
				if(ns.getIdNiveauScolaire()==membreConseil.getNiveauScolaire().getIdNiveauScolaire())
				{
					comboNiveauScolaire.getSelectionModel().select(ns);
					break;
				}
		}
		if(membreConseil.getDateNaissance()!=null)
			textDateNaissance.setValue(membreConseil.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		textLieuNaissance.setText((membreConseil.getLieuNaissance() != null)?membreConseil.getLieuNaissance():"");
		textNom.setText((membreConseil.getNom() != null)?membreConseil.getNom():"");
		textNomArabe.setText((membreConseil.getNomArabe() != null)?membreConseil.getNomArabe():"");
		if(membreConseil.getPartiepolitique()!=null)
		{
			for(Partiepolitique p : textPartiePolitique.getItems())
				if(p.getDesignationPartiePolitique().equals(membreConseil.getPartiepolitique().getDesignationPartiePolitique()))
				{
					textPartiePolitique.getSelectionModel().select(p);
					break;
				}
		}
		textPrenom.setText((membreConseil.getPrenom() != null)?membreConseil.getPrenom():"");
		textPrenomArabe.setText((membreConseil.getPrenomArabe() != null)?membreConseil.getPrenomArabe():"");
		textTelephone.setText((membreConseil.getTelephone()!=null)?membreConseil.getTelephone():"");
		textProfession.setText((membreConseil.getProfession() != null)?membreConseil.getProfession():"");
	}
	
	private void initializeComboBox()
	{
		textCirconscription.getItems().clear();
		textPartiePolitique.getItems().clear();
		comboFonction.getItems().clear();
		comboNiveauScolaire.getItems().clear();
		textCirconscription.getItems().addAll(gMembreConseil.findAllCirconscription());
		textPartiePolitique.getItems().addAll(gMembreConseil.findAllPartiePolitique());
		comboNiveauScolaire.getItems().addAll(gMembreConseil.findAllNiveauScolaire());
		comboFonction.getItems().addAll(gMembreConseil.findAllFonction());
	}
}
