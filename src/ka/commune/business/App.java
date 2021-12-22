package ka.commune.business;

import java.io.File;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ka.commune.entity.Mandat;
import ka.commune.entity.User;
import ka.commune.entityManager.EMF;

public class App extends Application {
	
	
	public static Stage stages=new Stage();
	private static Mandat mandat=null;
	
	public static BorderPane rootStage;
	
	private static User user;
	
	@Override
	public void start(Stage stage) 
	{
		try 
		{	
			EMF.getEntityManagerFactory();
			Parent root1 = FXMLLoader.load(getClass().getResource("/ka/commune/view/LoginView.fxml"));
			Scene scene = new Scene(root1);
			stages.setScene(scene);
			stages.setTitle("تسجيل الدخول");
			stages.show();
			stages.setResizable(false);
			scene.getWindow().onShowingProperty();
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	// Main
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			launch(args);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static int getCurrentYear()
	{
		EntityManager em =EMF.getEntityManagerFactory().createEntityManager();
		Query query =em.createNativeQuery("Select CURDATE()");
		Calendar calendar=Calendar.getInstance();
		calendar.setTime((Date)query.getSingleResult());
		return calendar.get(Calendar.YEAR) ;
	}

	public static List<String> getListYears()
	{
		List<String> list=new Vector<>();
		for (int i=getCurrentYear(); i>=2000;i--)
			list.add(i+"");
		return list;
	}

	public static List<String> getListYearsFilter()
	{
		List<String> list=new Vector<>();
		list.add(0,"Toutes les années");
		for (int i=getCurrentYear(); i>=2000;i--)
			list.add(i+"");
		return list;
	}
	
	public static Date getToDayDate()
	{
		EntityManager em =EMF.getEntityManagerFactory().createEntityManager();
		Query query =em.createNativeQuery("Select current_date()");
		return (Date)query.getSingleResult();
	}

	public static Mandat getMandat() {
			return mandat;
		}
	public static void setMandat(Mandat mandat) {
			App.mandat = mandat;
		}
	public static User getUser() {
			return user;
		}
	public static void setUser(User user) {
			App.user = user;
		}
}
