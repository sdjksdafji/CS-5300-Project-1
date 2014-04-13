package course.cs5300.project1a.service.implementation;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;
import course.cs5300.project1a.service.GetLocalIPService;
import course.cs5300.project1a.service.LocalSessionTableManager;

@Named
@Scope("singleton")
public class LocalSessionTableManagerImpl implements LocalSessionTableManager {
	private static Map<SessionID, SessionContent> sessionMap = new HashMap<SessionID, SessionContent>();
	private static long currentSessionId = 0;
	@Inject
	private GetLocalIPService getLocalIpService;
	@Override
	public synchronized SessionID addSession(SessionContent sessionContent) {
		// TODO Auto-generated method stub
		long sessionNumber = Math.max(sessionContent.getVersion(), currentSessionId);
		currentSessionId = sessionNumber+1;
		SessionID newSessionID = null;
		try {
			InetAddress addr = InetAddress.getByName(getLocalIpService.getLocalIP().toString());
			newSessionID = new SessionID(sessionNumber,addr);
			sessionMap.put(newSessionID, (SessionContent) sessionContent.clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newSessionID;
	}

	@Override
	public synchronized void updateSession(SessionID sessionId, SessionContent sessionContent) {
		// TODO Auto-generated method stub
		if(sessionContent.getVersion()==-1){
			removeSession(sessionId);
			return;
		}
		try {
			sessionMap.put(sessionId, (SessionContent) sessionContent.clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized SessionContent getSession(SessionID sessionId) {
		// TODO Auto-generated method stub
		SessionContent sc = new SessionContent();
		sc.setExpirationTimestamp(new Timestamp(75));
		sc.setMessage("Hello World");
		sc.setVersion(0);
		sessionMap.put(sessionId, sc);
		return sessionMap.get(sessionId);
	}

	@Override
	public synchronized void removeSession(SessionID sessionId) {
		// TODO Auto-generated method stub
		sessionMap.remove(sessionId);
		
	}

	@Override
	public synchronized void removeExpiredSession() {
		// TODO Auto-generated method stub
		System.out.println("Removing Expired Session  <<-------------------------------------");
		Timestamp currentTs = new Timestamp((new Date()).getTime());
		Iterator<Entry<SessionID, SessionContent>> iter = this.sessionMap.entrySet().iterator();
		while(iter.hasNext()){
			Entry<SessionID, SessionContent> entry = iter.next();
			SessionContent sessionContent = entry.getValue();
			if(sessionContent.getExpirationTimestamp().before(currentTs)){
				// ---------------------------------------------------
				System.out.println(entry.getKey() + "Expire Time" + entry.getValue().getExpirationTimestamp().toString() + " current time " + currentTs.toString() + " removed from session table <<------------------------------");
				// ---------------------------------------------------
				iter.remove();
			}
		}
	}

	@Override
	public synchronized void storeSessionLocally(SessionID sessionId,
			SessionContent sessionContent) {
		// TODO Auto-generated method stub
		updateSession(sessionId,sessionContent);
	}
	
	
	
}
