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
import course.cs5300.project1a.service.*;

@Named
@Scope("singleton")
public class LocalSessionTableManagerImpl implements LocalSessionTableManager {
	private static Map<SessionID, SessionContent> sessionMap = new HashMap<SessionID, SessionContent>();
	private final long cookieExpirationTimeInSec = 30;
	@Inject
	private GetLocalIPService getLocalIpService;

	// @Override
	// public synchronized SessionID addSession(SessionContent sessionContent) {
	// System.out.println("--------------->   add session called     <-------------");
	// // TODO Auto-generated method stub
	// long sessionNumber = Math.max(sessionContent.getVersion(),
	// currentSessionId);
	// currentSessionId = sessionNumber + 1;
	// InetAddress addr = null;
	// addr = getLocalIpService.getLocalIP();
	// SessionID newSessionID = null;
	// try {
	// newSessionID = new SessionID(sessionNumber, addr);
	// sessionMap.put(newSessionID,
	// (SessionContent) sessionContent.clone());
	// } catch (CloneNotSupportedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return newSessionID;
	// }

	@Override
	public synchronized void updateSession(SessionID sessionId,
			SessionContent sessionContent) {
		System.err
				.println("===========================> update session locally for timestap:"
						+ (sessionContent.getExpirationTimestamp() == null ? "null"
								: sessionContent.getExpirationTimestamp()
										.toString()));
		// TODO Auto-generated method stub
		if (sessionId == null || sessionContent == null)
			return;
		if (sessionContent.getVersion() < 0) {
			removeSession(sessionId);
			return;
		}
		try {
			SessionContent ownCopyOfSessionContent = sessionMap.get(sessionId);
			if (ownCopyOfSessionContent == null
					|| (ownCopyOfSessionContent != null && ownCopyOfSessionContent
							.getVersion() < sessionContent.getVersion())) {
				System.out.println("-----------------------> store content:"
						+ sessionContent.toString()
						+ "<-----------------------");
				sessionMap.put(sessionId,
						(SessionContent) sessionContent.clone());
			} else {
				System.out
						.println("session content ignored due to older version"
								+ ownCopyOfSessionContent.getVersion());
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized SessionContent getSession(SessionID sessionId) {
		// TODO Auto-generated method stub
		// SessionContent sc = new SessionContent();
		// sc.setExpirationTimestamp(currentTimestamp.getTime() +
		// cookieExpirationTimeInSec * 1000);
		// sc.setMessage("Hello World");
		// sc.setVersion(0);
		// sessionMap.put(sessionId, sc);
		SessionContent c = sessionMap.get(sessionId);

		// if (c == null) {
		// c = new SessionContent();
		// c.setMessage("Hello User");
		// c.setVersion(0);
		// Timestamp currentTs = new Timestamp((new Date()).getTime());
		// Timestamp expirationTimestamp = new Timestamp(currentTs.getTime()
		// + cookieExpirationTimeInSec * 1000);
		// c.setExpirationTimestamp(expirationTimestamp);
		// sessionMap.put(sessionId, c);
		// System.out.println(sessionId + " is added, Message:"
		// + c.getMessage());
		// }
		if (c != null) {
			System.err
					.println("===========================> get session locally for timestap:"
							+ (c.getExpirationTimestamp() == null ? "null" : c
									.getExpirationTimestamp().toString()));
		}
		return c;
	}

	@Override
	public synchronized void removeSession(SessionID sessionId) {
		// TODO Auto-generated method stub
		System.out.println(sessionId.getSessionNumber() + " is deleted!");
		sessionMap.remove(sessionId);

	}

	@Override
	@Scheduled(initialDelay=60000, fixedRate=60000)
	public synchronized void removeExpiredSession() {
		// TODO Auto-generated method stub
		System.out
				.println("Removing Expired Session  <<-------------------------------------");
		Timestamp currentTs = new Timestamp((new Date()).getTime());
		Iterator<Entry<SessionID, SessionContent>> iter = this.sessionMap
				.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<SessionID, SessionContent> entry = iter.next();
			SessionContent sessionContent = entry.getValue();
			if (sessionContent.getExpirationTimestamp().before(currentTs)) {
				// ---------------------------------------------------
				System.out
						.println(entry.getKey()
								+ "Expire Time"
								+ entry.getValue().getExpirationTimestamp()
										.toString()
								+ " current time "
								+ currentTs.toString()
								+ " removed from session table <<------------------------------");
				// ---------------------------------------------------
				iter.remove();
			}
		}
	}

	@Override
	public synchronized void storeSessionLocally(SessionID sessionId,
			SessionContent sessionContent) {
		// TODO Auto-generated method stub
		updateSession(sessionId, sessionContent);
	}

	@Override
	public synchronized List<String> getContentList() {
		List<String> list = new ArrayList<String>();
		Iterator it = sessionMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			list.add(((SessionID) pairs.getKey()).toString() + " = "
					+ ((SessionContent) pairs.getValue()).toString());
		}
		return list;
	}

}
