package security;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import security.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import entities.User;

@Named
@RequestScoped
public class LoginBB {
	private static final String PAGE_MAIN = "menu?faces-redirect=true";
	private static final String PAGE_LOGIN = "login";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String login;
	private String pass;
	private boolean isOn;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Inject
	UserDAO userDAO;

	public String doLogin() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		// 1. verify login and password - get User from "database"
		User user = userDAO.getUserFromDatabase(login, pass);

		// 2. if bad login or password - stay with error info
		if (user == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Niepoprawny login lub hasło", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		// 3. if logged in: get User roles, save in RemoteClient and store it in session
		
		RemoteClient<User> client = new RemoteClient<User>(); //create new RemoteClient
		client.setDetails(user);
		
		List <String> userRole=getUserRoles(user);
		setTrue();

		
		if (userRole != null) { //save roles in RemoteClient
			for (String role: userRole) {
				client.getRoles().add(role);
			}
		}
	
		//store RemoteClient with request info in session (needed for SecurityFilter)
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);

		// and enter the system (now SecurityFilter will pass the request)

		return PAGE_MAIN;
	}
	public void  setTrue(){
		this.isOn=true;
	}

	public boolean getOn(){


		return isOn;
	}

	private List<String> getUserRoles(User user){
		String roles=user.getRole();
		List<String> rolelist= Arrays.asList(roles.split(","));
		return rolelist;
	}

	public boolean isOnline(){

	return false;
	}


	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		//Invalidate session
		// - all objects within session will be destroyed
		// - new session will be created (with new ID)
		session.invalidate();
		return PAGE_MAIN;
	}
	
}
