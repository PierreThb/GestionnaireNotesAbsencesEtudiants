<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%-- Directives de page import --%>
<%@ page import="java.util.*"%>
<%@ page import="projet.data.Etudiant"%>
<%@ page import="projet.data.GestionFactory"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Détail d'un étudiant</title>
</head>
<body>
	<p>page de détail</p>
	<%Etudiant etudiant = GestionFactory.getEtudiantById(Integer.parseInt(request.getParameter("id")));%>
	<p><%=etudiant.getNom()%>, <%=etudiant.getPrenom()%></p>
</body>
</html>