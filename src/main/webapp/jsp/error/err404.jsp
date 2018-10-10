<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<html>
<head>
<title><fmt:message key="page.error.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
<title>Error page</title>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class = "container text-center">
		<div class="text-primary">
			<h1 class=" .font-weight-bold" style="font-size: 140">404</h1>
		</div>
		<p><fmt:message	key="page.error.text404" bundle="${pagebundle}" /></p>
		<a href="${pageContext.request.contextPath}/index.jsp"><fmt:message
					key="page.error.return" bundle="${pagebundle}" /></a>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>