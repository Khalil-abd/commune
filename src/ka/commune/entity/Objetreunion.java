package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.layout.HBox;


/**
 * The persistent class for the objetreunion database table.
 * 
 */
@Entity
@NamedQuery(name="Objetreunion.findAll", query="SELECT o FROM Objetreunion o")
public class Objetreunion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idObjetReunion;

	private String designationObjetReunion;

	//bi-directional many-to-one association to Domaine
	@ManyToOne
	@JoinColumn(name="idDomaine")
	private Domaine domaine;

	//bi-directional many-to-one association to Sujetsession
	@ManyToOne
	@JoinColumn(name="idSujetSession")
	private Sujetsession sujetsession;

	@Transient
	private HBox action =new HBox();
	
	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}
	
	public Objetreunion() {
		action.setSpacing(10);
	}

	public int getIdObjetReunion() {
		return this.idObjetReunion;
	}

	public void setIdObjetReunion(int idObjetReunion) {
		this.idObjetReunion = idObjetReunion;
	}

	public String getDesignationObjetReunion() {
		return this.designationObjetReunion;
	}

	public void setDesignationObjetReunion(String designationObjetReunion) {
		this.designationObjetReunion = designationObjetReunion;
	}

	public Domaine getDomaine() {
		return this.domaine;
	}

	public void setDomaine(Domaine domaine) {
		this.domaine = domaine;
	}

	public Sujetsession getSujetsession() {
		return this.sujetsession;
	}

	public void setSujetsession(Sujetsession sujetsession) {
		this.sujetsession = sujetsession;
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