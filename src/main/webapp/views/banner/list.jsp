<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:choose> 
<jstl:when test="${admin || owner}">
<!-- Listing grids -->
<div class=center-text>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="banners" requestURI="${uri}" id="row">

  <!-- Attributes -->

  <spring:message code="banner.company" var="campaign" />
  <display:column property="campaign.sponsor.company" title="${campaign}"  sortable="true" />
  
  <spring:message code="banner.picture" var="picture" />
  <display:column title="${picture}">
	
	<jstl:if test="${row.picture != '' }">
		<img src="<jstl:out value="${row.picture}"/>" WIDTH=178 HEIGHT=100></img>
	</jstl:if>
  </display:column>
  
  <spring:message code="banner.costPerDisplay" var="costPerDisplay" />
  <display:column property="costPerDisplay" title="${costPerDisplay}" sortable="true" />
  
  <spring:message code="banner.currentNumberOfTimes" var="currentNumberOfTimes" />
  <display:column property="currentNumberOfTimes" title="${currentNumberOfTimes}" sortable="true" />
  
  <spring:message code="banner.maxTimesDisplayed" var="maxTimesDisplayed" />
  <display:column property="maxTimesDisplayed" title="${maxTimesDisplayed}" sortable="true" />
  
  <!--Action Links-->
  

   <display:column>
   <a href="banner/edit.do?bannerId=${row.id}">
   <spring:message code="banner.edit"/>
   </a>
   </display:column>
   </display:table>
      
   <security:authorize access="hasRole('SPONSOR')">
	<div>
		<a href="banner/create.do?campaignId=${campaignId}"> 
			<spring:message	code="banner.create" />
		</a>
	</div>
   </security:authorize>
   </div>
   
</jstl:when>
<jstl:otherwise>
<spring:message code="banner.restricted" />
</jstl:otherwise>
</jstl:choose>

<security:authorize access="hasRole('SPONSOR')">
<div>
  <a href="campaign/list.do"><spring:message code="banner.back" /></a>
</div>
</security:authorize>