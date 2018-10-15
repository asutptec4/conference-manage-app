<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<html>
<head>
<title><fmt:message key="page.error.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
<title><fmt:message key="page.error.title" bundle="${pagebundle}" /></title>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<hr>
	<div class="container">
		<div class="alert alert-danger">
			<h3>
				<fmt:message key="page.error.text" bundle="${pagebundle}" />
			</h3>
			Request from ${pageContext.errorData.requestURI} is failed <br /> Servlet name or
			type: ${pageContext.errorData.servletName} <br /> Status code:
			${pageContext.errorData.statusCode} <br /> Exception:
			${pageContext.errorData.throwable} <br />
		</div>
		<a href="${pageContext.request.contextPath}/index.jsp"><fmt:message
				key="page.error.return" bundle="${pagebundle}" /></a>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>