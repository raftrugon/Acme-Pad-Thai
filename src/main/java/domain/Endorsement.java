package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity{
	
	// Attributes -------------------------------------------------------------
	
	private String name;
	private String homePage;
	
	public String getName() {
		if(getActor()!=null)
			return getActor().getName() + " " + getActor().getSurname();
		else
			return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getHomePage() {
		if(getActor()!=null)
			return "profile/personalData/list.do?actorId="+getActor().getId(); //Returns actor's profile URL
		else
			return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	
	// Relationships ----------------------------------------------------------
	private Curricula curricula;
	private Actor actor;
	
	@NotNull
	@ManyToOne(optional=false)
	@Valid
	public Curricula getCurricula(){
		return curricula;
	}
	public void setCurricula(Curricula curricula){
		this.curricula = curricula;
	}
	
	@NotNull
	@ManyToOne(optional=false)
	@Valid
	public Actor getActor(){
		return actor;
	}
	public void setActor(Actor actor){
		this.actor = actor;
	}


}
