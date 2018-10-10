<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.user.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
</head>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="container">
		<form action="${pageContext.request.contextPath}/controller" method="post" role="form">
			<c:if test="${empty command}">
				<c:set var="command" value="addUser" />
			</c:if>
			<input type="hidden" name="command" value="${command}" />
			<input type="hidden" name="userId" value="${user.id}" />
			<c:if test="${command == 'editUser'}">
				<input type="hidden" name="login" value="${user.login}" />
			</c:if>
			<h3><fmt:message key="page.common.text" bundle="${pagebundle}" /></h3>
			<div class="form-group col-xs-4">
				<c:if test="${command == 'addUser'}">
					<label for="login" class="control-label col-xs-4"><fmt:message key="page.user.login" bundle="${pagebundle}" /></label>
					<input type="text" pattern="([a-zA-Z][a-zA-Z0-9]{4,39})" name="login" class="form-control" value="${user.login}" maxlength="40" required="true" />
				</c:if>
				<label for="password" class="control-label col-xs-4"><fmt:message key="page.user.password" bundle="${pagebundle}" /></label>
				<input type="text" name="password" class="form-control" value="" maxlength="40" <c:if test="${command == 'addUser'}">required="true"</c:if> />
				<label for="firstName" class="control-label col-xs-4"><fmt:message key="page.user.firstname" bundle="${pagebundle}" /></label>
				<input type="text" name="firstName" class="form-control" value="${user.firstName}" maxlength="40" required="true" />
				<label for="lastName" class="control-label col-xs-4"><fmt:message key="page.user.lastname" bundle="${pagebundle}" /></label>
				<input type="text" name="lastName" class="form-control" value="${user.lastName}" maxlength="40" required="true" />
				<label for="email"	class="control-label col-xs-4"><fmt:message key="page.user.email" bundle="${pagebundle}" /></label>
				<input type="text" pattern="(\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6})" name="email" class="form-control"	value="${user.email}" maxlength="60" placeholder="alexander@gmail.com" required="true" />
				<label for="phone" class="control-label col-xs-4"><fmt:message key="page.user.phone" bundle="${pagebundle}" /></label>
				<input type="text" pattern="([+][\d]+)" name="phone"  class="form-control" value="${user.phone}" maxlength="30" required="true" />
				<br>
				<button type="submit" class="btn btn-primary btn-md"><fmt:message key="page.common.accept" bundle="${pagebundle}" /></button>
				<c:if test="${not empty errorMessage}">
					<span class="alert alert-danger" >${errorMessage}</span>
				</c:if>
			</div>
		</form>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>