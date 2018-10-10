<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.view.applic.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="container">
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger">${errorMessage}</div>
		</c:if>
		<form action="${pageContext.request.contextPath}/controller" id="applicForm"
			method="post" role="form">
			<input type="hidden" id="applicationId" name="applicationId" /> <input type="hidden"
				id="sectionId" name="sectionId" /> <input type="hidden" id="command" name="command" />
			<c:choose>
				<c:when test="${not empty applicInfoList}">
					<table class="table table-striped">
						<thead>
							<tr>
								<td>ID</td>
								<td><fmt:message key="page.view.confer.name" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.view.section.name" bundle="${pagebundle}" /></td>
								<c:if test="${role == 'ADMIN' }">
									<td><fmt:message key="page.view.applic.user" bundle="${pagebundle}" /></td>
								</c:if>
								<td><fmt:message key="page.view.report.name" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.view.applic.date" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.view.applic.status" bundle="${pagebundle}" /></td>
								<td></td>
							</tr>
						</thead>
						<c:forEach var="applic" items="${applicInfoList}">
							<tr>
								<td>${applic.id}</td>
								<td>${applic.conferenceName}</td>
								<td>${applic.sectionName}</td>
								<c:if test="${role == 'ADMIN' }">
									<td>${applic.userName}</td>
								</c:if>
								<td>${applic.reportName}</td>
								<td><ctl:long-date format="datetime">${applic.reportDate}</ctl:long-date></td>
								<td>${applic.status.name}</td>
								<c:if test="${role == 'USER'}">
									<td>
										<button type="submit" class="btn btn-primary  btn-md"
											onclick="document.getElementById('applicationId').value='${applic.id}';
											document.getElementById('command').value='removeApplic';
											document.getElementById('applicForm').submit();">
											<fmt:message key="page.view.applic.remove" bundle="${pagebundle}" />
										</button>
									</td>
								</c:if>
								<c:if test="${role == 'ADMIN'}">
									<td>
										<button type="submit" class="btn btn-primary  btn-md"
											onclick="document.getElementById('applicationId').value='${applic.id}';
											document.getElementById('command').value='searchApplication';
											document.getElementById('applicForm').submit();">
											<fmt:message key="page.view.applic.change" bundle="${pagebundle}" />
										</button>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					<div class="alert alert-dark text-center">
						<fmt:message key="page.view.applic.noresult" bundle="${pagebundle}" />
					</div>
				</c:otherwise>
			</c:choose>
		</form>
		<c:if test="${role == 'USER' }">
			<a class="btn btn-primary  btn-md"
				href="${pageContext.request.contextPath}/controller?command=searchConfer"> <fmt:message
					key="page.view.applic.new" bundle="${pagebundle}" /></a>
		</c:if>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>