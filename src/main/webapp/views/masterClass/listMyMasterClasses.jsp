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
    name="masterClasses" 
    id="row" 
    requestURI="masterClass/cook/list.do" 
    pagesize="5" 
    class="displaytag" >	

<!-- Attributes --> 

  <spring:message code="masterClass.title" var="rowTitle" />
  <display:column property="title" title="${rowTitle}" sortable="true" />  

  <spring:message code="masterClass.description" var="rowDescription" />
  <display:column property="description" title="${rowDescription}" sortable="true" />
  
  
  <display:column>
  	<a href="learningMaterial/cook/list.do?masterClassId=${row.id}">
  		<spring:message code="masterClass.learningMaterials" />
  	</a>  
  </display:column>

  
 

  <!-- Action links --> 
  
  <security:authorize access="hasRole('COOK')">
	<display:column>
      <a href="masterClass/cook/edit.do?masterClassId=${row.id}">
        <spring:message code="masterClass.edit" />
      </a>
    </display:column>
  </security:authorize>
  
 
</display:table>

<!-- Action links -->
  
  <security:authorize access="hasRole('COOK')">
  <div>
    <a href="masterClass/cook/create.do"><spring:message code="masterClass.create" /></a>
  </div>
</security:authorize>