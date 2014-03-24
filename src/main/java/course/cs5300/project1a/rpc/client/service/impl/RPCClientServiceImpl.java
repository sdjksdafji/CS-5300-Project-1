package course.cs5300.project1a.rpc.client.service.impl;

import java.net.InetAddress;
import java.util.List;

import javax.inject.Named;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;
import course.cs5300.project1a.rpc.client.service.RPCClientService;

@Named
public class RPCClientServiceImpl implements RPCClientService {

	@Override
	public SessionContent readSession(InetAddress serverId,
			SessionID sessionId, long version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeSession(InetAddress serverId,
			SessionContent sessionContent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<InetAddress> getView(InetAddress serverId) {
		// TODO Auto-generated method stub
		return null;
	}

}
