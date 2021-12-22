package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the realisation database table.
 * 
 */
@Entity
@NamedQuery(name="Realisation.findAll", query="SELECT r FROM Realisation r")
public class Realisation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRealisation;

	@Lob
	private String designation;

	//bi-directional many-to-one association to Operation
	@OneToMany(mappedBy="realisationBean")
	private List<Operation> operations;

	public Realisation() {
		action=new HBox();
		action.setSpacing(5);
	}

	public int getIdRealisation() {
		return this.idRealisation;
	}

	public void setIdRealisation(int idRealisation) {
		this.idRealisation = idRealisation;
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
		operation.setRealisationBean(this);

		return operation;
	}

	public Operation removeOperation(Operation operation) {
		getOperations().remove(operation);
		operation.setRealisationBean(null);

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