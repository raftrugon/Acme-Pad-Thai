<%@ page language="java" 
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="ingredient/nutritionist/editProperties.do" modelAttribute="ingredientProperties">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  
	<form:label path="name">
		<spring:message code="ingredient.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors class="error" path="name" />
	<br />
	
	<form:label path="properties">
		<spring:message code="ingredient.new.properties" />:
	</form:label>
	<form:textarea path="properties" />
	<form:errors class="error" path="properties" />
	<br />	
	
	<form:label path="ingredient">
		<spring:message code="ingredient.ingredient" />:
	</form:label>
	<select name="ingredient">
		<option value="${variableNull}"> ----</option>
	    <jstl:forEach var="item" items="${ingredients}">
	    	<jstl:choose>
	    	<jstl:when test="${item.id == ingredientProperties.ingredient.id}">
	        <option value="${item.id}" selected> ${item.name}</option>
	        </jstl:when>
	        <jstl:otherwise>
	        <option value="${item.id}"> ${item.name}</option>
	        </jstl:otherwise>
	        </jstl:choose>
	    </jstl:forEach>
	</select>
	<form:errors class="error" path="ingredient" />
	<br />
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="ingredient.commit.error" />
		</div>
	</jstl:if>
	
	
	<br />
	<input type="submit" name="save" value="<spring:message code="ingredient.save" />" />&nbsp;
		
	<jstl:if test="${ingredientProperties.id != 0}">
	   	<input type="submit" name="delete" value="<spring:message code="ingredient.delete" />" 
	   	onclick="return confirm('<spring:message code="ingredientProperties.confirm.delete" />')" /> &nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="ingredient.cancel"/>"
		onclick="javascript: window.location.replace('ingredient/nutritionist/listProperties.do')" />
		
  
</form:form>