package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the partenaireoperation database table.
 * 
 */
@Entity
@NamedQuery(name="PartenaireOperation.findAll", query="SELECT p FROM PartenaireOperation p")
public class PartenaireOperation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpartenaire;

	private String designation;

	//bi-directional many-to-one association to Operation
	@ManyToOne()
	@JoinColumn(name="operation")
	private Operation operationBean;

	public PartenaireOperation() {
	}

	public int getIdpartenaire() {
		return this.idpartenaire;
	}

	public void setIdpartenaire(int idpartenaire) {
		this.idpartenaire = idpartenaire;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Operation getOperationBean() {
		return this.operationBean;
	}

	public void setOperationBean(Operation operationBean) {
		this.operationBean = operationBean;
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
}