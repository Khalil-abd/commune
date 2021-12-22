package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pretedmateriel database table.
 * 
 */
@Entity
@NamedQuery(name="PretedMateriel.findAll", query="SELECT p FROM PretedMateriel p")
public class PretedMateriel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PretedMaterielPK id;

	private double quantite;

	//bi-directional many-to-one association to Materiel
	@ManyToOne
	@JoinColumn(name="idMateriel")
	private Materiel materiel;

	//bi-directional many-to-one association to PretMateriel
	@ManyToOne(cascade=CascadeType.ALL )
	@JoinColumn(name="idPretMateriel")
	private PretMateriel pretmateriel;

	public PretedMateriel() {
		action.setSpacing(5);
	}

	public PretedMaterielPK getId() {
		return this.id;
	}

	public void setId(PretedMaterielPK id) {
		this.id = id;
	}

	public double getQuantite() {
		return this.quantite;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	public Materiel getMateriel() {
		return this.materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public PretMateriel getPretmateriel() {
		return this.pretmateriel;
	}

	public void setPretmateriel(PretMateriel pretmateriel) {
		this.pretmateriel = pretmateriel;
	}

	@Transient
	private int numero;

	@Transient
	private HBox action=new HBox();

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

	@Override
	public String toString() {
		return materiel.getDesignation();
	}
}