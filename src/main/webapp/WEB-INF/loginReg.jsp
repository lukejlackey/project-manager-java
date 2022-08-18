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
<title>Project Manager</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
</head>
<body class="bg-dark">
	<div class="bg-warning py-3 my-5">
		<div class="container gap-3 my-1">
			<div class="container rounded bg-dark py-3 d-flex flex-column align-items-center text-light">
				<h1 class="display-2 font-weight-bolder text-warning">Project Manager</h1>
				<p>A place for teams to manage projects.</p>
			</div>
			<div class="container d-flex gap-4 my-3">
				<div class="container bg-light text-primary rounded">
					<h2 class="display-5">Register</h2>
					<form:form action="/register" method="POST" modelAttribute="user">
						<div>
							<form:label class="form-label" path="firstName">First Name:</form:label>
							<form:input class="form-control" type="text" path="firstName" />
							<form:errors class="text-danger" path="firstName"/>
						</div>
						<div>
							<form:label class="form-label" path="lastName">Last Name:</form:label> 
							<form:input class="form-control" type="text" path="lastName"/>
							<form:errors class="text-danger" path="lastName"/>
						</div>
						<div>
							<form:label class="form-label" path="email">Email:</form:label>
							<form:input class="form-control" type="text" path="email"/>
							<form:errors class="text-danger" path="email"/>
						</div>
						<div>
							<form:label class="form-label" path="password">Password:</form:label>
							<form:input class="form-control" type="password" path="password"/>
							<form:errors class="text-danger" path="password"/>
						</div>
						<div>
							<form:label class="form-label" path="confirm">Confirm Password:</form:label> 
							<form:input class="form-control" type="password" path="confirm"/>
							<form:errors class="text-danger" path="confirm"/>
						</div>
						<button class="btn btn-primary my-4" type="submit">Register</button>
					</form:form >
				</div>
				<div class="container bg-light text-success rounded h-100">
					<h2 class="display-5">Login</h2>
					<form:form action="/login" method="POST" modelAttribute="loginUser">
						<div>
							<form:label class="form-label" path="email">Email:</form:label>
							<form:input class="form-control" type="text" path="email"/>
							<form:errors class="text-danger" path="email"/>
						</div>
						<div>
							<form:label class="form-label" path="password">Password:</form:label>
							<form:input class="form-control" type="password" path="password"/>
							<form:errors class="text-danger" path="password"/>
						</div>
						<div>
							<button class="btn btn-success my-4" type="submit">Login</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>