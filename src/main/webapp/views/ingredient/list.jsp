<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Listing grid -->
<div class=center-text>
<display:table name="ingredientQuantities" id="row" requestURI="ingredient/list.do" pagesize="5" class="displaytag" >


<!-- Attributes -->

  <spring:message code="ingredient.name" var="nameHeader" />
  <display:column title="${nameHeader}" sortable="true">
  	<jstl:out value="${row.ingredient.name}"/>
  </display:column>
  
  <spring:message code="ingredient.description" var="descriptionHeader" />
  <display:column title="${descriptionHeader}" sortable="true">
  	<jstl:out value="${row.ingredient.description}"/>
  </display:column>

  <spring:message code="ingredient.picture" var="pictureHeader" />
  <display:column title="${pictureHeader}">
  <jstl:if test="${!empty row.ingredient.picture}" >
  	<img src="${row.ingredient.picture}" alt="Picture" WIDTH=128 HEIGHT=100/>
  </jstl:if>
  </display:column>
  
  <spring:message code="ingredient.measure" var="measureHeader" />
  <display:column title="${measureHeader}" sortable="true">
  	<jstl:out value="${row.measure.name}"/>
  </display:column>
  
  <spring:message code="ingredient.quantity" var="quantityHeader" />
  <display:column property="quantity" title="${quantityHeader}" sortable="true" />  
  
  <spring:message code="ingredient.properties" var="ingredientPropertiesHeader" />
  <display:column title="${ingredientPropertiesHeader}">
  <jstl:forEach var="item" items="${row.ingredient.ingredientProperties}">
  	<jstl:forEach var="item2" items="${item.properties}">
  		<jstl:out value="${item2}"/> <br>
  	</jstl:forEach>
  </jstl:forEach>
  </display:column>

<!-- Action links -->
	<security:authorize access="hasRole('USER')">
	<jstl:if test="${row.recipe.user.userAccount.username eq pageContext.request.userPrincipal.name}">
  	<display:column>
      <a href="ingredient/user/edit.do?ingredientQuantityId=${row.id}">
        <spring:message code="ingredient.user.edit" />
      </a>	
   	</display:column>
   	</jstl:if>
  	</security:authorize>  

   	<security:authorize access="hasRole('NUTRITIONIST')">
  	<display:column>
      <a href="ingredient/nutritionist/edit.do?ingredientId=${row.ingredient.id}">
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

	<security:authorize access="hasRole('USER')">
	<jstl:if test="${recipe.user.userAccount.username eq pageContext.request.userPrincipal.name}">
      <a href="ingredient/user/add.do?recipeId=${recipe.id}">
        <spring:message code="ingredient.add" />
      </a>	
   	</jstl:if>
  	</security:authorize>
  	
<div>
  <a href="recipe/list.do"><spring:message code="ingredient.back" /></a>
</div> 