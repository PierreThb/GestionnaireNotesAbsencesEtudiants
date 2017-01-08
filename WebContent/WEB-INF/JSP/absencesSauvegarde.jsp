<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Succès !</title>
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
	<a href="<%= getServletContext().getContextPath()%>/do/editionNotes"><button type="button" class="btn btn-default" >Editer Notes</button></a>
	<a href="<%= getServletContext().getContextPath()%>/do/editionAbsences"><button type="button" class="btn btn-default" >Editer Absences</button></a>
	<a href="<%= getServletContext().getContextPath()%>/do/voirHome"><button type="button" class="btn btn-default" >Voir Etudiants</button></a>	
	<a href="<%= getServletContext().getContextPath()%>/do/voirGroupes"><button type="button" class="btn btn-default" >Voir Groupe</button></a>	
</div>
<p>absences sauvegardées!</p>
</body>
</html>