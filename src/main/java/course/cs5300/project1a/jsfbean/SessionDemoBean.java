package course.cs5300.project1a.jsfbean;

import java.net.InetAddress;
import java.sql.Timestamp;

import course.cs5300.project1a.pojo.SessionID;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import course.cs5300.project1a.service.*;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.service.SessionCookieService;
import course.cs5300.project1a.service.LocalSessionTableManager;
import course.cs5300.project1a.service.VersionManager;
import course.cs5300.project1a.dao.*;;

@Named
@Scope("request")
public class SessionDemoBean {
	private String sessionMessage;
	private String userInput;
	private String sessionExpireTime;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SessionContent sessionContent;
	private long versionOfThisRequest;
	private Timestamp timestampOfThisRequest;
	private SessionID sessionId;

	@Inject
	private GetLocalIPService getLocalIPService;
	
	@Inject
	private SessionCookieService sessionCookieService;

	@Inject
	private LocalSessionTableManager sessionStateTableManager;
	
	@Inject
	private SessionDAO sessionDAO;

	@Inject
	private VersionManager versionManager;

	public SessionDemoBean() {
		super();
		// ----------------------------------------------
		System.out
				.println("JSF bean constructed<<--------------------------------------------");
		// ----------------------------------------------

	}

	@PostConstruct
	private void init() {
		this.versionOfThisRequest = this.versionManager.getVersionNumber();
		java.util.Date date = new java.util.Date();
		this.timestampOfThisRequest = new Timestamp(date.getTime());
		this.checkHttpRequestContent();
		this.checkHttpResponseContent();
		this.readSession();
		// ----------------------------------------------
		System.out
				.println("JSF bean initialized<<--------------------------------------------");
		// ----------------------------------------------
	}

	public String getSessionMessage() {
		// ----------------------------------------------
		System.out
				.println("getSessionMsg called<<--------------------------------------------");
		// ----------------------------------------------
		this.sessionContent = this.sessionStateTableManager
				.getSession(null); // all null equals this.sessionId
		if (this.sessionContent != null) {
			this.sessionMessage = this.sessionContent.getMessage();
		} else {
			this.sessionMessage = "";
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
		// ----------------------------------------------
		System.out
				.println("setUserInput called<<--------------------------------------------");
		// ----------------------------------------------
		this.userInput = userInput;
	}

	public String getSessionExpireTime() {
		this.sessionContent = this.sessionStateTableManager
				.getSession(null);
		if (this.sessionContent != null) {
			this.sessionExpireTime = this.sessionContent
					.getExpirationTimestamp().toString();
		} else {
			this.sessionExpireTime = "";
		}
		return sessionExpireTime;
	}

	public void setSessionExpireTime(String sessionExpireTime) {
		this.sessionExpireTime = sessionExpireTime;
	}

	public String replace() {
		if (this.userInput != null) {
			this.sessionCookieService.updateSessionMessage(sessionId,
					this.userInput);
		}
		return "/views/SessionDemo.xhtml";
	}

	public String refresh() {
		// ----------------------------------------------
		System.out
				.println("refresh clicked<<--------------------------------------------");
		
		// ----------------------------------------------
		return "/views/SessionDemo.xhtml";
	}

	public String logout() {
		// ----------------------------------------------
		System.out
				.println("logout clicked<<--------------------------------------------");
		// ----------------------------------------------
		this.sessionCookieService.deleteSession(this.sessionId, this.response);
		this.sessionId = new SessionID(-1,getLocalIPService.getLocalIP());
		this.sessionContent = null;
		return "/views/SessionDemo.xhtml";
	}

	private void checkHttpRequestContent() {
		if (this.request == null) {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			this.request = (HttpServletRequest) context.getRequest();
		}
	}

	private void checkHttpResponseContent() {
		if (this.response == null) {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			this.response = (HttpServletResponse) context.getResponse();
		}
	}

	private void readSession() {
		sessionId = new SessionID(-1,getLocalIPService.getLocalIP());
		this.sessionId = this.sessionCookieService.getSessionId(request);
		this.sessionContent = this.sessionStateTableManager
				.getSession(null);
		if (sessionContent == null) {
			System.out
					.println("session NOT found <<------------------------------------------");
			this.sessionId = this.sessionCookieService
					.createSession(this.response, timestampOfThisRequest,
							versionOfThisRequest);
			this.sessionContent = this.sessionStateTableManager
					.getSession(null);
		} else {
			System.out
					.println("session found <<------------------------------------------");
		}
		if (sessionContent == null) {
			System.err
					.println("serious error !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			sessionContent = new SessionContent();
			//throw new NullPointerException();
		}
		this.sessionCookieService.updateSession(this.sessionId, response,
				timestampOfThisRequest, versionOfThisRequest);
	}

}
