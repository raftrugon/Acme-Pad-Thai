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
    name="comments" 
    id="row" 
    requestURI="comment/list.do?recipeId=${recipeId}" 
    pagesize="5" 
    class="displaytag" >	

<!-- Attributes --> 

  <spring:message code="comment.title" var="rowTitle" />
  <display:column property="title" title="${rowTitle}" sortable="true" /> 
  
  <spring:message code="comment.text" var="rowText" />
  <display:column property="text" title="${rowText}" sortable="true" /> 
  
  <spring:message code="comment.numberOfStars" var="rowNumberOfStars" />
  <display:column property="numberOfStars" title="${rowNumberOfStars}" sortable="true" />
  
  <spring:message code="comment.date" var="rowDate" />
  <display:column property="date" title="${rowDate}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" /> 

</display:table>

<!-- Action links -->
<jstl:if test="${noAdmin}" >
  <div>
    <a href="comment/create.do?recipeId=${recipeId}"><spring:message code="comment.create" /></a>
  </div>
</jstl:if>

<div>
  <a href="recipe/list.do"><spring:message code="comment.back" /></a>
</div>