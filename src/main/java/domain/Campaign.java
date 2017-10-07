package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Campaign extends DomainEntity {

	// Attributes -------------------------------------------------------------
	
	private Date startDate;
	private Date endDate;
	private Boolean starCampaign;
	
	private Double cost;
	private Integer numberOfBanners;
	

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Min(0)
	public Integer getNumberOfBanners() {
		if(getBanners()!=null)
			return getBanners().size();
		else
			return numberOfBanners;
	}
	
	public void setNumberOfBanners(Integer numberOfBanners){
		this.numberOfBanners = numberOfBanners;
	}
	
	@Min(0)
	//@Digits(fraction = 2, integer = 9)
	public Double getCost() {
			return cost;
	}
	
	public void setCost(Double cost){
		this.cost = cost;
	}

	public Boolean getStarCampaign() {
		return starCampaign;
	}
	public void setStarCampaign(Boolean starCampaign) {
		this.starCampaign = starCampaign;
	}
	
// Relationships ----------------------------------------------------------
	
	private Sponsor sponsor;
	private Collection<Banner> banners;

	@ManyToOne(optional=false)
	@NotNull
	@Valid
	public Sponsor getSponsor() {
		return sponsor;
	}
	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
	
	@OneToMany(mappedBy="campaign",cascade=CascadeType.ALL)
	@NotNull
	public Collection<Banner> getBanners() {
		return banners;
	}
	public void setBanners(Collection<Banner> banners) {
		this.banners = banners;
	}
	
	
	
	
	
	
}
