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
<title>Tasks Page</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<body class="bg-dark">
	<nav class="navbar navbar-dark bg-dark p-2">
		<h1 class="display-3 text-light">Welcome ${loggedUser.getFullName()}!</h1>
		<div class="d-flex align-items-center gap-3">
			<a class="badge badge-primary" href="/dashboard">Dashboard</a>
			<a class="btn btn-danger fw-bold" href="/logout">Logout</a>
		</div>
	</nav>
	<div class="container-fluid bg-warning text-dark p-3">
		<div class="container">
			<h2 class="display-5 fw-bold">Project: <c:out value="${project.title}"/></h2>
			<h4>Project Lead: <c:out value="${project.user.getFullName()}"/></h4>
			<form:form class="container rounded p-3 bg-dark text-light" action="/projects/${project.id}/tasks" method="post" modelAttribute="task">
				<h4 class="text-warning">Add a Task</h4>
				<div>
					<form:label class="form-label" path="title">Task Title:</form:label>
					<form:input class="form-control mb-2" type="text" path="title"/>
					<form:errors class="text-danger" path="title"/>
				</div>
				<div>
					<form:label class="form-label" path="description">Description:</form:label> 
					<form:textarea class="form-control mb-2" path="description"/>
					<form:errors class="text-danger" path="description"/>
				</div>
				<div>
					<form:label class="form-label" path="dueDate">Due Date:</form:label>
					<form:input class="form-control mb-2" type="date" path="dueDate"/>
					<form:errors class="text-danger" path="dueDate"/>
				</div>
				<div class="d-flex gap-3 justify-content-end">
					<button class="btn btn-warning my-4 fw-bold" type="submit">Submit</button>
				</div>
			</form:form>
		</div>
		<div class="container">
			<h2 class="display-5 fw-bold">Active Tasks:</h2>
			<c:forEach var="projectTask" items="${project.getTasks()}">
				<div>
					<h5 class="fw-bold"><c:out value="${projectTask.title}"/></h5>
					<p>Added by <c:out value="${projectTask.poster.getFullName()}"/> at <c:out value="${projectTask.createdAt}"/>:</p>
					<p><c:out value="${projectTask.description}"/></p>
					<p>Due: <c:out value="${projectTask.dueDate}"/></p>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>