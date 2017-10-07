package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {
	
		// Attributes -------------------------------------------------------------

		private String company;
		private CreditCard creditCard;
		
		@NotBlank
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		
		@Valid
		@NotNull
		public CreditCard getCreditCard() {
			return creditCard;
		}
		public void setCreditCard(CreditCard creditCard) {
			this.creditCard = creditCard;
		}
		

	// Relationships ----------------------------------------------------------

		private Collection<Campaign> campaigns;
		private Collection<Bill> bills;
		
		@OneToMany(mappedBy="sponsor")
		@NotNull
		public Collection<Campaign> getCampaigns(){
			return campaigns;
		}
		public void setCampaigns(Collection<Campaign> campaigns){
			this.campaigns = campaigns;
		}
		
		@OneToMany(mappedBy="sponsor")
		@NotNull
		public Collection<Bill> getBills(){
			return bills;
		}
		public void setBills(Collection<Bill> bills){
			this.bills = bills;
		}
}
