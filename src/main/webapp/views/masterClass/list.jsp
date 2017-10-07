<%@page language="java" 
        contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Listing grid -->

<display:table 
    name="masterClasses" 
    id="row" 
    requestURI="masterClass/list.do" 
    pagesize="5" 
    class="displaytag" >	

<!-- Attributes --> 

  <spring:message code="masterClass.title" var="rowTitle" />
  <display:column property="title" title="${rowTitle}" sortable="true" />  

  <spring:message code="masterClass.description" var="rowDescription" />
  <display:column property="description" title="${rowDescription}" sortable="true" />
  
  <spring:message code="masterClass.cook" var="rowCook" />
  <display:column title="${rowCook}" sortable="true">
  <a href="profile/personalData/list.do?actorId=<jstl:out value="${row.cook.id}"/>"><jstl:out value="${row.cook.name} ${row.cook.surname}"/></a>
  </display:column>
  
  
	<security:authorize access="isAuthenticated()">
		<display:column>
	   	<jstl:set var="contains" value="false" />
		<jstl:forEach var="item" items="${row.actors}">
		  <jstl:if test="${item.userAccount.username eq pageContext.request.userPrincipal.name || row.cook.userAccount.username eq pageContext.request.userPrincipal.name}">
		    <jstl:set var="contains" value="true" />
		  </jstl:if>
		</jstl:forEach>
		 <jstl:if test="${contains}">
		  	<a href="learningMaterial/list.do?masterClassId=${row.id}">
		  		<spring:message code="masterClass.learningMaterials" />
		  	</a>
		  
		 </jstl:if>
		 </display:column>
  	</security:authorize>

  <!-- Action links --> 
  
  <security:authorize access="isAuthenticated()">
  	 <jstl:choose> 
	 <jstl:when test="${not registered}">
	 	<display:column>
			<a href="masterClass/actor/register.do?masterClassId=${row.id}">
			  <spring:message	code="masterClass.register" />
			</a>
		</display:column>
	 </jstl:when>
	 <jstl:otherwise>
	 	<display:column>
	 		<jstl:set var="deregister" value="false" />
			<jstl:forEach var="item" items="${row.actors}">
			  <jstl:if test="${item.userAccount.username eq pageContext.request.userPrincipal.name}">
			    <jstl:set var="deregister" value="true" />
			  </jstl:if>
			</jstl:forEach>
	 		<jstl:if test="${deregister}">
		 		<a href="masterClass/actor/deregister.do?masterClassId=${row.id}">
				  <spring:message	code="masterClass.deregister" />
				</a>
			</jstl:if>
		</display:column>
	 </jstl:otherwise>
	 </jstl:choose>

  </security:authorize>
  
  <security:authorize access="hasRole('COOK')">
  	<display:column>
  		<jstl:if test="${row.cook.userAccount.username eq pageContext.request.userPrincipal.name}">
	      <a href="masterClass/cook/edit.do?masterClassId=${row.id}">
	        <spring:message code="masterClass.edit" />
	      </a>	
   		</jstl:if>
   	</display:column>
  </security:authorize>
  
  <security:authorize access="hasRole('ADMIN')">
	<display:column>
		<jstl:choose> 
		<jstl:when test="${row.isPromoted}">
		    <a href="masterClass/admin/demote.do?masterClassId=${row.id}">
		      <spring:message code="masterClass.demote" />
		    </a>
		</jstl:when>
		<jstl:otherwise>
			<a href="masterClass/admin/promote.do?masterClassId=${row.id}">
		      <spring:message code="masterClass.promote" />
		    </a>
		</jstl:otherwise>
		</jstl:choose>
	</display:column>
  </security:authorize>
 
</display:table>

<!-- Action links -->
  
<security:authorize access="hasRole('COOK')">
  <div>
    <a href="masterClass/cook/create.do"><spring:message code="masterClass.create" /></a>
  </div>
</security:authorize>