package ka.commune.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;

public class HomeController implements Initializable{
	
	@FXML public BorderPane homePane;
	@FXML private Button conseil;
	@FXML private Button services;
	@FXML private Button comptabilite;
	@FXML private Label setting;
	@FXML FontAwesomeIconView iconSetting;
	private Vector<Button> menuButtons=new Vector<Button>();

	public static BorderPane temphomePane;

	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		temphomePane=homePane;
		menuButtons.add(conseil);
		menuButtons.add(services);
		menuButtons.add(comptabilite);
		openConseilHome();
		
	}
	
	
	public void openConseilHome()
	{
		//styleButton(buttonCommission);
		try 
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/ConseilHomeView.fxml"));
			homePane.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void openServicesHome()
	{
		//styleButton(buttonCommission);
		try 
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/ServicesHomeView.fxml"));
			homePane.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void openSettingsView()
	{
		try
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/SettingsMenuView.fxml"));
			homePane.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void openComptabilite() {
		try
		{
			Parent root ;
			root=FXMLLoader.load(getClass().getResource("/ka/commune/view/ComptabiliteMenuView.fxml"));
			homePane.setCenter(root);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@FXML
	public void comptabiliteOnClick()
	{
		styleButton(comptabilite);
		openComptabilite();
	}

	@FXML
	public void settingsClick()
	{
		iconSetting.setFill(Paint.valueOf("#1C3A47"));
		styleButton(new Button());
		openSettingsView();
	}

	@FXML
	public void conseilOnClick()
	{
		styleButton(conseil);
		openConseilHome();
	}
	
	@FXML
	public void servicesOnClick()
	{
		styleButton(services);
		openServicesHome();
	}
	private void styleButton(Button btn) {
		for(Button b : menuButtons)
		{
			if(b.equals(btn))
			{
				b.getStyleClass().clear();
				b.getStyleClass().add("selected-menu");
				iconSetting.setFill(Paint.valueOf("white"));
			}
			else
			{
				b.getStyleClass().clear();
				b.getStyleClass().add("not-selected-menu");
			}
		}
    }
	

}
