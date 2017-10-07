<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jstl:choose>
<jstl:when test="${owner}">
<h2><jstl:out value="${folder.name}"/></h2>
<!-- Listing grid -->
<display:table name="messages" id="row" requestURI="profile/message/list.do"
	pagesize="5" class="displaytag">

	<!-- Attributes -->
	<spring:message code="message.subject" var="rowSubject" />
 	<display:column property="subject" title="${rowSubject}" sortable="true" />
	
  	<spring:message code="message.actorFrom" var="rowActorFrom" />
 	<display:column title="${rowActorFrom}" sortable="true">
  	<a href="profile/personalData/list.do?actorId=<jstl:out value="${row.actorFrom.id}"/>"><jstl:out value="${row.actorFrom.name} ${row.actorFrom.surname}"/></a>
  	</display:column> 
 	
 	<spring:message code="message.actorTo" var="rowActorTo" />
 	 	<display:column title="${rowActorTo}" sortable="true">
  	<a href="profile/personalData/list.do?actorId=<jstl:out value="${row.actorTo.id}"/>"><jstl:out value="${row.actorTo.name} ${row.actorTo.surname}"/></a>
  	</display:column> 
 	
 	<spring:message code="message.deliveryDate" var="rowDeliveryDate" />
 	<display:column property="deliveryDate" title="${rowDeliveryDate}" sortable="true" />
 	
 	<spring:message code="message.priority" var="rowPriority" />
 	<jstl:choose>
  	<jstl:when test="${row.priority == 'HIGH'}">
		<display:column title="${rowPriority}"> <spring:message code="message.high" /> </display:column>
  	</jstl:when>
  	<jstl:when test="${row.priority == 'NEUTRAL'}">
		<display:column title="${rowPriority}"> <spring:message code="message.neutral" /> </display:column>
  	</jstl:when>
  	<jstl:when test="${row.priority == 'LOW'}">
		<display:column title="${rowPriority}"> <spring:message code="message.low" /> </display:column>
  	</jstl:when>
  	</jstl:choose>
 	
 	<spring:message code="message.body" var="rowBody" />
 	<display:column property="body" title="${rowBody}" sortable="true" />
 	
 	<jstl:set var="folderId" value="${row.folder.id}" />
 	
 	<!-- Action links -->
	<security:authorize access="isAuthenticated()">
		<display:column>
			<a href="profile/message/move.do?messageId=${row.id}">
				<spring:message code="message.move" />
			</a>
		</display:column>
  	</security:authorize>

	<!-- Action links -->
	<security:authorize access="isAuthenticated()">
		<display:column>
			<a href="profile/message/delete.do?messageId=${row.id}">
				<spring:message code="message.delete" />
			</a>
		</display:column>
  	</security:authorize>

</display:table>

<!-- Action links -->

<security:authorize access="isAuthenticated()">
	<div>
		<a href="profile/message/create.do"><spring:message
				code="message.send" /></a>
	</div>
</security:authorize>

<div>
  <a href="profile/folder/list.do?folderId=${folderId}"><spring:message code="message.back" /></a>
</div>
</jstl:when>
<jstl:otherwise>
	<spring:message code="message.restricted" />
</jstl:otherwise>
</jstl:choose>