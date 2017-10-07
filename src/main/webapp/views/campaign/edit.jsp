<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="campaign/edit.do" modelAttribute="campaign">
  
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="banners"/>
	<form:hidden path="sponsor"/>
  	<form:hidden path="numberOfBanners"/>
  	<form:hidden path="cost"/>
  
      <form:label path="startDate">
	    <spring:message code="campaign.new.startDate" />:
	  </form:label>
	  <form:input path="startDate" /> 
  <form:errors cssClass="error" path="startDate" />
  <br />
      	  <form:label path="endDate">
	    <spring:message code="campaign.new.endDate" />:
	  </form:label>
	  <form:input path="endDate"  />
  <form:errors cssClass="error" path="endDate" />
  <br />
	<form:label path="starCampaign">
	<spring:message code="campaign.starCampaign" />:
	</form:label>
	<select name="starCampaign">
		<option value="true"> <spring:message code="campaign.yes" /></option>
		<option value="false"> <spring:message code="campaign.no" /></option>
	</select>
  <form:errors cssClass="error" path="starCampaign" />
  <br />
  

  
	<input type="submit" name="save"
	value="<spring:message code="campaign.save" />" />&nbsp; 
	<jstl:if test="${campaign.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="campaign.delete" />"
			onclick="return confirm('<spring:message code="campaign.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="campaign.cancel" />"
		onclick="javascript: window.location.replace('campaign/list.do')" />
	<br />
  

  
  
</form:form>