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

<form:form action="profile/socialIdentity/register.do"  modelAttribute="socialIdentity">

	<form:hidden path="id"/>
	<form:hidden path="version"/>

	<form:label path="nick">
		<spring:message code="socialIdentity.nick" />:
	</form:label>
	<form:input path="nick" placeholder="Pedro" value="${socialIdentity.nick}"/>
	<form:errors cssClass="error" path="nick" />
	<br />

	<form:label path="socialNetworkName">
		<spring:message code="socialIdentity.socialNetworkName" />:
	</form:label>
	<form:input path="socialNetworkName" placeholder="Facebook" value="${socialIdentity.socialNetworkName}"/>
	<form:errors cssClass="error" path="socialNetworkName" />
	<br />

	<form:label path="socialNetworkLink">
		<spring:message code="socialIdentity.socialNetworkLink" />:
	</form:label>
	<form:input path="socialNetworkLink" placeholder="www.facebook.com" value="${socialIdentity.socialNetworkLink}"/>
	<form:errors cssClass="error" type="url" path="socialNetworkLink" />
	<br />

	<form:label path="picture">
		<spring:message code="socialIdentity.picture" />:
	</form:label>
	<form:input path="picture" type="url" placeholder="Introduzca la URL de la imagen" value="${socialIdentity.picture}"/>
	<form:errors cssClass="error" path="picture" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="actor.save" />" />
	&nbsp; 
	
	<jstl:if test="${socialIdentity.id != 0}">
    <input type="submit" name="delete" value="<spring:message code="socialIdentity.delete" />" 
    onclick="return confirm('<spring:message code="socialIdentity.confirm.delete" />')" /> &nbsp;
  	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="window.location.replace('profile/personalData/list.do');" />
	<br />
	
	
</form:form> 
