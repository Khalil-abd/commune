package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.layout.HBox;

import java.util.List;


/**
 * The persistent class for the session database table.
 * 
 */
@Entity
@NamedQuery(name="Session.findAll", query="SELECT s FROM Session s")
public class Session implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSession;

	private String designationSession;

	//bi-directional many-to-one association to Sujetsession
	@OneToMany(mappedBy="session")
	private List<Sujetsession> sujetsessions;

	@Transient
	private HBox action =new HBox();
	
	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}
	
	public Session() {
		action.setSpacing(10);
	}

	public int getIdSession() {
		return this.idSession;
	}

	public void setIdSession(int idSession) {
		this.idSession = idSession;
	}

	public String getDesignationSession() {
		return this.designationSession;
	}

	public void setDesignationSession(String designationSession) {
		this.designationSession = designationSession;
	}

	public List<Sujetsession> getSujetsessions() {
		return this.sujetsessions;
	}

	public void setSujetsessions(List<Sujetsession> sujetsessions) {
		this.sujetsessions = sujetsessions;
	}

	public Sujetsession addSujetsession(Sujetsession sujetsession) {
		getSujetsessions().add(sujetsession);
		sujetsession.setSession(this);

		return sujetsession;
	}

	public Sujetsession removeSujetsession(Sujetsession sujetsession) {
		getSujetsessions().remove(sujetsession);
		sujetsession.setSession(null);

		return sujetsession;
	}

	@Transient
	private int numero;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.designationSession;
	}
}