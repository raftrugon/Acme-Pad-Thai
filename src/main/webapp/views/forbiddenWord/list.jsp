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
<display:table pagesize="5" class="displaytag" keepStatus="true" name="forbiddenWord" requestURI="forbiddenWord/list.do" id="row">

  <!-- Attributes -->


  
  <spring:message code="forbiddenWord.name" var="name" />
  <display:column property="name" title="${name}" sortable="true" />
  
  <!--Action Links-->
  <security:authorize access="hasRole('ADMIN')">
   <display:column>
   <jstl:if test="${row.name != 'viagra' && row.name != 'sex' && row.name != 'love' && row.name != 'cialis'}">
      <a href="forbiddenWord/edit.do?fwId=${row.id}" >
        <spring:message code="forbiddenWord.edit" />
      </a>
    </jstl:if>
    </display:column>
    
  </security:authorize>
       
       </display:table>
       
     <security:authorize access="hasRole('ADMIN')">
    	<div>
      		<a href="forbiddenWord/create.do" >
       			 <spring:message code="forbiddenWord.create" />
     	    </a>
     	 </div>
    </security:authorize>
   


</div>