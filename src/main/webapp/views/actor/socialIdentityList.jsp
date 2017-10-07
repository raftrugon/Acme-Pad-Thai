<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grids -->

<h2><spring:message code="actor.personalData" var="personalDataHeader" /> 
	<jstl:out value="${personalDataHeader}"/></h2>
<div class=center-text>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="actor" requestURI="profile/personalData/list.do" id="row">
  <spring:message code="actor.name" var="name" />
  <display:column property="name" title="${name}" sortable="true" />
  
  <spring:message code="actor.surname" var="surname" />
  <display:column property="surname" title="${surname}" sortable="true" />
  
  <spring:message code="actor.email" var="email" />
  <display:column property="email" title="${email}" sortable="true" />
  
  <spring:message code="actor.phone" var="phone" />
  <display:column property="phone" title="${phone}" sortable="true" />
  
  <spring:message code="actor.address" var="address" />
  <display:column property="address" title="${address}" sortable="true" />
  
</display:table>
</div>

<!-- Listing grids -->
<jstl:if test="${!empty socialIdentity}" >
<h2><spring:message code="actor.socialIdentity" var="socialIdentityHeader" /> 
	<jstl:out value="${socialIdentityHeader}"/></h2>
<div class=center-text>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="socialIdentity" requestURI="profile/personalData/list.do" id="row">

  <!-- Attributes -->
  <spring:message code="socialIdentity.nick" var="nick" />
  <display:column property="nick" title="${nick}" sortable="true" />
  
  <spring:message code="socialIdentity.socialNetworkName" var="socialNetworkName" />
  <display:column  property="socialNetworkName" title="${socialNetworkName}"/>
	
  <spring:message code="socialIdentity.picture" var="pictureHeader" />
  <display:column title="${pictureHeader}">
  <jstl:if test="${!empty row.picture}" >
  	<img src="${row.picture}" alt="Picture" WIDTH=128 HEIGHT=100/>
  </jstl:if>
  </display:column>
  
  <spring:message code="socialIdentity.socialNetworkLink" var="socialNetworkLink" />
  <display:column property="socialNetworkLink" title="${socialNetworkLink}" sortable="true" />
  
  
   <jstl:if test="${actor.userAccount.username eq pageContext.request.userPrincipal.name}">
    <display:column>
      <a href="profile/socialIdentity/edit.do" >
        <spring:message code="socialIdentity.edit" />
      </a>
    </display:column>
   </jstl:if>

</display:table>
</div>
<br />
</jstl:if>
<security:authorize access="isAuthenticated()">
<jstl:if test="${empty socialIdentity && actor.userAccount.username eq pageContext.request.userPrincipal.name}" >
	  <a href="profile/socialIdentity/register.do" >
        <spring:message code="socialIdentity.create" />
      </a> <br>
</jstl:if>
</security:authorize>


<jstl:if test="${esNoAdmin}">
	<jstl:choose>
	<jstl:when test="${follow}">
      <a href="profile/personalData/follow.do?actorId=${actor.id}" >
        <spring:message code="socialIdentity.follow" />
      </a>
	</jstl:when>
	<jstl:otherwise>
      <a href="profile/personalData/unfollow.do?actorId=${actor.id}" >
        <spring:message code="socialIdentity.unfollow" />
      </a>
	</jstl:otherwise>
    </jstl:choose>
</jstl:if>

<jstl:if test="${rol eq 0 and actor.userAccount.username eq pageContext.request.userPrincipal.name}" >
<a href="admin/edit.do" >
	<spring:message code="actor.edit" />
</a>
</jstl:if>
<jstl:if test="${rol eq 1 and actor.userAccount.username eq pageContext.request.userPrincipal.name}" >
<a href="user/edit.do" >
	<spring:message code="actor.edit" />
</a>
</jstl:if>
<jstl:if test="${rol eq 2 and actor.userAccount.username eq pageContext.request.userPrincipal.name}" >
<a href="nutritionist/edit.do" >
	<spring:message code="actor.edit" />
</a>
</jstl:if>
<jstl:if test="${rol eq 3 and actor.userAccount.username eq pageContext.request.userPrincipal.name}" >
<a href="sponsor/edit.do" >
	<spring:message code="actor.edit" />
</a>
</jstl:if>
<jstl:if test="${rol eq 4 and actor.userAccount.username eq pageContext.request.userPrincipal.name}" >
<a href="cook/edit.do" >
	<spring:message code="actor.edit" />
</a>
</jstl:if>

<jstl:if test="${nutritionist}">
<h2><spring:message code="actor.curricula" var="curriculaHeader" /> 
	<jstl:out value="${curriculaHeader}"/></h2>
<div class=center-text>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="curricula" requestURI="profile/personalData/list.do" id="row">
  <spring:message code="actor.photo" var="photo" />
  <display:column title="${photo}">
  <jstl:if test="${!empty row.photo}" >
  	<img src="${row.photo}" alt="Photo" WIDTH=128 HEIGHT=100/>
  </jstl:if>
  </display:column>
  
  <spring:message code="actor.educateSection" var="educateSection" />
  <display:column property="educateSection" title="${educateSection}" sortable="true" />
  
  <spring:message code="actor.experienceSection" var="experienceSection" />
  <display:column property="experienceSection" title="${experienceSection}" sortable="true" />
  
  <spring:message code="actor.hobbiesSection" var="hobbiesSection" />
  <display:column property="hobbiesSection" title="${hobbiesSection}" sortable="true" />
  
  <security:authorize access="isAuthenticated()">
  <display:column>
  	<jstl:choose>
	<jstl:when test="${endorse}">
      <a href="profile/personalData/endorse.do?curriculaId=${curricula.id}" >
        <spring:message code="curricula.endorse" />
      </a>
	</jstl:when>
	<jstl:otherwise>
      <a href="profile/personalData/noEndorse.do?curriculaId=${curricula.id}" >
        <spring:message code="curricula.noEndorse" />
      </a>
	</jstl:otherwise>
    </jstl:choose>
  </display:column>
  </security:authorize>
  
  <jstl:if test="${actor.userAccount.username eq pageContext.request.userPrincipal.name}">
    <display:column>
      <a href="profile/curricula/edit.do" >
        <spring:message code="curricula.edit" />
      </a>
    </display:column>
  </jstl:if>
</display:table>
</div>
<jstl:if test="${empty curricula && actor.userAccount.username eq pageContext.request.userPrincipal.name}" >
	  <a href="profile/curricula/create.do" >
        <spring:message code="curricula.create" />
      </a> <br>
</jstl:if>

<h2><spring:message code="actor.endorsements" var="endorsementsHeader" /> 
	<jstl:out value="${endorsementsHeader}"/></h2>
<div class=center-text>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="endorsements" requestURI="profile/personalData/list.do" id="row">
  <spring:message code="actor.name" var="name" />
  <display:column property="name" title="${name}" sortable="true" />
  
  <spring:message code="actor.homePage" var="homePage" />
  <display:column title="${homePage}" sortable="true">
  <a href="<jstl:out value="${row.homePage}"/>">${homePage}</a>
  </display:column>
</display:table>
</div>
</jstl:if>