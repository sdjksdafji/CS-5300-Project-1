package course.cs5300.project1a.dao.impl;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Vector;

import course.cs5300.project1a.rpc.lib.RPCOperationCode;
import course.cs5300.project1a.rpc.lib.service.*;
import course.cs5300.project1a.service.GetLocalIPService;
import course.cs5300.project1a.service.LocalSessionTableManager;
import course.cs5300.project1a.service.SessionNumberManager;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.dao.SessionDAO;
import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;
import course.cs5300.project1a.rpc.client.service.RPCClientService;
import course.cs5300.project1a.service.implementation.LocalSessionTableManagerImpl;

@SuppressWarnings("unused")
@Named
@Scope("singleton")
public class SessionDAOImpl implements SessionDAO {
	@Inject
	private LocalSessionTableManager localSessionTableManager;
	@Inject
	private SessionNumberManager sessionNumberManager;
	@Inject
	private GetLocalIPService getLocalIpService;
	@Inject
	private RPCClientService rPCClientService;
	public static final int MAX_UDP_PKT_SIZE = 512; // Bytes

	@Override
	public SessionID addSession(SessionContent sessionContent,
			List<InetAddress> metadata) {
		// TODO Auto-generated method stub
		SessionID sessionId = new SessionID(
				this.sessionNumberManager.getSessionNum(),
				this.getLocalIpService.getLocalIP());

		boolean isStoredAtLeastOnce = false;

		for (InetAddress ip : metadata) {
			if (ip == null) {
				continue;
			}
			isStoredAtLeastOnce = isStoredAtLeastOnce
					|| this.rPCClientService.writeSession(ip, sessionId,
							sessionContent);
		}
		if (!isStoredAtLeastOnce) {
			System.err.println("Serious Error, not stored on server");
		}
		return sessionId;
	}

	@Override
	public void updateSession(SessionID sessionId,
			SessionContent sessionContent, List<InetAddress> metadata) {
		// TODO Auto-generated method stub
		localSessionTableManager.updateSession(sessionId, sessionContent);
		for (InetAddress m : metadata) {
			rPCClientService.writeSession(m, sessionId, sessionContent);
		}
	}

	@Override
	public SessionContent getSession(SessionID sessionId,
			List<InetAddress> metadata) {
		// TODO Auto-generated method stub
		if (sessionId == null || metadata == null)
			return null;
		SessionContent localSession = localSessionTableManager
				.getSession(sessionId);
		SessionContent returnedSessionContent = null;
		for (InetAddress m : metadata) {
			if (localSession != null) {
				returnedSessionContent = rPCClientService.readSession(m,
						sessionId, localSession.getVersion());
			} else {
				returnedSessionContent = rPCClientService.readSession(m,
						sessionId, -1);
			}
		}
		return returnedSessionContent;
	}

	@Override
	public void removeSession(SessionID sessionId, List<InetAddress> metadata) {
		// TODO Auto-generated method stub
		localSessionTableManager.removeSession(sessionId);
		for (InetAddress m : metadata) {
			SessionContent sessionContent = new SessionContent();
			sessionContent.setVersion(-1);
			sessionContent.setMessage("");
			rPCClientService.writeSession(m, sessionId, sessionContent);
		}
	}

}
