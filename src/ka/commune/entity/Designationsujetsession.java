package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.layout.HBox;

import java.util.List;


/**
 * The persistent class for the designationsujetsession database table.
 * 
 */
@Entity
@NamedQuery(name="Designationsujetsession.findAll", query="SELECT d FROM Designationsujetsession d")
public class Designationsujetsession implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDesignation;

	private String designation;

	//bi-directional many-to-one association to Sujetsession
	@OneToMany(mappedBy="designationsujetsession")
	private List<Sujetsession> sujetsessions;

	@Transient
	private HBox action =new HBox();
	
	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}
	
	public Designationsujetsession() {
	}

	public int getIdDesignation() {
		return this.idDesignation;
	}

	public void setIdDesignation(int idDesignation) {
		this.idDesignation = idDesignation;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<Sujetsession> getSujetsessions() {
		return this.sujetsessions;
	}

	public void setSujetsessions(List<Sujetsession> sujetsessions) {
		this.sujetsessions = sujetsessions;
	}

	public Sujetsession addSujetsession(Sujetsession sujetsession) {
		getSujetsessions().add(sujetsession);
		sujetsession.setDesignationsujetsession(this);

		return sujetsession;
	}

	public Sujetsession removeSujetsession(Sujetsession sujetsession) {
		getSujetsessions().remove(sujetsession);
		sujetsession.setDesignationsujetsession(null);

		return sujetsession;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.designation;
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