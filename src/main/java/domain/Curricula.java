package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity{
	
	// Attributes -------------------------------------------------------------
	
	private String photo;
	private String educateSection;
	private String experienceSection;
	private String hobbiesSection;
	
	@NotBlank
	@URL
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@NotBlank
	public String getEducateSection() {
		return educateSection;
	}
	public void setEducateSection(String educateSection) {
		this.educateSection = educateSection;
	}

	@NotBlank
	public String getExperienceSection() {
		return experienceSection;
	}
	public void setExperienceSection(String experienceSection) {
		this.experienceSection = experienceSection;
	}

	@NotBlank
	public String getHobbiesSection() {
		return hobbiesSection;
	}
	public void setHobbiesSection(String hobbiesSection) {
		this.hobbiesSection = hobbiesSection;
	}
	
	// Relationships ----------------------------------------------------------
	
	private Collection<Endorsement> endorsements;
	
	
	@OneToMany(mappedBy="curricula")
	@NotNull
	public Collection<Endorsement> getEndorsements(){
		return endorsements;
	}
	public void setEndorsements(Collection<Endorsement> endorsements){
		this.endorsements = endorsements;
	}

}
