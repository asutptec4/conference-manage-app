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
		<c:if test="${role == 'ADMIN'}">
			<br>
			<form action="${pageContext.request.contextPath}/controller" method="get" role="form">
				<input type="hidden" name="command" value="search-confer">
				<div class="form-group col-xs-5">
					<input type="text" name="searchKey" class="form-control"
						placeholder="<fmt:message key="page.view.confer.search" bundle="${pagebundle}" />" />
				</div>
				<button type="submit" class="btn btn-primary">
					<span class="fas fa-search"></span> <fmt:message key="page.common.search" bundle="${pagebundle}" />
				</button>
			</form>
			<br>
		</c:if>
		<form action="${pageContext.request.contextPath}/controller" id="conferForm"
			method="post" role="form">
			<input type="hidden" id="conferenceId" name="conferenceId"/>
			<input type="hidden" id="sectionId" name="sectionId"/>
			<input type="hidden" id="command" name="command"/>
			<c:if test="${not empty message}">
				<div class="alert alert-success">${message}</div>
			</c:if>
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>
			<c:choose>
				<c:when test="${not empty conferenceList}">
					<table class="table table-striped">
						<thead>
							<tr>
								<td><fmt:message key="page.view.confer.name" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.view.confer.desc" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.view.confer.location" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.view.confer.startdate" bundle="${pagebundle}" /></td>
								<td><fmt:message key="page.view.confer.enddate" bundle="${pagebundle}" /></td>
								<td></td>
							</tr>
						</thead>
						<c:forEach var="conference" items="${conferenceList}">
							<tr>
								<td>${conference.name}</td>
								<td>${conference.description}</td>
								<td>${conference.location}</td>
								<td><ctl:long-date format="date">${conference.startDate}</ctl:long-date></td>
								<td><ctl:long-date format="date">${conference.endDate}</ctl:long-date></td>
								<c:if test="${role == 'ADMIN'}">
									<td>
										<div class="btn-group" role="group">
											<button type="submit" class="btn btn-primary  btn-md"
												onclick="document.getElementById('conferenceId').value='${conference.id}';
												document.getElementById('command').value='search-conference-byid';
							              		document.getElementById('conferForm').submit();">
												<fmt:message key="page.common.edit" bundle="${pagebundle}" />
											</button> 
											<button type="submit" class="btn btn-primary  btn-md"
												onclick="document.getElementById('conferenceId').value='${conference.id}';
												document.getElementById('command').value='delete-conference';
							              		document.getElementById('conferForm').submit();">
												<fmt:message key="page.common.delete" bundle="${pagebundle}" />
											</button>
										</div>
									</td>
								</c:if>
							</tr>
							<tr>
								<td colspan="6">
									<table class="table table-striped">
										<thead>
											<tr>
												<td><fmt:message key="page.view.section.name" bundle="${pagebundle}" /></td>
												<td><fmt:message key="page.view.section.desc" bundle="${pagebundle}" /></td>
												<td></td>
											</tr>
										</thead>
										<c:forEach var="section" items="${sectionList}">
											<c:if test="${section.conferenceId == conference.id}">
											<tr>
												<td>${section.name}</td>
												<td>${section.description}</td>
												<c:if test="${role == 'ADMIN'}">
													<td>
														<div class="btn-group" role="group">
															<a class="btn btn-primary  btn-md"
															href="${pageContext.request.contextPath}/controller?command=search-section&sectionId=${section.id}"><fmt:message
															key="page.common.edit" bundle="${pagebundle}" /></a>
															<button type="submit" class="btn btn-primary  btn-md"
															onclick="document.getElementById('sectionId').value='${section.id}';
															document.getElementById('command').value='delete-section';
										              		document.getElementById('conferForm').submit();">
															<fmt:message key="page.common.delete" bundle="${pagebundle}" />
														</button>
														</div>
													</td>
												</c:if>
												<c:if test="${role == 'USER'}">
													<td><button type="submit" class="btn btn-primary  btn-md"
															onclick="document.getElementById('sectionId').value='${section.id}';
															document.getElementById('command').value='search-user-report';
										              		document.getElementById('conferForm').submit();">
															<fmt:message key="page.viev.confer.addapplic" bundle="${pagebundle}" /></button>
													</td>
												</c:if>
											</tr>
											</c:if>
										</c:forEach>
										<c:if test="${role == 'ADMIN'}">
											<tr>
												<td colspan="3">
													<a class="btn btn-primary  btn-md"
													href="${pageContext.request.contextPath}/controller?command=search-section&conferenceId=${conference.id}"><fmt:message
													key="page.view.section.new" bundle="${pagebundle}" /></a>
												</td>
											</tr>
										</c:if>
									</table>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<br>
					<div class="alert alert-dark text-center">
						<fmt:message key="page.view.confer.noresult" bundle="${pagebundle}" />
					</div>
				</c:otherwise>
			</c:choose>
		</form>
		<c:if test="${role == 'ADMIN'}">
			<form action="${pageContext.request.contextPath}/jsp/new/conference.jsp">
				<button type="submit" class="btn btn-primary  btn-md">
					<fmt:message key="page.view.confer.new" bundle="${pagebundle}" />
				</button>
			</form>
		</c:if>
	</div>
	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>