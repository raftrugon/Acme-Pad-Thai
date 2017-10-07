package domain;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "lyke")
@Entity
@Access(AccessType.PROPERTY)
public class Like extends DomainEntity{
	
	// Attributes -------------------------------------------------------------

	private boolean isDislike;
	
	public boolean getIsDislike() {
		return isDislike;
	}

	public void setIsDislike(Boolean isDislike) {
		this.isDislike = isDislike;
	}
	
	// Relationships ----------------------------------------------------------
	private NoAdmin noAdmin;
	
	@NotNull
	@ManyToOne(optional=false)
	public NoAdmin getNoAdmin(){
		return noAdmin;
	}
	public void setNoAdmin(NoAdmin noAdmin){
		this.noAdmin = noAdmin;
	}
}
