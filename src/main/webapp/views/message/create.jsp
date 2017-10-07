<%@ page language="java" 
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="profile/message/create.do" modelAttribute="m">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="actorFrom" />
  
  
	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors class="error" path="subject" />
	<br />
	
	<form:label path="actorTo">
		<spring:message code="message.actorTo" />:
	</form:label>
	<select name="actorTo">
	    <jstl:forEach var="item" items="${actors}">
	        <option value="${item.id}"> ${item.name} ${item.surname}</option>
	    </jstl:forEach>
	</select>
	<form:errors class="error" path="actorTo" />
	<br />
	
	<form:label path="priority">
		<spring:message code="message.priority" />:
	</form:label>
	<select name="priority">
		<option value="HIGH"> <spring:message code="message.high" /></option>
		<option value="NEUTRAL"> <spring:message code="message.neutral" /></option>
		<option value="LOW"> <spring:message code="message.low" /></option>		
	</select>
	<form:errors class="error" path="priority" />
	<br />
	
	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors class="error" path="body" />
	<br />
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="message.commit.error" />
		</div>
	</jstl:if>
	
	
	<br />
	<input type="submit" name="save" value="<spring:message code="message.save" />" />&nbsp;
	
	<input type="button" name="cancel"
		value="<spring:message code="message.cancel"/>"
		onclick="history.back()" />
		
  
</form:form>