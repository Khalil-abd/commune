package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.control.Button;


/**
 * The persistent class for the objetactivitebs database table.
 * 
 */
@Entity
@NamedQuery(name="ObjetActiviteBS.findAll", query="SELECT o FROM ObjetActiviteBS o")
public class ObjetActiviteBS implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idObjetActiviteBS;

	private String designation;

	//bi-directional many-to-one association to ActiviteBS
	@ManyToOne
	@JoinColumn(name="idActiviteBS")
	private ActiviteBS activiteb;
	
	@Transient
	private Button delete=new Button("حذف");
	
	
	public Button getDelete() {
		return delete;
	}

	public void setDelete(Button delete) {
		this.delete = delete;
	}

	public ObjetActiviteBS() {
	}

	public int getIdObjetActiviteBS() {
		return this.idObjetActiviteBS;
	}

	public void setIdObjetActiviteBS(int idObjetActiviteBS) {
		this.idObjetActiviteBS = idObjetActiviteBS;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public ActiviteBS getActiviteb() {
		return this.activiteb;
	}

	public void setActiviteb(ActiviteBS activiteb) {
		this.activiteb = activiteb;
	}

}