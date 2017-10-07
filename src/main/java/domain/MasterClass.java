package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MasterClass extends DomainEntity{
	
	// Attributes -------------------------------------------------------------
	
	private String title;
	private String description;
	private boolean isPromoted;
	
	@NotBlank
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	@NotBlank
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public boolean getIsPromoted(){
		return this.isPromoted;
	}
	
	public void setIsPromoted(boolean isPromoted){
		this.isPromoted = isPromoted;
	}
	
	
	// Relationships ----------------------------------------------------------
	private Cook cook;
	private Collection<LearningMaterial> learningMaterials;
	private Collection<Actor> actors;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Cook getCook(){
		return cook;
	}
	
	public void setCook(Cook cook){
		this.cook = cook;
	}
	
	@NotNull
	@OneToMany(mappedBy="masterClass",cascade=CascadeType.ALL)
	public Collection<LearningMaterial> getLearningMaterials(){
		return learningMaterials;
	}
	public void setLearningMaterials(Collection<LearningMaterial> learningMaterials){
		this.learningMaterials = learningMaterials;
	}
	
	@NotNull
	@OneToMany
	public Collection<Actor> getActors() {
		return actors;
	}

	public void setActors(Collection<Actor> actors) {
		this.actors = actors;
	}	

}
