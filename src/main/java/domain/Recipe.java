package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
public class Recipe extends DomainEntity{
	
	// Attributes -------------------------------------------------------------
	private String ticker;
	private String title;
	private String summary;
	private Date authorDate;
	private Date lastUpdate;
	private Collection<String> pictures;
	private Boolean readOnly;

	@Pattern(regexp = "^\\d\\d\\d\\d\\d\\d-\\w\\w\\w\\w$")
	@NotBlank
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String tittle) {
		this.title = tittle;
	}
	
	@NotBlank
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	public Date getAuthorDate() {
		return authorDate;
	}
	public void setAuthorDate(Date authorDate) {
		this.authorDate = authorDate;
	}
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@NotNull
	@ElementCollection (fetch = FetchType.EAGER)
	public Collection<String> getPictures() {
		return pictures;
	}

	public void setPictures(Collection<String> pictures) {
		this.pictures = pictures;
	}
	
	public Boolean getReadOnly() {
		return readOnly;
	}
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	
	// Relationships ----------------------------------------------------------
	private User user;
	private Collection<Step> steps;
	private Collection<Category> categories;
	private Collection<Comment> comments;
	private Collection<IngredientQuantity> ingredientQuantities;
	private Collection<Like> likes;
	private WinnerRecipe winnerRecipe;
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@NotNull
	@OneToMany(mappedBy="recipe",cascade=CascadeType.ALL)
	public Collection<Step> getSteps() {
		return steps;
	}
	public void setSteps(Collection<Step> steps) {
		this.steps = steps;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@NotNull
	public Collection<Category> getCategories() {
		return categories;
	}
	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="recipe",cascade=CascadeType.ALL)
	@NotNull
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="recipe")
	@NotNull
	public Collection<IngredientQuantity> getIngredientQuantities() {
		return ingredientQuantities;
	}
	public void setIngredientQuantities(Collection<IngredientQuantity> ingredientQuantities) {
		this.ingredientQuantities = ingredientQuantities;
	}	
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany 
	@NotNull
	public Collection<Like> getLikes() {
		return likes;
	}
	public void setLikes(Collection<Like> likes) {
		this.likes = likes;
	}
	
	@OneToOne(optional=true, cascade=CascadeType.ALL)
	@Valid
	public WinnerRecipe getWinnerRecipe() {
		return winnerRecipe;
	}
	public void setWinnerRecipe(WinnerRecipe winnerRecipe) {
		this.winnerRecipe = winnerRecipe;
	}




}
