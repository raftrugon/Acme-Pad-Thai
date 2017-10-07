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
<div class=center-text>
<display:table pagesize="5" class="displaytag" keepStatus="true" name="users" requestURI="user/list.do" id="row">

  <!-- Attributes -->
  

  <spring:message code="user.name" var="nameHeader" />
  <display:column title="${nameHeader}" sortable="true">
  <a href="profile/personalData/list.do?actorId=<jstl:out value="${row.id}"/>"><jstl:out value="${row.name}"/></a>
  </display:column>

  <spring:message code="user.surname" var="surnameHeader" />
  <display:column property="surname" title="${surnameHeader}" sortable="true" />
  
  <spring:message code="user.email" var="emailHeader" />
  <display:column property="email" title="${emailHeader}" sortable="true" />
  
  <spring:message code="user.address" var="addressHeader" />
  <display:column property="address" title="${addressHeader}" sortable="true" />
  
  <spring:message code="user.phone" var="phoneHeader" />
  <display:column property="phone" title="${phoneHeader}" sortable="true" />
  
  <display:column >
		<a  href="recipe/userList.do?userId=${row.id}"><spring:message code="user.recipes" /></a>
  </display:column>
  
</display:table>
</div>

<form action="user/search.do" method="post">  
	<spring:message code="user.search" />
	<input type="text" name="keyword" value="${keyword}" /><br/>
	<input type="submit" value="<spring:message code="user.find" />"/>  
</form>   