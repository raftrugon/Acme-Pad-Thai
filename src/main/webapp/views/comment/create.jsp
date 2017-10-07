<%@ page language="java" 
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jstl:choose> 
<jstl:when test="${noAdmin}">
<form:form action="comment/create.do" modelAttribute="comment">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="date" />
  <form:hidden path="recipe" />
  
	<form:label path="title">
		<spring:message code="comment.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors class="error" path="title" />
	<br />
	
	<form:label path="text">
		<spring:message code="comment.text" />:
	</form:label>
	<form:textarea path="text" />
	<form:errors class="error" path="text" />
	<br />
	
	<form:label path="numberOfStars">
	<spring:message code="comment.numberOfStars" />:
	</form:label>
	<select name="numberOfStars">
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
	</select>
	<form:errors class="error" path="numberOfStars" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="comment.save" />" />&nbsp;
	
	<input type="button" name="cancel"
		value="<spring:message code="comment.cancel"/>"
		onclick="javascript: window.location.replace('comment/list.do?recipeId=${recipeId}')" />
		
  
</form:form>
</jstl:when>
<jstl:otherwise>
<spring:message code="comment.restricted" />
</jstl:otherwise>
</jstl:choose>