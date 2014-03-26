package course.cs5300.project1a.dao;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;

public interface SessionDAO {
	
	public SessionID addSession(SessionContent sessionContent);
	
	public void updateSession(SessionID sessionId, SessionContent sessionContent);
	
	public SessionContent getSession(SessionID sessionId);
	
	public void removeSession(SessionID sessionId);

}
