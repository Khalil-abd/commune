package ka.commune.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pretedmateriel database table.
 * 
 */
@Embeddable
public class PretedMaterielPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idPretMateriel;

	@Column(insertable=false, updatable=false)
	private int idMateriel;

	public PretedMaterielPK() {
	}
	public int getIdPretMateriel() {
		return this.idPretMateriel;
	}
	public void setIdPretMateriel(int idPretMateriel) {
		this.idPretMateriel = idPretMateriel;
	}
	public int getIdMateriel() {
		return this.idMateriel;
	}
	public void setIdMateriel(int idMateriel) {
		this.idMateriel = idMateriel;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PretedMaterielPK)) {
			return false;
		}
		PretedMaterielPK castOther = (PretedMaterielPK)other;
		return 
			(this.idPretMateriel == castOther.idPretMateriel)
			&& (this.idMateriel == castOther.idMateriel);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPretMateriel;
		hash = hash * prime + this.idMateriel;
		
		return hash;
	}
}