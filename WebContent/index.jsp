<%@page import="projet.data.Etudiant"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%-- Directives de page import --%>
<%@ page import="java.util.*"%>
<%@ page import="projet.data.Etudiant"%>
<%@ page import="projet.data.GestionFactory"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title>Home</title>
</head>
<body>
	<%-- Element d'action : jsp:include --%>
	<jsp:include page="commun\entetedepage.jsp" />
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Nom</th>
				<th>Pr�nom</th>
				<th>Absences</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Etudiant etudiant : GestionFactory.getEtudiants()) {
					System.out.print("Etudiant : " + etudiant.getPrenom() + " " + etudiant.getNom());
					System.out.println(" -> nombre d'absences : " + GestionFactory.getAbsencesByEtudiantId(etudiant.getId()));
			%>
			<tr>
				<td><a href="/Project_LP/details.jsp?id=<%=etudiant.getId()%>"><%=etudiant.getPrenom()%></a></td>
				<td><%=etudiant.getNom()%></td>
				<td><%=GestionFactory.getAbsencesByEtudiantId(etudiant.getId())%></td>
			</tr>
			<%
				}
			%>
		</tbody>

	</table>
	<br>

	<%-- Element d'action : jsp:include --%>
	<jsp:include page="commun\pieddepage.jsp" />
</body>
</html>