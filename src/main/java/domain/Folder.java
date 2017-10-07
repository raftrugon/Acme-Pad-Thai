package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {
	
	// Attributes -------------------------------------------------------------
	
	private String name;
	private Boolean system;
			
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getSystem() {
		return system;
	}
	
	public void setSystem(Boolean system) {
		this.system = system;
	}
	
	// Relationships ----------------------------------------------------------
	private Collection<Message> messages;
	
	@OneToMany(mappedBy="folder")
	@NotNull
	public Collection<Message> getMessages() {
		return messages;
	}
	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

}
