<%@ page language="java" 
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="ingredient/nutritionist/edit.do" modelAttribute="ingredient">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="ingredientQuantities" />
  <form:hidden path="ingredientProperties" />
  
	<form:label path="name">
		<spring:message code="ingredient.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors class="error" path="name" />
	<br />
	
	<form:label path="description">
		<spring:message code="ingredient.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors class="error" path="description" />
	<br />	
	
	<form:label path="picture">
		<spring:message code="ingredient.picture" />:
	</form:label>
	<form:input path="picture" />
	<form:errors class="error" path="picture" />
	<br />
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="ingredient.commit.error" />
		</div>
	</jstl:if>
	
	
	<br />
	<input type="submit" name="save" value="<spring:message code="ingredient.save" />" />&nbsp;
		
	<jstl:if test="${ingredient.id != 0}">
	   	<input type="submit" name="delete" value="<spring:message code="ingredient.delete" />" 
	   	onclick="return confirm('<spring:message code="ingredient.confirm.delete" />')" /> &nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="ingredient.cancel"/>"
		onclick="history.back()" />
		
  
</form:form>