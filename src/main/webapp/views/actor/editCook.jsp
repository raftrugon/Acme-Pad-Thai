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

<form:form action="cook/edit.do"  modelAttribute="cook">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount.authorities" value="COOK"/>
	<form:hidden path="userAccount"/>
	<form:hidden path="folders"/>
	<form:hidden path="socialIdentity"/>
	<form:hidden path="masterClasses"/>

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
		
	

	<input type="submit" name="save" value="<spring:message code="actor.save" />" />
	&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="window.location.replace('profile/personalData/list.do');" />
	<br />
	
	
</form:form> 
