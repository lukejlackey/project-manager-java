<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- c:out ; c:forEach etc. --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Page</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
</head>
<body class="bg-dark">
	<nav class="navbar navbar-dark bg-dark p-2">
		<h1 class="display-3 text-light">Welcome ${loggedUser.fullName}!</h1>
		<div class="d-flex align-items-center gap-3">
			<a class="badge badge-primary" href="/dashboard">Dashboard</a>
			<a class="btn btn-danger fw-bold" href="/logout">Logout</a>
		</div>
	</nav>
	<div class="container-fluid bg-warning text-dark p-3">
		<div class="container">
			<h2 class="display-5">Project Details</h2>
			<div class="container rounded p-3 bg-dark text-light">
				<div>
					<label class="fw-bold" for="title">Project Title:</label>
					<p class="mb-2" id="title"><c:out value="${project.title}"/></p>
				</div>
				<div>
					<label class="fw-bold" for="description">Project Description:</label>
					<p class="mb-2" id="description"><c:out value="${project.description}"/></p>
				</div>
				<div>
					<label class="fw-bold" for="dueDate">Due Date:</label>
					<p class="mb-2" id="dueDate"><c:out value="${project.dueDate}"/></p>
				</div>
				<div class="d-flex justify-content-between">
					<c:if test="${project.teamMembers.contains(loggedUser)}">
						<a class="btn btn-warning my-4 fw-bold" href="/projects/${project.id}/tasks">See Tasks</a>
					</c:if>
					<c:if test="${myProject}">
						<form action="/projects/${project.id}" method="post">
							<input type="hidden" name="_method" value="delete"/>
							<button class="btn btn-danger my-4 fw-bold" type="submit">Delete Project</button>
						</form>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>