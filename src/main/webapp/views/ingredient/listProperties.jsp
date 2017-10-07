<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Listing grid -->
<div class=center-text>
<display:table name="ingredientProperties" id="row" requestURI="ingredient/nutritionist/listProperties.do" pagesize="5" class="displaytag" >


<!-- Attributes -->

  <spring:message code="ingredient.name" var="nameHeader" />
  <display:column property="name" title="${nameHeader}" sortable="true" />  
  
  <spring:message code="ingredient.properties" var="ingredientPropertiesHeader" />
  <display:column title="${ingredientPropertiesHeader}">
  <jstl:forEach var="item" items="${row.properties}">
	<jstl:out value="${item}"/> <br>
  </jstl:forEach>
  </display:column>
  
  <spring:message code="ingredient.ingredient" var="ingredientHeader" />
  <display:column title="${ingredientHeader}" sortable="true">
  	<jstl:out value="${row.ingredient.name}"/>
  </display:column>

<!-- Action links -->
   	<security:authorize access="hasRole('NUTRITIONIST')">
  	<display:column>
      <a href="ingredient/nutritionist/editProperties.do?ingredientPropertiesId=${row.id}">
        <spring:message code="ingredient.edit" />
      </a>	
   	</display:column>
  	</security:authorize>   

</display:table>
</div>

<security:authorize access="hasRole('NUTRITIONIST')">
  <div>
    <a href="ingredient/nutritionist/createProperties.do"><spring:message code="ingredientProperties.create" /></a>
  </div>
</security:authorize>
