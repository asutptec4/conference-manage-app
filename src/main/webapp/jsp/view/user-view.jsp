<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.view.confer.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="container">
		<form action="${pageContext.request.contextPath}/controller" id="userForm"
			method="post" role="form">
			<input type="hidden" id="userId" name="userId"/>
			<input type="hidden" id="command" name="command"/>
			<c:if test="${not empty message}">
				<div class="alert alert-success">${message}</div>
			</c:if>
			<c:choose>
				<c:when test="${not empty userList}">
					<table class="table table-striped">
						<thead>
							<tr>
								<td>ID</td>
								<td><fmt:message key="page.user.login" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.user.firstname" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.user.lastname" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.user.email" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.user.phone" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.view.user.createtime" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.view.user.isblock" bundle="${pagebundle}" /></td>
							</tr>
						</thead>
						<c:forEach var="user" items="${userList}">
							<tr>
								<td>${user.id}</td>
								<td>${user.login}</td>
								<td>${user.firstName}</td>
								<td>${user.lastName}</td>
								<td>${user.phone}</td>
								<td>${user.email}</td>
								<td><ctl:long-date format="datetime">${user.createTime}</ctl:long-date></td>
								<td>${user.blocked}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					<div class="alert alert-dark text-center">
						<fmt:message key="page.view.user.noresult" bundle="${pagebundle}" />
					</div>
				</c:otherwise>
			</c:choose>
		</form>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>