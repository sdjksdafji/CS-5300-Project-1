package course.cs5300.project1a.service;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;

public interface SessionCookieService {

	public SessionContent createSession(HttpServletResponse response,
			Timestamp currentTimestamp, long version);

	public void updateSession(SessionID sessionId, HttpServletResponse response,
			Timestamp currentTimestamp, long version);
	
	public void updateSessionMessage(SessionID sessionId, String message);

	public void deleteSession(SessionID sessionId, HttpServletResponse response);

	public SessionID getSessionId(HttpServletRequest request);

	public String getCookieVal(HttpServletRequest request);
	
}
