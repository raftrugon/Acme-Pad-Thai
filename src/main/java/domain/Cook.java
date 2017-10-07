package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Cook extends Actor {

	// Relationships ----------------------------------------------------------

	private Collection<MasterClass> masterClasses;
	
	@NotNull
	@OneToMany(mappedBy="cook")
	public Collection<MasterClass> getMasterClasses(){
		return masterClasses;
	}
	public void setMasterClasses(Collection<MasterClass> masterClasses){
		this.masterClasses = masterClasses;
	}
	
	
	
	
}
