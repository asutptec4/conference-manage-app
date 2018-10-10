<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.application.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="container">
		<form action="${pageContext.request.contextPath}/controller" method="post" role="form">
			<input type="hidden" name="sectionId" value="${sectionId}" /> <input type="hidden"
				id="command" name="command" value="addApplic" />
			<label for="reportDate" class="control-label col-xs-4"> <fmt:message
					key="page.application.date" bundle="${pagebundle}" /></label> <input
				type="datetime-local" name=reportDate class="form-control" />
			<h4>
				<fmt:message key="page.application.text" bundle="${pagebundle}" />
			</h4>
			<c:choose>
				<c:when test="${not empty reportList}">
					<c:forEach var="report" items="${reportList}">
						<div class="radio">
							<label><input type="radio" name="reportId" value="${report.id}" />
								${report.name}</label>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<br>
					<div class="alert alert-dark text-center">
						<fmt:message key="page.view.report.noresult" bundle="${pagebundle}" />
					</div>
				</c:otherwise>
			</c:choose>
			<button type="submit" class="btn btn-primary btn-md">
				<fmt:message key="page.common.accept" bundle="${pagebundle}" />
			</button>
			<c:if test="${not empty errorMessage}">
				<span class="alert alert-danger">${errorMessage}</span>
			</c:if>
		</form>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>