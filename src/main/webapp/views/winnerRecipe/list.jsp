<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Listing grid -->
<div class=center-text>
<display:table name="winnerRecipes" id="row" requestURI="winnerRecipe/list.do" pagesize="5" class="displaytag" >


<!-- Attributes -->

  <spring:message code="winnerRecipe.ticker" var="tickerHeader" />
  <display:column title="${tickerHeader}" sortable="true">
  	<jstl:out value="${row.recipe.ticker}"/>
  </display:column> 

  <spring:message code="winnerRecipe.title" var="titleHeader" />
  <display:column title="${titleHeader}" sortable="true">
  	<jstl:out value="${row.recipe.title}"/>
  </display:column>  
  
  <spring:message code="winnerRecipe.summary" var="summaryHeader" />
  <display:column title="${summaryHeader}" sortable="true">
  	<jstl:out value="${row.recipe.summary}"/>
  </display:column>
  
  <spring:message code="winnerRecipe.authorDate" var="authorDateHeader" />
  <display:column title="${authorDateHeader}" sortable="true">
  	<jstl:out value="${row.recipe.authorDate}"/>
  </display:column>
  
  <spring:message code="winnerRecipe.lastUpdate" var="lastUpdateHeader" />
  <display:column title="${lastUpdateHeader}" sortable="true">
  	<jstl:out value="${row.recipe.lastUpdate}"/>
  </display:column>
  
  <spring:message code="winnerRecipe.pictures" var="picturesHeader" />
  <display:column title="${picturesHeader}">
  <jstl:forEach var="item" items="${row.recipe.pictures}">
  	<img src="${item}" alt="Picture" WIDTH=128 HEIGHT=100/> <br>
  </jstl:forEach>
  </display:column>
  
  <jstl:set var="numberOfLikes" value="0" />
  	<jstl:set var="numberOfDislikes" value="0" />
  	<jstl:forEach var="item" items="${row.recipe.likes}">
  		<jstl:choose>
  		<jstl:when test="${item.isDislike==false}">
  			<jstl:set var="numberOfLikes" value="${numberOfLikes + 1}" />
  		</jstl:when>
  		<jstl:otherwise>
  			<jstl:set var="numberOfDislikes" value="${numberOfDislikes + 1}" />
  		</jstl:otherwise>
  		</jstl:choose>
 	</jstl:forEach>
  <spring:message code="recipe.likes" var="likesHeader" />
  <display:column title="${likesHeader}" sortable="true">
  	<jstl:out value="${numberOfLikes}"/>
  </display:column>
  
  <spring:message code="recipe.dislikes" var="dislikesHeader" />
  <display:column title="${dislikesHeader}" sortable="true">
  	<jstl:out value="${numberOfDislikes}"/>
  </display:column>  
  
  <spring:message code="winnerRecipe.categories" var="categoriesHeader" />
  <display:column title="${categoriesHeader}">
  <jstl:forEach var="item" items="${row.recipe.categories}">
  	<jstl:out value="${item.name}"/> <br>
  </jstl:forEach>
  </display:column>
  
  <spring:message code="winnerRecipe.ingredients" var="ingredientsHeader" />
  <display:column title="${ingredientsHeader}">
  <jstl:forEach var="item" items="${row.recipe.ingredientQuantities}">
  	<jstl:out value="${item.ingredient.name}"/> <br>
  </jstl:forEach>
  </display:column>
  
  <spring:message code="winnerRecipe.comments" var="commentsHeader" />
  <display:column title="${commentsHeader}">
  <jstl:forEach var="item" items="${row.recipe.comments}">
  	<jstl:out value="${item.text}"/> <br>
  </jstl:forEach>
  </display:column>
  
  <spring:message code="winnerRecipe.steps" var="stepsHeader" />
  <display:column title="${stepsHeader}">
  <jstl:forEach var="item" items="${row.recipe.steps}">
  	<jstl:out value="${item.description}"/> <br>
  </jstl:forEach>
  </display:column> 


</display:table>
</div>



