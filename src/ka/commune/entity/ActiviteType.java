package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the activitetype database table.
 * 
 */
@Entity
@NamedQuery(name="ActiviteType.findAll", query="SELECT a FROM ActiviteType a")
public class ActiviteType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idActiviteType;

	@Lob
	private String designation;

	//bi-directional many-to-one association to Operation
	@OneToMany(mappedBy="activitetype")
	private List<Operation> operations;

	public ActiviteType() {
		action=new HBox();
		action.setSpacing(5);
	}

	public int getIdActiviteType() {
		return this.idActiviteType;
	}

	public void setIdActiviteType(int idActiviteType) {
		this.idActiviteType = idActiviteType;
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
		operation.setActivitetype(this);

		return operation;
	}

	public Operation removeOperation(Operation operation) {
		getOperations().remove(operation);
		operation.setActivitetype(null);

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