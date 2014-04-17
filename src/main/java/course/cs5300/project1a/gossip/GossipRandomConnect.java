package course.cs5300.project1a.gossip;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import course.cs5300.project1a.dao.BootstrapViewDAO;
import course.cs5300.project1a.pojo.View;
import course.cs5300.project1a.rpc.client.service.RPCClientService;
import course.cs5300.project1a.service.GetLocalIPService;

@Named
public class GossipRandomConnect {
	private static final int CONNECT_INTERVAL = 45;
	private static final int VIEW_SIZE = 5;
	private static final int GOSSIP_SECS = 60;

	@Inject
	private BootstrapViewDAO bootstrapViewDAO;

	@Inject
	private GetLocalIPService getLocalIPService;
	@Inject
	private RPCClientService rpcClientService;

	@Inject
	private GossipService gossipService;

	public void updateBootstrapView() {
		// TODO Auto-generated method stub
		View bootstrapView = this.bootstrapViewDAO.getBootstrapView();
		View copy = this.gossipService.getView();
		bootstrapView.union(copy);
		bootstrapView.insert(this.getLocalIPService.getLocalIP());
		bootstrapView.shrink(VIEW_SIZE);
		this.bootstrapViewDAO.setBootstrapView(bootstrapView);
		this.gossipService.union(bootstrapView);
	}

	@Scheduled(initialDelay = 10000, fixedRate = java.lang.Long.MAX_VALUE)
	@Async
	public void runUpdateBootstrapView() {
		while (true) {
			System.out.println("Updating bootstrap view");
			this.updateBootstrapView();
			Random generator = new Random();
			try {
				Thread.sleep((GOSSIP_SECS / 2 * 1000)
						+ generator.nextInt(GOSSIP_SECS * 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

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
