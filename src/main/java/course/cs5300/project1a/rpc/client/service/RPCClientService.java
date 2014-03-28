package course.cs5300.project1a.rpc.client.service;

import java.net.InetAddress;
import java.util.List;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;

public interface RPCClientService {
	public SessionContent readSession(InetAddress serverId,
			SessionID sessionId, long version);

	public boolean writeSession(InetAddress serverId, SessionID sessionId,
			SessionContent sessionContent);

	public List<InetAddress> getView(InetAddress serverId);
}
