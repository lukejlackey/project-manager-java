<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- c:out ; c:forEach etc. -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dashboard</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
</head>
<body class="bg-dark">
	<nav class="navbar navbar-dark bg-dark p-2">
		<h1 class="display-3 text-light">Welcome ${loggedUser.getFullName()}!</h1>
		<div class="d-flex align-items-center gap-3">
			<a class="badge badge-primary" href="/projects/new">Add Project</a>
			<a class="btn btn-danger fw-bold" href="/logout">Logout</a>
		</div>
	</nav>
	<div class="container-fluid bg-warning text-dark p-3">
		<div class="container">
			<div class="d-flex align-items-center">
				<h2 class="display-5">All Projects:</h2>
			</div>
			<c:choose>
				<c:when test="${otherProjects.isEmpty()}">
					<h3 class="container border-2 border-dark mx-2">No new projects yet...</h3>
				</c:when>
				<c:otherwise>
					<div class="container rounded bg-dark text-light p-3">
						<table class="table table-primary table-striped">
							<thead>
								<tr>
									<th>Project</th>
									<th>Team Lead</th>
									<th>Due Date</th>	
									<th>Actions</th>			
								</tr>
							</thead>
							<tbody>
								<c:forEach var="oneProject" items="${otherProjects}">
									<tr>
										<td><a href="/projects/${oneProject.id}"><c:out value="${oneProject.title}"/></a></td>	
										<td><c:out value="${oneProject.user.getFullName()}"/></td>	
										<td><c:out value="${oneProject.dueDate}"/></td>
										<td>
											<form action="/dashboard" method="post">
												<input type="hidden" name="_method" value="put"/>
												<input type="hidden" name="projectId" value="${oneProject.id}"/>
												<input class="link-primary" type="submit" value="Join Team"/>
											</form>
										</td>					
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="d-flex align-items-center">
				<h2 class="display-5">Your Projects:</h2>
			</div>
			<c:choose>
				<c:when test="${yourProjects.isEmpty()}">
					<h3 class="container border-2 border-dark mx-2">No projects yet...</h3>
				</c:when>
				<c:otherwise>
					<div class="container rounded bg-dark text-light p-3">
						<table class="table table-primary table-striped">
							<thead>
								<tr>
									<th>Project</th>
									<th>Team Lead</th>
									<th>Due Date</th>	
									<th>Actions</th>			
								</tr>
							</thead>
							<tbody>
								<c:forEach var="oneProject" items="${yourProjects}">
									<tr>
										<td><a href="/projects/${oneProject.id}"><c:out value="${oneProject.title}"/></a></td>	
										<td><c:out value="${oneProject.user.getFullName()}"/></td>	
										<td><c:out value="${oneProject.dueDate}"/></td>
										<td>
											<c:choose>
												<c:when test="${oneProject.user.equals(loggedUser)}">
													<form action="/projects/edit/${oneProject.id}" method="get">
														<input class="link-primary" type="submit" value="Edit"/>
													</form>
												</c:when>
												<c:otherwise>
													<form action="/dashboard" method="post">
														<input type="hidden" name="_method" value="delete"/>
														<input type="hidden" name="projectId" value="${oneProject.id}"/>
														<input class="link-primary" type="submit" value="Leave team"/>
													</form>
												</c:otherwise>
											</c:choose>
										</td>					
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>