<%@ page language="java" 
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jstl:choose> 
<jstl:when test="${registered}">
<form:form action="learningMaterial/cook/edit.do" modelAttribute="learningMaterial">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="masterClass" />
  
  	<form:label path="title">
		<spring:message code="learningMaterial.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors class="error" path="title" />
	<br />

	<form:label path="summary">
		<spring:message code="learningMaterial.summary" />:
	</form:label>
	<form:textarea path="summary" />
	<form:errors class="error" path="summary" />
	<br />	
	
	<form:label path="attachment">
		<spring:message code="learningMaterial.attachment" /><spring:message code="learningMaterial.attachment.parenthesis" />:
	</form:label>
	<form:textarea path="attachment" />
	<form:errors class="error" path="attachment" />
	<br />	
	
	<spring:message code="learningMaterial.learningMaterialType" />:&nbsp
	
	<form:label path="learningMaterialType">
		<spring:message code="learningMaterial.text" />
	</form:label>
	<form:radiobutton path="learningMaterialType" var="learningMaterialType" value="TEXT"/>
	<form:label path="learningMaterialType">
		&nbsp<spring:message code="learningMaterial.presentation" />
	</form:label>
	<form:radiobutton path="learningMaterialType" var="learningMaterialType" value="PRESENTATION"/>
	<form:label path="learningMaterialType">
		&nbsp<spring:message code="learningMaterial.video" />
	</form:label>
	<form:radiobutton path="learningMaterialType" var="learningMaterialType" value="VIDEO"/>
	<br />
	<form:label path="body">
		<spring:message code="learningMaterial.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors class="error" path="body" />
	<br />

	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="learningMaterial.commit.error" />
		</div>
	</jstl:if>


	<br />
	<input type="submit" name="save"
		value="<spring:message code="learningMaterial.save"/>" />
		
	<jstl:if test="${learningMaterial.id != 0}">
    	<input type="submit" name="delete" value="<spring:message code="learningMaterial.delete" />" 
    	onclick="return confirm('<spring:message code="learningMaterial.confirm.delete" />')" /> &nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="learningMaterial.cancel"/>"
		onclick="javascript: window.location.replace('learningMaterial/list.do?masterClassId=${masterClassId}')" />
  
</form:form>
</jstl:when>
<jstl:otherwise>
<spring:message code="learningMaterial.restricted" />
</jstl:otherwise>
</jstl:choose>