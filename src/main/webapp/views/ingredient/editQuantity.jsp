<%@ page language="java" 
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jstl:choose> 
<jstl:when test="${!(ingredientQuantity.recipe.readOnly)}">
<jstl:choose>
<jstl:when test="${!(empty ingredients)}" >
<form:form action="ingredient/user/edit.do" modelAttribute="ingredientQuantity">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="recipe" />  
  
    <form:label path="ingredient">
		<spring:message code="ingredient.ingredient" />:
	</form:label>
	<select name="ingredient">
		<jstl:forEach var="item" items="${ingredients}">
			<jstl:choose>
		    <jstl:when test="${item.id == ingredientQuantity.ingredient.id}">
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
	
	<form:label path="measure">
		<spring:message code="ingredient.measure" />:
	</form:label>
	<select name="measure">
	    <jstl:forEach var="item" items="${measures}">
	        <option value="${item.id}"> ${item.name}</option>
	    </jstl:forEach>
	</select>
	<form:errors class="error" path="measure" />
	<br />
	
	<form:label path="quantity">
	    <spring:message code="ingredient.quantity" />:
	</form:label>
	<form:input path="quantity" type="number" min="1.0" step="0.1" value="${ingredient.quantity}"  />
  	<form:errors cssClass="error" path="quantity" /> 
  	<br />
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="ingredient.commit.error" />
		</div>
	</jstl:if>
	
	
	<br />
	<input type="submit" name="save" value="<spring:message code="ingredient.save" />" />&nbsp;
		
	<jstl:if test="${ingredientQuantity.id != 0}">
	   	<input type="submit" name="delete" value="<spring:message code="ingredient.delete" />" 
	   	onclick="return confirm('<spring:message code="ingredient.confirm.delete" />')" /> &nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="ingredient.cancel"/>"
		onclick="history.back()" />
		
  
</form:form>
</jstl:when>
<jstl:otherwise>
<spring:message code="ingredient.add.error" />
</jstl:otherwise>
</jstl:choose>
</jstl:when>
<jstl:otherwise>
<spring:message code="recipe.restricted" />
</jstl:otherwise>
</jstl:choose>