package ka.commune.entityManager;

import ka.commune.business.ReadXMLFile;
import ka.commune.entity.DataBaseInfo;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

import static org.eclipse.persistence.config.PersistenceUnitProperties.*;

public class EMF {
    private static EntityManagerFactory entityManagerFactory =null;

    @SuppressWarnings("static-access")
    public static EntityManagerFactory getEntityManagerFactory() {
        if(entityManagerFactory==null || !entityManagerFactory.isOpen())
        {

            DataBaseInfo dbi= new ReadXMLFile().readFile();
            Map properties = new HashMap();

            properties.put(JDBC_URL, "jdbc:mysql://"+dbi.getUrl()+":3306/commune");
            properties.put(JDBC_USER, dbi.getUser());
            properties.put(JDBC_PASSWORD, dbi.getPassword());
            entityManagerFactory = Persistence.createEntityManagerFactory("Commune001",properties);


            //entityManagerFactory = Persistence.createEntityManagerFactory("Commune001");
        }
        return entityManagerFactory;
    }

    public static EntityManagerFactory getMysqlEMF()
    {
        Map properties = new HashMap();
        DataBaseInfo dbi= new ReadXMLFile().readFile();
        properties.put(JDBC_URL, "jdbc:mysql://"+dbi.getUrl()+":3306/mysql");
        properties.put(JDBC_USER, dbi.getUser());
        properties.put(JDBC_PASSWORD, dbi.getPassword());
        return Persistence.createEntityManagerFactory("Commune001",properties);
    }
}
