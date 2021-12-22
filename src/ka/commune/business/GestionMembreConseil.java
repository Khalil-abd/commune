package ka.commune.business;

import java.util.List;
import java.util.Vector;

import javax.persistence.NoResultException;

import ka.commune.entity.*;
import ka.commune.entityManager.*;
import org.eclipse.persistence.exceptions.DatabaseException;

public class GestionMembreConseil {

	private final MandatManager mm;
	private final MembreConseilManager mcm;
	private final FonctionManager fm;
	private final NiveauScolaireManager nsm;
	private final PartiePolitiqueManager ppm;
	private final CirconscriptionManager cm;
	private final CommissionManager com;
	private final SujetSessionManager ssm;
	private final SessionManager sm;
	private final DomaineManager dm;
	private final DesignationSujetSessionManager dssm;
	private final ObjetReunionManager orm;
	
	public GestionMembreConseil() {

		// TODO Auto-generated constructor stub
		mm=new MandatManager();
		mcm=new MembreConseilManager();
		ppm=new PartiePolitiqueManager();
		cm=new CirconscriptionManager();
		com=new CommissionManager();
		ssm=new SujetSessionManager();
		sm=new SessionManager();
		dm=new DomaineManager();
		dssm=new DesignationSujetSessionManager();
		orm=new ObjetReunionManager();
		this.fm = new FonctionManager();
		this.nsm=new NiveauScolaireManager();
	}
	
	// Mandat
	
	public List<Mandat> findAllMandat()
	{
		return mm.findAll();
	}
	
	public boolean addMandat(Mandat m)
	{
		for(Mandat mandat : findAllMandat())
		{
			if (MandatsAreEquals(m, mandat)) return false;
		}
		return mm.add(m) != null;
	}
	
	public boolean deleteMandat(Mandat m)
	{
		return mm.delete(m);
	}
	
	public boolean updateMandat(Mandat m)
	{
		for(Mandat mandat : findAllMandat())
		{
			if(m.getIdMandat()!= mandat.getIdMandat())
			{
				if (MandatsAreEquals(m, mandat)) return false;
			}
		}
		return mm.update(m) != null;
	}

	private boolean MandatsAreEquals(Mandat m, Mandat mandat) {
		if(m.getDateDebut().equals(mandat.getDateDebut()) || m.getDateFin().equals(mandat.getDateFin()))
			return true;
		if(m.getDateDebut().after(mandat.getDateDebut()) && m.getDateDebut().before(mandat.getDateFin()))
			return true;
		if(m.getDateFin().after(mandat.getDateDebut()) && m.getDateFin().before(mandat.getDateFin()))
			return true;
		return m.getDateDebut().before(mandat.getDateDebut()) && m.getDateFin().after(mandat.getDateFin());
	}

	// Membre Conseil
	
	public List<Membreconseil> findAllMembreConseil() throws NoResultException
	{
		List<Membreconseil> temp=new Vector<>();
		for(Membreconseil mc : mcm.findAll())
		{
			if(mc.getMandat().getIdMandat()==App.getMandat().getIdMandat())
				temp.add(mc);
		}
		return temp;
	}
	
	public boolean addMembreConseil(Membreconseil membreConseil)
	{
		return mcm.add(membreConseil) != null;
	}
	
	public boolean deleteMembreConseil(Membreconseil membreconseil)
	{
		return mcm.delete(membreconseil);
	}
	
	public boolean updateMembreConseil(Membreconseil membreConseil)
	{
		try {
			return mcm.update(membreConseil) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	// Fonction

	public List<Fonction> findAllFonction() throws NoResultException
	{
		return fm.findAll();
	}

	public boolean addFonction(Fonction fonction) throws DatabaseException,IllegalStateException
	{
		try {
			return fm.add(fonction) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteFocntion(Fonction fonction)
	{
		return fm.delete(fonction);
	}

	public boolean updateFonction(Fonction fonction)
	{
		try {
			return fm.update(fonction) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	// Niveau Scolaire
	public List<NiveauScolaire> findAllNiveauScolaire() throws NoResultException
	{
		return nsm.findAll();
	}

	public boolean addNiveauScolaire(NiveauScolaire niveauScolaire) throws DatabaseException,IllegalStateException
	{
		try {
			return nsm.add(niveauScolaire) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteNiveauScolaire(NiveauScolaire niveauScolaire)
	{
		return nsm.delete(niveauScolaire);
	}

	public boolean updateNiveauScolaire(NiveauScolaire niveauScolaire)
	{
		try {
			return nsm.update(niveauScolaire) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	// Partie Politique
	
	public List<Partiepolitique> findAllPartiePolitique() throws NoResultException
	{
		return ppm.findAll();
	}
	
	public boolean addPartiePolitiuqe(Partiepolitique partiePolitique) throws DatabaseException,IllegalStateException
	{
		try {
			return ppm.add(partiePolitique) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deletePartiePolitique(Partiepolitique partiePolitique)
	{
		return ppm.delete(partiePolitique);
	}
	
	public boolean updatePartiePolitique(Partiepolitique partiePolitique)
	{
		try {
			return ppm.update(partiePolitique) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	// Circonscription
	
	public List<Circonscription> findAllCirconscription() throws NoResultException
	{
		return cm.findAll();
	}
	
	public boolean addCirconscription(Circonscription circonscription) 
	{
		try 
		{
			return cm.add(circonscription) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteCirconscription(Circonscription circonscription)
	{
		return cm.delete(circonscription);
	}
	
	public boolean updateCirconscription(Circonscription circonscription)
	{
		try {
			return cm.update(circonscription) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	// Commission
	
	public List<Commission> findAllCommission()
	{
		return com.findAll();
	}
	
	public boolean addCommission(Commission commission)
	{
		return com.add(commission) != null;
	}
	
	public boolean deleteCommission(Commission commission)
	{
		for(Membreconseil mc : commission.getMembreconseils())
		{
			mc.getCommissions().remove(commission);
			mcm.update(mc);
		}
		return com.delete(commission);
	}
	
	public boolean updateCommission(Commission commission)
	{
		try {
			return com.update(commission) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	// Membre Commission
	
	
	public boolean addMembreCommission(MembreCommission membreCommission)
	{
		Commission cm=membreCommission.getCommission();
		Membreconseil m=membreCommission.getMembreConseil();
		for(Membreconseil mc: cm.getMembreconseils())
		{
			if(mc.getIdMembreConseil()==m.getIdMembreConseil())
				return false;
		}
		cm.getMembreconseils().add(m);
		com.update(cm);
		m.getCommissions().add(cm);
		mcm.update(m);
		return true;
	}
	
	public boolean removeMembreCommission(MembreCommission membreCommission)
	{
		try {
			Commission cm=membreCommission.getCommission();
			for(Membreconseil mc: cm.getMembreconseils())
			{
				if(mc.equals(membreCommission.getMembreConseil()))
				{
					mc.getCommissions().remove(cm);
					this.mcm.update(mc);
					cm.getMembreconseils().remove(mc);
					this.com.update(cm);
				}
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	// r�union
	
	public List<Sujetsession> findAllReunions()
	{
		List<Sujetsession> temp=new Vector<>();
		for(Sujetsession ss : ssm.findAll())
		{
			if(ss.getMandat().getIdMandat()==App.getMandat().getIdMandat())
				temp.add(ss);
		}
		return temp;
	}
	
	public boolean addReunion(Sujetsession ss)
	{
		return ssm.add(ss) != null;
	}
	
	public void deleteReunion(Sujetsession ss)
	{
		ssm.delete(ss);
	}
	
	public Sujetsession updateReunion(Sujetsession ss)
	{
		return ssm.update(ss) ;
	}
	
	// Titre
	
	public List<Designationsujetsession> findAllTitres()
	{
		return dssm.findAll();
	}
	
	public boolean addTitre(Designationsujetsession d)
	{
		return dssm.add(d) != null;
	}
	
	public boolean deleteTitre(Designationsujetsession d)
	{
		return dssm.delete(d);
	}
	
	public boolean updateTitre(Designationsujetsession d)
	{
		return dssm.update(d) != null;
	}
	
	// Session
	
	public List<Session> findAllSessions()
	{
		return sm.findAll();
	}
	
	public boolean addSession(Session ss)
	{
		return sm.add(ss) != null;
	}
	
	public boolean deleteSession(Session ss)
	{
		return sm.delete(ss);
	}
	
	public boolean updateSession(Session ss)
	{
		return sm.update(ss) != null;
	}
	
	// Domaine
	
	public List<Domaine> findAllDomaines()
	{
		return dm.findAll();
	}
	
	public boolean addDomaine(Domaine d)
	{
		return dm.add(d) != null;
	}
	
	public boolean deleteDomaine(Domaine d)
	{
		return dm.delete(d);
	}
	
	public boolean updateDomaine(Domaine d)
	{
		return dm.update(d) != null;
	}
	
	// Objet Reunion
	
	public List<Objetreunion> findAllObjets(int id)
	{
		List<Objetreunion> temp=new Vector<>();
		for(Objetreunion o :orm.findAll())
		{
			if(o.getSujetsession().getIdSujetSession()==id)
				temp.add(o);
		}
		return temp;
	}
	
	public Objetreunion addObjet(Objetreunion o)
	{
		return orm.add(o);
	}
	
	public boolean deleteObjet(Objetreunion o)
	{
		return orm.delete(o);
	}
	
	public Objetreunion updateObjet(Objetreunion o)
	{
		return orm.update(o);
	}

	public Objetreunion findObjetById(int id)
	{
		return orm.findById(id);
	}
	
}
