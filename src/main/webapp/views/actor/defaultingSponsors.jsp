<%@page language="java" 
        contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Listing grid -->

<display:table 
    name="sponsors" 
    id="row" 
    requestURI="sponsor/admin/list.do" 
    pagesize="5" 
    class="displaytag" >	

<!-- Attributes --> 

  <spring:message code="actor.name" var="rowName" />
  <display:column title="${rowName}" sortable="true">
  <a href="profile/personalData/list.do?actorId=<jstl:out value="${row.id}"/>"><jstl:out value="${row.name}"/></a>
  </display:column>
  
  <spring:message code="actor.surname" var="rowSurname" />
  <display:column property="surname" title="${rowSurname}" sortable="true" />  
  
  <spring:message code="actor.email" var="rowEmail" />
  <display:column property="email" title="${rowEmail}" sortable="true" />  
  
  <spring:message code="actor.phone" var="rowPhone" />
  <display:column property="phone" title="${rowPhone}" sortable="true" />  
  
  <spring:message code="actor.address" var="rowAddress" />
  <display:column property="address" title="${rowAddress}" sortable="true" />  
  
  <spring:message code="actor.company" var="rowCompany" />
  <display:column property="company" title="${rowCompany}" sortable="true" />    
 
</display:table>

<!-- Action links -->
  
<div>
  <a href="sponsor/admin/message.do"><spring:message code="actor.message" /></a>
</div>
