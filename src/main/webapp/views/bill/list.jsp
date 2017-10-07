<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jstl:choose> 
<jstl:when test="${sponsorAdmin}">
<!-- Browse his monthly bills and set them as paid. -->

<!-- Listing grids -->
<div class=center-text>
<display:table pagesize="5" class= "displaytag" keepStatus="true" name="bills" requestURI="bill/list.do" id="row">

<!-- Attributes -->

  <spring:message code="bill.sponsor" var="sponsorHeader" />
  <display:column title="${sponsorHeader}" sortable="true">
  <a href="profile/personalData/list.do?actorId=<jstl:out value="${row.sponsor.id}"/>"><jstl:out value="${row.sponsor.name} ${row.sponsor.surname}"/></a>
  </display:column>
    
  <spring:message code="bill.creationDate" var="creationDateHeader" />
  <display:column property="creationDate" title="${creationDateHeader}" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}" />
  
  <spring:message code="bill.issueDate" var="issueDateHeader" />
  <display:column property="issueDate" title="${issueDateHeader}" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}" />
  
  <spring:message code="bill.payDate" var="payDateHeader" />
  <display:column property="payDate" title="${payDateHeader}" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"  />

  <spring:message code="bill.cost" var="costHeader" />
  <display:column property="cost" title="${costHeader}" sortable="true" />
  
  <spring:message code="bill.description" var="descriptionHeader" />
  <display:column property="description" title="${descriptionHeader}" sortable="true" />


<!-- Action links -->
  <security:authorize access="hasRole('ADMIN')">
	<display:column>
	<jstl:if test="${empty row.issueDate}">
		<a href="bill/issueBill.do?billId=${row.id}">
			<spring:message	code="bill.issue"/>
		</a>
	</jstl:if>	
    </display:column>
  </security:authorize>


  <security:authorize access="hasRole('SPONSOR')">
	<display:column>
	    <jstl:if test="${empty row.payDate}">
			<a href="bill/payBill.do?billId=${row.id}">
				<spring:message	code="bill.pay"/>
			</a>
	    </jstl:if>	
    </display:column>
  </security:authorize>

</display:table>
</div>
</jstl:when>
<jstl:otherwise>
<spring:message code="bill.restricted" />
</jstl:otherwise>
</jstl:choose>