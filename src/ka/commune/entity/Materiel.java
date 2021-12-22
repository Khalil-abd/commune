package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.layout.HBox;

import java.util.List;


/**
 * The persistent class for the materiel database table.
 * 
 */
@Entity
@NamedQuery(name="Materiel.findAll", query="SELECT m FROM Materiel m")
public class Materiel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMateriel;

	private String designation;

	private double quantite;

	//bi-directional many-to-one association to PretedMateriel
	@OneToMany(mappedBy="materiel")
	private List<PretedMateriel> pretedmateriels;

	//bi-directional many-to-many association to PretMateriel
	@ManyToMany(mappedBy="materiels")
	private List<PretMateriel> pretmateriels;

	public Materiel() {
		action.setSpacing(5);
	}

	public int getIdMateriel() {
		return this.idMateriel;
	}

	public void setIdMateriel(int idMateriel) {
		this.idMateriel = idMateriel;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getQuantite() {
		return this.quantite;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	public List<PretedMateriel> getPretedmateriels() {
		return this.pretedmateriels;
	}

	public void setPretedmateriels(List<PretedMateriel> pretedmateriels) {
		this.pretedmateriels = pretedmateriels;
	}

	public PretedMateriel addPretedmateriel(PretedMateriel pretedmateriel) {
		getPretedmateriels().add(pretedmateriel);
		pretedmateriel.setMateriel(this);

		return pretedmateriel;
	}

	public PretedMateriel removePretedmateriel(PretedMateriel pretedmateriel) {
		getPretedmateriels().remove(pretedmateriel);
		pretedmateriel.setMateriel(null);

		return pretedmateriel;
	}

	public List<PretMateriel> getPretmateriels() {
		return this.pretmateriels;
	}

	public void setPretmateriels(List<PretMateriel> pretmateriels) {
		this.pretmateriels = pretmateriels;
	}
	
	@Transient
	private int numero;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
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
	public String toString() {
		return designation;
	}

	public String getSearchText() {
		return  designation + "^^^" + quantite;
	}
}