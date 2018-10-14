<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.conference.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="container">
		<form action="${pageContext.request.contextPath}/controller" method="post" role="form">
			<c:if test="${empty command}">
				<c:set var="command" value="add-conference" />
			</c:if>
			<input type="hidden" name="command" value="${command}" />
			<c:if test="${command == 'edit-conference'}">
				<input type="hidden" name="conferenceId" value="${conference.id}" />
			</c:if>
			<h3>
				<fmt:message key="page.common.text" bundle="${pagebundle}" />
			</h3>
			<div class="form-group col-xs-4">
				<label for="conferName" class="control-label col-xs-4">
					<fmt:message key="page.view.confer.name" bundle="${pagebundle}" />
				</label>
				<input type="text" name="conferName" class="form-control" value="${conference.name}" maxlength="100" required="true" />
				
				<label for="conferStart" class="control-label col-xs-4">
					<fmt:message key="page.view.confer.startdate" bundle="${pagebundle}" />
				</label>
				<input type="datetime-local" name=conferStart class="form-control" value="<ctl:long-date format="html">${conference.startDate}</ctl:long-date>" required="true" />
				
				<label for="conferEnd" class="control-label col-xs-4">
					<fmt:message key="page.view.confer.enddate" bundle="${pagebundle}" />
				</label>
				<input type="datetime-local" name="conferEnd" class="form-control" value="<ctl:long-date format="html">${conference.endDate}</ctl:long-date>" required="true" />
				
				<label for="conferLocation" class="control-label col-xs-4">
					<fmt:message key="page.view.confer.location" bundle="${pagebundle}" />
				</label>
				<input type="text" name="conferLocation" class="form-control" value="${conference.location}" maxlength="255" required="true" />
				
				<label for="conferDesc" class="control-label col-xs-4">
					<fmt:message key="page.view.confer.desc" bundle="${pagebundle}" />
				</label>
				<input type="text" name="conferDesc" class="form-control" value="${conference.description}" maxlength="255" />
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