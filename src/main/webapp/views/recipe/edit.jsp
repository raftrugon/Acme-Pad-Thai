<%@ page language="java" 
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jstl:choose> 
<jstl:when test="${owner && !(recipe.readOnly)}">
<form:form action="recipe/user/edit.do" modelAttribute="recipe">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="ticker" />
  <form:hidden path="authorDate" />
  <form:hidden path="lastUpdate" />
  <form:hidden path="readOnly" />
  <form:hidden path="user" />
  <form:hidden path="ingredientQuantities" />
  <form:hidden path="steps" />
  <form:hidden path="comments" />
  <form:hidden path="winnerRecipe" />
  <form:hidden path="likes" />
  
	<form:label path="title">
		<spring:message code="recipe.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors class="error" path="title" />
	<br />
	
	<form:label path="summary">
		<spring:message code="recipe.summary" />:
	</form:label>
	<form:textarea path="summary" />
	<form:errors class="error" path="summary" />
	<br />	
	
	<form:label path="pictures">
		<spring:message code="recipe.pictures.new" />:
	</form:label>
	<form:input path="pictures" />
	<form:errors class="error" path="pictures" />
	<br />
	
	<form:label path="categories">
	    <spring:message code="recipe.categories" />:
  	</form:label>
  	<form:select multiple="true" path="categories" items="${categories}" itemLabel="name" itemValue="id" />
  	<form:errors cssClass="error" path="categories" />
 	<br />
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="recipe.commit.error" />
		</div>
	</jstl:if>
	
	
	<br />
	<input type="submit" name="save" value="<spring:message code="recipe.save" />" />&nbsp;
		
	<jstl:if test="${recipe.id != 0}">
	   	<input type="submit" name="delete" value="<spring:message code="recipe.delete" />" 
	   	onclick="return confirm('<spring:message code="recipe.confirm.delete" />')" /> &nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="recipe.cancel"/>"
		onclick="javascript: window.location.replace('recipe/list.do')" />
		
  
</form:form>
</jstl:when>
<jstl:otherwise>
<spring:message code="recipe.restricted" />
</jstl:otherwise>
</jstl:choose>