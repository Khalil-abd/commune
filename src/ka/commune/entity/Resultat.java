package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the resultat database table.
 * 
 */
@Entity
@NamedQuery(name="Resultat.findAll", query="SELECT r FROM Resultat r")
public class Resultat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idResultat;

	@Lob
	private String designation;

	//bi-directional many-to-one association to Operation
	@OneToMany(mappedBy="resultatBean")
	private List<Operation> operations;

	public Resultat() {
		action=new HBox();
		action.setSpacing(5);
	}

	public int getIdResultat() {
		return this.idResultat;
	}

	public void setIdResultat(int idResultat) {
		this.idResultat = idResultat;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<Operation> getOperations() {
		return this.operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public Operation addOperation(Operation operation) {
		getOperations().add(operation);
		operation.setResultatBean(this);

		return operation;
	}

	public Operation removeOperation(Operation operation) {
		getOperations().remove(operation);
		operation.setResultatBean(null);

		return operation;
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
	private HBox action;

	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return designation ;
	}

}