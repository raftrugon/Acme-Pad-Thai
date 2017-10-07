<%@ page language="java" 
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jstl:choose> 
<jstl:when test="${owner}">
<form:form action="step/user/edit.do" modelAttribute="step">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="recipe" />
  
  	<jstl:choose>
  	<jstl:when test="${step.id==0}">
  	<form:hidden path="numberOfStep" />
  	</jstl:when>
  	<jstl:otherwise>
  	<form:label path="numberOfStep">
		<spring:message code="step.numberOfStep" />:
	</form:label>
  	<select name="numberOfStep">
	    <jstl:forEach var="item" items="${numeros}">
	    	<jstl:choose>
	    	<jstl:when test="${item == step.numberOfStep}">
	        <option value="${item}" selected> ${item}</option>
	        </jstl:when>
	        <jstl:otherwise>
	        <option value="${item}"> ${item}</option>
	        </jstl:otherwise>
	        </jstl:choose>
	    </jstl:forEach>
	</select> <br>
  	</jstl:otherwise>
  	</jstl:choose>

	<form:label path="description">
		<spring:message code="step.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors class="error" path="description" />
	<br />	
	
	<form:label path="picture">
		<spring:message code="step.picture" />:
	</form:label>
	<form:input path="picture" />
	<form:errors class="error" path="picture" />
	<br />	
	
	<form:label path="hints">
		<spring:message code="step.new.hints" />:
	</form:label>
	<form:textarea path="hints" />
	<form:errors class="error" path="hints" />
	<br />	

	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="step.commit.error" />
		</div>
	</jstl:if>


	<br />
	<input type="submit" name="save"
		value="<spring:message code="step.save"/>" />
		
	<jstl:if test="${step.id != 0}">
    	<input type="submit" name="delete" value="<spring:message code="step.delete" />" 
    	onclick="return confirm('<spring:message code="step.confirm.delete" />')" /> &nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="step.cancel"/>"
		onclick="javascript: window.location.replace('step/list.do?recipeId=${step.recipe.id}')" />
  
</form:form>
</jstl:when>
<jstl:otherwise>
<spring:message code="step.restricted" />
</jstl:otherwise>
</jstl:choose>