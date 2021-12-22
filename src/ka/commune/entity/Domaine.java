package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.layout.HBox;

import java.util.List;


/**
 * The persistent class for the domaine database table.
 * 
 */
@Entity
@NamedQuery(name="Domaine.findAll", query="SELECT d FROM Domaine d")
public class Domaine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDomaine;

	private String designationDomaine;

	//bi-directional many-to-one association to Objetreunion
	@OneToMany(mappedBy="domaine")
	private List<Objetreunion> objetreunions;

	@Transient
	private HBox action =new HBox();
	
	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}
	
	public Domaine() {
	}

	public int getIdDomaine() {
		return this.idDomaine;
	}

	public void setIdDomaine(int idDomaine) {
		this.idDomaine = idDomaine;
	}

	public String getDesignationDomaine() {
		return this.designationDomaine;
	}

	public void setDesignationDomaine(String designationDomaine) {
		this.designationDomaine = designationDomaine;
	}

	public List<Objetreunion> getObjetreunions() {
		return this.objetreunions;
	}

	public void setObjetreunions(List<Objetreunion> objetreunions) {
		this.objetreunions = objetreunions;
	}

	public Objetreunion addObjetreunion(Objetreunion objetreunion) {
		getObjetreunions().add(objetreunion);
		objetreunion.setDomaine(this);

		return objetreunion;
	}

	public Objetreunion removeObjetreunion(Objetreunion objetreunion) {
		getObjetreunions().remove(objetreunion);
		objetreunion.setDomaine(null);

		return objetreunion;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.designationDomaine;
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