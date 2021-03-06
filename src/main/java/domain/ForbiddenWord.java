package domain;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ForbiddenWord extends DomainEntity{
	
	// Attributes -------------------------------------------------------------

	private String name;
		
    @NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
