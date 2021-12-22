package ka.commune.entity;

import javafx.scene.layout.HBox;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the reunioncommissionec database table.
 * 
 */
@Entity
@NamedQuery(name="ReunionCommissionec.findAll", query="SELECT r FROM ReunionCommissionec r")
public class ReunionCommissionec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idReunion;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String sujet;

	private Time time;

	public ReunionCommissionec() {
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

	public void setAction(HBox action) {
		this.action = action;
	}

	public int getIdReunion() {
		return this.idReunion;
	}

	public void setIdReunion(int idReunion) {
		this.idReunion = idReunion;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSujet() {
		return this.sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public Time getTime() {
		return this.time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getSearchText() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		return format1.format(date) + "^^" + sujet + "^^" + time + "^^" + numero;
	}
}