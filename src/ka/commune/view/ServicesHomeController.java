package ka.commune.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ka.commune.business.App;

public class ServicesHomeController implements Initializable{

	@FXML private HBox buttonAssociation;
	@FXML private HBox buttonCommissionEC;
	@FXML private HBox buttonActiviteBS;
	@FXML private HBox buttonGestionSalle;
	@FXML private HBox buttonGestionMateriel;
	
	@FXML private BorderPane rootStage;
	@FXML private ScrollPane homePane;
	
	
	private Vector<HBox> menuButtons=new Vector<HBox>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		App.rootStage=this.rootStage;
		menuButtons.add(buttonActiviteBS);
		menuButtons.add(buttonAssociation);
		menuButtons.add(buttonCommissionEC);
		menuButtons.add(buttonGestionMateriel);
		menuButtons.add(buttonGestionSalle);
		associationOnClick();
	}

	
	@FXML
	public void activiteBSOnClick()
	{
		styleButton(buttonActiviteBS);
		try 
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/ActiviteBSView.fxml"));
	       rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	public void associationOnClick()
	{
		styleButton(buttonAssociation);
		try
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/AssociationView.fxml"));
			rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	public void CommissionECOnClick()
	{
		styleButton(buttonCommissionEC);
		try
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/CommissionECView.fxml"));
			rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	public void gestionSalleOnClick()
	{
		styleButton(buttonGestionSalle);
		try
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/GestionSalleView.fxml"));
			rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	public void gestionMaterielOnClick()
	{
		styleButton(buttonGestionMateriel);
		try
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/GestionMaterielView.fxml"));
			rootStage.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
	
}
