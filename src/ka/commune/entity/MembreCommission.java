package ka.commune.entity;

import javafx.scene.control.Button;

import javax.persistence.Transient;

public class MembreCommission {
	
	private Membreconseil membreConseil;
	private Commission commission;
	private Button delete=new Button("حذف");
	private int numero;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public Button getDelete() {
		return delete;
	}
	public void setDelete(Button delete) {
		this.delete = delete;
	}
	public Membreconseil getMembreConseil() {
		return membreConseil;
	}
	public void setMembreConseil(Membreconseil membreConseil) {
		this.membreConseil = membreConseil;
	}
	public Commission getCommission() {
		return commission;
	}
	public void setCommission(Commission commission) {
		this.commission = commission;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.commission.toString();
	}

}
