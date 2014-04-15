package course.cs5300.project1a.service;

import java.util.List;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;

public interface LocalSessionTableManager {
//	public SessionID addSession(SessionContent sessionContent);

	public void updateSession(SessionID sessionId, SessionContent sessionContent);

	public SessionContent getSession(SessionID sessionId);

	public void removeSession(SessionID sessionId);
	
	public void removeExpiredSession();
	
	public List<String> getContentList();
	
	
	// this function will be called after RPC server receives a "sessionWrite" call
	// it will store into local hash table if it is the newest version for that session.
	public void storeSessionLocally(SessionID sessionId, SessionContent sessionContent);
}
