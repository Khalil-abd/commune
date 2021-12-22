package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the commissionec database table.
 * 
 */
@Entity
@NamedQuery(name="CommissionEC.findAll", query="SELECT c FROM CommissionEC c")
public class CommissionEC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCommissionEC;

	private String adresse;

	@Temporal(TemporalType.DATE)
	private Date dateExpiration;

	@Temporal(TemporalType.DATE)
	private Date dateFondation;

	@Temporal(TemporalType.DATE)
	private Date dateRenouvelement;

	private String domaine;

	private String nom;

	private String phone;

	private String represantant;

	@Transient
	private int numero;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public CommissionEC() {
		action.setSpacing(5);
	}

	@Transient
	private HBox action=new HBox();

	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}

	public int getIdCommissionEC() {
		return this.idCommissionEC;
	}

	public void setIdCommissionEC(int idCommissionEC) {
		this.idCommissionEC = idCommissionEC;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Date getDateExpiration() {
		return this.dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public Date getDateFondation() {
		return this.dateFondation;
	}

	public void setDateFondation(Date dateFondation) {
		this.dateFondation = dateFondation;
	}

	public Date getDateRenouvelement() {
		return this.dateRenouvelement;
	}

	public void setDateRenouvelement(Date dateRenouvelement) {
		this.dateRenouvelement = dateRenouvelement;
	}

	public String getDomaine() {
		return this.domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRepresantant() {
		return this.represantant;
	}

	public void setRepresantant(String represantant) {
		this.represantant = represantant;
	}

	public String getSearchText() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		return adresse + "^^" + format1.format(dateExpiration) +
				"^^" + format1.format(dateFondation) + "^^" + format1.format(dateRenouvelement) +
				"^^" + domaine + "^^" + nom + "^^" + phone+
				"^^" + represantant + "^^" + numero;
	}
}