package course.cs5300.project1a.gossip;

import java.net.InetAddress;

import course.cs5300.project1a.pojo.View;

public interface GossipService {
	public void shrink(int k);

	public void insert(InetAddress ip);

	public void remove(InetAddress ip);

	public InetAddress choose();

	public void union(View view);
	
	public View getView();
	
}
