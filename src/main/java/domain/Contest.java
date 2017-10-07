package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
	
@Entity
@Access(AccessType.PROPERTY)	
public class Contest extends DomainEntity {
	
	// Attributes -------------------------------------------------------------
	
	private String title;
	private Date openingTime;
	private Date closingTime;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	public Date getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	public Date getClosingTime() {
		return closingTime;
	}
	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
	}
	
	// Relationships ----------------------------------------------------------
	private Collection<Recipe> recipes;
	private Collection<WinnerRecipe> winnerRecipes;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@NotNull
	@OneToMany
	public Collection<Recipe> getRecipes() {
		return recipes;
	}
	public void setRecipes(Collection<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	@NotNull
	@OneToMany
	public Collection<WinnerRecipe> getWinnerRecipes() {
		return winnerRecipes;
	}
	public void setWinnerRecipes(Collection<WinnerRecipe> winnerRecipes) {
		this.winnerRecipes = winnerRecipes;
	}
}
