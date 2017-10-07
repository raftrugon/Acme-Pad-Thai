package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {
	
	// Attributes -------------------------------------------------------------

	private String name;
	private String surname;
	private String email;
	private String phone;
	private String address;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Pattern(regexp = "^([+](([1-9][0-9][0-9])|([1-9][0-9])|([1-9])) )?([(]\\d\\d\\d[)] )?\\d{9}$")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	// Relationships ----------------------------------------------------------

	private Collection<Folder> folders;
	private UserAccount userAccount;
	private SocialIdentity socialIdentity;

	@NotNull
	@OneToMany
	public Collection<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Collection<Folder> folders) {
		this.folders = folders;
	}
	
	@Valid
	@NotNull
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	@Valid
	@OneToOne
	public SocialIdentity getSocialIdentity() {
		return socialIdentity;
	}

	public void setSocialIdentity(SocialIdentity socialIdentity) {
		this.socialIdentity = socialIdentity;
	}

}

