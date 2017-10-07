<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="category/administrator/edit.do" modelAttribute="categoryh">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  <form:hidden path="categoriesSons" />
  <form:hidden path="recipes" />
  
 
  
  <form:label path="name">
    <spring:message code="category.name" />
  </form:label>
  <form:input path="name" />
  <form:errors cssClass="error" path="name" />
  <br />
  
  <form:label path="description">
    <spring:message code="category.description" />
  </form:label>
  <form:textarea path="description" />
  <form:errors cssClass="error" path="description" />
  <br />
  
  <form:label path="picture">
    <spring:message code="category.picture" />
  </form:label>
  <form:input path="picture" placeholder="http://example"/>
  <form:errors cssClass="error" path="picture" />
  <br />
  
  <form:label path="tags">
    <spring:message code="category.tags" />
  </form:label>
  <form:textarea path="tags" />
  <form:errors cssClass="error" path="tags" />
  <br />
  
	<form:label path="categoryFather">
	    <spring:message code="category.category" />:
  	</form:label>

	
	<select name="categoryFather">
		<option value="${emptyValue}">---</option>
	    <jstl:forEach var="item" items="${categoriesh}">
	    	<jstl:choose>
	    	<jstl:when test="${item.id == categoryh.categoryFather.id}">
	        <option value="${item.id}" selected> ${item.name}</option>
	        </jstl:when>
	        <jstl:otherwise>
	        <option value="${item.id}"> ${item.name}</option>
	        </jstl:otherwise>
	        </jstl:choose>
	    </jstl:forEach>
	</select>
    
  	
  	<form:errors cssClass="error" path="categoryFather" />
 	<br />
    
  <input type="submit" name="save" value="<spring:message code="category.save" />" />&nbsp;
  <jstl:if test="${categoryh.id != 0}">
    <input type="submit" name="delete" value="<spring:message code="category.delete" />" 
    onclick="return confirm('<spring:message code="category.confirm.delete" />')" /> &nbsp;
  </jstl:if>
  <input type="button" name="cancel" value="<spring:message code="category.cancel" />" 
  onclick="window.location.replace('category/administrator/list.do');" />
  <br />
  
  
</form:form>