<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jstl:choose> 
<jstl:when test="${noAdmin}">
<jstl:if test="${!empty banner}" >
<img src="${banner.picture}" alt="Campaign" WIDTH=356 HEIGHT=200/> <br>
</jstl:if>

<!-- Listing grid -->
<div class=center-text>
<display:table name="recipes" id="row" requestURI="recipe/recommendedRecipes/list.do" pagesize="5" class="displaytag" >

<!-- Attributes -->

  <spring:message code="recipe.ticker" var="tickerHeader" />
  <display:column property="ticker" title="${tickerHeader}" sortable="true" />  

  <spring:message code="recipe.title" var="titleHeader" />
  <display:column property="title" title="${titleHeader}" sortable="true" /> 
  
  <spring:message code="recipe.summary" var="summaryHeader" />
  <display:column property="summary" title="${summaryHeader}" sortable="true" />
  
  <spring:message code="recipe.user" var="userHeader" />
  <display:column title="${userHeader}" sortable="true">
    <a href="profile/personalData/list.do?actorId=<jstl:out value="${row.user.id}"/>"><jstl:out value="${row.user.name} ${row.user.surname}"/></a>
  </display:column> 
  
  <spring:message code="recipe.authorDate" var="authorDateHeader" />
  <display:column property="authorDate" title="${authorDateHeader}" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"/>
  
  <spring:message code="recipe.lastUpdate" var="lastUpdateHeader" />
  <display:column property="lastUpdate" title="${lastUpdateHeader}" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"/>
  
  <spring:message code="recipe.pictures" var="picturesHeader" />
  <display:column title="${picturesHeader}">
  <jstl:forEach var="item" items="${row.pictures}">
  	<img src="${item}" alt="Picture" WIDTH=128 HEIGHT=100/> <br>
  </jstl:forEach>
  </display:column>
  
  <spring:message code="recipe.categories" var="categoriesHeader" />
  <display:column title="${categoriesHeader}">
  <jstl:forEach var="item" items="${row.categories}">
  	<jstl:out value="${item.name}"/> <br>
  </jstl:forEach>
  </display:column>
  
  <spring:message code="recipe.ingredients" var="ingredientsHeader" />
  <display:column>
  		<a href="ingredient/list.do?recipeId=${row.id}">
 			<spring:message code="recipe.ingredients" />
 		</a>	
  </display:column>
  
  <spring:message code="recipe.comments" var="commentsHeader" />
  <display:column>
  		<a href="comment/list.do?recipeId=${row.id}">
 			<spring:message code="recipe.comments" />
 		</a>	
  </display:column>
  
  <spring:message code="recipe.steps" var="stepsHeader" />
  <display:column>
  		<a href="step/list.do?recipeId=${row.id}">
 			<spring:message code="recipe.steps" />
 		</a>	
  </display:column>
  
  <jstl:set var="hasLike" value="0" />
  <jstl:forEach var="item" items="${row.likes}">
  	<jstl:if test="${item.isDislike eq false && item.noAdmin.userAccount.username eq pageContext.request.userPrincipal.name}">
  		<jstl:set var="hasLike" value="1" />
  		<jstl:set var="id" value="${item.id}" />
  	</jstl:if>
  	<jstl:if test="${item.isDislike eq true && item.noAdmin.userAccount.username eq pageContext.request.userPrincipal.name}">
  		<jstl:set var="hasLike" value="-1" />
  		<jstl:set var="id" value="${item.id}" />
  	</jstl:if>
  </jstl:forEach>
  
  <display:column>
  	<jstl:if test="${hasLike eq 0 && !(row.user.userAccount.username eq pageContext.request.userPrincipal.name)}">
  		<a href="recipe/like.do?recipeId=${row.id}">
	  		<spring:message code="recipe.like" />
	  	</a>	
  	</jstl:if>
  	<jstl:if test="${hasLike eq 1 && !(row.user.userAccount.username eq pageContext.request.userPrincipal.name)}">
  		<a href="recipe/noLike.do?likeId=${id}">
	  		<spring:message code="recipe.noLike" />
	  	</a>	
  	</jstl:if>
  </display:column>
  
  <display:column>
  	<jstl:if test="${hasLike eq 0 && !(row.user.userAccount.username eq pageContext.request.userPrincipal.name)}">
  		<a href="recipe/dislike.do?recipeId=${row.id}">
 			<spring:message code="recipe.dislike" />
 		</a>	
  	</jstl:if>
  	<jstl:if test="${hasLike eq -1 && !(row.user.userAccount.username eq pageContext.request.userPrincipal.name)}">
  		<a href="recipe/noLike.do?likeId=${id}">
 			<spring:message code="recipe.noDislike" />
 		</a>
  	</jstl:if>
  </display:column>
    

</display:table>
</div>
</jstl:when>
<jstl:otherwise>
<spring:message code="recipe.restricted" />
</jstl:otherwise>
</jstl:choose>