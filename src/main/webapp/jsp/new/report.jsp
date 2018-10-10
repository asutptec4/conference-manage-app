<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.report.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="container">
		<form action="${pageContext.request.contextPath}/controller" method="post" role="form">
			<c:if test="${empty command}">
				<c:set var="command" value="addReport" />
			</c:if>
			<input type="hidden" name="command" value="${command}" />
			<c:if test="${command == 'editReport'}">
				<input type="hidden" name="reportId" value="${report.id}" />
			</c:if>
			<h3>
				<fmt:message key="page.common.text" bundle="${pagebundle}" />
			</h3>
			<div class="form-group col-xs-4">
				<label for="reportName" class="control-label col-xs-4"><fmt:message
						key="page.view.report.name" bundle="${pagebundle}" /></label> <input type="text"
					name="reportName" class="form-control" value="${report.name}" maxlength="200"
					required="true" /> <label for="reportDesc" class="control-label col-xs-4"><fmt:message
						key="page.view.report.desc" bundle="${pagebundle}" /></label> <input type="text"
					name="reportDesc" class="form-control" value="${report.description}" maxlength="600"
					required="true" /> <br>
				<button type="submit" class="btn btn-primary btn-md">
					<fmt:message key="page.common.accept" bundle="${pagebundle}" />
				</button>
				<c:if test="${not empty errorMessage}">
					<span class="alert alert-danger">${errorMessage}</span>
				</c:if>
			</div>
		</form>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>