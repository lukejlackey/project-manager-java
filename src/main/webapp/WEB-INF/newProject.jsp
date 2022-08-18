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
<title>Create New Entry</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
</head>
<body class="bg-dark">
	<nav class="navbar navbar-dark bg-dark p-2">
		<h1 class="display-3 text-light">Welcome ${loggedUser.fullName}!</h1>
		<div class="d-flex align-items-center gap-3">
			<a class="badge badge-primary" href="/dashboard">Dashboard</a>
			<a class="btn btn-danger" href="/logout">Logout</a>
		</div>
	</nav>
	<div class="container-fluid bg-warning text-dark p-3">
		<div class="container">
			<h2 class="display-5">Add a Project</h2>
			<form:form class="container rounded bg-dark text-warning py-2" action="/projects/new" method="post" modelAttribute="project">
				<div>
					<form:label class="form-label" path="title">Title:</form:label>
					<form:input class="form-control" type="text" path="title" />
					<form:errors class="text-danger" path="title"/>
				</div>
				<div>
					<form:label class="form-label" path="description">Description:</form:label> 
					<form:textarea class="form-control" path="description"/>
					<form:errors class="text-danger" path="description"/>
				</div>
				<div>
					<form:label class="form-label" path="dueDate">Due Date:</form:label>
					<form:input class="form-control" type="date" path="dueDate"/>
					<form:errors class="text-danger" path="dueDate"/>
				</div>
				<div class="d-flex gap-3 justify-content-end">
					<a class="btn btn-danger my-4" href="/dashboard">Cancel</a>
					<button class="btn btn-warning my-4" type="submit">Submit</button>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>