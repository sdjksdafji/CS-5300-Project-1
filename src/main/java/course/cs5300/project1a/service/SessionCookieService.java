package course.cs5300.project1a.service;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import course.cs5300.project1a.pojo.SessionContent;

public interface SessionCookieService {
	public SessionContent getSession(HttpServletRequest request);

	public void createSession(HttpServletResponse response,
			Timestamp currentTimestamp, long version);
}
