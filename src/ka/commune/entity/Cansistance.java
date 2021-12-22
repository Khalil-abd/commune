package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cansistance database table.
 * 
 */
@Entity
@NamedQuery(name="Cansistance.findAll", query="SELECT c FROM Cansistance c")
public class Cansistance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCansistance;

	private String designation;

	//bi-directional many-to-one association to Operation
	@ManyToOne()
	@JoinColumn(name="operation")
	private Operation operationBean;

	public Cansistance() {
		action.setSpacing(5);
	}

	public int getIdCansistance() {
		return this.idCansistance;
	}

	public void setIdCansistance(int idCansistance) {
		this.idCansistance = idCansistance;
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