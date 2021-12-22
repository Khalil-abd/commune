package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;
import javafx.scene.layout.HBox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the membreconseil database table.
 * 
 */
@Entity
@NamedQuery(name="Membreconseil.findAll", query="SELECT m FROM Membreconseil m")
public class Membreconseil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMembreConseil;

	private String adresse;

	private String cin;

	@Temporal(TemporalType.DATE)
	private Date dateNaissance;

	private String lieuNaissance;

	private String nom;

	private String nomArabe;

	private String prenom;

	private String prenomArabe;

	private String email;

	private String profession;

	private String telephone;

	//bi-directional many-to-many association to Commission
	@ManyToMany
	@JoinTable(
		name="membrecommission"
		, joinColumns={
			@JoinColumn(name="idMembreConseil")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idCommission")
			}
		)
	private List<Commission> commissions;

	//bi-directional many-to-one association to Circonscription
	@ManyToOne
	@JoinColumn(name="idCirconscription")
	private Circonscription circonscription;

	//bi-directional many-to-one association to Mandat
	@ManyToOne
	@JoinColumn(name="idMandat")
	private Mandat mandat;

	@Transient
	private int numero;
	//bi-directional many-to-one association to Partiepolitique
	@ManyToOne
	@JoinColumn(name="idPartiePolitique")
	private Partiepolitique partiepolitique;
	
	@ManyToOne
	@JoinColumn(name="fonction")
	private Fonction fonction;
	
	@ManyToOne
	@JoinColumn(name="niveauScolaire")
	private NiveauScolaire niveauScolaire;
	
	
	@Transient
	private HBox actionButtons;
	
	
	public HBox getActionButtons() {
		return actionButtons;
	}

	public void setActionButtons(HBox actionButtons) {
		this.actionButtons = actionButtons;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Membreconseil() {
		this.actionButtons=new HBox();
		this.actionButtons.setSpacing(5);
	}

	public int getIdMembreConseil() {
		return this.idMembreConseil;
	}

	public void setIdMembreConseil(int idMembreConseil) {
		this.idMembreConseil = idMembreConseil;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCin() {
		return this.cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public Date getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	

	public String getLieuNaissance() {
		return this.lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	
	public Fonction getFonction() {
		return fonction;
	}

	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}

	public NiveauScolaire getNiveauScolaire() {
		return niveauScolaire;
	}

	public void setNiveauScolaire(NiveauScolaire niveauScolaire) {
		this.niveauScolaire = niveauScolaire;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNomArabe() {
		return this.nomArabe;
	}

	public void setNomArabe(String nomArabe) {
		this.nomArabe = nomArabe;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPrenomArabe() {
		return this.prenomArabe;
	}

	public void setPrenomArabe(String prenomArabe) {
		this.prenomArabe = prenomArabe;
	}

	public String getProfession() {
		return this.profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Commission> getCommissions() {
		return this.commissions;
	}

	public void setCommissions(List<Commission> commissions) {
		this.commissions = commissions;
	}

	public Circonscription getCirconscription() {
		return this.circonscription;
	}

	public void setCirconscription(Circonscription circonscription) {
		this.circonscription = circonscription;
	}

	public Mandat getMandat() {
		return this.mandat;
	}

	public void setMandat(Mandat mandat) {
		this.mandat = mandat;
	}

	public Partiepolitique getPartiepolitique() {
		return this.partiepolitique;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPartiepolitique(Partiepolitique partiepolitique) {
		this.partiepolitique = partiepolitique;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getNomArabe()+" "+this.getPrenomArabe();
	}


	public String getSearchText() {
		String commissions="";
		for(Commission c : this.getCommissions())
		{
			commissions+=c.getDesignationCommission()+"^^";
		}
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		return adresse  + "^^" + cin + "^^" + format1.format(dateNaissance) + "^^"
				+ lieuNaissance + "^^" + nom + "^^" + nomArabe + "^^" + prenom +
				"^^" + prenomArabe +"^^" + profession +
				"^^" + telephone + "^^" + circonscription.getDesignationCirconscription() + "^^" +
				"^^" + partiepolitique.getDesignationPartiePolitique() + "^^"
				+ fonction.getDesignation() + "^^" + niveauScolaire.getDesignation()+
				"^^"+ email+"^^"+commissions;
	}
}