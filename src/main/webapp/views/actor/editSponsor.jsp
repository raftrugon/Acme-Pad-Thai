<%--
 * register.jsp
 *
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="sponsor/edit.do"  modelAttribute="sponsor">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount.authorities" value="SPONSOR"/>
	<form:hidden path="folders"/>
	<form:hidden path="userAccount"/>
	<form:hidden path="socialIdentity"/>
	<form:hidden path="bills"/>
	<form:hidden path="campaigns"/>
	


	<form:label path="name">
		<spring:message code="actor.name" />:
	</form:label>
	<form:input path="name" placeholder="Pedro"/>
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="actor.surname" />:
	</form:label>
	<form:input path="surname" placeholder="Márquez"/>
	<form:errors cssClass="error" path="surname" />
	<br />

	<form:label path="email">
		<spring:message code="actor.email" />:
	</form:label>
	<form:input path="email" placeholder="pepe@gmail.com"/>
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="actor.phone" />:
	</form:label>
	<form:input path="phone" placeholder="912345678"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<form:label path="address">
		<spring:message code="actor.address" />:
	</form:label>
	<form:input path="address" placeholder="Sin número, 4"/>
	<form:errors cssClass="error" path="address" />
	<br />
	
  	<form:label path="company">
    	<spring:message code="actor.company" />:
  	</form:label>
  	<form:input path="company" required="required" placeholder="acme"/>
  	<form:errors cssClass="error" path="company" />
  	<br />

	<form:label path="creditCard.holderName">
		<spring:message code="actor.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName" placeholder="Your name" />
	<form:errors cssClass="error" path="creditCard.holderName" />
	<br />

	<form:label path="creditCard.brandName">
		<spring:message code="actor.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" placeholder="Bankia" />
	<form:errors cssClass="error" path="creditCard.brandName"  />
	<br />

	<form:label path="creditCard.number">
		<spring:message code="actor.creditCardNumber"  />:
	</form:label>
	<form:input path="creditCard.number" placeholder="insert a valid credit card number" />
	<form:errors cssClass="error" path="creditCard.number" />
	<br />

	<form:label path="creditCard.expirationMonth">
		<spring:message code="actor.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" placeholder="01~12"/>
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br />
	
	<form:label path="creditCard.expirationYear">
		<spring:message code="actor.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" placeholder="00~99"/>
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br />
	
	<form:label path="creditCard.cvvCode">
		<spring:message code="actor.cvvCode" />:
	</form:label>
	<form:input path="creditCard.cvvCode" type="number" placeholder="100~999" min="100" max="999"/>
	<form:errors cssClass="error" path="creditCard.cvvCode" />
	<br />

	<input type="submit" name="save" value="<spring:message code="actor.save" />" />
	&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="window.location.replace('profile/personalData/list.do');" />
	<br />
	
	
</form:form> 
