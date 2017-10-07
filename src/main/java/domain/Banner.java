package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;



@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity {
	
	// Attributes -------------------------------------------------------------
	
	private String picture;
	private Double costPerDisplay;
	private int maxTimesDisplayed;
	private int currentNumberOfTimes;
	
	@NotBlank
	@URL
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Min(0)
	@Digits(fraction = 2, integer = 9)
	public Double getCostPerDisplay() {
		return costPerDisplay;
	}
	public void setCostPerDisplay(Double costPerDisplay) {
		this.costPerDisplay = costPerDisplay;
	}
	
	@Min(0)
	public int getMaxTimesDisplayed() {
		return maxTimesDisplayed;
	}
	public void setMaxTimesDisplayed(int maxTimesDisplayed) {
		this.maxTimesDisplayed = maxTimesDisplayed;
	}
	
	@Min(0)
	public int getCurrentNumberOfTimes() {
		return currentNumberOfTimes;
	}
	public void setCurrentNumberOfTimes(int currentNumberOfTimes) {
		this.currentNumberOfTimes = currentNumberOfTimes;
	}
	
// Relationships ----------------------------------------------------------

	private Campaign campaign;

	@ManyToOne(optional=false)
	@NotNull
	@Valid
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	
	
}
