package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity{
	
	// Attributes -------------------------------------------------------------
	
	private String nick;
	private String socialNetworkName;
	private String socialNetworkLink;
	private String picture;
		
	@NotBlank
	@Column(unique=true)
	public String getNick(){
		return nick;
	}
	
	public void setNick(String nick){
		this.nick = nick;
	}
	
	@NotBlank
	public String getSocialNetworkName(){
		return socialNetworkName;
	}
	
	public void setSocialNetworkName(String socialNetworkName){
		this.socialNetworkName = socialNetworkName;
	}
	
	@NotBlank
	@URL
	public String getSocialNetworkLink(){
		return socialNetworkLink;
	}
	
	public void setSocialNetworkLink(String socialNetworkLink){
		this.socialNetworkLink = socialNetworkLink;
	}
	
	@URL
	public String getPicture(){
		return picture;
	}
	
	public void setPicture(String picture){
		this.picture = picture;
	}
	
}
