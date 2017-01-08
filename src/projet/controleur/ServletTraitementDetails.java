package projet.controleur;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.ldap.HasControls;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord.NULL;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import projet.data.Etudiant;
import projet.data.EtudiantDAO;
import projet.data.GestionFactory;
import projet.data.Groupe;
import projet.data.GroupeDAO;
import projet.data.Matière;
import projet.data.MatièreDAO;
import projet.data.Note;
import projet.data.NoteDAO;

/**
 * Servlet implementation class ServletTraitementDetails
 */
public class ServletTraitementDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String urlHome;
	private String urlDetail;
	private String urlEditionNotes;
	private String urlEditionAbsences;
	private String urlGroupes;
	private String urlAbsencesSauvegarde;
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTraitementDetails() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * INIT
	 */
	public void init() throws ServletException{
		urlHome = getServletConfig().getInitParameter("urlHome");
		urlDetail = getServletConfig().getInitParameter("urlDetail");
		urlEditionNotes = getServletConfig().getInitParameter("urlEditionNotes");
		urlEditionAbsences = getServletConfig().getInitParameter("urlEditionAbsences");
		urlGroupes = getServletConfig().getInitParameter("urlGroupes");
		urlAbsencesSauvegarde = getServletConfig().getInitParameter("urlAbsencesSauvegarde");
		
		GestionFactory.open();
		
		///// INITIALISATION DE LA BD
		// Normalement l'initialisation se fait directement dans la base de donnÃ©es
		if ((GroupeDAO.getAll().size() == 0) && (EtudiantDAO.getAll().size() == 0) && (MatièreDAO.getAll().size() == 0)) {

			// Creation des groupes
			Groupe MIAM = GroupeDAO.create("miam");
			Groupe SIMO = GroupeDAO.create("SIMO");
			Groupe MESSI = GroupeDAO.create("MESSI");

			// Creation des Ã©tudiants
			Etudiant etu = EtudiantDAO.create("Francis", "Brunet-Manquat", MIAM);
			EtudiantDAO.create("Philippe", "Martin", MIAM);
			EtudiantDAO.create("Mario", "Cortes-Cornax", MIAM);
			EtudiantDAO.create("Françoise", "Coat", SIMO);
			EtudiantDAO.create("Laurent", "Bonnaud", MESSI);
			EtudiantDAO.create("Sébastien", "Bourdon", MESSI);
			EtudiantDAO.create("Mathieu", "Gatumel", SIMO);
			
			//Création des matières
			Matière ANDROID = MatièreDAO.create("Android");
			Matière J2EE = MatièreDAO.create("J2EE");
			Matière ERP = MatièreDAO.create("ERP");
			
			//Création d'une note pour initialiser la table dans la BDD
			Note note = NoteDAO.create(12, ANDROID, etu);
		}
	}
	
	/**
	 * DESTROY
	 */
	@Override
	public void destroy() {
		super.destroy();

		// Fermeture de la factory
		GestionFactory.close();
	}

	/**
	 * POST
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		java.util.List<Etudiant> etudiants = EtudiantDAO.getAll();
		int i = 1;
		for(Etudiant etudiant : etudiants){
			String idInput = "inputAbs"+Integer.toString(i);
			System.out.println(idInput);
			try{
				Integer nombre = new Integer(request.getParameter(idInput)); //recupe nouvelle valeur abs
				if(nombre != null){ //vérifie si null
					int idEtu = etudiant.getId();
					EtudiantDAO.addAbsences(idEtu, nombre);
				}
			}catch (NumberFormatException e) {
				// TODO: handle exception 
				}
			i++;
		}
		loadJSP(urlAbsencesSauvegarde, request, response);
	}
	
	/**
	 * GET
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methode = request.getMethod().toLowerCase();
		
		String action = request.getPathInfo();
		if(action == null){
			action = "/home";
		}
		System.out.println("action == "+action);
		
		if(methode.equals("get") && action.equals("/index")){
			doHome(request, response);
		}else if(/*methode.equals("post") &&*/ action.equals("/detail")){
			doDetail(request, response);
		}else if(action.equals("/editionNotes")){
			doEditionNotes(request, response);
		}else if(action.equals("/editionAbsences")){
			doEditionAbsences(request, response);
		}else if(action.equals("/voirGroupes")){
			doGroupes(request, response);
		}else if(action.equals("/urlAbsencesSauvegarde")){
			loadJSP(urlAbsencesSauvegarde, request, response);
		}else{
			System.out.println("default");
			doHome(request, response);
		}
	}
	
	private void doHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("listEtu", EtudiantDAO.getAll());
		loadJSP(urlHome, request, response);
	}
	
	private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Integer id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("idEtu", id);
		request.setAttribute("myEtu", EtudiantDAO.retrieveById(id));
		loadJSP(urlDetail, request, response);
	}
	
	private void doEditionNotes(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		loadJSP(urlEditionNotes, request, response);
	}
	
	private void doEditionAbsences(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		request.setAttribute("listEtu", EtudiantDAO.getAll());
		loadJSP(urlEditionAbsences, request, response);
	}
	
	private void doGroupes(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		request.setAttribute("groupes", GroupeDAO.getAll());
		loadJSP(urlGroupes,request,response);
	}
	
	/**
	 * Charge la JSP indiquÃ©e en paramÃ¨tre
	 * 
	 * @param url
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadJSP(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
}
