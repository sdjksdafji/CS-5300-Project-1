package course.cs5300.project1a.dao.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import course.cs5300.project1a.dao.BootstrapViewDAO;
import course.cs5300.project1a.pojo.View;

@Named
public class BootstrapViewDAO_MOCK implements BootstrapViewDAO {

	@Override
	public void setBootstrapView(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public View getBootstrapView() {
		View returnVal = new View();
		try {
			returnVal.getIpAddresses().add(InetAddress.getByName("192.168.1.1"));
			returnVal.getIpAddresses().add(InetAddress.getByName("192.168.1.2"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnVal;
	}

}
