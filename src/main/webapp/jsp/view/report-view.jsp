<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.view.report.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="container">
		<form action="${pageContext.request.contextPath}/controller" id="reportForm"
			method="post" role="form">
			<input type="hidden" id="reportId" name="reportId"/>
			<input type="hidden" id="command" name="command" value="searchReportById"/>
			<c:if test="${not empty message}">
				<div class="alert alert-success">${message}</div>
			</c:if>
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>
			<c:choose>
				<c:when test="${not empty reportList}">
					<table class="table table-striped">
						<thead>
							<tr>
								<td><fmt:message key="page.view.report.name" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.view.report.desc" bundle="${pagebundle}" /></td>
							</tr>
						</thead>
						<c:forEach var="report" items="${reportList}">
							<tr>
								<td>${report.name}</td>
								<td>${report.description}</td>
								<td><button type="submit" class="btn btn-primary  btn-md"
										onclick="document.getElementById('reportId').value='${report.id}';
					              document.getElementById('reportForm').submit();">
										<fmt:message key="page.common.edit" bundle="${pagebundle}" />
									</button></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					<div class="alert alert-dark text-center">
						<fmt:message key="page.view.report.noresult" bundle="${pagebundle}" />
					</div>
				</c:otherwise>
			</c:choose>
		</form>
		<form action="${pageContext.request.contextPath}/jsp/new/report.jsp">
			<button type="submit" class="btn btn-primary  btn-md">
				<fmt:message key="page.view.report.new" bundle="${pagebundle}" />
			</button>
		</form>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>