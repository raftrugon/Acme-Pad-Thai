<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
 
 <display:table name="campaigns" id="row" requestURI="campaign/list.do" pagesize="5" class="displaytag">
 
	
 <!-- Attributes -->
  
  
  <spring:message code="campaign.startDate" var="startDate" />
	<display:column property="startDate" title="${startDate}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
  <spring:message code="campaign.endDate" var="endDate" />
  <display:column property="endDate" title="${endDate}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
  
  <spring:message code="campaign.starCampaign" var="starCampaign" />
  <jstl:choose>
  	<jstl:when test="${row.starCampaign == 'true'}">
		<display:column title="${starCampaign}" sortable="true"> <spring:message code="campaign.yes"  /> </display:column>
  	</jstl:when>
  	<jstl:when test="${row.starCampaign == 'false'}">
		<display:column title="${starCampaign}" sortable="true"> <spring:message code="campaign.no" /> </display:column>
  	</jstl:when>
  </jstl:choose>
  
  <spring:message code="campaign.cost" var="cost" />
  <display:column property="cost" title="${cost}" sortable="true" />

  <spring:message code="campaign.numberOfBanners" var="numberOfBanners" />
  <display:column property="numberOfBanners" title="${numberOfBanners}" sortable="true" />
  

  
  <!--Action Links-->
  
  	<security:authorize access="hasRole('SPONSOR')">
	<display:column>
  	<jstl:if test="${row.startDate > actualDate}">
			<a href="campaign/edit.do?campaignId=${row.id}"><spring:message code="campaign.edit" /></a>
	</jstl:if>
	</display:column>		
	</security:authorize>
		
	<security:authorize access="hasRole('SPONSOR')">
	<display:column>
      <a href="banner/list.do?campaignId=${row.id}">
        <spring:message code="campaign.banners" />
      </a>
    </display:column>
  </security:authorize>
		
		</display:table>
		
	<security:authorize access="hasRole('SPONSOR')">
	<div>
		<a href="campaign/create.do"> 
			<spring:message	code="campaign.create" />
		</a>
	</div>
	</security:authorize>
	



  