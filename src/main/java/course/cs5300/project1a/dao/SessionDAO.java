package course.cs5300.project1a.dao;

import java.net.InetAddress;
import java.util.List;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;

public interface SessionDAO {
	
	public SessionID addSession(SessionContent sessionContent, List<InetAddress> metadata);
	
	public void updateSession(SessionID sessionId, SessionContent sessionContent, List<InetAddress> metadata);
	
	public SessionContent getSession(SessionID sessionId, List<InetAddress> metadata);
	
	public void removeSession(SessionID sessionId, List<InetAddress> metadata);

}
