package projet.controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.ldap.HasControls;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			Groupe MIAM = GroupeDAO.create("MIAM");
			Groupe SIMO = GroupeDAO.create("SIMO");
			Groupe MESSI = GroupeDAO.create("MESSI");

			// Creation des étudiants
			Etudiant etu = EtudiantDAO.create("Francis", "Brunet-Manquat", MIAM);
			Etudiant etu2 = EtudiantDAO.create("Philippe", "Martin", MIAM);
			Etudiant etu3 = EtudiantDAO.create("Françoise", "Coat", SIMO);
			Etudiant etu4 = EtudiantDAO.create("Mathieu", "Gatumel", SIMO);
			Etudiant etu5 = EtudiantDAO.create("Laurent", "Bonnaud", MESSI);
			Etudiant etu6 = EtudiantDAO.create("Sébastien", "Bourdon", MESSI);

			//Création des matières
			Matière ANDROID = MatièreDAO.create("Android");
			Matière J2EE = MatièreDAO.create("J2EE");
			Matière ERP = MatièreDAO.create("ERP");
			Matière Statistiques = MatièreDAO.create("Statistiques");
			
			//Ajout des groupes aux matières
			ANDROID.addGroupe(MIAM);
			ANDROID.addGroupe(SIMO);
			J2EE.addGroupe(MIAM);
			J2EE.addGroupe(MESSI);
			ERP.addGroupe(SIMO);
			Statistiques.addGroupe(MESSI);
			
			MIAM.addMatière(ANDROID);
			MIAM.addMatière(J2EE);
			SIMO.addMatière(ANDROID);
			SIMO.addMatière(ERP);
			MESSI.addMatière(Statistiques);
			MESSI.addMatière(J2EE);
			
			//Création de notes pour initialiser la table dans la BDD
			NoteDAO.create(0, ANDROID, etu);
			NoteDAO.create(0, J2EE, etu);
			NoteDAO.create(0, ANDROID, etu2);
			NoteDAO.create(0, J2EE, etu2);
			NoteDAO.create(0, ERP, etu3);
			NoteDAO.create(0, ANDROID, etu3);
			NoteDAO.create(0, ERP, etu4);
			NoteDAO.create(0, ANDROID, etu4);
			NoteDAO.create(0, J2EE, etu5);
			NoteDAO.create(0, Statistiques, etu5);
			NoteDAO.create(0, J2EE, etu6);
			NoteDAO.create(0, Statistiques, etu6);
			
			//update etudiants
			etu.addMatièreToList(J2EE);
			etu.addMatièreToList(ANDROID);
			etu2.addMatièreToList(J2EE);
			etu2.addMatièreToList(ANDROID);
			etu3.addMatièreToList(ERP);
			etu3.addMatièreToList(ANDROID);
			etu4.addMatièreToList(ERP);
			etu4.addMatièreToList(ANDROID);
			etu5.addMatièreToList(J2EE);
			etu5.addMatièreToList(Statistiques);
			etu6.addMatièreToList(J2EE);
			etu6.addMatièreToList(Statistiques);
			
			EtudiantDAO.update(etu6);
			EtudiantDAO.update(etu5);
			EtudiantDAO.update(etu4);
			EtudiantDAO.update(etu3);
			EtudiantDAO.update(etu2);
			EtudiantDAO.update(etu);
			GroupeDAO.update(MIAM);
			GroupeDAO.update(MESSI);
			GroupeDAO.update(SIMO);
			MatièreDAO.update(Statistiques);
			MatièreDAO.update(ANDROID);
			MatièreDAO.update(ERP);
			MatièreDAO.update(J2EE);
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
		String action = request.getPathInfo();
		if(action.equals("/updateAbsences")){
			doUpdateAbsences(request, response);
		}else if(action.equals("/updateNotes")){
			doUpdateNotes(request, response);
		}
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
		
		if(methode.equals("get") && action.equals("/voirHome")){
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
			doHome(request, response);
		}
	}
	
	/* Function qui affiche la liste des étudiants */
	private void doHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		/* Initialise */
		Integer conditionGroupe; //-1 if groupe not selected yet, 1 else
		Integer conditionMatière; //-2 if matière not selected yet, 2 else
		String actualGroupeName;
		Groupe actualGroupe = new Groupe();
		String actualMatièreName;
		Matière actualMatière = new Matière();
		java.util.List<Matière> matières = new ArrayList<Matière>();
		java.util.List<Etudiant> etudiants;
		String action = request.getPathInfo();
		
		if(request.getParameterMap().containsKey("groupe")){ // groupe selected yet
			conditionGroupe = 1;
			actualGroupeName = request.getParameter("groupe");
			if(!actualGroupeName.equals("tous")){ //si ggroupe séléctionné différent de "tous"
				actualGroupe = GroupeDAO.getGroupeByName(actualGroupeName); //récupère le groupe
				etudiants = EtudiantDAO.getAllByGroupe(actualGroupe); //récupère les étudiants du groupes
				matières = actualGroupe.getMatières(); //récupère les matières du groupe
				if(request.getParameterMap().containsKey("matiere")){ //matière selected yet 
					conditionMatière = 2;
					actualMatièreName = request.getParameter("matiere");
					actualMatière = MatièreDAO.getByNom(actualMatièreName);
				}else{ // matière not selected yet 
					conditionMatière = -2;
					actualMatièreName = "unspecified";
				}
			}else{
				etudiants = EtudiantDAO.getAll();
				conditionMatière = -2;
				actualMatièreName = "unspecified";
			}	
		}else{// groupe not selected yet
			conditionGroupe = -1;
			conditionMatière = -2;
			actualGroupeName = "tous";
			actualMatièreName = "unspecified";
			etudiants = EtudiantDAO.getAll(); //récupère tous les étudiants si aucun spécifié
			matières = null;
		}
		
		/* Set attributes */
		request.getSession().setAttribute("actualGroupeName", actualGroupeName);
		request.getSession().setAttribute("conditionGroupe", conditionGroupe);
		request.getSession().setAttribute("actualGroupe", actualGroupe);
		request.getSession().setAttribute("conditionMatière", conditionMatière);
		request.getSession().setAttribute("actualMatièreName", actualMatièreName);
		request.getSession().setAttribute("actualMatière", actualMatière);
		request.getSession().setAttribute("matières", matières);
		request.setAttribute("actualGroupeName", actualGroupeName);
		request.setAttribute("listEtu", etudiants);
		request.setAttribute("action", action);
		loadJSP(urlHome, request, response);
	}
	
	/* Fonction qui affiche les détails d'un étudiant */
	private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Integer id = Integer.parseInt(request.getParameter("id"));
		String action = request.getPathInfo();
		Etudiant etudiant = EtudiantDAO.retrieveById(id);
		Float moyenne = new Float(0);
		java.util.List<Note> notes = new ArrayList<Note>();
		
		notes = NoteDAO.getNotesForOneEtudiant(etudiant);
		for(Note note : notes){
			moyenne += note.getValeur();
		}
		moyenne = (moyenne/notes.size());
		
		request.setAttribute("idEtu", id);
		request.setAttribute("myEtu", etudiant);
		request.setAttribute("action", action);
		request.setAttribute("moyenne", moyenne);
		request.setAttribute("actualGroupeName", request.getSession().getAttribute("actualGroupeName"));
		loadJSP(urlDetail, request, response);
	}
	
	/* Fonction qui affiche tous les groupes */
	@SuppressWarnings("null")
	private void doGroupes(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String actualGroupeName = request.getParameter("groupe");
		String action = request.getPathInfo();
		java.util.List<Groupe> groupes = new ArrayList<Groupe>();
		Integer moyenneGroupe;
		java.util.List<Etudiant> etudiants = new ArrayList<Etudiant>();
		HashMap<Integer, Integer> mapGroupeIDMoyenne = new HashMap<Integer,Integer>();
		
		groupes = GroupeDAO.getAll();
		
		for(Groupe groupe : groupes){ //parcours les groupes 
			moyenneGroupe = 0;
			etudiants = groupe.getEtudiants();
			for(Etudiant etudiant : etudiants){ // parcours les étudiants du groupe
				java.util.List<Note> notes = NoteDAO.getNotesForOneEtudiant(etudiant);
				Integer moyenneEtudiant = 0;
				for(Note note : notes){ //parcours les notes de l'étudiant
					moyenneEtudiant += note.getValeur();
				}
				moyenneEtudiant = (moyenneEtudiant/notes.size());
				moyenneGroupe += moyenneEtudiant;
			}
			moyenneGroupe = (moyenneGroupe/etudiants.size());
			mapGroupeIDMoyenne.put(groupe.getId(), moyenneGroupe);
		}
		
		request.getSession().setAttribute("actualGroupeName",actualGroupeName);
		request.setAttribute("actualGroupeName",actualGroupeName);
		request.setAttribute("groupes", groupes);
		request.setAttribute("action", action);
		request.setAttribute("mapMoyenne", mapGroupeIDMoyenne);
		
		loadJSP(urlGroupes,request,response);
	}
	
	/* Fonction qui affiche la page pour éditer les absences d'un groupe */
	private void doEditionAbsences(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		/* Initialisation des variables et des attributs */
		String action = request.getPathInfo();
		Groupe groupe = new Groupe();
		java.util.List<Etudiant> etudiants = new ArrayList<Etudiant>();
		String actualGroupeName;
			
		if(!request.getParameter("groupe").equals("tous")){ // un groupe est déjà spécifié
			actualGroupeName = request.getParameter("groupe");
			groupe = GroupeDAO.getGroupeByName(actualGroupeName); // récupère le groupe actuellement selectionné
			etudiants = EtudiantDAO.getAllByGroupe(groupe); // récupère les étudiants du groupe
		}else{
			actualGroupeName = "tous"; //aucun groupe spécifié
			etudiants = EtudiantDAO.getAll(); //récupère tous les étudiants
		}
		
		/* Set attributes */
		request.getSession().setAttribute("actualGroupeName",actualGroupeName);
		request.getSession().setAttribute("actualGroupe",groupe);
		request.setAttribute("actualGroupeName", actualGroupeName);
		request.setAttribute("actualGroupe",groupe);
		request.setAttribute("etudiants", etudiants);
		request.setAttribute("action", action);
		loadJSP(urlEditionAbsences, request, response);
	}
		

	/* Fonction qui sauvegarde les absences dans la BDD */
	private void doUpdateAbsences(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		request.getSession().setAttribute("actualGroupe",request.getParameter("groupe"));
		request.setAttribute("actualGroupe", request.getSession().getAttribute("actualGroupe"));
		/* Initialisation des variables et des attributs */
		String action = request.getPathInfo();
		Groupe groupe = new Groupe();
		java.util.List<Etudiant> etudiants = new ArrayList<Etudiant>();
		String actualGroupeName;
		
		if(request.getParameter("groupe").equals("tous")){ //aucun groupe spécifié
			etudiants = EtudiantDAO.getAll();
			actualGroupeName = "tous";
		}else{ // un groupe est spécifié
			actualGroupeName = request.getParameter("groupe");
			groupe = GroupeDAO.getGroupeByName(actualGroupeName);
			etudiants = EtudiantDAO.getAllByGroupe(groupe);
		}
		
		int i = 1;
		for(Etudiant etudiant : etudiants){
			String idInput = "inputAbs"+Integer.toString(i); //créer chaine de caractère identique à l'id de l'input
			try{
				Integer nombre = new Integer(request.getParameter(idInput)); //recupe nouvelle valeur de l'input
				if(nombre != null){ //vérifie si null
					int idEtu = etudiant.getId();
					int nbAbsencesActuel = etudiant.getNbAbsences();
					int newValeur = nbAbsencesActuel + nombre;
					if(newValeur < 0){ //si nouvelle négative
						etudiant.setNbAbsences(0); 
						EtudiantDAO.update(etudiant);
					}else{
						EtudiantDAO.addAbsences(idEtu, nombre);
					}					
				}
			}catch (NumberFormatException e) {}
			i++;
		}
		
		/* Set attributes */
		request.getSession().setAttribute("actualGroupeName",actualGroupeName);
		request.getSession().setAttribute("actualGroupe",groupe);
		request.setAttribute("actualGroupeName", actualGroupeName);
		request.setAttribute("actualGroupe",groupe);
		request.setAttribute("etudiants", etudiants);
		request.setAttribute("action", action);
		//loadJSP(urlEditionAbsences, request, response);
		doEditionAbsences(request, response);
	}
	
	/* Fonction qui affiche la page d'édition des notes d'un groupe/matière */
	private void doEditionNotes(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		/* Initialisation des variables et des attributs */
		java.util.List<Groupe> groupes = new ArrayList<Groupe>(); // tous les groupes de la bdd
		String actualGroupeName;
		Groupe actualGroupe = new Groupe();
		Integer conditionGroupe; //-1 if groupe not selected yet, 1 else
		Integer conditionMatière; //-2 if matière not selected yet, 2 else
		String actualMatièreName;
		Matière actualMatière = new Matière();
		java.util.List<Matière> matières = new ArrayList<Matière>();
		java.util.List<Etudiant> etudiants;
		String action = request.getPathInfo();
		
		if(request.getParameterMap().containsKey("groupe")){ // groupe selected yet
			actualGroupeName = request.getParameter("groupe");
			if(!actualGroupeName.equals("tous")){ //différent de "tous"
				conditionGroupe = 1;
				actualGroupe = GroupeDAO.getGroupeByName(actualGroupeName); //récupère le groupe
				etudiants = EtudiantDAO.getAllByGroupe(actualGroupe); //récupère les étudiants du groupes 
				matières = actualGroupe.getMatières();
				groupes = GroupeDAO.getAll();
				if(request.getParameterMap().containsKey("matiere")){ //matière selected yet 
					conditionMatière = 2;
					actualMatièreName = request.getParameter("matiere");
					actualMatière = MatièreDAO.getByNom(actualMatièreName);
				}else{ 												// matière not selected yet 
					conditionMatière = -2;
					actualMatièreName = "unspecified";
				}
			}else{ // groupe = "tous"
				etudiants = EtudiantDAO.getAll();
				conditionGroupe = -1;
				conditionMatière = -2;
				actualMatièreName = "unspecified";
				groupes = GroupeDAO.getAll();
			}
		}else{// groupe not selected yet
			conditionGroupe = -1;
			conditionMatière = -2;
			actualGroupeName = "tous";
			actualMatièreName = "unspecified";
			etudiants = EtudiantDAO.getAll(); //récupère tous les étudiants si aucun spécifié
			groupes = GroupeDAO.getAll();
			matières = null;
		}
		
		/* Set attributes */
		request.getSession().setAttribute("actualGroupeName", actualGroupeName);
		request.getSession().setAttribute("conditionMatière", conditionMatière);
		request.getSession().setAttribute("actualGroupe", actualGroupe);
		request.getSession().setAttribute("conditionGroupe", conditionGroupe);
		request.getSession().setAttribute("actualMatièreName", actualMatièreName);
		request.getSession().setAttribute("actualMatière", actualMatière);
		request.setAttribute("conditionGroupe", conditionGroupe);
		request.setAttribute("conditionMatière", conditionMatière);
		request.setAttribute("actualMatière", actualMatière);
		request.setAttribute("actualGroupe", actualGroupe);
		request.setAttribute("actualGroupeName", actualGroupeName);
		request.setAttribute("actualMatièreName", actualMatièreName);
		request.setAttribute("matières", matières);
		request.setAttribute("listEtu", etudiants);
		request.setAttribute("groupes", groupes);
		request.setAttribute("action", action);
		
		/* load JSP */
		loadJSP(urlEditionNotes, request, response);
	}
	
	/* Fonction qui enregistre les notes dans la BDD */
	private void doUpdateNotes(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		/* Initialisation des variables et des attributs */
		java.util.List<Groupe> groupes = new ArrayList<Groupe>(); // tous les groupes de la bdd
		String actualGroupeName;
		Groupe actualGroupe = new Groupe();
		Integer conditionGroupe; //-1 if groupe not selected yet, 1 else
		Integer conditionMatière; //-2 if matière not selected yet, 2 else
		String actualMatièreName;
		Matière actualMatière = new Matière();
		java.util.List<Matière> matières = new ArrayList<Matière>();
		java.util.List<Etudiant> etudiants;
		String action = request.getPathInfo();
		
		actualGroupeName = request.getSession().getAttribute("actualGroupeName").toString();
		actualGroupe = GroupeDAO.getGroupeByName(actualGroupeName);
		etudiants = EtudiantDAO.getAllByGroupe(actualGroupe); //étudiant du groupe sélectionné 
		actualMatièreName = request.getSession().getAttribute("actualMatièreName").toString();
		actualMatière = MatièreDAO.getByNom(actualMatièreName);
		
		int i = 1;
		for(Etudiant etudiant : etudiants){ //parcours de la liste des étudiants
			try{
				String idInput = "inputNote"+Integer.toString(i); //id de l'input
				Integer newNoteVal = new Integer(request.getParameter(idInput)); //recupère valeur de l'input
				if(newNoteVal != null){ //vérifie si null
					int idEtu = etudiant.getId();
					try {
						//note existe déjà, update de cette dernière
						Note note = NoteDAO.getNoteByEtudiantMatière(etudiant, actualMatière); //récupère la note
						note.setValeur(newNoteVal); //update la note
						NoteDAO.update(note); //update la BDD
					} catch (Exception e) {}
				}
			}catch (NumberFormatException e) {}
			i++;
		}
		
		/* Set attributes */
		request.getSession().setAttribute("actualGroupeName", actualGroupeName);
		request.getSession().setAttribute("actualGroupe", actualGroupe);
		request.getSession().setAttribute("actualMatièreName", actualMatièreName);
		request.getSession().setAttribute("actualMatière", actualMatière);
		request.setAttribute("actualMatière", actualMatière);
		request.setAttribute("actualGroupe", actualGroupe);
		request.setAttribute("actualGroupeName", actualGroupeName);
		request.setAttribute("actualMatièreName", actualMatièreName);
		request.setAttribute("matières", matières);
		request.setAttribute("listEtu", etudiants);
		request.setAttribute("groupes", groupes);
		request.setAttribute("action", action);
		
		doEditionNotes(request, response);
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
