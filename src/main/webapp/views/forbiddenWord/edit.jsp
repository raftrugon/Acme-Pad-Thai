<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="forbiddenWord/edit.do" modelAttribute="forbiddenWord">
  
  <form:hidden path="id" />
  <form:hidden path="version" />
  
  <security:authorize access="hasRole('ADMIN')">
  
      <form:label path="name">
	    <spring:message code="forbiddenWord.name" />:
	  </form:label>
	  <form:input path="name" value="${name}"  />
  <form:errors cssClass="error" path="name" />
  <br />
  </security:authorize>
  
  

  
  <input type="submit" name="save" value="<spring:message code="forbiddenWord.save" />" />&nbsp;
  
  <security:authorize access="hasRole('ADMIN')">
  <jstl:if test="${forbiddenWord.id != 0}">
    <input type="submit" name="delete" value="<spring:message code="forbiddenWord.delete" />" 
    onclick="return confirm('<spring:message code="forbiddenWord.confirm.delete" />')" /> &nbsp;
  </jstl:if>
  </security:authorize>
  
  <input type="button" name="cancel" value="<spring:message code="forbiddenWord.cancel" />" 
  onclick="window.location.replace('forbiddenWord/list.do');" />
  <br />
  
  
</form:form>