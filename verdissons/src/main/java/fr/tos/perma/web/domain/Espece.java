package fr.tos.perma.web.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ESPECE")
public class Espece extends BotanicItem {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getLibelle() == null) ? 0 : getLibelle().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Espece other = (Espece) obj;
		if (getLibelle() == null) {
			if (other.getLibelle() != null)
				return false;
		} else if (!getLibelle().equals(other.getLibelle()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Espece [genre=" + getParent() + ", libelle=" + getLibelle() + "]";
	}

}
