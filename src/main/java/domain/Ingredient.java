package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Ingredient extends DomainEntity{
	
	// Attributes -------------------------------------------------------------
	
	private String name;
	private String description;
	private String picture;
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@URL
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	// Relationships ----------------------------------------------------------
	
	private Collection<IngredientProperties> ingredientProperties;
	private Collection<IngredientQuantity> ingredientQuantities;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="ingredient")
	@NotNull
	public Collection<IngredientProperties> getIngredientProperties(){
		return ingredientProperties;
	}
	public void setIngredientProperties(Collection<IngredientProperties> ingredientProperties){
		this.ingredientProperties = ingredientProperties;
	}
	
	@OneToMany(mappedBy="ingredient")
	@NotNull
	public Collection<IngredientQuantity> getIngredientQuantities(){
		return ingredientQuantities;
	}
	public void setIngredientQuantities(Collection<IngredientQuantity> ingredientQuantities){
		this.ingredientQuantities = ingredientQuantities;
	}
}
