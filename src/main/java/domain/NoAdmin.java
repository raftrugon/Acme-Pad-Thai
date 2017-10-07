package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
@Entity
@Access(AccessType.PROPERTY)
public abstract class NoAdmin extends Actor {

	// Relationships ----------------------------------------------------------

	private Collection<NoAdmin> noAdmins; // NoAdmin's followers

	@ManyToMany
	@NotNull
	public Collection<NoAdmin> getNoAdmins() { //NoAdmin's followers
		return noAdmins;
	}
	
	public void setNoAdmins(Collection<NoAdmin> noAdmins) {
		this.noAdmins = noAdmins;
	}	
}
