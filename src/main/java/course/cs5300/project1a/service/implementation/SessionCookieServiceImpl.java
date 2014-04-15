package course.cs5300.project1a.service.implementation;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.service.GetLocalIPService;
import course.cs5300.project1a.service.SessionCookieService;
import course.cs5300.project1a.service.LocalSessionTableManager;
import course.cs5300.project1a.pojo.SessionID;
import course.cs5300.project1a.service.*;
import course.cs5300.project1a.dao.*;
import course.cs5300.project1a.gossip.GossipService;

@Named
public class SessionCookieServiceImpl implements SessionCookieService {

	private static final String COOKIE_NAME = "CS5300PROJ1SESSIONBYSW773";

	private static final long cookieExpirationTimeInSec = 30;

	@Inject
	private BootstrapViewDAO bootstrapViewDAO;

	@Inject
	private GetLocalIPService getLocalIPService;

	@Inject
	private LocalSessionTableManager localSessionTableManager;

	@Inject
	private SessionDAO sessionDAO;

	@Inject
	private SessionNumberManager sessionNumberManager;

	@Inject
	private GossipService gossipService;

	@Override
	public SessionContent createSession(HttpServletResponse response,
			Timestamp currentTimestamp, long version) {
		// TODO Auto-generated method stub
		List<InetAddress> metadata = new ArrayList<InetAddress>();
		Timestamp expirationTS = new Timestamp(currentTimestamp.getTime()
				+ cookieExpirationTimeInSec * 1000);
		SessionContent sessionContent = new SessionContent("Hello User",
				version, expirationTS);
		metadata.add(this.getLocalIPService.getLocalIP());
		InetAddress secondaryServer = this.gossipService.choose();
		if (secondaryServer == null) {
			System.err.println("Serious error, gossip returns null address");
			metadata.add(this.getLocalIPService.getLocalIP());
		} else {
			metadata.add(secondaryServer);
		}

		SessionID sessionId = this.sessionDAO.addSession(sessionContent,
				metadata);
		this.writeSessionInfoToCookie(response, sessionId, version, 0, metadata);
		return sessionContent;
	}

	@Override
	public void updateSession(SessionID sessionId,
			HttpServletResponse response, Timestamp currentTimestamp,
			long version) {
		// TODO Auto-generated method stub
		List<InetAddress> metadata = new ArrayList<InetAddress>(
				bootstrapViewDAO.getBootstrapView().getIpAddresses());
		SessionContent sessionContent = sessionDAO.getSession(sessionId,
				metadata);
		// SessionContent sessionContent = this.localSessionTableManager
		// .getSession(sessionId);
		if (sessionContent != null) {
			System.out.println(currentTimestamp.getTime());
			Timestamp expirationTS = new Timestamp(currentTimestamp.getTime()
					+ cookieExpirationTimeInSec * 1000);
			sessionContent.setExpirationTimestamp(expirationTS);
			sessionContent.setVersion(version);
			sessionDAO.updateSession(sessionId, sessionContent, metadata);
			// this.localSessionTableManager.updateSession(sessionId,
			// sessionContent);
			this.writeSessionInfoToCookie(response, sessionId, version, 0,
					metadata);
		}
	}

	@Override
	public void updateSessionMessage(SessionID sessionId, String message) {
		List<InetAddress> metadata = new ArrayList<InetAddress>(
				bootstrapViewDAO.getBootstrapView().getIpAddresses());
		SessionContent sessionContent = sessionDAO.getSession(sessionId,
				metadata);
		// SessionContent sessionContent = this.localSessionTableManager
		// .getSession(sessionId); // null = sessionId
		if (sessionContent != null) {
			sessionContent.setMessage(message);
			sessionDAO.updateSession(sessionId, sessionContent, metadata);
			// this.localSessionTableManager.updateSession(sessionId,
			// sessionContent);
		}
	}

	@Override
	public void deleteSession(SessionID sessionId, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<InetAddress> metadata = new ArrayList<InetAddress>(
				bootstrapViewDAO.getBootstrapView().getIpAddresses());
		sessionDAO.removeSession(sessionId, metadata);
		// this.localSessionTableManager.removeSession(sessionId);
		removeCookie(response);
	}

	@Override
	public SessionID getSessionId(HttpServletRequest request) {
		String cookieVal = this.getCookieVal(request);
		if (cookieVal != null) {
			Scanner scanner = new Scanner(cookieVal).useDelimiter("_");
			System.out.println(cookieVal);
			String serverIpStr = scanner.next();
			long sessionNum = Long.valueOf((scanner.next()));
			scanner.close();
			InetAddress serverId = null;
			try {
				serverId = InetAddress.getByName(this.getLocalIPService
						.moveFirstSlash(serverIpStr));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			SessionID sessionId = new SessionID(sessionNum, serverId);

			return sessionId;
		}
		return null;
	}

	@Override
	public List<InetAddress> getMetadata(HttpServletRequest request) {
		String cookieVal = this.getCookieVal(request);
		if (cookieVal != null) {
			Scanner scanner = new Scanner(cookieVal).useDelimiter("_");
			for (int i = 0; i < 3; i++) {
				scanner.next();
				//System.out.println("Testing: "+scanner.next().toString());
			}
			String primaryIpStr = scanner.next();
			String secondaryIpStr = scanner.next();
			scanner.close();
			InetAddress primaryIp = null;
			InetAddress secondaryIp = null;
			try {
				primaryIp = InetAddress.getByName(this.getLocalIPService
						.moveFirstSlash(primaryIpStr));
				secondaryIp = InetAddress.getByName(this.getLocalIPService
						.moveFirstSlash(secondaryIpStr));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			List<InetAddress> metadata = new ArrayList<InetAddress>();
			metadata.add(primaryIp);
			metadata.add(secondaryIp);

			return metadata;
		}
		return null;
	}

	@Override
	public String getCookieVal(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();
				if (name.equalsIgnoreCase(COOKIE_NAME)) {
					return value;
				}
			}
		}
		return null;
	}

	private void writeSessionInfoToCookie(HttpServletResponse response,
			SessionID sessionId, long version, long expiration,
			List<InetAddress> metadata) {
		String cookieValue = sessionId.getServerID() + "_" + version + "_"
				+ expiration;
		for (InetAddress ip : metadata) {
			cookieValue = cookieValue + "_" + ip.toString();
		}
		Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
		cookie.setMaxAge((int) cookieExpirationTimeInSec);
		response.addCookie(cookie);
		// ---------------------------------------------------
		System.out
				.println("cookie added to response <<------------------------------");
		// ---------------------------------------------------
	}

	private void removeCookie(HttpServletResponse response) {
		String cookieValue = "removed";
		Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		// ---------------------------------------------------
		System.out.println("cookie removed <<------------------------------");
		// ---------------------------------------------------
	}

}
