package course.cs5300.project1a.service;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import course.cs5300.project1a.pojo.SessionContent;

public interface SessionCookieService {

	public void createSession(HttpServletResponse response,
			Timestamp currentTimestamp, long version);
	
	public void updateSession(long sessionId, HttpServletResponse response,
			Timestamp currentTimestamp, long version);

	public long getSessionId(HttpServletRequest request);
	public String getCookieVal(HttpServletRequest request);
}
