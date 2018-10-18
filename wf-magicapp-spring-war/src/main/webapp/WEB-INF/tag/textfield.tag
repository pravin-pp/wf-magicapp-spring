<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
<%@taglib prefix="log" uri="http://logging.apache.org/log4j/tld/log"%>

<%@ attribute name="id" required="true" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="true" %>
<%@ attribute name="cssClass" rtexprvalue="true" %>
<%@ attribute name="labelClass"  rtexprvalue="true" %>
<%@ attribute name="elementClass" rtexprvalue="true" %>
<%@ attribute name="type" required="true" rtexprvalue="true" %>
<%@ attribute name="placeHolder" required="true" rtexprvalue="true" %>

<%-- <c:if test="${empty cssClass}">
	<log:info message="T1"></log:info>
	<c:set var="cssClass" value="form-group"></c:set>
</c:if>
<c:if test="${empty elementClass}">
	<log:info message="T2"></log:info>
	<c:set var="elementClass" value="form-control"></c:set>
</c:if> --%>

<div class="${cssClass}">
	<log:info message="T3"></log:info>
	<label for="${id}" class="${labelClass}">${label}</label>
	<input type="${type}" class="${elementClass}" id="${id}" placeholder="${placeHolder}">
</div>
