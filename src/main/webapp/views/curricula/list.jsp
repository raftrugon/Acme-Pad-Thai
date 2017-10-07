<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!-- Listing grids -->
<display:table name="curricula" id="row" pagesize="5" requestURI="curricula/list.do" class="displaytag">

<!-- Attributes -->
	<spring:message code="curricula.photo" var="rowphoto" />
	<display:column title="${rowphoto}">

		<jstl:if test="${row.photo != '' }">
			<img src="<jstl:out value="${row.photo}"/>" WIDTH=178 HEIGHT=100></img>
		</jstl:if>
	</display:column>


	<spring:message code="curricula.educateSection" var="roweducateSection" />
	<display:column title="${roweducateSection}" property="educateSection" />

	<spring:message code="curricula.experienceSection" var="rowexperienceSection" />
	<display:column title="${rowexperienceSection}" property="experienceSection" />

	<spring:message code="curricula.hobbiesSection" var="rowhobbiesSection" />
	<display:column title="${rowhobbiesSection}" property="hobbiesSection" />
	
<!--Action Links-->
	<security:authorize access="hasRole('NUTRITIONIST')">
	<display:column>
		<a  href="curricula/nutritionist/edit.do?curriculaId=${row.id}"><spring:message code="curricula.edit"/></a>
	</display:column>
	</security:authorize>

  <security:authorize access="isAuthenticated()">
    <display:column>
      <a href="curricula/endorse.do?curriculaId=${row.id}">
        <spring:message code="curricula.endorse" />
      </a>
    </display:column>
  </security:authorize>
		

</display:table>

<!-- Listing grids -->
<display:table name="curriculaEndorsers" id="row" pagesize="5" requestURI="${requestURI2}" class="displaytag">

<!-- Attributes -->
	<spring:message code="curricula.endorserName" var="rowEndorserName" />
	<display:column title="${rowEndorserName}" property="endorserName" />

	<spring:message code="curricula.endorserHomepage" var="rowEndorserHomepage" />
	<display:column title="${rowEndorserHomepage}" property="endorserHomepage" />

	
</display:table>
