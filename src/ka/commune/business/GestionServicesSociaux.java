package ka.commune.business;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import ka.commune.entity.*;
import ka.commune.entityManager.*;

public class GestionServicesSociaux {
	
	private final ActiviteBSManager absm;
	private final ObjetActiviteBSManager oabsm;
	private final SalleManager sm;
	private final PretSalleManager psm;
	private final AssociationManager am;
	private final ReunionAssociationManager ram;
	private final CommissionECManager cecm;
	private final ReunionCommissionECManager rcecm;
	private final PretMaterielManager pmm;
	private final MaterielManger mm;
	private final PretedMaterielManager pdmm;
	
	public GestionServicesSociaux() {
		// TODO Auto-generated constructor stub
		absm=new ActiviteBSManager();
		oabsm=new ObjetActiviteBSManager();
		sm=new SalleManager();
		psm=new PretSalleManager();
		am=new AssociationManager();
		ram=new ReunionAssociationManager();
		cecm=new CommissionECManager();
		rcecm=new ReunionCommissionECManager();
		pmm=new PretMaterielManager();
		mm=new MaterielManger();
		pdmm=new PretedMaterielManager();
	}
	
	// Activites BS
	
	public List<ActiviteBS> findAllActiviteBS()
	{
		return new Vector<>(absm.findAll());
	}
	
	public boolean addActiviteBS(ActiviteBS abs)
	{
		return absm.add(abs) != null;
	}
	
	public boolean deleteActiviteBS(ActiviteBS abs)
	{
		return absm.delete(abs);
	}
	
	public boolean updateActiviteBS(ActiviteBS abs)
	{
		return absm.update(abs) != null;
	}
	
	// Objet Activite BS
	
	public List<ObjetActiviteBS> findAllObjets(int id)
	{
		List<ObjetActiviteBS> temp=new Vector<>();
		for(ObjetActiviteBS o :oabsm.findAll())
		{
			if(o.getActiviteb().getIdActiviteBS()==id)
				temp.add(o);
		}
		return temp;
	}
		
	public boolean addObjet(ObjetActiviteBS o)
	{
		return oabsm.add(o) != null;
	}
		
	public boolean deleteObjet(ObjetActiviteBS o)
	{
		return oabsm.delete(o);
	}
	
	public ObjetActiviteBS updateObjet(ObjetActiviteBS o)
	{
		return oabsm.update(o) ;
	}
	
	// Salles
	
	public List<Salle> findAllSalle()
	{
		return new Vector<>(sm.findAll());
	}
		
	public boolean addSalle(Salle s)
	{
		return sm.add(s) != null;
	}
		
	public boolean deleteSalle(Salle s)
	{
		return sm.delete(s);
	}
	
	public boolean updateSalle(Salle s)
	{
		if(sm.update(s)!=null) {
			for(PretSalle pretSalle : findAllPretSalle())
			{
				if (pretSalle.getSalle().getIdSalle()==s.getIdSalle())
				{
					pretSalle.setSalle(s);
					updatePretSalle(pretSalle);
				}
			}
			return true;
		}
		return false;
	}
	
	// Pret Salles
	
	public List<PretSalle> findAllPretSalle()
	{
		return new Vector<>(psm.findAll());
	}
			
	public String addPretSalle(PretSalle s)
	{
		for(PretSalle pretSalle:findAllPretSalle())
		{
			if(pretSalle.getSalle().getIdSalle()!=s.getSalle().getIdSalle()) break;
			if(s.getDateFin() == null ||s.getDateFin().equals(s.getDateDebut()))
			{
				if(pretSalle.getDateFin() == null || pretSalle.getDateDebut().equals(pretSalle.getDateFin()))
				{
					if(s.getDateDebut().equals(pretSalle.getDateDebut()))
					{
						if(  s.getHeureDebut().after(pretSalle.getHeureDebut()) && s.getHeureDebut().before(pretSalle.getHeureFin()))
							return "intersection";
						if(s.getHeureDebut().before(pretSalle.getHeureDebut()) &&
								(s.getHeureFin().after(pretSalle.getHeureDebut())))
							return "intersection";
						if(s.getHeureDebut().equals(pretSalle.getHeureDebut()))
							return "intersection";
					}
				}
				else
				{
					if(  s.getDateDebut().after(pretSalle.getDateDebut()) && s.getDateDebut().before(pretSalle.getDateFin()))
						return "intersection";
					if(s.getDateDebut().equals(pretSalle.getDateDebut()))
						if(s.getHeureFin().after(pretSalle.getHeureDebut()))
							return "intersection";
					if(s.getDateDebut().equals(pretSalle.getDateFin()))
						if(s.getHeureDebut().before(pretSalle.getHeureFin()))
							return "intersection";
				}
			}
			else // s.dateDebut != s.dateFin
			{
				if(pretSalle.getDateFin().equals(null) || pretSalle.getDateDebut().equals(pretSalle.getDateFin()))
				{
					if(s.getDateDebut().equals(pretSalle.getDateDebut()))
						if(s.getHeureDebut().before(pretSalle.getHeureFin()))
							return "intersection";
					if(s.getDateFin().equals(pretSalle.getDateDebut()))
						if(s.getHeureFin().after(pretSalle.getHeureDebut()))
							return "intersection";
				}
				else
				{
					if( s.getDateDebut().after(pretSalle.getDateDebut()) && s.getDateDebut().before(pretSalle.getDateFin()))
						return "intersection";
					if(s.getDateDebut().equals(pretSalle.getDateDebut()))
						return "intersection";
					if(s.getDateDebut().equals(pretSalle.getDateFin()))
						if(s.getHeureDebut().before(pretSalle.getHeureFin()))
							return "intersection";
					if(s.getDateFin().equals(pretSalle.getDateDebut()))
						if(s.getHeureFin().after(pretSalle.getHeureDebut()))
							return "intersection";
				}
				if(s.getDateDebut().before(pretSalle.getDateDebut()) && s.getDateFin().after(pretSalle.getDateDebut()))
					return "intersection";
			}
		}
		if(psm.add(s)!=null)
			return "ok";
		return "nok";
	}

	public boolean deletePretSalle(PretSalle s)
	{
		return psm.delete(s);
	}

	public String updatePretSalle(PretSalle s)
	{
		for(PretSalle pretSalle:findAllPretSalle())
		{
			if(s.getIdPretSalle()==pretSalle.getIdPretSalle()) break;
			if(pretSalle.getSalle().getIdSalle()!=s.getSalle().getIdSalle()) break;
			if(s.getDateFin().equals(null) ||s.getDateFin().equals(s.getDateDebut()))
			{
				if(pretSalle.getDateFin().equals(null) || pretSalle.getDateDebut().equals(pretSalle.getDateFin()))
				{
					if(s.getDateDebut().equals(pretSalle.getDateDebut()))
					{
						if(  s.getHeureDebut().after(pretSalle.getHeureDebut()) && s.getHeureDebut().before(pretSalle.getHeureFin()))
							return "intersection";
						if(s.getHeureDebut().before(pretSalle.getHeureDebut()) &&
								(s.getHeureFin().after(pretSalle.getHeureDebut())))
							return "intersection";
						if(s.getHeureDebut().equals(pretSalle.getHeureDebut()))
							return "intersection";
					}
				}
				else
				{
					if(  s.getDateDebut().after(pretSalle.getDateDebut()) && s.getDateDebut().before(pretSalle.getDateFin()))
						return "intersection";
					if(s.getDateDebut().equals(pretSalle.getDateDebut()))
						if(s.getHeureFin().after(pretSalle.getHeureDebut()))
							return "intersection";
					if(s.getDateDebut().equals(pretSalle.getDateFin()))
						if(s.getHeureDebut().before(pretSalle.getHeureFin()))
							return "intersection";
				}
			}
			else // s.dateDebut != s.dateFin
			{
				if(pretSalle.getDateFin().equals(null) || pretSalle.getDateDebut().equals(pretSalle.getDateFin()))
				{
					if(s.getDateDebut().equals(pretSalle.getDateDebut()))
						if(s.getHeureDebut().before(pretSalle.getHeureFin()))
							return "intersection";
					if(s.getDateFin().equals(pretSalle.getDateDebut()))
						if(s.getHeureFin().after(pretSalle.getHeureDebut()))
							return "intersection";
				}
				else
				{
					if( s.getDateDebut().after(pretSalle.getDateDebut()) && s.getDateDebut().before(pretSalle.getDateFin()))
						return "intersection";
					if(s.getDateDebut().equals(pretSalle.getDateDebut()))
						return "intersection";
					if(s.getDateDebut().equals(pretSalle.getDateFin()))
						if(s.getHeureDebut().before(pretSalle.getHeureFin()))
							return "intersection";
					if(s.getDateFin().equals(pretSalle.getDateDebut()))
						if(s.getHeureFin().after(pretSalle.getHeureDebut()))
							return "intersection";
				}
				if(s.getDateDebut().before(pretSalle.getDateDebut()) && s.getDateFin().after(pretSalle.getDateDebut()))
					return "intersection";
			}
		}
		if(psm.update(s)!=null)
			return "ok";
		return "nok";
	}
	// Materiels

	public List<Materiel> findAllMateriel()
	{
		return mm.findAll();
	}

	public boolean addMateriel(Materiel materiel)
	{
		try {
			return mm.add(materiel) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteMateriel(Materiel materiel)
	{
		return mm.delete(materiel);
	}

	public boolean updateMateriel(Materiel materiel)
	{
		try {
			return mm.update(materiel) != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean materielIsPreted(Materiel m)
	{
		for(PretedMateriel p:findAllPretedMateriel())
		{
			if(p.getMateriel().getIdMateriel()==m.getIdMateriel())
				return true;
		}
		return false;
	}

	// Pret Materiels

	public List<PretMateriel> findAllPretMateriel()
	{
		return new Vector<>(pmm.findAll());
	}

	public String addPretMateriel(PretMateriel m)
	{
		/*for(PretMateriel pretMateriel:findAllPretMateriel())
		{
			if(m.getDateDebut().after(pretMateriel.getDateDebut()) && m.getDateDebut().before(pretMateriel.getDateFin()))
				return "intersection";
			if(m.getDateDebut().before(pretMateriel.getDateDebut()) &&
					(m.getDateFin().after(pretMateriel.getDateDebut())))
				return "intersection";
			if(m.getDateDebut().equals(pretMateriel.getDateDebut()))
				return "intersection";
		}

		 */
		if(pmm.add(m)!=null)
			return "ok";
		return "nok";
	}

	public boolean deletePretMateriel(PretMateriel p)
	{
		return pmm.delete(p);
	}

	public String updatePretMateriel(PretMateriel m)
	{
		/*for(PretMateriel pretMateriel:findAllPretMateriel())
		{
			if(m.getDateDebut().after(pretMateriel.getDateDebut()) && m.getDateDebut().before(pretMateriel.getDateFin()))
				return "intersection";
			if(m.getDateDebut().before(pretMateriel.getDateDebut()) &&
					(m.getDateFin().after(pretMateriel.getDateDebut())))
				return "intersection";
			if(m.getDateDebut().equals(pretMateriel.getDateDebut()))
				return "intersection";
		}

		 */
		if(pmm.update(m)!=null)
			return "ok";
		return "nok";
	}

	// PretedMateriel

	public List<PretedMateriel> findAllPretedMateriel()
	{
		return pdmm.findAll();
	}

	public boolean addPretedMateriel(PretedMateriel p)
	{
		try {
			return pdmm.add(p) != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletePretedMateriel(PretedMateriel materiel)
	{
		return pdmm.delete(materiel);
	}

	public boolean updatePretedMateriel(PretedMateriel materiel)
	{
		try {
			return pdmm.update(materiel) != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public double getQuantiteDisponible(PretedMateriel pretedMateriel)
	{
		double quantite=0;
		Date db=pretedMateriel.getPretmateriel().getDateDebut();
		Date df=pretedMateriel.getPretmateriel().getDateFin();
		int i=0;
		for(PretedMateriel p:findAllPretedMateriel())
		{
			if(p.getMateriel().getIdMateriel()!=pretedMateriel.getMateriel().getIdMateriel())
				continue;
			if(pretedMateriel.getPretmateriel().getIdPretMateriel()==p.getPretmateriel().getIdPretMateriel())
				continue;
			Date dbu=p.getPretmateriel().getDateDebut();
			Date dfin=p.getPretmateriel().getDateFin();

				if(db.after(dbu) && db.before(dfin))
				{
					quantite+=p.getQuantite();
					continue;
				}
				if(db.before(dbu) && df.after(dbu))
				{
					quantite+=p.getQuantite();
					continue;
				}
				if(db.equals(dbu))
				{
					quantite+=p.getQuantite();
					continue;
				}
				if(db.equals(dfin))
				{
					quantite+=p.getQuantite();
				}

		}
		return pretedMateriel.getMateriel().getQuantite()-quantite;
	}

	// Association

	public List<Association> findAllAssociation()
	{
		return new Vector<>(am.findAll());
	}

	public boolean addAssociation(Association a)
	{
		return am.add(a) != null;
	}

	public boolean deleteAssociation(Association a)
	{
		return am.delete(a);
	}

	public boolean updateAssociation(Association a)
	{
		return am.update(a) != null;
	}

	//Reunion Association

	public List<ReunionAssociation> findAllReunionAssociation()
	{
		return new Vector<>(ram.findAll());
	}

	public boolean addReunionAssociation(ReunionAssociation a)
	{
		return ram.add(a) != null;
	}

	public boolean deleteReunionAssociation(ReunionAssociation a)
	{
		return ram.delete(a);
	}

	public boolean updateReunionAssociation(ReunionAssociation a)
	{
		return ram.update(a) != null;
	}

	// Commission EC

	public List<CommissionEC> findAllCommissionEC()
	{
		return new Vector<>(cecm.findAll());
	}

	public boolean addCommissionEC(CommissionEC c)
	{
		return cecm.add(c) != null;
	}

	public boolean deleteCommissionEC(CommissionEC c)
	{
		return cecm.delete(c);
	}

	public boolean updateCommissionEC(CommissionEC c)
	{
		return cecm.update(c) != null;
	}

	// Reunion Commission EC

	public List<ReunionCommissionec> findAllReunionCommissionEC()
	{
		return new Vector<>(rcecm.findAll());
	}

	public boolean addReunionCommissionEC(ReunionCommissionec c)
	{
		return rcecm.add(c) != null;
	}

	public boolean deleteReunionCommissionEC(ReunionCommissionec c)
	{
		return rcecm.delete(c);
	}

	public boolean updateReunionCommissionEC(ReunionCommissionec c)
	{
		return rcecm.update(c) != null;
	}


}
