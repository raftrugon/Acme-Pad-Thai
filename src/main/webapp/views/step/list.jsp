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
    name="steps" 
    id="row" 
    requestURI="step/list.do" 
    pagesize="5" 
    class="displaytag" >	

<!-- Attributes --> 

  <spring:message code="step.numberOfStep" var="rowNumberOfStep" />
  <display:column property="numberOfStep" title="${rowNumberOfStep}" sortable="true" />  

  <spring:message code="step.description" var="rowDescription" />
  <display:column property="description" title="${rowDescription}" sortable="true" />
  
  <spring:message code="step.picture" var="rowPicture" />
  <display:column title="${rowPicture}" >
  <jstl:if test="${!empty row.picture}" >
  	<img src="${row.picture}" alt="Picture" WIDTH=128 HEIGHT=100/>
  </jstl:if>
  </display:column>
 
  <spring:message code="step.hints" var="rowHints" />
  <display:column title="${rowHints}">
  <jstl:forEach var="item" items="${row.hints}">
	<jstl:out value="${item}"/> <br>
  </jstl:forEach>
  </display:column>
  
  <!-- Action links --> 
  
  <security:authorize access="hasRole('USER')">
  	<jstl:if test="${row.recipe.user.userAccount.username eq pageContext.request.userPrincipal.name}">
		<display:column>
	      <a href="step/user/edit.do?stepId=${row.id}">
	        <spring:message code="step.edit" />
	      </a>
   		</display:column>
   	</jstl:if>
  </security:authorize>
 
</display:table>

<!-- Action links -->
  
<security:authorize access="hasRole('USER')">
	<jstl:if test="${owner}">
	  <div>
	    <a href="step/user/create.do?recipeId=${recipeId}"><spring:message code="step.create" /></a>
	  </div>
	</jstl:if>
</security:authorize>

<div>
  <a href="recipe/list.do"><spring:message code="step.back" /></a>
</div>
