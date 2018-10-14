<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.view.message.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="container">
		<br>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger">${errorMessage}</div>
		</c:if>
		<c:if test="${empty messageList}">
			<div class="alert alert-dark text-center">
				<fmt:message key="page.view.message.noresult" bundle="${pagebundle}" />
			</div>
		</c:if>
		<div style="overflow: auto; height: 50vh;">
			<c:forEach var="message" items="${messageList}">
				<h5>
					${message.from} <small><i><fmt:message key="page.view.message.posted" bundle="${pagebundle}" /> <ctl:long-date format="datetime">${message.time}</ctl:long-date></i></small>
				</h5>
				<p>${message.text}</p>
			</c:forEach>
		</div>
		<form role="form" action="${pageContext.request.contextPath}/controller" method="post">
			<div class="form-group col-xs-4">
				<input type="hidden" name="command" value="send-message" />
				<input type="hidden" name="receiver" value="${receiver}" />
				<label for="messageText" class="control-label col-xs-4"><h4>
						<fmt:message key="page.view.message.text" bundle="${pagebundle}" />
					</h4></label> <input type="text" name="messageText" class="form-control" maxlength="255" /><br>
				<button type="submit" class="btn btn-primary btn-md">
					<fmt:message key="page.view.message.send" bundle="${pagebundle}" />
				</button>
			</div>
		</form>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>
