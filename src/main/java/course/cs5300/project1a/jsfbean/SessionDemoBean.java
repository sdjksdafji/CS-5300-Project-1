package course.cs5300.project1a.jsfbean;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;


@Named
@Scope("request")
public class SessionDemoBean {
	private String sessionMessage;
	private String userInput;
	
	public String getSessionMessage() {
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
	

	public String replace(){
		return null;
	}

	public String refresh(){
		return null;
	}
	public String logout(){
		return null;
	}
	
}
