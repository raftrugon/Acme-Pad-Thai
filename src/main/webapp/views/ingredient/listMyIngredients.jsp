<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Listing grid -->
<div class=center-text>
<display:table name="ingredients" id="row" requestURI="ingredient/nutritionist/list.do" pagesize="5" class="displaytag" >


<!-- Attributes -->

  <spring:message code="ingredient.name" var="nameHeader" />
  <display:column property="name" title="${nameHeader}" sortable="true" />  
  
  <spring:message code="ingredient.description" var="descriptionHeader" />
  <display:column property="description" title="${descriptionHeader}" sortable="true" />  


  <spring:message code="ingredient.picture" var="pictureHeader" />
  <display:column title="${pictureHeader}">
  <jstl:if test="${!empty row.picture}" >
  	<img src="${row.picture}" alt="Picture" WIDTH=128 HEIGHT=100/>
  </jstl:if>
  </display:column>
  
  <spring:message code="ingredient.properties" var="ingredientPropertiesHeader" />
  <display:column title="${ingredientPropertiesHeader}">
  <jstl:forEach var="item" items="${row.ingredientProperties}">
  	<jstl:forEach var="item2" items="${item.properties}">
  		<jstl:out value="${item2}"/> <br>
  	</jstl:forEach>
  </jstl:forEach>
  </display:column>

<!-- Action links -->
   	<security:authorize access="hasRole('NUTRITIONIST')">
  	<display:column>
      <a href="ingredient/nutritionist/edit.do?ingredientId=${row.id}">
        <spring:message code="ingredient.edit" />
      </a>	
   	</display:column>
  	</security:authorize>   

</display:table>
</div>

<security:authorize access="hasRole('NUTRITIONIST')">
  <div>
    <a href="ingredient/nutritionist/create.do"><spring:message code="ingredient.create" /></a>
  </div>
</security:authorize>
