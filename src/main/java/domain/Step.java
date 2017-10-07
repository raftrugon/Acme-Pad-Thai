package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


@Entity
@Access(AccessType.PROPERTY)
public class Step extends DomainEntity{
	
	// Attributes -------------------------------------------------------------

	private String description;
	private String picture;
	private Collection<String> hints;
	private int numberOfStep;
	
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

	@LazyCollection(LazyCollectionOption.FALSE)
	@NotNull
	@ElementCollection
	public Collection<String> getHints() {
		return hints;
	}

	public void setHints(Collection<String> hints) {
		this.hints = hints;
	}

	@NotNull
	@Min(0)
	public int getNumberOfStep() {
		return numberOfStep;
	}

	public void setNumberOfStep(int numberOfStep) {
		this.numberOfStep = numberOfStep;
	}

	
	// Relationships ----------------------------------------------------------
	
	private Recipe recipe;

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
