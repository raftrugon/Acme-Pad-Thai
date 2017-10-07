package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {
	
	// Attributes -------------------------------------------------------------

	private String name;
	private String description;
	private String picture;
	private Collection<String> tags;
	
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
	
	@ElementCollection
	@NotNull
	public Collection<String> getTags() {
		return tags;
	}
	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}
	
	// Relationships ----------------------------------------------------------
	private Category categoryFather; //Father category
	private Collection<Category> categoriesSons; //Sons category
	private Collection<Recipe> recipes;

	@ManyToOne(optional=true)
	@Valid
	public Category getCategoryFather() { //Father Category
		return categoryFather;
	}

	public void setCategoryFather(Category categoryFather) {
		this.categoryFather = categoryFather;
	}

	@OneToMany(mappedBy="categoryFather")
	@NotNull
	public Collection<Category> getCategoriesSons() { //Sons Category
		return categoriesSons;
	}

	public void setCategoriesSons(Collection<Category> categoriesSons) {
		this.categoriesSons = categoriesSons;
	}
	
	@ManyToMany
	@NotNull
	public Collection<Recipe> getRecipes() {
		return recipes;
	}
	public void setRecipes(Collection<Recipe> recipes) {
		this.recipes = recipes;
	}

}
