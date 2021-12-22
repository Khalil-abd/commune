package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the pretsalle database table.
 * 
 */
@Entity
@NamedQuery(name="PretSalle.findAll", query="SELECT p FROM PretSalle p")
public class PretSalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPretSalle;

	private int numero;

	private int year;

	private String activite;

	private String beneficiare;

	@Temporal(TemporalType.DATE)
	private Date dateDebut;

	@Temporal(TemporalType.DATE)
	private Date dateDemande;

	@Temporal(TemporalType.DATE)
	private Date dateFin;

	private Time heureDebut;

	private Time heureFin;

	private String representant;

	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAction(HBox action) {
		this.action = action;
	}

	//bi-directional many-to-one association to Salle
	@ManyToOne
	@JoinColumn(name="idSalle")
	private Salle salle;

	public PretSalle() {
		action.setSpacing(5);
	}


	@Transient
	private HBox action=new HBox();

	public HBox getAction() {
		return action;
	}

	public int getIdPretSalle() {
		return this.idPretSalle;
	}

	public void setIdPretSalle(int idPretSalle) {
		this.idPretSalle = idPretSalle;
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

	public Time getHeureDebut() {
		return this.heureDebut;
	}

	public void setHeureDebut(Time heureDebut) {
		this.heureDebut = heureDebut;
	}

	public Time getHeureFin() {
		return this.heureFin;
	}

	public void setHeureFin(Time heureFin) {
		this.heureFin = heureFin;
	}

	public String getRepresentant() {
		return this.representant;
	}

	public void setRepresentant(String representant) {
		this.representant = representant;
	}

	public Salle getSalle() {
		return this.salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public String getSearchText() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		return activite+ "   "+ beneficiare+ "   " + format1.format(dateDebut)+ "   " + format1.format(dateDemande)+ "   "
				+ format1.format(dateFin)+ "   " + heureDebut+ "   "+ heureFin+ "   " +
				representant+ "   " + salle+ "   " + numero+"/"+year ;
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