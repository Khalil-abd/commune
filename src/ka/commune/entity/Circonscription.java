package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.control.Button;

import java.util.List;


/**
 * The persistent class for the circonscription database table.
 * 
 */
@Entity
@NamedQuery(name="Circonscription.findAll", query="SELECT c FROM Circonscription c")
public class Circonscription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCirconscription;

	private String designationCirconscription;

	private int numero;
	
	@Transient
	private Button delete=new Button("حذف");
	
	public Button getDelete() {
		return delete;
	}

	public void setDelete(Button delete) {
		this.delete = delete;
	} 

	//bi-directional many-to-one association to Membreconseil
	@OneToMany(mappedBy="circonscription")
	private List<Membreconseil> membreconseils;

	public Circonscription() {
	}

	public int getIdCirconscription() {
		return this.idCirconscription;
	}

	public void setIdCirconscription(int idCirconscription) {
		this.idCirconscription = idCirconscription;
	}

	public String getDesignationCirconscription() {
		return this.designationCirconscription;
	}

	public void setDesignationCirconscription(String designationCirconscription) {
		this.designationCirconscription = designationCirconscription;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<Membreconseil> getMembreconseils() {
		return this.membreconseils;
	}

	public void setMembreconseils(List<Membreconseil> membreconseils) {
		this.membreconseils = membreconseils;
	}

	public Membreconseil addMembreconseil(Membreconseil membreconseil) {
		getMembreconseils().add(membreconseil);
		membreconseil.setCirconscription(this);

		return membreconseil;
	}

	public Membreconseil removeMembreconseil(Membreconseil membreconseil) {
		getMembreconseils().remove(membreconseil);
		membreconseil.setCirconscription(null);

		return membreconseil;
	}

	@Override
	public String toString() {
		return numero+" - " + designationCirconscription;
	}


}