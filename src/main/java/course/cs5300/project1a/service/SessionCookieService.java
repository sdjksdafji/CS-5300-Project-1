package course.cs5300.project1a.service;

import javax.servlet.http.HttpServletRequest;

import course.cs5300.project1a.pojo.SessionContent;

public interface SessionCookieService {
	public SessionContent getSession(HttpServletRequest request);
	
}
