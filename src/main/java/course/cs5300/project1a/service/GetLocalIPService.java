package course.cs5300.project1a.service;

import java.net.InetAddress;

public interface GetLocalIPService {
	public InetAddress getLocalIP();

	public String moveFirstSlash(String ip);
}
