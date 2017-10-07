package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {
	
	// Attributes -------------------------------------------------------------
	
	private String holderName;
	private String brandName;
	private String number;
	private String expirationMonth;
	private String expirationYear;
	private int cvvCode;

	@NotBlank
	public String getHolderName(){
		return holderName;
	}
	public void setHolderName(String holderName){
		this.holderName = holderName;
	}
	@NotBlank
	public String getBrandName(){
		return brandName;
	}
	public void setBrandName(String brandName){
		this.brandName = brandName;
	}
	@CreditCardNumber
	public String getNumber(){
		return number;
	}
	public void setNumber(String number){
		this.number = number;
	}
	@NotBlank
	@Pattern(regexp = "(^0[1-9]$)|(^1[0-2]$)")
	public String getExpirationMonth(){
		return expirationMonth;
	}
	public void setExpirationMonth(String expirationMonth){
		this.expirationMonth = expirationMonth;
	}
	@NotBlank
	@Pattern(regexp = "^\\d\\d$")
	public String getExpirationYear(){
		return expirationYear;
	}
	public void setExpirationYear(String expirationYear){
		this.expirationYear = expirationYear;
	}
	
	@Range(min = 100, max = 999)
	@NotNull
	public int getCvvCode(){
		return cvvCode;
	}
	public void setCvvCode(int cvvCode){
		this.cvvCode = cvvCode;
	}

}
