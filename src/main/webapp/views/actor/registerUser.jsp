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

<form:form action="user/register.do"  modelAttribute="user">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount.authorities" value="USER"/>
	<form:hidden path="folders"/>
	<form:hidden path="socialIdentity"/>
	<form:hidden path="recipes"/>
	<form:hidden path="noAdmins"/>

	
	<form:label path="name">
		<spring:message code="actor.name" />:
	</form:label>
	<form:input path="name" placeholder="Pedro"/>
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="actor.surname" />:
	</form:label>
	<form:input path="surname" placeholder="M�rquez"/>
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
	<form:input path="address" placeholder="Sin n�mero, 4"/>
	<form:errors cssClass="error" path="address" />
	<br />
	
	
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
