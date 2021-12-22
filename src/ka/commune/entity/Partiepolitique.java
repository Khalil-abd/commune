package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.control.Button;

import java.util.List;


/**
 * The persistent class for the partiepolitique database table.
 * 
 */
@Entity
@NamedQuery(name="Partiepolitique.findAll", query="SELECT p FROM Partiepolitique p")
public class Partiepolitique implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPartiePolitique;
	private String designationPartiePolitique;
	@Transient
	private Button delete=new Button("حذف");
	
	public Button getDelete() {
		return delete;
	}

	public void setDelete(Button delete) {
		this.delete = delete;
	}

	//bi-directional many-to-one association to Membreconseil
	@OneToMany(mappedBy="partiepolitique")
	private List<Membreconseil> membreconseils;

	public Partiepolitique() {
	}

	public int getIdPartiePolitique() {
		return this.idPartiePolitique;
	}

	public void setIdPartiePolitique(int idPartiePolitique) {
		this.idPartiePolitique = idPartiePolitique;
	}

	public String getDesignationPartiePolitique() {
		return this.designationPartiePolitique;
	}

	public void setDesignationPartiePolitique(String designationPartiePolitique) {
		this.designationPartiePolitique = designationPartiePolitique;
	}

	public List<Membreconseil> getMembreconseils() {
		return this.membreconseils;
	}

	public void setMembreconseils(List<Membreconseil> membreconseils) {
		this.membreconseils = membreconseils;
	}

	public Membreconseil addMembreconseil(Membreconseil membreconseil) {
		getMembreconseils().add(membreconseil);
		membreconseil.setPartiepolitique(this);

		return membreconseil;
	}

	public Membreconseil removeMembreconseil(Membreconseil membreconseil) {
		getMembreconseils().remove(membreconseil);
		membreconseil.setPartiepolitique(null);

		return membreconseil;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return designationPartiePolitique;
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