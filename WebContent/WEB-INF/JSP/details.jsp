<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%-- Directives de page import --%>
<%@ page import="java.util.*"%>
<%@ page import="projet.data.Etudiant"%>
<%@ page import="projet.data.GestionFactory"%>

<jsp:useBean id="idEtu" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="myEtu" class="projet.data.Etudiant" scope="request"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<title>Détail d'un étudiant</title>
</head>
<body>
	<p>Nom: <%=myEtu.getNom()%></p>
	<p>Prénom: <%=myEtu.getPrenom()%></p>
	<p>Absences: <%=myEtu.getNbAbsences()%></p>
	<br>
	<%-- Element d'action : jsp:include --%>
	<jsp:include page="<%=getServletContext().getInitParameter(\"pieddepage\")%>" />
</body>
</html>