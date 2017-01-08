<%@page import="java.util.Collection"%>
<%@page import="projet.data.Etudiant"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="listEtu" type="java.util.Collection<Etudiant>" scope="request"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edition Absences</title>
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

<div class="btn-group" role="group" aria-label="...">
	<a href="<%= getServletContext().getContextPath()%>/do/voirHome"><button type="button" class="btn btn-default" >Voir Etudiants</button></a>
	<a href="<%= getServletContext().getContextPath()%>/do/voirGroupes"><button type="button" class="btn btn-default" >Voir Groupe</button></a>	
</div>
	
<%-- Affichage des étudiants --%>
<form action="<%= getServletContext().getContextPath()%>/do/urlAbsencesSauvegarde" method="post" class="form-inline">
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
		for (Iterator<Etudiant> etuIt = listEtu.iterator(); etuIt.hasNext();) {
				Etudiant etudiant = etuIt.next();
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