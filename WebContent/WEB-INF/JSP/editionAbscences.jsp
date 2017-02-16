<%@page import="java.util.Collection"%>
<%@page import="projet.data.Etudiant"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="etudiants" type="java.util.Collection<Etudiant>" scope="request"/>
<jsp:useBean id="actualGroupeName" type="java.lang.String" scope="request"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edition Absences</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
	
<link rel="stylesheet" href="<%= getServletContext().getContextPath()%>/css/style.css">
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
</head>
<body>
<%-- Element d'action : jsp:include --%>
<jsp:include page="<%=getServletContext().getInitParameter(\"entetedepage\")%>" />
<jsp:include page="<%=getServletContext().getInitParameter(\"secondeEntete\")%>"/>
		
<p id="indications">* Pour supprimer des absences, utilisez le signe "-". (ex: -2 supprimera 2 abscences à l'étudiant). Le nombre d'absence minimal sera 0.</p>

<%-- Affichage des étudiants --%>
<form action="<%= getServletContext().getContextPath()%>/do/updateAbsences?groupe=<%=actualGroupeName%>" method="post" class="form-inline">
	<table id="tableAbsences" class="table table-hover">
		<thead>
			<tr>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Absences</th>
			</tr>
		</thead>
		<tbody>
			<%
			int i = 1;
			String string = "";
			for (Etudiant etudiant : etudiants) {
				string = "inputAbs"+Integer.toString(i);
				%>
				<tr>
					<td><a href="<%= getServletContext().getContextPath()%>/do/detail?id=<%=etudiant.getId()%>"><%=etudiant.getPrenom()%></a></td>
					<td><%=etudiant.getNom()%></td>
					<td><%=etudiant.getNbAbsences()%> <input id="<%=string%>" name="<%=string%>" class="form-control" type="number" placeholder="Absence(s)"></td> 
				</tr>
				<%
				i++;
			}
			%> 
		</tbody>
	</table>
	<button type="submit" class="btn btn-default" >Sauvegarder</button>
</form>
	
</body>
</html>