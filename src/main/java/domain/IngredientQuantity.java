package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class IngredientQuantity extends DomainEntity{
	
	// Attributes -------------------------------------------------------------
		
	private Double quantity;
	
	@Min(1)
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	

	// Relationships ----------------------------------------------------------
	
	private Measure measure;
	private Recipe recipe;
	private Ingredient ingredient;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Measure getMeasure() {
		return measure;
	}
	public void setMeasure(Measure measure) {
		this.measure = measure;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Ingredient getIngredient(){
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient){
		this.ingredient = ingredient;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
