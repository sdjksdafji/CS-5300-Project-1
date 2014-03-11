package course.cs5300.project1a.service;

import course.cs5300.project1a.pojo.SessionContent;

public interface SessionStateTableManager {
	public long addSession(SessionContent sessionContent);

	public void updateSession(long sessionId, SessionContent sessionContent);

	public SessionContent getSession(long sessionId);

	public void removeSession(long sessionId);
	
	public void removeExpiredSession();
}
