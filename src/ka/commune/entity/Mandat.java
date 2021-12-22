package ka.commune.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.persistence.*;
import javafx.scene.layout.HBox;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the mandat database table.
 * 
 */
@Entity
@NamedQuery(name="Mandat.findAll", query="SELECT m FROM Mandat m")
public class Mandat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMandat;

	@Temporal(TemporalType.DATE)
	private Date dateDebut;

	@Temporal(TemporalType.DATE)
	private Date dateFin;

	//bi-directional many-to-one association to Membreconseil
	@OneToMany(mappedBy="mandat")
	private List<Membreconseil> membreconseils;

	@OneToMany(mappedBy="mandat")
	private List<Sujetsession> sujetsessions;
	
	@Transient
	private HBox action =new HBox();
	
	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}
	
	public Mandat() {
		this.action.setSpacing(10);
	}

	public int getIdMandat() {
		return this.idMandat;
	}

	public void setIdMandat(int idMandat) {
		this.idMandat = idMandat;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public List<Membreconseil> getMembreconseils() {
		return this.membreconseils;
	}

	public void setMembreconseils(List<Membreconseil> membreconseils) {
		this.membreconseils = membreconseils;
	}

	public Membreconseil addMembreconseil(Membreconseil membreconseil) {
		getMembreconseils().add(membreconseil);
		membreconseil.setMandat(this);

		return membreconseil;
	}

	public Membreconseil removeMembreconseil(Membreconseil membreconseil) {
		getMembreconseils().remove(membreconseil);
		membreconseil.setMandat(null);

		return membreconseil;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		return format1.format(dateFin)+" - "+format1.format(dateDebut);
	}

	public List<Sujetsession> getSujetsessions() {
		return sujetsessions;
	}

	public void setSujetsessions(List<Sujetsession> sujetsessions) {
		this.sujetsessions = sujetsessions;
	}

}