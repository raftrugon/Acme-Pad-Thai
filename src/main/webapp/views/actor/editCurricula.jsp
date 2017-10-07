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

<form:form action="profile/curricula/edit.do"  modelAttribute="curricula">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="endorsements"/>
	
	<form:label path="photo">
		<spring:message code="actor.photo" />:
	</form:label>
	<form:input path="photo" type="url" placeholder="Introduzca la URL de la imagen" value="${curricula.photo}"/>
	<form:errors cssClass="error" path="photo" />
	<br />

	<form:label path="educateSection">
		<spring:message code="actor.educateSection" />:
	</form:label>
	<form:textarea path="educateSection" value="${curricula.educateSection}"/>
	<form:errors cssClass="error" path="educateSection" />
	<br />

	<form:label path="experienceSection">
		<spring:message code="actor.experienceSection" />:
	</form:label>
	<form:textarea path="experienceSection" value="${curricula.experienceSection}"/>
	<form:errors cssClass="error" path="experienceSection" />
	<br />
	
	<form:label path="hobbiesSection">
		<spring:message code="actor.hobbiesSection" />:
	</form:label>
	<form:textarea path="hobbiesSection" value="${curricula.hobbiesSection}"/>
	<form:errors cssClass="error" path="hobbiesSection" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="actor.save" />" />
	&nbsp; 
	
	<jstl:if test="${curricula.id != 0}">
    <input type="submit" name="delete" value="<spring:message code="curricula.delete" />" 
    onclick="return confirm('<spring:message code="curricula.confirm.delete" />')" /> &nbsp;
  	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="window.location.replace('profile/personalData/list.do');" />
	<br />
	
	
</form:form> 
