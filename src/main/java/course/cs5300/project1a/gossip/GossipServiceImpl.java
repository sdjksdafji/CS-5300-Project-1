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
public class GossipServiceImpl implements GossipService {

	private static View view = new View();
	private static final int VIEW_SIZE = 5;
	private static final int GOSSIP_SECS = 60;
	private static final int CONNECT_INTERVAL = 45;
	
	@Inject
	private BootstrapViewDAO bootstrapViewDAO;
	
	@Inject
	private GetLocalIPService getLocalIPService;
	
	@Inject
	private RPCClientService rpcClientService;

	@Override
	public synchronized void shrink(int k) {
		// TODO Auto-generated method stub
		view.shrink(k);
	}

	@Override
	public synchronized void insert(InetAddress ip) {
		// TODO Auto-generated method stub
		view.insert(ip);
	}

	@Override
	public synchronized void remove(InetAddress ip) {
		// TODO Auto-generated method stub
		view.remove(ip);
	}

	@Override
	public synchronized InetAddress choose() {
		// TODO Auto-generated method stub
		return view.choose();
	}

	@Override
	public synchronized void union(View view) {
		// TODO Auto-generated method stub
		GossipServiceImpl.view.union(view);
	}

	@Override
	public synchronized View getView() {
		// TODO Auto-generated method stub
		return view.clone();
	}

	@Override
	public void updateBootstrapView() {
		// TODO Auto-generated method stub
		View bootstrapView = this.bootstrapViewDAO.getBootstrapView();
		View copy = this.getView();
		bootstrapView.union(copy);
		bootstrapView.insert(this.getLocalIPService.getLocalIP());
		bootstrapView.shrink(VIEW_SIZE);
		this.bootstrapViewDAO.setBootstrapView(bootstrapView);
	}
	
	@Scheduled(initialDelay = 10000, fixedRate = java.lang.Long.MAX_VALUE)
	@Async
	public void runUpdateBootstrapView(){
		System.out.println("Updating bootstrap view");
		 while(true) {
			 this.updateBootstrapView();
			 Random generator = new Random();
			 try {
				Thread.sleep( (GOSSIP_SECS/2) + generator.nextInt( GOSSIP_SECS ) );
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
		InetAddress peer = this.view.choose();
		if (peer != null) {
			List<InetAddress> list = this.rpcClientService.getView(peer);
			Set<InetAddress> set = new HashSet<InetAddress>(list);
			View peerView = new View();
			peerView.setIpAddresses(set);
			this.union(peerView);
		}
	}


}
