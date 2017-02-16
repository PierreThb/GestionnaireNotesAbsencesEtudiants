<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<jsp:useBean id="action" type="java.lang.String" scope="request"/>

<!-- Nav tabs -->
<ul id="listeEntete">
  <li id="liSimo"><a href="<%=getServletContext().getContextPath()%>/do<%=action%>?groupe=SIMO">SIMO</a></li>
  <li id="liMiam"><a href="<%=getServletContext().getContextPath()%>/do<%=action%>?groupe=MIAM">MIAM</a></li>
  <li id="liMessi"><a href="<%=getServletContext().getContextPath()%>/do<%=action%>?groupe=MESSI">MESSI</a></li>
  <li id="liTous"><a href="<%=getServletContext().getContextPath()%>/do<%=action%>?groupe=tous">Tous les groupes</a></li>  
</ul>
