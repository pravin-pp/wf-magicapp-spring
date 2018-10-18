<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="log" uri="http://logging.apache.org/log4j/tld/log"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid">
	
	<div class="row">
		<div class="col-sm-offset-3 col-sm-9">
			<h2 class="pageHeading">
				<span class="glyphicon glyphicon-plus"></span> 
				Add Relationship
			</h2>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-offset-3 col-sm-12">
			<form:form id="login_form" action="${pageContext.request.contextPath}/others/addrelationship" 
					   method="POST" 
					   cssClass="well form-horizontal col-sm-5" commandName="relationship">
				<c:catch>
					<spring:hasBindErrors name="relationship">
						<div class="alert alert-danger" role="alert">
							<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
							<form:errors path="*"  element="span" />
						</div>
					</spring:hasBindErrors>
				</c:catch>
				<div class="form-group">
					<label for="relationshipid" class="control-label col-sm-4">
						Relationship Name
						<i class="glyphicon glyphicon-info-sign s2b_tooltip" title="Enter Relationship Name"></i>
					</label>
					<div class="col-sm-7">
						<form:input id="relationshipid" 
									type="text" 
									path="relationName" 
									cssClass="form-control"
									placeholder="Relationship Name"></form:input>
						<form:errors path="relationName" cssClass="help-block alert-danger" element="span"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-4 col-sm-9">
						<button type="submit" class="btn btn-primary">Add</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

