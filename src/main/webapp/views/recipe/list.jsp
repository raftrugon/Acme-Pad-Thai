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

<!-- Browse the catalogue of recipes grouped by their categories and navigate to their authors. -->
<!-- Search for a recipe using a single keyword that must appear verbatim in its ticker, title, or summary. -->

<!-- Group by categories -->
<form action="recipe/group.do" method="post">  
	<spring:message code="recipe.group-by" />
	<select name="categoryId" >
	    <option value="0">	<spring:message code="recipe.select" /> </option>
	     <jstl:forEach var="cat" items="${categories}">
	    <option value="${cat.id}">${cat.name }</option>
	    </jstl:forEach>
	</select>
	<input type="submit" value="<spring:message code="recipe.filter" />"/> 
</form>  

<!-- Listing grid -->
<div class=center-text>
<display:table name="recipes" id="row" requestURI="recipe/list.do" pagesize="5" class="displaytag" >


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
  
  
  <jstl:if test="${noAdmin}">
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
  </jstl:if>


<!-- Action links -->
   	<security:authorize access="hasRole('USER')">
  	<display:column>
  		<jstl:if test="${row.user.userAccount.username eq pageContext.request.userPrincipal.name}">
	      <a href="recipe/user/edit.do?recipeId=${row.id}">
	        <spring:message code="recipe.edit" />
	      </a>	
   		</jstl:if>
   	</display:column>
  	</security:authorize>   

</display:table>
</div>

<form action="recipe/search.do" method="post">  
	<spring:message code="recipe.search" />
	<input type="text" name="keyword" value="${keyword}"/><br/>
	<input type="submit" value="<spring:message code="recipe.find" />"/>  
</form>  

<jstl:if test="${!empty search}">
	<div class=center-text>
	<display:table name="search" id="row" class="displaytag" >	
  <spring:message code="recipe.ticker" var="tickerHeader" />
  <display:column property="ticker" title="${tickerHeader}" sortable="true" />  

  <spring:message code="recipe.title" var="titleHeader" />
  <display:column property="title" title="${titleHeader}" sortable="true" /> 
  
  <spring:message code="recipe.summary" var="summaryHeader" />
  <display:column property="summary" title="${summaryHeader}" sortable="true" />
  
  <spring:message code="recipe.user" var="userHeaderCook" />
  <display:column title="${userHeaderCook}" sortable="true">
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
  
  <jstl:if test="${noAdmin}">
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
  </jstl:if>
  
  <!-- Action links -->
   	<security:authorize access="hasRole('USER')">
  	<display:column>
  		<jstl:if test="${row.user.userAccount.username eq pageContext.request.userPrincipal.name}">
	      <a href="recipe/user/edit.do?recipeId=${row.id}">
	        <spring:message code="recipe.edit" />
	      </a>	
   		</jstl:if>
   	</display:column>
  	</security:authorize>   
      
      
</display:table>
</div>
</jstl:if>

<security:authorize access="hasRole('USER')">
  <div>
    <a href="recipe/user/create.do"><spring:message code="recipe.create" /></a>
  </div>
</security:authorize>
