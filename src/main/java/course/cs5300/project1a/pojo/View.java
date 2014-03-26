package course.cs5300.project1a.pojo;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class View {
	private List<InetAddress> ipAddresses;

	public View() {
		ipAddresses = new ArrayList<InetAddress>();
	}

	public List<InetAddress> getIpAddresses() {
		return ipAddresses;
	}

	public void setIpAddresses(List<InetAddress> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}
}
