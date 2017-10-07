<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="curricula/nutritionist/edit.do" modelAttribute="curricula">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="photo">
		<spring:message code="curricula.photo" />
	</form:label>
	<form:input path="photo" />
	<form:errors class="error" path="photo" />
	<br />

	<form:label path="educateSection">
		<spring:message code="curricula.educateSection" />
	</form:label>
	<form:input path="educateSection" />
	<form:errors class="error" path="educateSection" />
	<br />

	<form:label path="experienceSection">
		<spring:message code="curricula.experienceSection" />
	</form:label>
	<form:input path="experienceSection" />
	<form:errors class="error" path="experienceSection" />
	<br />

	<form:label path="hobbiesSection">
		<spring:message code="curricula.hobbiesSection" />
	</form:label>
	<form:input path="hobbiesSection" />
	<form:errors class="error" path="hobbiesSection" />
	<br />
		

	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="curricula.error" />
		</div>
	</jstl:if>


	<br />
	<input type="submit" name="save"
		value="<spring:message code="curricula.save"/>" />
		
	<jstl:if test="${curricula.id != 0}">
   		<input type="submit" name="delete" value="<spring:message code="curricula.delete" />" 
    	onclick="return confirm('<spring:message code="curricula.confirm.delete" />')" /> &nbsp;
  	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="curricula.cancel"/>"
		onclick="javascript: window.location.replace('curricula/list.do')" />
		
</form:form>