<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<display:table name="contests" id="row" pagesize="5" requestURI="${requestURI}" class="displaytag">

	<spring:message code="contest.title" var="rowTitle" />
	<display:column title="${rowTitle}" property="title" />

	<spring:message code="contest.openingTime" var="rowOpeningTime" />
	<display:column property="openingTime" title="${rowOpeningTime}" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"/>

	<spring:message code="contest.closingTime" var="rowClosingTime" />
    <display:column property="closingTime" title="${rowClosingTime}" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}"/>
	
	
	
		<display:column>
			<li><a class="fNiv" href="recipe/contestList.do?contestId=${row.id}"><spring:message code="contest.recipes" /></a></li>
			</display:column>
			
			<display:column>
			<li><a class="fNiv" href="winnerRecipe/list.do?contestId=${row.id}"><spring:message code="contest.winnerRecipes" /></a></li>
			</display:column>
		

	
	<security:authorize access="hasRole('USER')">
	<display:column >
	<jstl:if test="${actualDate> row.openingTime && actualDate < row.closingTime}">
		<a  href="contest/user/recipes.do?contestId=${row.id}"><spring:message code="contest.qualify" /></a>	
	</jstl:if>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	<display:column>
	<jstl:if test="${fn:length(row.recipes)==0}">   
      <a href="contest/admin/edit.do?contestId=${row.id}">
        <spring:message code="contest.edit" />
      </a>
    </jstl:if>
    </display:column>
    
    <display:column>
	<jstl:if test="${actualDate>row.closingTime && empty row.winnerRecipes && !(empty row.recipes)}">   
      <a href="contest/admin/selectWinners.do?contestId=${row.id}">
        <spring:message code="contest.getWinners" />
      </a>
    </jstl:if>
    </display:column>
    </security:authorize>
    
   
	
</display:table>

<!-- Action links -->

<security:authorize access="hasRole('ADMIN')">
  <div>
    <a href="contest/admin/create.do"><spring:message code="contest.create" /></a>
  </div>
</security:authorize>
