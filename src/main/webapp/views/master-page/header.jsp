<%--
 * header.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href=""><img src="images/logo.png" alt="Acme-Pad-Thai Co., Inc." /></a>
</div>

<div>
	<ul id="sample-menu-1" class="sf-menu sf-js-enabled sf-arrows">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<li>
			<a class="fNiv"> 
				<spring:message code="master.page.start" /> 
			</a>
			<ul>
				<li class="arrow"></li>
				<li><a href="recipe/list.do"><spring:message code="master.page.start.recipes"/></a></li>
				<li><a href="user/list.do"><spring:message code="master.page.start.users"/></a></li>
				<li><a href="contest/user/list.do"><spring:message code="master.page.start.contests"/></a></li>
				<li><a href="masterClass/list.do"><spring:message code="master.page.start.masterClasses"/></a></li>
				
			</ul>
		</li>
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.admin" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="category/administrator/list.do"><spring:message code="master.page.admin.categories" /></a></li>
					<li><a href="banner/list.do"><spring:message code="master.page.admin.banners" /></a></li>
					<li><a href="bill/list.do"><spring:message code="master.page.admin.bills" /></a></li>
					<li><a href="forbiddenWord/list.do"><spring:message code="master.page.admin.forbiddenWords" /></a></li>
					<li><a href="sponsor/admin/list.do"><spring:message code="master.page.admin.defaultingSponsors" /></a></li>
					<li><a href="cook/register.do"><spring:message code="master.page.admin.registerCook" /></a></li>
					<li><a href="dashboard/admin/list.do"><spring:message code="master.page.admin.dashboard" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="recipe/user/list.do"><spring:message code="master.page.user.recipes" /></a></li>
					<li><a href="recipe/recommendedRecipes/list.do"><spring:message code="master.page.actor.recommendedRecipes" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('NUTRITIONIST')">
			<li><a class="fNiv"><spring:message	code="master.page.nutritionist" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="ingredient/nutritionist/list.do"><spring:message code="master.page.nutritionist.ingredients" /></a></li>
					<li><a href="ingredient/nutritionist/listProperties.do"><spring:message code="master.page.nutritionist.ingredientProperties" /></a></li>
					<li><a href="recipe/recommendedRecipes/list.do"><spring:message code="master.page.actor.recommendedRecipes" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="bill/list.do"><spring:message code="master.page.sponsor.bills" /></a></li>
					<li><a href="campaign/list.do"><spring:message code="master.page.sponsor.campaigns" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('COOK')">
			<li><a class="fNiv"><spring:message	code="master.page.cook" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="masterClass/cook/list.do"><spring:message code="master.page.cook.masterClass" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			
			<li><a class="fNiv"> 
			<spring:message code="master.page.register" /> 
			        
			</a>
			<ul>
				<li class="arrow"></li>
				<li><a href="sponsor/register.do"><spring:message code="master.page.registerSponsor" /></a></li>
				<li><a href="user/register.do"><spring:message code="master.page.registerUser" /></a></li>
				<li><a href="nutritionist/register.do"><spring:message code="master.page.registerNutritionist" /></a></li>					
				
			</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/personalData/list.do"><spring:message code="master.page.profile.personalData" /></a></li>
					<li><a href="profile/folder/list.do"><spring:message code="master.page.profile.mail" /></a></li>								
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

