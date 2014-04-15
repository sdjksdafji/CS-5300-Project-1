package course.cs5300.project1a.service;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;

public interface SessionCookieService {

	public SessionContent createSession(HttpServletResponse response,
			Timestamp currentTimestamp, long version);

	public void updateSession(SessionID sessionId,
			SessionContent sessionContent, HttpServletResponse response,
			Timestamp currentTimestamp, long version, List<InetAddress> metadata);

	public void updateSessionMessage(SessionID sessionId,
			SessionContent sessionContent, List<InetAddress> metadata,
			String message);

	public void deleteSession(SessionID sessionId, List<InetAddress> metadata,
			HttpServletResponse response);

	public SessionID getSessionId(HttpServletRequest request);

	public List<InetAddress> getMetadata(HttpServletRequest request);

	public String getCookieVal(HttpServletRequest request);

}
