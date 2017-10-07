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
<form:form action="banner/edit.do" modelAttribute="banner">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="campaign" />
  
  <security:authorize access="hasRole('SPONSOR')">
    <form:hidden path="costPerDisplay" />
  
  
      <form:label path="picture">
	    <spring:message code="banner.picture" />:
	  </form:label>
	  <form:input path="picture" type="url" value="${banner.picture}"  />
  <form:errors cssClass="error" path="picture" />
  <br />
  
      <form:label path="maxTimesDisplayed">
	    <spring:message code="banner.maxTimesDisplayed" />:
	  </form:label>
	  <form:input path="maxTimesDisplayed" type="number" min="0" value="${banner.maxTimesDisplayed}"  />
  <form:errors cssClass="error" path="maxTimesDisplayed" />
  <br />
  
  <security:authorize access="hasRole('SPONSOR')">
  <jstl:if test="${banner.id == 0}">
	<form:hidden path="currentNumberOfTimes" />
  </jstl:if>
  
  <jstl:if test="${banner.id != 0}">
  	  <form:label path="currentNumberOfTimes">
	    <spring:message code="banner.currentNumberOfTimes" />:
	  </form:label>
	  <form:input path="currentNumberOfTimes" readonly="true"/>
  <form:errors cssClass="error" path="currentNumberOfTimes" />
  </jstl:if>
  <br />
  </security:authorize>
  
  </security:authorize>
  
	<security:authorize access="hasRole('ADMIN')">
	<form:label path="picture">
		<spring:message code="banner.picture" />:
	</form:label>
	<form:input path="picture" readonly="true" />   <br />
	<form:label path="maxTimesDisplayed">
		<spring:message code="banner.maxTimesDisplayed" />:
	</form:label>
	<form:input path="maxTimesDisplayed" readonly="true"/>   <br />
	<form:label path="currentNumberOfTimes">
	    <spring:message code="banner.currentNumberOfTimes" />:
 	</form:label>
	<form:input path="currentNumberOfTimes" readonly="true"/>   <br />
    	
    	
    	
	<form:label path="costPerDisplay">
	    <spring:message code="banner.costPerDisplay" />:
	  </form:label>
	  <form:input path="costPerDisplay" type="number" min="0.0" step="0.01" value="${banner.costPerDisplay}"  />
  	<form:errors cssClass="error" path="costPerDisplay" /> <br />
	</security:authorize>

  
  <input type="submit" name="save" value="<spring:message code="banner.sponsor.save" />" />&nbsp;
  
  <security:authorize access="hasRole('SPONSOR')">
  <jstl:if test="${banner.id != 0}">
    <input type="submit" name="delete" value="<spring:message code="banner.sponsor.delete" />" 
    onclick="return confirm('<spring:message code="banner.sponsor.confirm.delete" />')" /> &nbsp;
  </jstl:if>
  </security:authorize>
  
  <input type="button" name="cancel" value="<spring:message code="banner.sponsor.cancel" />" 
  onclick="window.location.replace('banner/list.do?campaignId=${banner.campaign.id}');" />
  <br />
  
  
</form:form>
</jstl:when>
<jstl:otherwise>
<spring:message code="banner.restricted" />
</jstl:otherwise>
</jstl:choose>