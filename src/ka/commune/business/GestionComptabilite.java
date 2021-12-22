package ka.commune.business;

import ka.commune.entity.*;
import ka.commune.entityManager.*;

import java.util.List;
import java.util.Vector;

public class GestionComptabilite {

    private PartenaireManager pm;
    private OperationManager om;
    private CategorieManager cm;
    private CansistanceManager cnm;
    private PartenaireOperationManager pom;
    private RealisationManager rm;
    private ResultatManager rem;
    private ActiviteTypeManager atm;

    public GestionComptabilite() {
        pm=new PartenaireManager();
        om=new OperationManager();
        cm=new CategorieManager();
        cnm=new CansistanceManager();
        rm=new RealisationManager();
        rem=new ResultatManager();
        atm=new ActiviteTypeManager();
    }

    // Partenaire

    public List<Partenaire> findAllPartenaire()
    {
        return new Vector<>(pm.findAll());
    }

    public boolean addPartenaire(Partenaire p)
    {
        return pm.add(p) != null;
    }

    public boolean deletePartenaire(Partenaire p)
    {
        return pm.delete(p);
    }

    public boolean updatePartenaire(Partenaire p)
    {
        return pm.update(p) != null;
    }

    // Operation

    public List<Operation> findAllOperation()
    {
        return new Vector<>(om.findAll());
    }

    public List<Operation> findPartenaireOperations(Partenaire p)
    {
        return new Vector<>(om.findByPartenaire(p));
    }

    public boolean addOperation(Operation o)
    {
        return om.add(o) != null;
    }

    public boolean deleteOperation(Operation o)
    {
        return om.delete(o);
    }

    public boolean updateOperation(Operation o)
    {
        return om.update(o) != null;
    }

    public Operation findOperationByID(int id)
    {
        return om.findById(id);
    }

    // Categorie

    public List<Categorie> findAllCategorie()
    {
        return new Vector<>(cm.findAll());
    }

    public boolean addCategorie(Categorie p)
    {
        return cm.add(p) != null;
    }

    public boolean deleteCategorie(Categorie p)
    {
        return cm.delete(p);
    }

    public boolean updateCategorie(Categorie p)
    {
        return cm.update(p) != null;
    }

    // Cansistance

    public List<Cansistance> findAllCansistance()
    {
        return new Vector<>(cnm.findAll());
    }

    public Cansistance addCansistance(Cansistance p)
    {
        return cnm.add(p) ;
    }

    public boolean deleteCansistance(Cansistance p)
    {
        return cnm.delete(p);
    }

    public Cansistance updateCansistance(Cansistance p)
    {
        return cnm.update(p) ;
    }

    public Cansistance findCansistanceById(int id)
    {
        return cnm.findById(id);
    }

    // Operation partenaire

    public List<PartenaireOperation> findAllPartenaireOperation()
    {
        return new Vector<>(pom.findAll());
    }

    public PartenaireOperation addPartenaireOperation(PartenaireOperation p)
    {
        return pom.add(p) ;
    }

    public boolean deletePartenaireOperation(PartenaireOperation p)
    {
        return pom.delete(p);
    }

    public PartenaireOperation updatePartenaireOperation(PartenaireOperation p)
    {
        return pom.update(p) ;
    }

    public PartenaireOperation findPartenaireById(int id)
    {
        return pom.findById(id);
    }

    // Realisation

    public List<Realisation> findAllRealisation()
    {
        return new Vector<>(rm.findAll());
    }

    public boolean addRealisation(Realisation p)
    {
        return rm.add(p) != null;
    }

    public boolean deleteRealisation(Realisation p)
    {
        return rm.delete(p);
    }

    public boolean updateRealisation(Realisation p)
    {
        return rm.update(p) != null;
    }

    // Resultat

    public List<Resultat> findAllResultat()
    {
        return new Vector<>(rem.findAll());
    }

    public boolean addResultat(Resultat p)
    {
        return rem.add(p) != null;
    }

    public boolean deleteResultat(Resultat p)
    {
        return rem.delete(p);
    }

    public boolean updateResultat(Resultat p)
    {
        return rem.update(p) != null;
    }

    // ActiviteType

    public List<ActiviteType> findAllActiviteType()
    {
        return new Vector<>(atm.findAll());
    }

    public boolean addActiviteType(ActiviteType p)
    {
        return atm.add(p) != null;
    }

    public boolean deleteActiviteType(ActiviteType p)
    {
        return atm.delete(p);
    }

    public boolean updateActiviteType(ActiviteType p)
    {
        return atm.update(p) != null;
    }
}
