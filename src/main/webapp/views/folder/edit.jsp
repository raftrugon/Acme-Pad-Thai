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
<form:form action="profile/folder/edit.do" modelAttribute="folder">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="system" />
  <form:hidden path="messages" />
  
	<form:label path="name">
		<spring:message code="folder.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors class="error" path="name" />
	<br />
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="folder.commit.error" />
		</div>
	</jstl:if>
	
	
	<br />
	<input type="submit" name="save" value="<spring:message code="folder.save" />" />&nbsp;
		
	<jstl:if test="${folder.id != 0}">
	   	<input type="submit" name="delete" value="<spring:message code="folder.delete" />" 
	   	onclick="return confirm('<spring:message code="folder.confirm.delete" />')" /> &nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="folder.cancel"/>"
		onclick="javascript: window.location.replace('profile/folder/list.do')" />
		
  
</form:form>
</jstl:when>
<jstl:otherwise>
<spring:message code="folder.restricted" />
</jstl:otherwise>
</jstl:choose>