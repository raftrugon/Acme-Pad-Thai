package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
public class Bill extends DomainEntity {
	
	// Attributes -------------------------------------------------------------

	private Date creationDate;
	private Date issueDate;
	private Date payDate;
	
	private Double cost;
	private Collection<String> description;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Min(0)
	//@Digits(fraction = 2, integer = 9)
	public Double getCost() {
		return cost;
	}
	
	public void setCost(Double cost){
		this.cost = cost;
	}
	
	@ElementCollection
	@NotNull
	public Collection<String> getDescription(){	
		return description;
	}
	
	public void setDescription(Collection<String> description){
		this.description = description;
	}


// Relationships ----------------------------------------------------------

	private Sponsor sponsor;
	
	@ManyToOne(optional=false)
	@Valid
	@NotNull
	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
	
}
