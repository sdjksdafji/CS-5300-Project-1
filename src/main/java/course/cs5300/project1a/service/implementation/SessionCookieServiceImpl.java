package course.cs5300.project1a.service.implementation;

import java.util.Scanner;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.service.SessionCookieService;
import course.cs5300.project1a.service.SessionStateTableManager;

@Named
public class SessionCookieServiceImpl implements SessionCookieService {
	
	private static final String COOKIE_NAME = "CS5300PROJ1SESSIONBYSW773";
	
	@Inject
	private SessionStateTableManager sessionStateTableManager;

	@Override
	public SessionContent getSession(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie:cookies){
				String name = cookie.getName( );
				String value = cookie.getValue();
				if(name.equalsIgnoreCase(COOKIE_NAME)){
					Scanner scanner = new Scanner(value);
					long sessionId = scanner.nextLong();
					long version = scanner.nextLong();
					long expiration = scanner.nextLong();
					long metadata = scanner.nextLong();
					scanner.close();
					return this.sessionStateTableManager.getSession(sessionId);
				}
			}
		}
		return null;
	}

}
