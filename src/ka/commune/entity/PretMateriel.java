package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.layout.HBox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the pretmateriel database table.
 * 
 */
@Entity
@NamedQuery(name="PretMateriel.findAll", query="SELECT p FROM PretMateriel p")
public class PretMateriel implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPretMateriel;
	private String activite;
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	@Temporal(TemporalType.DATE)
	private Date dateDemande;
	@Temporal(TemporalType.DATE)
	private Date dateFin;
	//bi-directional many-to-one association to PretedMateriel
	@OneToMany(mappedBy="pretmateriel", cascade = CascadeType.ALL)
	private List<PretedMateriel> pretedmateriels;
	//bi-directional many-to-many association to Materiel
	@ManyToMany
	@JoinTable(
		name="pretedmateriel"
		, joinColumns={
			@JoinColumn(name="idPretMateriel")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idMateriel")
			}
		)
	private List<Materiel> materiels;

	private String beneficiare;
	private int numero;
	private int year;
	private String phone;
	private String representant;
	public PretMateriel() {
		action.setSpacing(5);
	}

	public int getIdPretMateriel() {
		return this.idPretMateriel;
	}

	public void setIdPretMateriel(int idPretMateriel) {
		this.idPretMateriel = idPretMateriel;
	}

	public String getActivite() {
		return this.activite;
	}

	public void setActivite(String activite) {
		this.activite = activite;
	}

	public String getBeneficiare() {
		return this.beneficiare;
	}

	public void setBeneficiare(String beneficiare) {
		this.beneficiare = beneficiare;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateDemande() {
		return this.dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRepresentant() {
		return this.representant;
	}

	public void setRepresentant(String representant) {
		this.representant = representant;
	}

	public List<PretedMateriel> getPretedmateriels() {
		return this.pretedmateriels;
	}

	public void setPretedmateriels(List<PretedMateriel> pretedmateriels) {
		this.pretedmateriels = pretedmateriels;
	}

	public PretedMateriel addPretedmateriel(PretedMateriel pretedmateriel) {
		getPretedmateriels().add(pretedmateriel);
		pretedmateriel.setPretmateriel(this);

		return pretedmateriel;
	}

	public PretedMateriel removePretedmateriel(PretedMateriel pretedmateriel) {
		getPretedmateriels().remove(pretedmateriel);
		pretedmateriel.setPretmateriel(null);

		return pretedmateriel;
	}

	public List<Materiel> getMateriels() {
		return this.materiels;
	}

	public void setMateriels(List<Materiel> materiels) {
		this.materiels = materiels;
	}
	
	@Transient
	private HBox action=new HBox();

	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}

	@Override
	public String  toString() {
		return "PretMateriel{" +
				"activite='" + activite + '\'' +
				", beneficiare='" + beneficiare + '\'' +
				", numero=" + numero +
				", year=" + year +
				", dateDebut=" + dateDebut +
				", dateDemande=" + dateDemande +
				", dateFin=" + dateFin +
				", phone='" + phone + '\'' +
				", representant='" + representant + '\'' +
				'}';
	}

	public String getSearchText() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		return activite + "$" + beneficiare + "$" + format1.format(dateDebut) + "$"
				+ format1.format(dateDemande)+ "$" + format1.format(dateFin) + "$" + phone + "$"
				+ representant+ "$"+numero+"/"+year;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}