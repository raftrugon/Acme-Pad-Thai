<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jstl:if test="${!empty banner}" >
<img src="${banner.picture}" alt="Campaign" WIDTH=356 HEIGHT=200/> <br>
</jstl:if>

<!-- Listing grid -->
<div class=center-text>
<display:table name="recipes" id="row" requestURI="recipe/contestList.do" pagesize="5" class="displaytag" >

<!-- Attributes -->

  <spring:message code="recipe.ticker" var="tickerHeader" />
  <display:column property="ticker" title="${tickerHeader}" sortable="true" />  

  <spring:message code="recipe.title" var="titleHeader" />
  <display:column property="title" title="${titleHeader}" sortable="true" /> 
  
  <spring:message code="recipe.summary" var="summaryHeader" />
  <display:column property="summary" title="${summaryHeader}" sortable="true" />
  
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
  

  	<jstl:set var="numberOfLikes" value="0" />
  	<jstl:set var="numberOfDislikes" value="0" />
  	<jstl:forEach var="item" items="${row.likes}">
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
  
  <spring:message code="recipe.categories" var="categoriesHeader" />
  <display:column title="${categoriesHeader}">
  <jstl:forEach var="item" items="${row.categories}">
  	<jstl:out value="${item.name}"/> <br>
  </jstl:forEach>
  </display:column>
  
  <spring:message code="recipe.ingredients" var="ingredientsHeader" />
  <display:column title="${ingredientsHeader}">
  <jstl:forEach var="item" items="${row.ingredientQuantities}">
  	<jstl:out value="${item.ingredient.name}"/> <br>
  </jstl:forEach>
  </display:column>
  
  <spring:message code="recipe.comments" var="commentsHeader" />
  <display:column title="${commentsHeader}">
  <jstl:forEach var="item" items="${row.comments}">
  	<jstl:out value="${item.text}"/> <br>
  </jstl:forEach>
  </display:column>
  
  <spring:message code="recipe.steps" var="stepsHeader" />
  <display:column title="${stepsHeader}">
  <jstl:forEach var="item" items="${row.steps}">
  	<jstl:out value="${item.description}"/> <br>
  </jstl:forEach>
  </display:column>
  

</display:table>
</div> 

<div>
  <a href="contest/user/list.do"><spring:message code="recipe.back" /></a>
</div>
