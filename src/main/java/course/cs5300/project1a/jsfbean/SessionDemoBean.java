package course.cs5300.project1a.jsfbean;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.service.SessionCookieService;

@Named
@Scope("request")
public class SessionDemoBean {
	private String sessionMessage;
	private String userInput;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private SessionContent sessionContent;
	
	@Inject
	private SessionCookieService sessionCookieService;

	public SessionDemoBean() {
		super();

		// ----------------------------------------------
		System.out.println("JSF bean initialized<<--------------------------------------------");
		// ----------------------------------------------


	}

	public String getSessionMessage() {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		this.request = (HttpServletRequest)context.getRequest();
		//this.response = (HttpServletResponse)context.getRequest();
		this.sessionContent = this.sessionCookieService.getSession(request);
		if(sessionContent == null){
			this.sessionMessage = "Hello User";
		}else{
			this.sessionMessage = this.sessionContent.getMessage();
		}
		return sessionMessage;
	}

	public void setSessionMessage(String sessionMessage) {
		this.sessionMessage = sessionMessage;
	}

	public String getUserInput() {
		return userInput;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}

	public String replace() {
		return null;
	}

	public String refresh() {
		// ----------------------------------------------
		System.out.println("refresh clicked<<--------------------------------------------");
		// ----------------------------------------------

		return "/views/SessionDemo2.xhtml";
	}

	public String logout() {
		return null;
	}

}
