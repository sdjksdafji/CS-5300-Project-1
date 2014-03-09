package course.cs5300.project1a.service.implementation;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.service.SessionStateTableManager;

@Named
@Scope("singleton")
public class SessionStateTableManagerImpl implements SessionStateTableManager {

	private static Map<Long, SessionContent> sessionMap = new HashMap<Long, SessionContent>();
	private static long currentSessionId = 0;

	@Override
	public synchronized long addSession(SessionContent sessionContent) {
		// TODO Auto-generated method stub
		long returnVal = currentSessionId++;
		try {
			sessionMap.put(returnVal, (SessionContent) sessionContent.clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnVal;
	}

	@Override
	public synchronized void updateSession(long sessionId,
			SessionContent sessionContent) {
		// TODO Auto-generated method stub
		try {
			sessionMap.put(sessionId, (SessionContent) sessionContent.clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized SessionContent getSession(long sessionId) {
		// TODO Auto-generated method stub
		SessionContent returnVal = sessionMap.get(sessionId);
		try {
			returnVal = (SessionContent) returnVal.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnVal;
	}

	@Override
	public synchronized void removeSession(long sessionId) {
		// TODO Auto-generated method stub
		sessionMap.remove(sessionId);
	}

}
