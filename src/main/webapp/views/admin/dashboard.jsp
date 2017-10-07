<%--
 * list.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- The minimum, the average, and the maximum number of recipes per user. -->
<p>	<span><spring:message code="admin.dashboard.c.1" />:</span> <br/>
<spring:message code="admin.dashboard.min" />:&nbsp; <jstl:out value="${minNumberOfRecipes}"></jstl:out><br/>
<spring:message code="admin.dashboard.avg" />:&nbsp; <jstl:out value="${avgNumberOfRecipes}"></jstl:out><br/>
<spring:message code="admin.dashboard.max" />:&nbsp; <jstl:out value="${maxNumberOfRecipes}"></jstl:out><br/>
</p>

<!-- The user/s who has/have authored more recipes. -->
<p>	<span><spring:message code="admin.dashboard.c.2" />:</span><br/>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="userWithMoreRecipes" requestURI="dashboard/admin/list.do" id="row">
  	<spring:message code="admin.dashboard.name" var="nameHeader" />
  	<display:column title="${nameHeader}" sortable="true">
	<a href="profile/personalData/list.do?actorId=<jstl:out value="${row.id}"/>"><jstl:out value="${row.name}"/></a>
	</display:column>
  	<spring:message code="admin.dashboard.surname" var="surnameHeader" />
  	<display:column property="surname" title="${surnameHeader}" sortable="true" />
</display:table>

<!-- The minimum, the average, and the maximum number of recipes that have qualified for a contest. -->
<p>	<span><spring:message code="admin.dashboard.c.3" />:</span> <br/>
<spring:message code="admin.dashboard.min" />:&nbsp; <jstl:out value="${minRecipesQualified}"></jstl:out><br/>
<spring:message code="admin.dashboard.avg" />:&nbsp; <jstl:out value="${avgRecipesQualified}"></jstl:out><br/>
<spring:message code="admin.dashboard.max" />:&nbsp; <jstl:out value="${maxRecipesQualified}"></jstl:out><br/>
</p>

<!-- The contest/s for which more recipes has/have qualified. -->
<p>	<span><spring:message code="admin.dashboard.c.4" />:</span><br/>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="contestsWithMoreRecipes" requestURI="dashboard/admin/list.do" id="row">
  	<spring:message code="admin.dashboard.title" var="titleHeader" />
  	<display:column property="title" title="${titleHeader}" sortable="true" />
  	<spring:message code="admin.dashboard.openingTime" var="openingTimeHeader" />
  	<display:column property="openingTime" title="${openingTimeHeader}" sortable="true" />
  	<spring:message code="admin.dashboard.closingTime" var="closingTimeHeader" />
  	<display:column property="closingTime" title="${closingTimeHeader}" sortable="true" />
</display:table>

<!-- The average and the standard deviation of number of steps per recipe. -->
<p>	<span><spring:message code="admin.dashboard.c.5" />:</span> <br/>
<spring:message code="admin.dashboard.avg" />:&nbsp; <jstl:out value="${avgNumberOfSteps}"></jstl:out><br/>
<spring:message code="admin.dashboard.std" />:&nbsp; <jstl:out value="${stdNumberOfSteps}"></jstl:out><br/>
</p>

<!-- The average and the standard deviation of number of ingredients per recipe. -->
<p>	<span><spring:message code="admin.dashboard.c.6" />:</span> <br/>
<spring:message code="admin.dashboard.avg" />:&nbsp; <jstl:out value="${avgNumberOfIngredients}"></jstl:out><br/>
<spring:message code="admin.dashboard.std" />:&nbsp; <jstl:out value="${stdNumberOfIngredients}"></jstl:out><br/>
</p>

<!-- A listing of users in descending order of popularity. -->
<p>	<span><spring:message code="admin.dashboard.c.7" />:</span><br/>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="findByPopularity" requestURI="dashboard/admin/list.do" id="row">
  	<spring:message code="admin.dashboard.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="true">
	<a href="profile/personalData/list.do?actorId=<jstl:out value="${row.id}"/>"><jstl:out value="${row.name}"/></a>
	</display:column>
  	<spring:message code="admin.dashboard.surname" var="surnameHeader" />
  	<display:column property="surname" title="${surnameHeader}" sortable="true" />
</display:table>

<!-- A listing of users in descending order regarding the average number of likes and dislikes that their recipes get. -->
<p>	<span><spring:message code="admin.dashboard.c.8" />:</span><br/>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="findByLikesAndDislikes" requestURI="dashboard/admin/list.do" id="row">
  	<spring:message code="admin.dashboard.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="true">
	<a href="profile/personalData/list.do?actorId=<jstl:out value="${row.id}"/>"><jstl:out value="${row.name}"/></a>
	</display:column>
  	<spring:message code="admin.dashboard.surname" var="surnameHeader" />
  	<display:column property="surname" title="${surnameHeader}" sortable="true" />
</display:table>

<!-- The minimum, the average, and the maximum number of campaigns per sponsor. -->
<p>	<span><spring:message code="admin.dashboard.b.1" />:</span> <br/>
<spring:message code="admin.dashboard.min" />:&nbsp; <jstl:out value="${minCampaignPerSponsor}"></jstl:out><br/>
<spring:message code="admin.dashboard.avg" />:&nbsp; <jstl:out value="${avgCampaignPerSponsor}"></jstl:out><br/>
<spring:message code="admin.dashboard.max" />:&nbsp; <jstl:out value="${maxCampaignPerSponsor}"></jstl:out><br/>
</p>

<!-- The minimum, the average, and the maximum number of active campaigns per sponsor. -->
<p>	<span><spring:message code="admin.dashboard.b.2" />:</span> <br/>
<spring:message code="admin.dashboard.min" />:&nbsp; <jstl:out value="${minOfCampaignActivesPerSponsor}"></jstl:out><br/>
<spring:message code="admin.dashboard.avg" />:&nbsp; <jstl:out value="${avgOfCampaignActivesPerSponsor}"></jstl:out><br/>
<spring:message code="admin.dashboard.max" />:&nbsp; <jstl:out value="${maxOfCampaignActivesPerSponsor}"></jstl:out><br/>
</p>

<!-- The ranking of companies according the number of campaigns that they've organised via their sponsors. -->
<p>	<span><spring:message code="admin.dashboard.b.3" />:</span><br/>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="rankingCompaniesPerNumCampaign" requestURI="dashboard/admin/list.do" id="row">
  	<spring:message code="admin.dashboard.company" var="companyHeader" />
  	<display:column title="${companyHeader}" sortable="true">
  	<jstl:out value="${row}"></jstl:out>
  	</display:column>
</display:table>

<!-- The ranking of companies according their monthly bills. -->
<p>	<span><spring:message code="admin.dashboard.b.4" />:</span><br/>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="rankingCompaniesPerBills" requestURI="dashboard/admin/list.do" id="row">
  	<spring:message code="admin.dashboard.company" var="companyHeader" />
  	<display:column title="${companyHeader}" sortable="true">
  	<jstl:out value="${row}"></jstl:out>
  	</display:column>
</display:table>

<!-- The average and the standard deviation of paid and unpaid monthly bills. -->
<p>	<span><spring:message code="admin.dashboard.b.5" />:</span> <br/>
<spring:message code="admin.dashboard.avg" />:&nbsp; <jstl:out value="${avgBillPaidandNoPaid}"></jstl:out><br/>
<spring:message code="admin.dashboard.std" />:&nbsp; <jstl:out value="${stdBillPaidandNoPaid}"></jstl:out><br/>
</p>

<!-- The sponsors who have not managed a campaign for the last three months. -->
<p>	<span><spring:message code="admin.dashboard.b.6" />:</span><br/>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="sponsorWithActiveCampaigns" requestURI="dashboard/admin/list.do" id="row">
  	<spring:message code="admin.dashboard.name" var="nameHeader" />
 	<display:column title="${nameHeader}" sortable="true">
	<a href="profile/personalData/list.do?actorId=<jstl:out value="${row.id}"/>"><jstl:out value="${row.name}"/></a>
	</display:column>
  	<spring:message code="admin.dashboard.surname" var="surnameHeader" />
  	<display:column property="surname" title="${surnameHeader}" sortable="true" />
</display:table>

<!-- The companies that have spent less than the average in their campaigns. -->
<p>	<span><spring:message code="admin.dashboard.b.7" />:</span><br/>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="companiesBelowAvg" requestURI="dashboard/admin/list.do" id="row">
  	<spring:message code="admin.dashboard.company" var="companyHeader" />
  	<display:column title="${companyHeader}" sortable="true">
  	<jstl:out value="${row}"></jstl:out>
  	</display:column>
</display:table>

<!-- The companies that have spent at least 90% the maximum amount of money that a company has spent on a campaign. -->
<p>	<span><spring:message code="admin.dashboard.b.8" />:</span><br/>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="mostExpensiveCompanies" requestURI="dashboard/admin/list.do" id="row">
  	<spring:message code="admin.dashboard.company" var="companyHeader" />
  	<display:column title="${companyHeader}" sortable="true">
  	<jstl:out value="${row}"></jstl:out>
  	</display:column>
</display:table>

<!-- The minimum, the maximum, the average, and the standard deviation of the number of master classes per cook. -->
<p>	<span><spring:message code="admin.dashboard.a.1" />:</span> <br/>
<spring:message code="admin.dashboard.min" />:&nbsp; <jstl:out value="${minMasterClassPerCook}"></jstl:out><br/>
<spring:message code="admin.dashboard.max" />:&nbsp; <jstl:out value="${maxMasterClassPerCook}"></jstl:out><br/>
<spring:message code="admin.dashboard.avg" />:&nbsp; <jstl:out value="${avgMasterClassPerCook}"></jstl:out><br/>
<spring:message code="admin.dashboard.std" />:&nbsp; <jstl:out value="${stdMasterClassPerCook}"></jstl:out>

</p>

<!-- The average number of learning materials per master class, grouped by kind of learning material -->
<p>	<span><spring:message code="admin.dashboard.a.2" />:</span> <br/>
<spring:message code="admin.dashboard.text" />:&nbsp; <jstl:out value="${averageByMasterClassGroupBy1}"></jstl:out><br/>
<spring:message code="admin.dashboard.presentation" />:&nbsp;<jstl:out value="${averageByMasterClassGroupBy2}"></jstl:out><br/>
<spring:message code="admin.dashboard.video" />:&nbsp;<jstl:out value="${averageByMasterClassGroupBy3}"></jstl:out><br/>
</p>

<!-- The number of master classes that have been promoted. -->
<p>	<span><spring:message code="admin.dashboard.a.3" />:</span> <jstl:out value="${numberOfPromoted}"></jstl:out><br/>

<!-- The listing of cooks, sorted according to the number of master classes that have been promoted. -->
<p>	<span><spring:message code="admin.dashboard.a.4" />:</span><br/>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="findAllOrderedByMasterClassesPromoted" requestURI="dashboard/admin/list.do" id="row">
  	<spring:message code="admin.dashboard.name" var="nameHeader" />
  	<display:column title="${nameHeader}" sortable="true">
	<a href="profile/personalData/list.do?actorId=<jstl:out value="${row.id}"/>"><jstl:out value="${row.name}"/></a>
	</display:column>
  	<spring:message code="admin.dashboard.surname" var="surnameHeader" />
  	<display:column property="surname" title="${surnameHeader}" sortable="true" />
</display:table>

<!-- The average number of promoted and demoted master classes per cook. -->
<p>	<span><spring:message code="admin.dashboard.a.5" />:</span> <br/>
<spring:message code="admin.dashboard.promoted" />:&nbsp; <jstl:out value="${averagePromotedAndDemoted1}"></jstl:out><br/>
<spring:message code="admin.dashboard.demoted" />:&nbsp;<jstl:out value="${averagePromotedAndDemoted2}"></jstl:out><br/>