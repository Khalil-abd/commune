package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the fonction database table.
 * 
 */
@Entity
@NamedQuery(name="Fonction.findAll", query="SELECT f FROM Fonction f")
public class Fonction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idFonction;

	private String designation;
	
	@OneToMany(mappedBy="fonction")
	private List<Membreconseil> membreconseils;

	@Transient
	private HBox action=new HBox();

	public Fonction() {
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

	public int getIdFonction() {
		return this.idFonction;
	}

	public void setIdFonction(int idFonction) {
		this.idFonction = idFonction;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public String toString() {
		return designation ;
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