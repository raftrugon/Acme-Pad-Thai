<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Listing grid -->
<div class=center-text>
<display:table name="categoriesh" id="row" requestURI="category/administrator/list.do" pagesize="5" class="table table-striped table-hover" >

<!-- Attributes -->

  <spring:message code="category.name" var="nameHeader" />
  <display:column property="name" title="${nameHeader}" sortable="true" />
  
  <spring:message code="category.description" var="descriptionHeader" />
  <display:column property="description" title="${descriptionHeader}" sortable="true" />
  
  <spring:message code="category.picture" var="pictureHeader" />
  <display:column title="${pictureHeader}" >
  <jstl:if test="${!empty row.picture}" >
  	<img src="${row.picture}" alt="Picture" WIDTH=128 HEIGHT=100/>
  </jstl:if>
  </display:column>
  
  <spring:message code="category.tags" var="tagsHeader" />
  <display:column property="tags" title="${tagsHeader}" sortable="true" />
  
  <display:column >
      <a  href="category/administrator/listFather.do?categoryId=${row.id}"><spring:message code="category.category" /></a>
  </display:column>
	
  <display:column >
	  <a  href="category/administrator/listSon.do?categoryId1=${row.id}"><spring:message code="category.categories" /></a>
  </display:column>
  
  <security:authorize access="hasRole('ADMIN')">
    <display:column>
      <a href="category/administrator/edit.do?categoryId=${row.id}">
        <spring:message code="category.edit" />
      </a>
    </display:column>
  </security:authorize>
  

  
</display:table>
</div>
<!-- Action links -->

<security:authorize access="hasRole('ADMIN')">
 
  <div>
    <a href="category/administrator/create.do"><spring:message code="category.create" /></a>
  </div>
 
</security:authorize>

