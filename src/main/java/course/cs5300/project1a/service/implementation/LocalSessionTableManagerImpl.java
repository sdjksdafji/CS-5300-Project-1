package course.cs5300.project1a.service.implementation;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import course.cs5300.project1a.service.*;
@Named
@Scope("singleton")
public class LocalSessionTableManagerImpl implements LocalSessionTableManager {
	private static Map<SessionID, SessionContent> sessionMap = new HashMap<SessionID, SessionContent>();
	private static long currentSessionId = 0;
	private final long cookieExpirationTimeInSec=30;
	@Inject
	private GetLocalIPService getLocalIpService;
	@Inject
	private SessionCookieService sessionCookieService;
	@Override
	public synchronized SessionID addSession(SessionContent sessionContent) {
		// TODO Auto-generated method stub
		long sessionNumber = Math.max(sessionContent.getVersion(), currentSessionId);
		currentSessionId = sessionNumber+1;
		SessionID newSessionID = null;
		try {
			InetAddress addr = InetAddress.getByName(getLocalIpService.getLocalIP().toString().substring(1));
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
//		SessionContent sc = new SessionContent();
//		sc.setExpirationTimestamp(currentTimestamp.getTime() + cookieExpirationTimeInSec * 1000);
//		sc.setMessage("Hello World");
//		sc.setVersion(0);
//		sessionMap.put(sessionId, sc);
		SessionContent c = sessionMap.get(sessionId);
		if(c==null){
			c = new SessionContent();
			c.setMessage("Hello User");
			c.setVersion(0);
			java.util.Date date = new java.util.Date();
			Timestamp currentTimestamp = new Timestamp(date.getTime());
			Timestamp expirationTimestamp = new Timestamp(currentTimestamp.getTime()+cookieExpirationTimeInSec*1000);
			c.setExpirationTimestamp(expirationTimestamp);
			sessionMap.put(sessionId, c);
			System.out.println(sessionId+" is added, Message:"+c.getMessage());
		}
		return c;
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
	
	@Override
	public List<String> getContentList(){
		List<String> list = new ArrayList<String>();
		   Iterator it = sessionMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        if(pairs!=null){
		        	System.out.println(pairs.getKey()+"What????????");
		        	System.out.println(pairs.getValue()+"What????????");
		        }
		        System.out.println(((SessionID)pairs.getKey()).toString()+"++++++++++++++++++++++++++++");
		        System.out.println(((SessionContent)pairs.getValue()).toString()+"-----------------------");
		        list.add(((SessionID)pairs.getKey()).toString() + " = " + ((SessionContent)pairs.getValue()).toString());
		    }
		return list;
	}
	
}
