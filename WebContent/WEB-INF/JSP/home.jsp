<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%-- Directives de page import --%>
<%@ page import="java.util.*"%>
<%@ page import="projet.data.Etudiant"%>
<%@ page import="projet.data.GestionFactory"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="listEtu" type="java.util.Collection<Etudiant>" scope="request"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<title><%=getServletContext().getInitParameter("title")%></title>
</head>
<body>
	<%-- Element d'action : jsp:include --%>
	<jsp:include page="<%=getServletContext().getInitParameter(\"entetedepage\")%>" /> <%-- TODO INCLUDE AVEC PARAMETRE --%>
	<jsp:include page="<%=getServletContext().getInitParameter(\"secondeEntete\")%>"/>
	
	<%-- Affichage des étudiants --%>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Absences</th>
				<th>Groupe</th>
			</tr>
		</thead>
		<tbody>
			<%
		for (Iterator<Etudiant> etuIt = listEtu.iterator(); etuIt.hasNext();) {
				Etudiant etudiant = etuIt.next();
			%>
			<tr>
				<td><a href="<%= getServletContext().getContextPath()%>/do/detail?id=<%=etudiant.getId()%>"><%=etudiant.getPrenom()%></a></td>
				<td><%=etudiant.getNom()%></td>
				<td><%=etudiant.getNbAbsences()%></td> 
				<td><%=etudiant.getGroupe().getNom() %></td>
			</tr>
			<% }
			%> 
		</tbody>
	</table>
	<%-- Element d'action : jsp:include --%>
	<jsp:include page="<%=getServletContext().getInitParameter(\"pieddepage\")%>" />
</body>
</html>