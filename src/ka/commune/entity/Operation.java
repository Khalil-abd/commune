package ka.commune.entity;

import javafx.scene.layout.HBox;
import ka.commune.entity.Cansistance;
import ka.commune.entity.Categorie;
import ka.commune.entity.Partenaire;
import ka.commune.entity.PartenaireOperation;

import java.io.Serializable;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the operation database table.
 * 
 */

@Entity
@NamedQuery(name="Operation.findAll", query="SELECT o FROM Operation o")
public class Operation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idComptabilite;

	private int annee;

	@Temporal(TemporalType.DATE)
	private Date dateOperation;

	@Temporal(TemporalType.DATE)
	private Date dateRealisation;

	private String designation;

	private String fournisseur;

	private double montant;

	private double montantRecu;

	private double montantVerse;

	private int numeroDossier;

	private String ref;

	//bi-directional many-to-one association to Partenaire
	@ManyToOne
	@JoinColumn(name="partenaire")
	private Partenaire partenaireBean;

	//bi-directional many-to-one association to PartenaireOperation
	@OneToMany(mappedBy="operationBean",orphanRemoval = true,cascade = CascadeType.ALL)
	private List<PartenaireOperation> partenaireoperations;

	//bi-directional many-to-one association to Cansistance
	@OneToMany(mappedBy="operationBean",orphanRemoval = true,cascade = CascadeType.ALL)
	private List<Cansistance> cansistances;

	//bi-directional many-to-one association to Categorie

	@ManyToOne
	@JoinColumn(name="realisation")
	private Realisation realisationBean;

	@ManyToOne
	@JoinColumn(name="resultat")
	private Resultat resultatBean;

	@ManyToOne
	@JoinColumn(name="activiteType")
	private ActiviteType activitetype;

	@ManyToOne
	@JoinColumn(name="categorie")
	private Categorie categorieBean;

	public Operation() {
		action=new HBox();
		action.setSpacing(5);
	}



	public int getIdComptabilite() {
		return this.idComptabilite;
	}

	public void setIdComptabilite(int idComptabilite) {
		this.idComptabilite = idComptabilite;
	}

	public Realisation getRealisationBean() {
		return realisationBean;
	}

	public void setRealisationBean(Realisation realisationBean) {
		this.realisationBean = realisationBean;
	}

	public Resultat getResultatBean() {
		return resultatBean;
	}

	public void setResultatBean(Resultat resultatBean) {
		this.resultatBean = resultatBean;
	}

	public ActiviteType getActivitetype() {
		return activitetype;
	}

	public void setActivitetype(ActiviteType activitetype) {
		this.activitetype = activitetype;
	}

	public int getAnnee() {
		return this.annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public Date getDateOperation() {
		return this.dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getFournisseur() {
		return this.fournisseur;
	}

	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}

	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public double getMontantRecu() {
		return this.montantRecu;
	}

	public void setMontantRecu(double montantRecu) {
		this.montantRecu = montantRecu;
	}

	public double getMontantVerse() {
		return this.montantVerse;
	}

	public void setMontantVerse(double montantVerse) {
		this.montantVerse = montantVerse;
	}

	public int getNumeroDossier() {
		return this.numeroDossier;
	}

	public void setNumeroDossier(int numeroDossier) {
		this.numeroDossier = numeroDossier;
	}


	public String getRef() {
		return this.ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Partenaire getPartenaireBean() {
		return this.partenaireBean;
	}

	public void setPartenaireBean(Partenaire partenaireBean) {
		this.partenaireBean = partenaireBean;
	}

	public List<PartenaireOperation> getPartenaireoperations() {
		return this.partenaireoperations;
	}

	public void setPartenaireoperations(List<PartenaireOperation> partenaireoperations) {
		this.partenaireoperations = partenaireoperations;
	}

	public PartenaireOperation addPartenaireoperation(PartenaireOperation partenaireoperation) {
		getPartenaireoperations().add(partenaireoperation);
		partenaireoperation.setOperationBean(this);

		return partenaireoperation;
	}

	public PartenaireOperation removePartenaireoperation(PartenaireOperation partenaireoperation) {
		getPartenaireoperations().remove(partenaireoperation);
		partenaireoperation.setOperationBean(null);

		return partenaireoperation;
	}

	public List<Cansistance> getCansistances() {
		return this.cansistances;
	}

	public void setCansistances(List<Cansistance> cansistances) {
		this.cansistances = cansistances;
	}

	public Cansistance addCansistance(Cansistance cansistance) {
		getCansistances().add(cansistance);
		cansistance.setOperationBean(this);

		return cansistance;
	}

	public Cansistance removeCansistance(Cansistance cansistance) {
		getCansistances().remove(cansistance);
		cansistance.setOperationBean(null);

		return cansistance;
	}

	public Categorie getCategorieBean() {
		return this.categorieBean;
	}

	public void setCategorieBean(Categorie categorieBean) {
		this.categorieBean = categorieBean;
	}

	public Date getDateRealisation() {
		return dateRealisation;
	}

	public void setDateRealisation(Date dateRealisation) {
		this.dateRealisation = dateRealisation;
	}

	@Transient
	private HBox action;

	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}

	public String getSearchText() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		return format1.format(dateOperation)+ "^^" + designation + "^^" + fournisseur +
				"^^" + montant + "^^" + montantRecu + "^^" + montantVerse +
				"^^" + numeroDossier + "^^"  + "^^" + ref ;
	}
}