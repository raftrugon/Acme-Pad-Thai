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

<form:form action="${actionURI}"  modelAttribute="${actor}">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<security:authorize access="hasRole('ADMIN')">
	<form:hidden path="userAccount.authorities" value="COOK"/>
	</security:authorize>
	
	<security:authorize access="isAnonymous()">
	<form:label path="user">
		<spring:message code="actor.user" />:
	</form:label>
	<form:radiobutton path="userAccount.authorities" var="auth" value="USER"/>
	<form:label path="sponsor">
		<spring:message code="actor.sponsor" />:
	</form:label>
	<form:radiobutton path="userAccount.authorities" var="auth" value="SPONSOR"/>
	<form:label path="nutritionist">
		<spring:message code="actor.nutritionist" />:
	</form:label>
	<form:radiobutton path="userAccount.authorities" var="auth" value="NUTRITIONIST"/>
	<spring:message code="actor.select" />:
	<form:errors cssClass="error" path="userAccount.authorities" />
	</security:authorize>

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
		<spring:message code="actor.addres" />:
	</form:label>
	<form:input path="address" placeholder="Sin número, 4"/>
	<form:errors cssClass="error" path="address" />
	<br />
	
  	<jstl:if test="${auth==SPONSOR}">
  	
  	<form:label path="company">
    	<spring:message code="actor.company" />:
  	</form:label>
  	<form:input path="company" required="required" placeholder="acme"/>
  	<form:errors cssClass="error" path="company" />
  	<br />

	<form:label path="creditCard.holderName">
		<spring:message code="order.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors cssClass="error" path="creditCard.holderName" />
	<br />

	<form:label path="creditCard.brandName">
		<spring:message code="order.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="creditCard.brandName" />
	<br />

	<form:label path="creditCard.creditCardNumber">
		<spring:message code="order.creditCardNumber" />:
	</form:label>
	<form:input path="creditCard.creditCardNumber" />
	<form:errors cssClass="error" path="creditCard.creditCardNumber" />
	<br />

	<form:label path="creditCard.expirationMonth">
		<spring:message code="order.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" placeholder="01~12"/>
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br />
	
	<form:label path="creditCard.expirationYear">
		<spring:message code="order.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" placeholder="00~99"/>
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br />
	
	<form:label path="creditCard.cvvCode">
		<spring:message code="order.cvvCode" />:
	</form:label>
	<form:input path="creditCard.cvvCode" type="number" placeholder="100~999" min="100" max="999"/>
	<form:errors cssClass="error" path="creditCard.cvvCode" />
	<br />
  </jstl:if>
	
	<form:label path="userAccount.username">
		<spring:message code="actor.username" />:
	</form:label>
	<form:input path="userAccount.username" placeholder="username"/>
	<form:errors cssClass="error" path="userAccount.username" />
	<br />
	
	<form:label path="userAccount.password">
		<spring:message code="actor.password" />:
	</form:label>
	<form:password path="userAccount.password" placeholder="5~32"/>
	<form:errors cssClass="error" path="userAccount.password" />
	<br />

	<input type="submit" name="save" value="<spring:message code="actor.save" />" />
	&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="window.location.replace('');" />
	<br />
	
	
</form:form> 
