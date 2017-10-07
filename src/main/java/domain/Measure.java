package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Measure extends DomainEntity {
	
	// Attributes -------------------------------------------------------------

	private String name;
	private boolean isInteger;
	
	@NotBlank
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public boolean getIsInteger(){
		return this.isInteger;
	}
	
	public void setIsInteger(boolean isInteger){
		this.isInteger = isInteger;
	}
}
