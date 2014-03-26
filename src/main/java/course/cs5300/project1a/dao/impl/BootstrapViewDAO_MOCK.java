package course.cs5300.project1a.dao.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import course.cs5300.project1a.dao.BootstrapViewDAO;

public class BootstrapViewDAO_MOCK implements BootstrapViewDAO {

	@Override
	public void setBootstrapView(List<InetAddress> view) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<InetAddress> getBootstrapView() {
		ArrayList<InetAddress> returnVal = new ArrayList<InetAddress>();
		try {
			returnVal.add(InetAddress.getByName("192.168.1.1"));
			returnVal.add(InetAddress.getByName("192.168.1.2"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnVal;
	}

}
