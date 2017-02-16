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
import projet.data.Mati�re;
import projet.data.Mati�reDAO;
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
		// Normalement l'initialisation se fait directement dans la base de données
		if ((GroupeDAO.getAll().size() == 0) && (EtudiantDAO.getAll().size() == 0) && (Mati�reDAO.getAll().size() == 0)) {

			// Creation des groupes
			Groupe MIAM = GroupeDAO.create("MIAM");
			Groupe SIMO = GroupeDAO.create("SIMO");
			Groupe MESSI = GroupeDAO.create("MESSI");

			// Creation des �tudiants
			Etudiant etu = EtudiantDAO.create("Francis", "Brunet-Manquat", MIAM);
			Etudiant etu2 = EtudiantDAO.create("Philippe", "Martin", MIAM);
			Etudiant etu3 = EtudiantDAO.create("Fran�oise", "Coat", SIMO);
			Etudiant etu4 = EtudiantDAO.create("Mathieu", "Gatumel", SIMO);
			Etudiant etu5 = EtudiantDAO.create("Laurent", "Bonnaud", MESSI);
			Etudiant etu6 = EtudiantDAO.create("S�bastien", "Bourdon", MESSI);

			//Cr�ation des mati�res
			Mati�re ANDROID = Mati�reDAO.create("Android");
			Mati�re J2EE = Mati�reDAO.create("J2EE");
			Mati�re ERP = Mati�reDAO.create("ERP");
			Mati�re Statistiques = Mati�reDAO.create("Statistiques");
			
			//Ajout des groupes aux mati�res
			ANDROID.addGroupe(MIAM);
			ANDROID.addGroupe(SIMO);
			J2EE.addGroupe(MIAM);
			J2EE.addGroupe(MESSI);
			ERP.addGroupe(SIMO);
			Statistiques.addGroupe(MESSI);
			
			MIAM.addMati�re(ANDROID);
			MIAM.addMati�re(J2EE);
			SIMO.addMati�re(ANDROID);
			SIMO.addMati�re(ERP);
			MESSI.addMati�re(Statistiques);
			MESSI.addMati�re(J2EE);
			
			//Cr�ation de notes pour initialiser la table dans la BDD
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
			etu.addMati�reToList(J2EE);
			etu.addMati�reToList(ANDROID);
			etu2.addMati�reToList(J2EE);
			etu2.addMati�reToList(ANDROID);
			etu3.addMati�reToList(ERP);
			etu3.addMati�reToList(ANDROID);
			etu4.addMati�reToList(ERP);
			etu4.addMati�reToList(ANDROID);
			etu5.addMati�reToList(J2EE);
			etu5.addMati�reToList(Statistiques);
			etu6.addMati�reToList(J2EE);
			etu6.addMati�reToList(Statistiques);
			
			EtudiantDAO.update(etu6);
			EtudiantDAO.update(etu5);
			EtudiantDAO.update(etu4);
			EtudiantDAO.update(etu3);
			EtudiantDAO.update(etu2);
			EtudiantDAO.update(etu);
			GroupeDAO.update(MIAM);
			GroupeDAO.update(MESSI);
			GroupeDAO.update(SIMO);
			Mati�reDAO.update(Statistiques);
			Mati�reDAO.update(ANDROID);
			Mati�reDAO.update(ERP);
			Mati�reDAO.update(J2EE);
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
	
	/* Function qui affiche la liste des �tudiants */
	private void doHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		/* Initialise */
		Integer conditionGroupe; //-1 if groupe not selected yet, 1 else
		Integer conditionMati�re; //-2 if mati�re not selected yet, 2 else
		String actualGroupeName;
		Groupe actualGroupe = new Groupe();
		String actualMati�reName;
		Mati�re actualMati�re = new Mati�re();
		java.util.List<Mati�re> mati�res = new ArrayList<Mati�re>();
		java.util.List<Etudiant> etudiants;
		String action = request.getPathInfo();
		
		if(request.getParameterMap().containsKey("groupe")){ // groupe selected yet
			conditionGroupe = 1;
			actualGroupeName = request.getParameter("groupe");
			if(!actualGroupeName.equals("tous")){ //si ggroupe s�l�ctionn� diff�rent de "tous"
				actualGroupe = GroupeDAO.getGroupeByName(actualGroupeName); //r�cup�re le groupe
				etudiants = EtudiantDAO.getAllByGroupe(actualGroupe); //r�cup�re les �tudiants du groupes
				mati�res = actualGroupe.getMati�res(); //r�cup�re les mati�res du groupe
				if(request.getParameterMap().containsKey("matiere")){ //mati�re selected yet 
					conditionMati�re = 2;
					actualMati�reName = request.getParameter("matiere");
					actualMati�re = Mati�reDAO.getByNom(actualMati�reName);
				}else{ // mati�re not selected yet 
					conditionMati�re = -2;
					actualMati�reName = "unspecified";
				}
			}else{
				etudiants = EtudiantDAO.getAll();
				conditionMati�re = -2;
				actualMati�reName = "unspecified";
			}	
		}else{// groupe not selected yet
			conditionGroupe = -1;
			conditionMati�re = -2;
			actualGroupeName = "tous";
			actualMati�reName = "unspecified";
			etudiants = EtudiantDAO.getAll(); //r�cup�re tous les �tudiants si aucun sp�cifi�
			mati�res = null;
		}
		
		/* Set attributes */
		request.getSession().setAttribute("actualGroupeName", actualGroupeName);
		request.getSession().setAttribute("conditionGroupe", conditionGroupe);
		request.getSession().setAttribute("actualGroupe", actualGroupe);
		request.getSession().setAttribute("conditionMati�re", conditionMati�re);
		request.getSession().setAttribute("actualMati�reName", actualMati�reName);
		request.getSession().setAttribute("actualMati�re", actualMati�re);
		request.getSession().setAttribute("mati�res", mati�res);
		request.setAttribute("actualGroupeName", actualGroupeName);
		request.setAttribute("listEtu", etudiants);
		request.setAttribute("action", action);
		loadJSP(urlHome, request, response);
	}
	
	/* Fonction qui affiche les d�tails d'un �tudiant */
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
			for(Etudiant etudiant : etudiants){ // parcours les �tudiants du groupe
				java.util.List<Note> notes = NoteDAO.getNotesForOneEtudiant(etudiant);
				Integer moyenneEtudiant = 0;
				for(Note note : notes){ //parcours les notes de l'�tudiant
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
	
	/* Fonction qui affiche la page pour �diter les absences d'un groupe */
	private void doEditionAbsences(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		/* Initialisation des variables et des attributs */
		String action = request.getPathInfo();
		Groupe groupe = new Groupe();
		java.util.List<Etudiant> etudiants = new ArrayList<Etudiant>();
		String actualGroupeName;
			
		if(!request.getParameter("groupe").equals("tous")){ // un groupe est d�j� sp�cifi�
			actualGroupeName = request.getParameter("groupe");
			groupe = GroupeDAO.getGroupeByName(actualGroupeName); // r�cup�re le groupe actuellement selectionn�
			etudiants = EtudiantDAO.getAllByGroupe(groupe); // r�cup�re les �tudiants du groupe
		}else{
			actualGroupeName = "tous"; //aucun groupe sp�cifi�
			etudiants = EtudiantDAO.getAll(); //r�cup�re tous les �tudiants
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
		
		if(request.getParameter("groupe").equals("tous")){ //aucun groupe sp�cifi�
			etudiants = EtudiantDAO.getAll();
			actualGroupeName = "tous";
		}else{ // un groupe est sp�cifi�
			actualGroupeName = request.getParameter("groupe");
			groupe = GroupeDAO.getGroupeByName(actualGroupeName);
			etudiants = EtudiantDAO.getAllByGroupe(groupe);
		}
		
		int i = 1;
		for(Etudiant etudiant : etudiants){
			String idInput = "inputAbs"+Integer.toString(i); //cr�er chaine de caract�re identique � l'id de l'input
			try{
				Integer nombre = new Integer(request.getParameter(idInput)); //recupe nouvelle valeur de l'input
				if(nombre != null){ //v�rifie si null
					int idEtu = etudiant.getId();
					int nbAbsencesActuel = etudiant.getNbAbsences();
					int newValeur = nbAbsencesActuel + nombre;
					if(newValeur < 0){ //si nouvelle n�gative
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
	
	/* Fonction qui affiche la page d'�dition des notes d'un groupe/mati�re */
	private void doEditionNotes(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		/* Initialisation des variables et des attributs */
		java.util.List<Groupe> groupes = new ArrayList<Groupe>(); // tous les groupes de la bdd
		String actualGroupeName;
		Groupe actualGroupe = new Groupe();
		Integer conditionGroupe; //-1 if groupe not selected yet, 1 else
		Integer conditionMati�re; //-2 if mati�re not selected yet, 2 else
		String actualMati�reName;
		Mati�re actualMati�re = new Mati�re();
		java.util.List<Mati�re> mati�res = new ArrayList<Mati�re>();
		java.util.List<Etudiant> etudiants;
		String action = request.getPathInfo();
		
		if(request.getParameterMap().containsKey("groupe")){ // groupe selected yet
			actualGroupeName = request.getParameter("groupe");
			if(!actualGroupeName.equals("tous")){ //diff�rent de "tous"
				conditionGroupe = 1;
				actualGroupe = GroupeDAO.getGroupeByName(actualGroupeName); //r�cup�re le groupe
				etudiants = EtudiantDAO.getAllByGroupe(actualGroupe); //r�cup�re les �tudiants du groupes 
				mati�res = actualGroupe.getMati�res();
				groupes = GroupeDAO.getAll();
				if(request.getParameterMap().containsKey("matiere")){ //mati�re selected yet 
					conditionMati�re = 2;
					actualMati�reName = request.getParameter("matiere");
					actualMati�re = Mati�reDAO.getByNom(actualMati�reName);
				}else{ 												// mati�re not selected yet 
					conditionMati�re = -2;
					actualMati�reName = "unspecified";
				}
			}else{ // groupe = "tous"
				etudiants = EtudiantDAO.getAll();
				conditionGroupe = -1;
				conditionMati�re = -2;
				actualMati�reName = "unspecified";
				groupes = GroupeDAO.getAll();
			}
		}else{// groupe not selected yet
			conditionGroupe = -1;
			conditionMati�re = -2;
			actualGroupeName = "tous";
			actualMati�reName = "unspecified";
			etudiants = EtudiantDAO.getAll(); //r�cup�re tous les �tudiants si aucun sp�cifi�
			groupes = GroupeDAO.getAll();
			mati�res = null;
		}
		
		/* Set attributes */
		request.getSession().setAttribute("actualGroupeName", actualGroupeName);
		request.getSession().setAttribute("conditionMati�re", conditionMati�re);
		request.getSession().setAttribute("actualGroupe", actualGroupe);
		request.getSession().setAttribute("conditionGroupe", conditionGroupe);
		request.getSession().setAttribute("actualMati�reName", actualMati�reName);
		request.getSession().setAttribute("actualMati�re", actualMati�re);
		request.setAttribute("conditionGroupe", conditionGroupe);
		request.setAttribute("conditionMati�re", conditionMati�re);
		request.setAttribute("actualMati�re", actualMati�re);
		request.setAttribute("actualGroupe", actualGroupe);
		request.setAttribute("actualGroupeName", actualGroupeName);
		request.setAttribute("actualMati�reName", actualMati�reName);
		request.setAttribute("mati�res", mati�res);
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
		Integer conditionMati�re; //-2 if mati�re not selected yet, 2 else
		String actualMati�reName;
		Mati�re actualMati�re = new Mati�re();
		java.util.List<Mati�re> mati�res = new ArrayList<Mati�re>();
		java.util.List<Etudiant> etudiants;
		String action = request.getPathInfo();
		
		actualGroupeName = request.getSession().getAttribute("actualGroupeName").toString();
		actualGroupe = GroupeDAO.getGroupeByName(actualGroupeName);
		etudiants = EtudiantDAO.getAllByGroupe(actualGroupe); //�tudiant du groupe s�lectionn� 
		actualMati�reName = request.getSession().getAttribute("actualMati�reName").toString();
		actualMati�re = Mati�reDAO.getByNom(actualMati�reName);
		
		int i = 1;
		for(Etudiant etudiant : etudiants){ //parcours de la liste des �tudiants
			try{
				String idInput = "inputNote"+Integer.toString(i); //id de l'input
				Integer newNoteVal = new Integer(request.getParameter(idInput)); //recup�re valeur de l'input
				if(newNoteVal != null){ //v�rifie si null
					int idEtu = etudiant.getId();
					try {
						//note existe d�j�, update de cette derni�re
						Note note = NoteDAO.getNoteByEtudiantMati�re(etudiant, actualMati�re); //r�cup�re la note
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
		request.getSession().setAttribute("actualMati�reName", actualMati�reName);
		request.getSession().setAttribute("actualMati�re", actualMati�re);
		request.setAttribute("actualMati�re", actualMati�re);
		request.setAttribute("actualGroupe", actualGroupe);
		request.setAttribute("actualGroupeName", actualGroupeName);
		request.setAttribute("actualMati�reName", actualMati�reName);
		request.setAttribute("mati�res", mati�res);
		request.setAttribute("listEtu", etudiants);
		request.setAttribute("groupes", groupes);
		request.setAttribute("action", action);
		
		doEditionNotes(request, response);
	}
	
	/**
	 * Charge la JSP indiquée en paramètre
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
