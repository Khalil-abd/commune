package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.layout.HBox;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the sujetsession database table.
 * 
 */
@Entity
@NamedQuery(name="Sujetsession.findAll", query="SELECT s FROM Sujetsession s")
public class Sujetsession implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSujetSession;

	private int annee;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Time heure;

	private String mois;

	//bi-directional many-to-one association to Objetreunion
	@OneToMany(mappedBy="sujetsession",cascade = CascadeType.ALL)
	private List<Objetreunion> objetreunions;

	//bi-directional many-to-one association to Designationsujetsession
	@ManyToOne
	@JoinColumn(name="idDesignation")
	private Designationsujetsession designationsujetsession;

	//bi-directional many-to-one association to Session
	@ManyToOne
	@JoinColumn(name="idSession")
	private Session session;
	
	@ManyToOne
	@JoinColumn(name="idMandat")
	private Mandat mandat;
	
	public Mandat getMandat() {
		return mandat;
	}

	public void setMandat(Mandat mandat) {
		this.mandat = mandat;
	}

	@Transient
	private HBox action =new HBox();

	@Transient
	private int numero;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}

	public Sujetsession() {
		this.action.setSpacing(5);
	}

	public int getIdSujetSession() {
		return this.idSujetSession;
	}

	public void setIdSujetSession(int idSujetSession) {
		this.idSujetSession = idSujetSession;
	}

	public int getAnnee() {
		return this.annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getHeure() {
		return this.heure;
	}

	public void setHeure(Time heure) {
		this.heure = heure;
	}

	public String getMois() {
		return this.mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}
	
	public List<Objetreunion> getObjetreunions() {
		return this.objetreunions;
	}

	public void setObjetreunions(List<Objetreunion> objetreunions) {
		this.objetreunions = objetreunions;
	}

	public Objetreunion addObjetreunion(Objetreunion objetreunion) {
		getObjetreunions().add(objetreunion);
		objetreunion.setSujetsession(this);

		return objetreunion;
	}

	public Objetreunion removeObjetreunion(Objetreunion objetreunion) {
		getObjetreunions().remove(objetreunion);
		objetreunion.setSujetsession(null);

		return objetreunion;
	}

	public Designationsujetsession getDesignationsujetsession() {
		return this.designationsujetsession;
	}

	public void setDesignationsujetsession(Designationsujetsession designationsujetsession) {
		this.designationsujetsession = designationsujetsession;
	}

	public Session getSession() {
		return this.session;
	}

	public void setSession(Session session) {
		this.session = session;
	}


	public String getSearchText() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		return  annee + "^^" + format1.format(date) + "^^" + heure + "^^" + mois +
				"^^" + designationsujetsession.getDesignation() + "^^" + session.getDesignationSession() +
				"^^" + mandat;
	}
}