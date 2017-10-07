<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="actor/edit.do" modelAttribute="actor">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:label path="name">
		<spring:message code="actor.name" />
	</form:label>
	<form:input path="name" />
	<form:errors class="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="actor.surname" />
	</form:label>
	<form:input path="surname" />
	<form:errors class="error" path="surname" />
	<br />

	<form:label path="email">
		<spring:message code="actor.email" />
	</form:label>
	<form:input path="email" />
	<form:errors class="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="actor.phone" />
	</form:label>
	<form:input path="phone" />
	<form:errors class="error" path="phone" />
	<br />
		
	<form:label path="address">
		<spring:message code="actor.address" />
	</form:label>
	<form:input path="address" />
	<form:errors class="error" path="address" />
	<br />

	<form:label path="nick">
		<spring:message code="actor.nick" />
	</form:label>
	<form:input path="nick" />
	<form:errors class="error" path="nick" />
	<br />
	
	<form:label path="socialNetworkName">
		<spring:message code="actor.socialNetworkName" />
	</form:label>
	<form:input path="socialNetworkName" />
	<form:errors class="error" path="socialNetworkName" />
	<br />
	
	<form:label path="socialNetworkLink">
		<spring:message code="actor.socialNetworkLink" />
	</form:label>
	<form:input path="socialNetworkLink" />
	<form:errors class="error" path="socialNetworkLink" />
	<br />
	
	<form:label path="picture">
		<spring:message code="actor.picture" />
	</form:label>
	<form:input path="picture" />
	<form:errors class="error" path="picture" />
	<br />
	

	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="actor.error" />
		</div>
	</jstl:if>

	<br />
	<input type="submit" name="save"
		value="<spring:message code="actor.save"/>" />

	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel"/>"
		onclick="javascript: window.location.replace('')" />
		
	

</form:form>