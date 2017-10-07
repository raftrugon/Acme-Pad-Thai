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
<display:table name="folders" id="row" requestURI="profile/message/move.do"
	pagesize="5" class="displaytag">

	<!-- Attributes -->
	<spring:message code="message.name" var="rowName" />
  	<display:column title="${rowName}">
 	<jstl:choose>
  	<jstl:when test="${row.name == 'Inbox'}">
		<spring:message code="message.inbox" />
  	</jstl:when>
  	<jstl:when test="${row.name == 'Outbox'}">
		<spring:message code="message.outbox" />
  	</jstl:when>
  	<jstl:when test="${row.name == 'Trashbox'}">
		<spring:message code="message.trashbox" />
  	</jstl:when>
  	<jstl:when test="${row.name == 'Spambox'}">
		<spring:message code="message.spambox" />
  	</jstl:when>
  	<jstl:otherwise>
  		<jstl:out value="${row.name}" />	
  	</jstl:otherwise>
  	</jstl:choose>
  	</display:column>
 	 	
 	<!-- Action links -->
	<display:column>
		<a href="profile/message/moveAction.do?messageId=${messageId}&folderId=${row.id}">
			<spring:message code="message.move" />
		</a>
	</display:column>
	
</display:table>
  

<!-- Action links -->

<div>
  <a href="javascript:history.back()"><spring:message code="message.back" /></a>
</div>
</jstl:when>
<jstl:otherwise>
<spring:message code="folder.restricted" />
</jstl:otherwise>
</jstl:choose>