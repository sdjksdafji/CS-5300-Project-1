package course.cs5300.project1a.service;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import course.cs5300.project1a.pojo.SessionContent;

public interface SessionCookieService {

	public long createSession(HttpServletResponse response,
			Timestamp currentTimestamp, long version);

	public void updateSession(long sessionId, HttpServletResponse response,
			Timestamp currentTimestamp, long version);
	
	public void updateSessionMessage(long sessionId, String message);

	public void deleteSession(long sessionId, HttpServletResponse response);

	public long getSessionId(HttpServletRequest request);

	public String getCookieVal(HttpServletRequest request);
}
