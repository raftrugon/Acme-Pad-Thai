package domain;


import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class LearningMaterial extends DomainEntity{
	
	// Attributes -------------------------------------------------------------

	private String title;
	private String summary;
	private Collection<String> attachment;
	
	@Enumerated(EnumType.STRING)
	private LearningMaterialType learningMaterialType; // Body is displayed depending of the learningMaterialType
	
	private String body;
		
	@NotBlank
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	@NotBlank
	public String getSummary(){
		return summary;
	}
	
	public void setSummary(String summary){
		this.summary = summary;
	}
	
	@NotNull
	@ElementCollection
	public Collection<String> getAttachment(){
		return attachment;
	}
	
	public void setAttachment(Collection<String> attachment){
		this.attachment = attachment;
	}
	
	@Valid
	@NotNull
	public LearningMaterialType getLearningMaterialType(){
		return learningMaterialType;
	}
	
	public void setLearningMaterialType(LearningMaterialType learningMT){
		this.learningMaterialType = learningMT;
	}
	
	@NotBlank
	public String getBody(){
		return body;
	}
	
	public void setBody(String body){
		this.body = body;
	}
	
	// Relationships ----------------------------------------------------------
	private MasterClass masterClass;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public MasterClass getMasterClass(){
		return masterClass;
	}
	
	public void setMasterClass(MasterClass masterClass){
		this.masterClass = masterClass;
	}
	
}