package course.cs5300.project1a.service.implementation;

import java.sql.Timestamp;
import java.util.Scanner;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.service.SessionCookieService;
import course.cs5300.project1a.service.SessionStateTableManager;

@Named
public class SessionCookieServiceImpl implements SessionCookieService {

	private static final String COOKIE_NAME = "CS5300PROJ1SESSIONBYSW773";
	private static final long cookieExpirationTimeInSec = 60;

	@Inject
	private SessionStateTableManager sessionStateTableManager;

	@Override
	public SessionContent getSession(HttpServletRequest request) {
		long sessionId = this.getSessionId(request);
		if (sessionId >= 0) {
			return this.sessionStateTableManager.getSession(sessionId);
		}
		return null;
	}

	@Override
	public void createSession(HttpServletResponse response,
			Timestamp currentTimestamp, long version) {
		// TODO Auto-generated method stub
		Timestamp expirationTS = new Timestamp(currentTimestamp.getTime()
				+ cookieExpirationTimeInSec * 60);
		SessionContent sessionContent = new SessionContent("Hello User",
				version, expirationTS);
		long sessionId = this.sessionStateTableManager
				.addSession(sessionContent);
		this.writeSessionInfoToCookie(response, sessionId, version, 0, 0);
	}

	private void writeSessionInfoToCookie(HttpServletResponse response,
			long sessionId, long version, long expiration, long metadata) {
		String cookieValue = sessionId + "_" + version + "_" + expiration + "_"
				+ metadata;
		Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
		cookie.setMaxAge((int) cookieExpirationTimeInSec);
		response.addCookie(cookie);
		// ---------------------------------------------------
		System.out
				.println("cookie added to response <<------------------------------");
		// ---------------------------------------------------
	}

	@Override
	public void updateSession(HttpServletRequest request,
			HttpServletResponse response, Timestamp currentTimestamp,
			long version) {
		// TODO Auto-generated method stub
		long sessionId = this.getSessionId(request);
		SessionContent sessionContent = this.sessionStateTableManager
				.getSession(sessionId);
		if (sessionContent != null) {
			Timestamp expirationTS = new Timestamp(currentTimestamp.getTime()
					+ cookieExpirationTimeInSec * 60);
			sessionContent.setExpirationTimestamp(expirationTS);
			sessionContent.setVersion(version);
			this.sessionStateTableManager.updateSession(sessionId,
					sessionContent);
		}
	}

	private long getSessionId(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();
				if (name.equalsIgnoreCase(COOKIE_NAME)) {
					Scanner scanner = new Scanner(value).useDelimiter("_");
					System.out.println(value == null ? "null" : value);
					long sessionId = scanner.nextLong();
					long version = scanner.nextLong();
					long expiration = scanner.nextLong();
					long metadata = scanner.nextLong();
					scanner.close();
					return sessionId;
				}
			}
		}
		return -1;
	}

}
