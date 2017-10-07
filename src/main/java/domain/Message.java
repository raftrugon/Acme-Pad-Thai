package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;


@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity{
	
	// Attributes -------------------------------------------------------------
	
	private Date deliveryDate;
	private String subject;
	private String body;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@NotNull
	@Valid
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	// Relationships ----------------------------------------------------------
	private Folder folder;
	private Actor actorFrom;
	private Actor actorTo;
	
	@ManyToOne(optional=true)
	@NotNull
	@Valid
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	
	@ManyToOne(optional=false)
	@NotNull
	@Valid
	public Actor getActorFrom() {
		return actorFrom;
	}
	public void setActorFrom(Actor actorFrom) {
		this.actorFrom = actorFrom;
	}
	
	@ManyToOne(optional=false)
	@NotNull
	@Valid
	public Actor getActorTo() {
		return actorTo;
	}
	public void setActorTo(Actor actorTo) {
		this.actorTo = actorTo;
	}

}
