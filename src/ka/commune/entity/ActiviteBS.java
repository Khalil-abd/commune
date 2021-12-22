package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.layout.HBox;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the activitebs database table.
 * 
 */
@Entity
@NamedQuery(name="ActiviteBS.findAll", query="SELECT a FROM ActiviteBS a")
public class ActiviteBS implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idActiviteBS;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String jointures;

	private String sujet;

	//bi-directional many-to-one association to ObjetActiviteBS
	@OneToMany(mappedBy="activiteb",cascade = CascadeType.ALL)
	private List<ObjetActiviteBS> objetactivitebs;
	
	@Transient
	private HBox action =new HBox();

	@Transient
	private int numero;
	
	public HBox getAction() {
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}

	public ActiviteBS() {
		this.action.setSpacing(5);
	}

	public int getIdActiviteBS() {
		return this.idActiviteBS;
	}

	public void setIdActiviteBS(int idActiviteBS) {
		this.idActiviteBS = idActiviteBS;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getJointures() {
		return jointures;
	}

	public void setJointures(String jointures) {
		this.jointures = jointures;
	}

	public String getSujet() {
		return this.sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public List<ObjetActiviteBS> getObjetactivitebs() {
		return this.objetactivitebs;
	}

	public void setObjetactivitebs(List<ObjetActiviteBS> objetactivitebs) {
		this.objetactivitebs = objetactivitebs;
	}

	public ObjetActiviteBS addObjetactiviteb(ObjetActiviteBS objetactiviteb) {
		getObjetactivitebs().add(objetactiviteb);
		objetactiviteb.setActiviteb(this);

		return objetactiviteb;
	}

	public ObjetActiviteBS removeObjetactiviteb(ObjetActiviteBS objetactiviteb) {
		getObjetactivitebs().remove(objetactiviteb);
		objetactiviteb.setActiviteb(null);

		return objetactiviteb;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getSearchText() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		return format1.format(date) +"^^"+ jointures + "^^" + sujet;
	}
}