<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.application.edit" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="container">
		<form action="${pageContext.request.contextPath}/controller" method="post" role="form">
			<input type="hidden" name="command" value="edit-applic" /> <input type="hidden"
				name="applicationId" value="${application.id}" /> <label for="reportDate"
				class="control-label col-xs-4"> <fmt:message key="page.application.date"
					bundle="${pagebundle}" /></label> <input type="datetime-local" name=reportDate
				class="form-control"
				value="<ctl:long-date format="html">${application.reportDate}</ctl:long-date>" />
			<c:choose>
				<c:when test="${not empty statusList}">
					<div class="form-group">
						<label for="statuses">Select list (select one):</label> <select
							class="custom-select" name="statusId">
							<c:forEach var="status" items="${statusList}">
								<option value="${status.id}">${status.name}</option>
							</c:forEach>
						</select>
					</div>
				</c:when>
				<c:otherwise>
					<br>
					<div class="alert alert-dark text-center">
						<fmt:message key="page.view.status.noresult" bundle="${pagebundle}" />
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