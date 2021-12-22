package ka.commune.business;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import com.smattme.MysqlExportService;
import com.smattme.MysqlImportService;
import ka.commune.entity.User;
import ka.commune.entityManager.EMF;
import ka.commune.entityManager.UserManager;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class GestionUser {
	UserManager um;

	public GestionUser()
	{
		um=new UserManager();
	}

	private static String mysqlPath="";
	public static void getMysqlPath() throws SQLException
	{
		Statement stm= EMF.getEntityManagerFactory().createEntityManager().unwrap(Connection.class).createStatement();
		ResultSet rs=stm.executeQuery("SELECT * FROM  info;");

		while(rs.next())
		{
			mysqlPath=rs.getString(1);
		}
	}

	public User login(String login,String password) throws NoResultException
	{
		return um.login(login, password);
	}

	public boolean updateUser(User user)
	{
		if(um.update(user)!=null)
			return true;
		return false;
	}

	public static boolean createDataBase()
	{
		EntityManager em= EMF.getMysqlEMF().createEntityManager();
		em.getTransaction().begin();
		Connection connection = em.unwrap(Connection.class);
		InputStream script = GestionUser.class.getResourceAsStream("/ka/commune/business/resources/DataBaseCreator.sql");
		InputStreamReader inr=new InputStreamReader(script);
		ScriptRunner sr = new ScriptRunner(connection,false,false);
		try {
			Reader reader = new BufferedReader( inr);
			sr.runScript(reader);

		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void exporterData(String chemin) {
		Properties properties = new Properties();
		properties.setProperty(MysqlExportService.DB_NAME, "commune");
		properties.setProperty(MysqlExportService.DB_USERNAME, "root");
		properties.setProperty(MysqlExportService.DB_PASSWORD, "Aptx-4869");
		properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
		properties.setProperty(MysqlExportService.TEMP_DIR, chemin);
		properties.setProperty(MysqlExportService.DELETE_EXISTING_DATA, "true");

		try {
			MysqlExportService mysqlExportService = new MysqlExportService(properties);
			mysqlExportService.export();
			File zipedFile= mysqlExportService.getGeneratedZipFile();
			UnzipFiles.unzip(zipedFile, chemin);
			mysqlExportService.clearTempFiles(false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void importDatabase(File file)
	{
		String sql = null;
		try {
			sql = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			System.out.println(sql);
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean res = false;
		try {
			res = MysqlImportService.builder()
					.setDatabase("commune")
					.setUsername("root")
					.setPassword("Aptx-4869")
					.setSqlString(sql)
					.setDeleteExisting(true)
					.setDropExisting(true)
					.importDatabase();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
