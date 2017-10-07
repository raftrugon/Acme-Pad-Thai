<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Listing grid -->
<div class=center-text>
<display:table name="recipes" id="row" requestURI="contest/user/recipes.do" pagesize="5" class="displaytag" >

<!-- Attributes -->

  <spring:message code="contest.ticker" var="tickerHeader" />
  <display:column property="ticker" title="${tickerHeader}" sortable="true" />  

  <spring:message code="contest.title" var="titleHeader" />
  <display:column property="title" title="${titleHeader}" sortable="true" /> 
  
  <spring:message code="contest.summary" var="summaryHeader" />
  <display:column property="summary" title="${summaryHeader}" sortable="true" />
  
  <spring:message code="contest.authorDate" var="authorDateHeader" />
  <display:column property="authorDate" title="${authorDateHeader}" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"/>
  
  <spring:message code="contest.lastUpdate" var="lastUpdateHeader" />
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
<!-- Action links -->
   	<security:authorize access="hasRole('USER')">
   	<jstl:set var="qualified" value="false" />
		<jstl:forEach var="item" items="${contest.recipes}">
				
		  <jstl:if test="${item.ticker eq row.ticker}">
		    <jstl:set var="qualified" value="true" />
		  </jstl:if>
		</jstl:forEach>
	<display:column>
	<jstl:if test="${!qualified && numberOfLikes>=5  && numberOfDislikes==0 && actualDate>contest.openingTime && actualDate<contest.closingTime}">
	    <a href="contest/user/qualify.do?contestId=${contestId}&recipeId=${row.id}">
	        <spring:message code="contest.qualify" />
	    </a>	
	</jstl:if>
   	</display:column>
   	
  	</security:authorize>   

</display:table>
</div> 
<a href="contest/user/list.do"><spring:message code="contest.back" /></a>