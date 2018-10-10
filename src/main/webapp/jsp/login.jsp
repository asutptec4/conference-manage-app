<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.login.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css" />
</head>
<body class="text-center">
	<form class="form-signin" name="loginForm" method="post"
		action="${pageContext.request.contextPath}/controller" role="form">
		<h1 class="h3 mb-3 font-weight-normal">
			<fmt:message key="page.login.sign" bundle="${pagebundle}" />
		</h1>
		<input type="hidden" name="command" value="login" /> <label for="inputLogin" class="sr-only"><fmt:message
				key="page.login.login" bundle="${pagebundle}" /></label> <input type="text" id="inputLogin"
			name="login" value="" class="form-control"
			placeholder="<fmt:message key="page.login.login" bundle="${pagebundle}" />" required autofocus />
		<label for="inputPassword" class="sr-only"><fmt:message key="page.login.pass"
				bundle="${pagebundle}" /></label> <input type="password" id="inputPassword" name="password" value=""
			class="form-control" placeholder="<fmt:message key="page.login.pass" bundle="${pagebundle}" />"
			required /> <input class="btn btn-lg btn-primary btn-block" type="submit"
			value="<fmt:message key="page.login.button" bundle="${pagebundle}" />">
		<p class="message">
			<fmt:message key="page.login.text" bundle="${pagebundle}" />
			<a href="${pageContext.request.contextPath}/jsp/new/user.jsp"><fmt:message
					key="page.login.create" bundle="${pagebundle}" /></a>
		</p>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger">${errorMessage}</div>
		</c:if>
		<p>
			<a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="page.login.return"
					bundle="${pagebundle}" /></a>
		</p>
		<hr>
		<p class="container">
			<fmt:message key="page.footer.text" bundle="${pagebundle}" />
		</p>
	</form>
</body>
</html>
