package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the partenaire database table.
 * 
 */
@Entity
@NamedQuery(name="Partenaire.findAll", query="SELECT p FROM Partenaire p")
public class Partenaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPartenaire;
	@Transient
	private HBox button=new HBox();

	private String nom;

	//bi-directional many-to-one association to Operation
	@OneToMany(mappedBy="partenaireBean")
	private List<Operation> operations;

	public Partenaire() {
	}

	public int getIdPartenaire() {
		return this.idPartenaire;
	}

	public void setIdPartenaire(int idPartenaire) {
		this.idPartenaire = idPartenaire;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Operation> getOperations() {
		return this.operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public Operation addOperation(Operation operation) {
		getOperations().add(operation);
		operation.setPartenaireBean(this);

		return operation;
	}

	public Operation removeOperation(Operation operation) {
		getOperations().remove(operation);
		operation.setPartenaireBean(null);

		return operation;
	}

	public HBox getButton() {
		return button;
	}

	public void setButton(HBox button) {
		this.button = button;
	}
}