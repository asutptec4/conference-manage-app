<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragment/taglib_conf.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="page.main.title" bundle="${pagebundle}" /></title>
<%@ include file="/WEB-INF/fragment/jslib_conf.jspf"%>
<style type="text/css">
.backimg {
	background-image:
		url(${pageContext.request.contextPath}/img/conference.jpg);
	background-size: cover;
}

.white {
	color: white;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/fragment/header.jspf"%>
	<div class="jumbotron backimg ">
		<div class="container">
			<h1 class="white">
				<fmt:message key="page.main.name" bundle="${pagebundle}" />
			</h1>
			<p class="white">
				<fmt:message key="page.main.desc" bundle="${pagebundle}" />
			</p>
		</div>
	</div>
	
	<div class="container">
		<c:choose>
			<c:when test="${not empty conferenceList}">
				<div class="row">
					<c:forEach var="conference" items="${conferenceList}">
						<div class="col-md-4">
							<h2>${conference.name}</h2>
							<p>${conference.description}</p>
							<p>Location: ${conference.location}</p>
							<p>Date: <ctl:long-date format="date">${conference.startDate}</ctl:long-date> - <ctl:long-date format="date">${conference.endDate}</ctl:long-date></p>
							<c:if test="${not empty user and role == 'USER'}">
								<p>
									<a class="btn btn-secondary" href="${pageContext.request.contextPath}/controller?command=search-confer-byid&conferenceId=${conference.id}" role="button"><fmt:message
											key="page.main.button" bundle="${pagebundle}" /></a>
								</p>
							</c:if>
						</div>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<br>
				<div class="alert alert-dark">
					<fmt:message key="message.error.noresult" bundle="${pagebundle}" />
				</div>
			</c:otherwise>
		</c:choose>
	</div>

	<%@ include file="/WEB-INF/fragment/footer.jspf"%>
</body>
</html>