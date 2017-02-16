<%@page import="projet.data.Groupe"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="groupes" type="java.util.List<projet.data.Groupe>" scope="request"/>
<jsp:useBean id="mapMoyenne" type="java.util.HashMap" scope="request"/>

<html>
<head>
<title><%=getServletContext().getInitParameter("title")%></title>
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
	
	<!-- tableau de groupes  -->
	<table class="table table-hover">
		<thead>
			<tr>
				<th>Nom du groupe</th>
				<th>Nombre d'étudiants</th>
				<th>Moyenne du groupe</th>
			</tr>
		</thead>
		<tbody>
			<%for (Groupe groupe : groupes) {%>
			<tr>
				<td><%=groupe.getNom()%></td>
				<td><%=groupe.getEtudiants().size()%></td>
				<td><%=mapMoyenne.get(groupe.getId())%></td>
			</tr>
			<%}%>
		</tbody>
	</table>
</body>
</html>