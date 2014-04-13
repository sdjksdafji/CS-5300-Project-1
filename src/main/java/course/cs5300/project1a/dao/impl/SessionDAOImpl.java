package course.cs5300.project1a.dao.impl;


import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Vector;

import course.cs5300.project1a.rpc.lib.RPCOperationCode;
import course.cs5300.project1a.rpc.lib.service.*;
import course.cs5300.project1a.service.LocalSessionTableManager;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.dao.SessionDAO;
import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;
import course.cs5300.project1a.rpc.client.service.RPCClientService;
import course.cs5300.project1a.service.implementation.LocalSessionTableManagerImpl;
@Named
@Scope("singleton")
public class SessionDAOImpl implements SessionDAO {
	@Inject
	private LocalSessionTableManager localSessionTableManager;
	@Inject
	private RPCClientService rPCClientService;
	public static final int MAX_UDP_PKT_SIZE = 512;	//Bytes
	@Override
	public SessionID addSession(SessionContent sessionContent,
			List<InetAddress> metadata) {
		// TODO Auto-generated method stub
		SessionID sessionId = localSessionTableManager.addSession(sessionContent);
		
		for(InetAddress m : metadata){
			SessionContent temp = rPCClientService.readSession(m, sessionId, sessionContent.getVersion());
			if(temp==null)	continue;
			sessionId = localSessionTableManager.addSession(temp);
			sessionContent = temp;
		}
		return sessionId;
	}

	@Override
	public void updateSession(SessionID sessionId,
			SessionContent sessionContent, List<InetAddress> metadata) {
		// TODO Auto-generated method stub
		long currentVersion = localSessionTableManager.getSession(sessionId).getVersion();
		if(sessionContent.getVersion()>currentVersion)
			localSessionTableManager.addSession(sessionContent);
	}

	@Override
	public SessionContent getSession(SessionID sessionId,
			List<InetAddress> metadata) {
		// TODO Auto-generated method stub
		return localSessionTableManager.getSession(sessionId);
	}

	@Override
	public void removeSession(SessionID sessionId, List<InetAddress> metadata) {
		// TODO Auto-generated method stub
		localSessionTableManager.removeSession(sessionId);
	}

}
