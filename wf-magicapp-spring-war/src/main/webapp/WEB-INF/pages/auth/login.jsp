<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="log" uri="http://logging.apache.org/log4j/tld/log"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid">
	
	<div class="row">
		<div class="col-sm-offset-3 col-sm-9">
			<h2 class="pageHeading">
				<span class="glyphicon glyphicon-user"></span> 
				Login
			</h2>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-offset-3 col-sm-12">
			<form:form id="login_form" action="${pageContext.request.contextPath}/auth/login" 
					   method="POST" 
					   cssClass="well form-horizontal col-sm-5">
				<c:catch>
					<c:set var="errorVar" value="${param.login_error}" />
					<c:if test="${errorVar eq 1}">
						<div class="row">
							<div class="col-sm-offset-4 col-sm-7">
									<div class="alert alert-danger">
							            <p>Username or Password Wrong</p>
									</div>
							</div>
						</div>
					</c:if>
					<c:if test="${errorVar eq 2}">
						<div class="row">
							<div class="col-sm-offset-4 col-sm-7">
									<div class="alert alert-danger">
							            <p>Username is Locked, Please contact to Admin</p>
									</div>
							</div>
						</div>
					</c:if>
					<c:if test="${errorVar eq 3}">
						<div class="row">
							<div class="col-sm-offset-4 col-sm-7">
									<div class="alert alert-danger">
							            <p>Username is Disabled, Please contact to Admin</p>
									</div>
							</div>
						</div>
					</c:if>
					<c:if test="${errorVar eq 4}">
						<div class="row">
							<div class="col-sm-offset-4 col-sm-7">
									<div class="alert alert-danger">
							            <p>Username is not Found</p>
									</div>
							</div>
						</div>
					</c:if>
				</c:catch>
				<div class="form-group">
					<label for="userid" class="control-label col-sm-4">
						Username
						<i class="glyphicon glyphicon-info-sign s2b_tooltip" title="Enter your Username here"></i>
					</label>
					<div class="col-sm-7">
						<input type="text" class="form-control" id="userid" placeholder="Username" name="username">
					</div>
				</div>
				<div class="form-group">
					<label for="pwdid" class="control-label col-sm-4">
						Password
						<i class="glyphicon glyphicon-info-sign s2b_tooltip" title="Enter your Password here"></i>
					</label>
					<div class="col-sm-7">
						<input type="password" class="form-control" id="pwdid" placeholder="Password" name="password">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-4 col-sm-9">
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

