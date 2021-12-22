package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

import javafx.scene.control.Button;

import java.util.List;


/**
 * The persistent class for the commission database table.
 * 
 */
@Entity
@NamedQuery(name="Commission.findAll", query="SELECT c FROM Commission c")
public class Commission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCommission;

	private String designationCommission;

	//bi-directional many-to-many association to Membreconseil
	@ManyToMany(mappedBy="commissions")
	private List<Membreconseil> membreconseils;
	
	@Transient
	private Button delete=new Button("حذف");
	
	
	public Button getDelete() {
		return delete;
	}

	public void setDelete(Button delete) {
		this.delete = delete;
	}

	public Commission() {
	}

	public int getIdCommission() {
		return this.idCommission;
	}

	public void setIdCommission(int idCommission) {
		this.idCommission = idCommission;
	}

	public String getDesignationCommission() {
		return this.designationCommission;
	}

	public void setDesignationCommission(String designationCommission) {
		this.designationCommission = designationCommission;
	}

	public List<Membreconseil> getMembreconseils() {
		return this.membreconseils;
	}

	public void setMembreconseils(List<Membreconseil> membreconseils) {
		this.membreconseils = membreconseils;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDesignationCommission();
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