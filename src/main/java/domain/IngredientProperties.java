package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class IngredientProperties extends DomainEntity{
	
	// Attributes -------------------------------------------------------------
		
	private String name;
	private String properties;
	
	@NotBlank
	@Column(unique = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	// Relationships ----------------------------------------------------------
	
	private Ingredient ingredient;
	
	@Valid
	@ManyToOne(optional=true)
	public Ingredient getIngredient(){
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient){
		this.ingredient = ingredient;
	}
	
}
