<%@page import="projet.data.Mati�reDAO"%>
<%@page import="projet.data.Groupe"%>
<%@page import="javax.persistence.NoResultException"%>
<%@page import="projet.data.Note"%>
<%@page import="projet.data.NoteDAO"%>
<%@page import="projet.data.Mati�re"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collection"%>
<%@page import="projet.data.Etudiant"%>
<%@page import="java.util.Iterator"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="groupes" type="java.util.Collection<Groupe>" scope="request"/>
<jsp:useBean id="mati�res" type="java.util.Collection<Mati�re>" scope="request"/>
<jsp:useBean id="listEtu" type="java.util.Collection<Etudiant>" scope="request"/>
<jsp:useBean id="actualGroupeName" type="java.lang.String" scope="request"/>
<jsp:useBean id="actualMati�reName" type="java.lang.String" scope="request"/>
<jsp:useBean id="conditionGroupe" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="conditionMati�re" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="actualMati�re" type="projet.data.Mati�re" scope="request"/>
<jsp:useBean id="actualGroupe" type="projet.data.Groupe" scope="request"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edition Notes</title>
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

<form method="get" action="editionNotes">
	<div class="form-group">
		<div class="row">
			<div class="col-sm-1 control-label">Groupe</div>
				<div class="col-sm-4">
					<select class="form-control select-bar" name="groupe">
						<option selected="selected">Choisissez un groupe</option>
						<% for(Groupe groupe : groupes){ %>
							<option value="<%= groupe.getNom()%>"<%if(actualGroupeName.equals(groupe.getNom())){ %> selected="selected"  <%}%> ><%=groupe.getNom()%></option>
						<% } %>
					</select>
				</div>
		</div>
	</div>
</form>

<%if(conditionGroupe==1){ %>
	<form method="get" action="editionNotes">
		<div class="form-group">
			<div class="row">
				<div class="col-sm-1 control-label">Mati�re</div>
					<div class="col-sm-4">
						<input type="hidden" name="groupe" value="<%= actualGroupeName %>">
						<select class="form-control select-bar" name="matiere">
							<option selected="selected">Choisissez une mati�re</option>
							<% for(Mati�re mat : mati�res){ %>
								<option value="<%= mat.getNom()%>"<%if(actualMati�reName.equals(mat.getNom())){ %> selected="selected"  <%}%> ><%=mat.getNom()%></option>
							<% } %>
						</select>
					</div>
			</div>
		</div>
	</form>
<%}%>

<%if(conditionGroupe==1 && conditionMati�re==2){
	//request.getSession().setAttribute("actualMati�reName", request.getParameter("matiere"));%>
	<%-- Affichage des �tudiants --%>
	<form action="<%= getServletContext().getContextPath()%>/do/updateNotes?groupe=<%=actualGroupeName%>" method="post" class="form-inline">
	  	<table id="tableAbsences" class="table table-hover"> 
	  		<thead> 
	  			<tr> 
	  				<th>Nom</th> 
	  				<th>Pr�nom</th> 
	  				<th>Mati�re</th> 
	  				<th>Note</th> 
	  			</tr> 
	  		</thead> 
	  		<tbody> 
				<%
	  			int i = 1; 
	 			String string = ""; 
	  			for (Iterator<Etudiant> etuIt = listEtu.iterator(); etuIt.hasNext();) { 
	  				Etudiant etudiant = etuIt.next(); 
	  				String noteVal = ""; 
	  				string = "inputNote"+Integer.toString(i); 
	  				try{ 
	  					noteVal = NoteDAO.getNoteByEtudiantMati�re(etudiant, actualMati�re).getValeur().toString(); 
	  				}catch(NoResultException e){} 
					%>
	  				<tr> 
						<td><a href="<%= getServletContext().getContextPath()%>/do/detail?id=<%=etudiant.getId()%>"><%=etudiant.getPrenom()%></a></td>
						<td><%=etudiant.getNom()%></td>
						<td><%=actualMati�re.getNom()%> </td> 
						<td><%=noteVal%><input id="<%=string%>" name="<%=string%>" class="form-control" type="number" min="0" max="20" placeholder="Note"></td>
	  				</tr> 
					<%
	  				i++; 
	  			}	  
			%> 
	  		</tbody> 
	  	</table> 
	  	<button type="submit" class="btn btn-default" >Sauvegarder</button> 
	  </form> 
<%}%>

<script type="text/javascript">
	window.addEventListener("load", function() {
		var selects = document.getElementsByClassName("select-bar");
		for(i=0; i < selects.length; i++){
			document.getElementsByClassName("select-bar")[i].addEventListener('change', function(){
				this.form.submit();
			});
		}
	});
</script>

</body>
</html>