package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the salle database table.
 * 
 */
@Entity
@NamedQuery(name="Salle.findAll", query="SELECT s FROM Salle s")
public class Salle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSalle;

	private String designation;

	//bi-directional many-to-one association to PretSalle
	@OneToMany(mappedBy="salle")
	private List<PretSalle> pretsalles;

	public Salle() {
		action.setSpacing(5);
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

	public int getIdSalle() {
		return this.idSalle;
	}

	public void setIdSalle(int idSalle) {
		this.idSalle = idSalle;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<PretSalle> getPretsalles() {
		return this.pretsalles;
	}

	public void setPretsalles(List<PretSalle> pretsalles) {
		this.pretsalles = pretsalles;
	}

	@Override
	public String toString() {
		return designation ;
	}
}