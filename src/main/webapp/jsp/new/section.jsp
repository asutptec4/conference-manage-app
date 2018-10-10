<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.section.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="container">
		<form action="${pageContext.request.contextPath}/controller" method="post" role="form">
			<input type="hidden" name="command" value="${command}" />
			<input type="hidden" name="conferenceId" value="${conferenceId}" />
			<c:if test="${command == 'editSection'}">
				<input type="hidden" name="sectionId" value="${section.id}" />
			</c:if>
			<h3>
				<fmt:message key="page.common.text" bundle="${pagebundle}" />
			</h3>
			<div class="form-group col-xs-4">
				<label for="sectionName" class="control-label col-xs-4">
					<fmt:message key="page.view.section.name" bundle="${pagebundle}" />
				</label>
				<input type="text" name="sectionName" class="form-control" value="${section.name}" maxlength="100" required="true" />
				
				<label for="sectionDesc" class="control-label col-xs-4">
					<fmt:message key="page.view.section.desc" bundle="${pagebundle}" />
				</label>
				<input type="text" name="sectionDesc" class="form-control" value="${section.description}" maxlength="255" />
				<br>
				<button type="submit" class="btn btn-primary btn-md">
					<fmt:message key="page.common.accept" bundle="${pagebundle}" />
				</button>
				<c:if test="${not empty errorMessage}">
					<span class="alert alert-danger">${errorMessage}</span>
				</c:if>
			</div>
		</form>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>