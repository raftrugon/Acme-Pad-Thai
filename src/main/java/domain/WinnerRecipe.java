package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class WinnerRecipe extends DomainEntity {

	// Relationships ----------------------------------------------------------
	private Recipe recipe;
	
	@Valid
	@NotNull
	@OneToOne(mappedBy="winnerRecipe", optional=false)
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
