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
<form:form action="masterClass/cook/edit.do" modelAttribute="masterClass">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="isPromoted" />
  <form:hidden path="cook" />
  <form:hidden path="learningMaterials" />
  <form:hidden path="actors" />
  
	<form:label path="title">
		<spring:message code="masterClass.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors class="error" path="title" />
	<br />
	
	<form:label path="description">
		<spring:message code="masterClass.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors class="error" path="description" />
	<br />	
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="masterClass.commit.error" />
		</div>
	</jstl:if>
	
	
	<br />
	<input type="submit" name="save" value="<spring:message code="masterClass.save" />" />&nbsp;
		
	<jstl:if test="${masterClass.id != 0}">
	   	<input type="submit" name="delete" value="<spring:message code="masterClass.delete" />" 
	   	onclick="return confirm('<spring:message code="masterClass.confirm.delete" />')" /> &nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="masterClass.cancel"/>"
		onclick="javascript: window.location.replace('masterClass/list.do')" />
		
  
</form:form>
</jstl:when>
<jstl:otherwise>
<spring:message code="masterClass.restricted" />
</jstl:otherwise>
</jstl:choose>