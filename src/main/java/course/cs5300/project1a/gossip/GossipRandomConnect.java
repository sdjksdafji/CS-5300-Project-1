package course.cs5300.project1a.gossip;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import course.cs5300.project1a.pojo.View;
import course.cs5300.project1a.rpc.client.service.RPCClientService;

@Named
public class GossipRandomConnect {
	private static final int CONNECT_INTERVAL = 45;
	@Inject
	private RPCClientService rpcClientService;

	@Inject
	private GossipService gossipService;

	@Scheduled(initialDelay = 5000, fixedRate = CONNECT_INTERVAL * 1000)
	@Async
	public void gossipConnect() {
		System.out.println("gossip connects to a random node in the view");
		InetAddress peer = this.gossipService.choose();
		if (peer != null) {
			List<InetAddress> list = this.rpcClientService.getView(peer);
			Set<InetAddress> set = new HashSet<InetAddress>(list);
			View peerView = new View();
			peerView.setIpAddresses(set);
			this.gossipService.union(peerView);
		}
	}
}
