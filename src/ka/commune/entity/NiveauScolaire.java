package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the niveauscolaire database table.
 * 
 */
@Entity
@NamedQuery(name="NiveauScolaire.findAll", query="SELECT n FROM NiveauScolaire n")
public class NiveauScolaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idNiveauScolaire;

	private String designation;
	@OneToMany(mappedBy="niveauScolaire")
	private List<Membreconseil> membreconseils;

	@Transient
	private HBox action=new HBox();

	public NiveauScolaire() {
		action.setSpacing(5);
	}

	public List<Membreconseil> getMembreconseils() {
		return membreconseils;
	}

	public void setMembreconseils(List<Membreconseil> membreconseils) {
		this.membreconseils = membreconseils;
	}

	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}

	public int getIdNiveauScolaire() {
		return this.idNiveauScolaire;
	}

	public void setIdNiveauScolaire(int idNiveauScolaire) {
		this.idNiveauScolaire = idNiveauScolaire;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public String toString() {
		return designation;
	}
	@Transient
	private int numero;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}