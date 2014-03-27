package course.cs5300.project1a.gossip;

import java.net.InetAddress;

import javax.inject.Inject;
import javax.inject.Named;

import course.cs5300.project1a.dao.BootstrapViewDAO;
import course.cs5300.project1a.pojo.View;
import course.cs5300.project1a.service.GetLocalIPService;

@Named
public class GossipServiceImpl implements GossipService {

	private static View view = new View();
	private static final int VIEW_SIZE = 5;
	
	@Inject
	private BootstrapViewDAO bootstrapViewDAO;
	
	@Inject
	private GetLocalIPService getLocalIPService;

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
		bootstrapView.union(view);
		bootstrapView.insert(this.getLocalIPService.getLocalIP());
		bootstrapView.shrink(VIEW_SIZE);
		this.bootstrapViewDAO.setBootstrapView(bootstrapView);
	}
	
	

}
