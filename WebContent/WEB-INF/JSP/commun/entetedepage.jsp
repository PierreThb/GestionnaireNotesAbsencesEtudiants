<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<jsp:useBean id="actualGroupeName" type="java.lang.String" scope="request"/>

<h2>Gestionnaire notes/absences</h2>
<br>
<div class="btn-group" role="group" aria-label="...">
	<a href="<%= getServletContext().getContextPath()%>/do/voirHome?groupe=<%=actualGroupeName%>"><button type="button" class="btn btn-default" >Acceuil</button></a>
	<a href="<%= getServletContext().getContextPath()%>/do/editionNotes?groupe=<%=actualGroupeName%>"><button type="button" class="btn btn-default" >Editer Notes</button></a>
	<a href="<%= getServletContext().getContextPath()%>/do/editionAbsences?groupe=<%=actualGroupeName%>"><button type="button" class="btn btn-default" >Editer Absences</button></a>
	<a href="<%= getServletContext().getContextPath()%>/do/voirGroupes?groupe=<%=actualGroupeName%>"><button type="button" class="btn btn-default" >Voir tous les groupes</button></a>	
</div>
<br>
<br>