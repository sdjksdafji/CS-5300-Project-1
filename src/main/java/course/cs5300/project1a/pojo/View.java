package course.cs5300.project1a.pojo;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class View {
	private Set<InetAddress> ipAddresses;

	public View() {
		this.ipAddresses = new HashSet<InetAddress>();
	}

	public Set<InetAddress> getIpAddresses() {
		return this.ipAddresses;
	}

	public void setIpAddresses(Set<InetAddress> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	public void shrink(int k) {
		while (this.ipAddresses.size() > k) {
			InetAddress ip = this.choose();
			this.remove(ip);
		}
	}

	public void insert(InetAddress ip) {
		this.ipAddresses.add(ip);
	}

	public void remove(InetAddress ip) {
		this.ipAddresses.remove(ip);
	}

	public InetAddress choose() {
		if (this.ipAddresses.size() == 0) {
			return null;
		} else {
			Random generator = new Random();
			int index = generator.nextInt(this.ipAddresses.size());
			int i = 0;
			for (InetAddress ip : this.ipAddresses) {
				if (i == index) {
					return ip;
				}
			}
			return null;
		}
	}

	public void union(View view) {
		this.ipAddresses.addAll(view.getIpAddresses());
	}
	
	@Override
	public View clone(){
		View returnVal = new View();
		returnVal.union(this);
		return returnVal;
	}
}
