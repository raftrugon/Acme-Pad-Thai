<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Listing grid -->

<display:table name="folders" id="row" requestURI="profile/folder/list.do"
	pagesize="5" class="displaytag">

	<!-- Attributes -->
  	<spring:message code="folder.name" var="rowName" />
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
	<security:authorize access="isAuthenticated()">
		<display:column>
			<a href="profile/message/list.do?folderId=${row.id}">
				<spring:message code="folder.message" />
			</a>
		</display:column>
  	</security:authorize>

	<!-- Action links -->
	<security:authorize access="isAuthenticated()">
		<display:column>
			<jstl:if test="${not row.system}">
				<a href="profile/folder/edit.do?folderId=${row.id}">
					<spring:message code="folder.edit" />
				</a>
			</jstl:if>
		</display:column>
  	</security:authorize>
  	
 

</display:table>

<!-- Action links -->

<security:authorize access="isAuthenticated()">
	<div>
		<a href="profile/folder/create.do"><spring:message
				code="folder.create" /></a>
	</div>
</security:authorize>

<div>
	<a href="profile/message/create.do"><spring:message
			code="folder.send" /></a>
</div>