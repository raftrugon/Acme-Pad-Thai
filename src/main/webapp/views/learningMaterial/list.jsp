<%@page language="java" 
        contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jstl:choose> 
<jstl:when test="${registered}">
<!-- Listing grid -->
<display:table 
    name="learningMaterials" 
    id="row" 
    requestURI="learningMaterial/list.do" 
    pagesize="5" 
    class="displaytag" >	

<!-- Attributes --> 

  <spring:message code="learningMaterial.title" var="rowTitle" />
  <display:column property="title" title="${rowTitle}" sortable="true" />  

  <spring:message code="learningMaterial.summary" var="rowSummary" />
  <display:column property="summary" title="${rowSummary}" sortable="true" />
  
  <spring:message code="learningMaterial.attachment" var="rowAttachment" />
  <display:column property="attachment" title="${rowAttachment}" sortable="true" />
  
  <spring:message code="learningMaterial.learningMaterialType" var="rowLearningMaterialType" />
	
  <jstl:choose>
  	<jstl:when test="${row.learningMaterialType == 'TEXT'}">
		<display:column title="${rowLearningMaterialType}"> <spring:message code="learningMaterial.text" /> </display:column>
  	</jstl:when>
  	<jstl:when test="${row.learningMaterialType == 'PRESENTATION'}">
		<display:column title="${rowLearningMaterialType}"> <spring:message code="learningMaterial.presentation" /> </display:column>
  	</jstl:when>
  	<jstl:when test="${row.learningMaterialType == 'VIDEO'}">
		<display:column title="${rowLearningMaterialType}"> <spring:message code="learningMaterial.video" /> </display:column>
  	</jstl:when>
  </jstl:choose>

  <spring:message code="learningMaterial.body" var="rowBody" />
  <display:column property="body" title="${rowBody}" sortable="true" />
  
  <!-- Action links --> 
  
  <security:authorize access="hasRole('COOK')">
  	<jstl:if test="${row.masterClass.cook.userAccount.username eq pageContext.request.userPrincipal.name}">
		<display:column>
	      <a href="learningMaterial/cook/edit.do?learningMaterialId=${row.id}">
	        <spring:message code="learningMaterial.edit" />
	      </a>
   		</display:column>
   	</jstl:if>
  </security:authorize>
 
</display:table>

<!-- Action links -->
  
<security:authorize access="hasRole('COOK')">
	<jstl:if test="${owner}">
	  <div>
	    <a href="learningMaterial/cook/create.do?masterClassId=${masterClassId}"><spring:message code="learningMaterial.create" /></a>
	  </div>
	</jstl:if>
</security:authorize>

<div>
  <a href="masterClass/list.do"><spring:message code="learningMaterial.back" /></a>
</div>
</jstl:when>
<jstl:otherwise>
<spring:message code="learningMaterial.restricted" />
</jstl:otherwise>
</jstl:choose>
