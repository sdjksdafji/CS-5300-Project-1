package course.cs5300.project1a.service.mock;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.inject.Named;

import course.cs5300.project1a.service.GetLocalIPService;

@Named
public class GetLocalIPMock implements GetLocalIPService {

	@Override
	public InetAddress getLocalIP() {
		// TODO Auto-generated method stub
		try {
			return InetAddress.getByName("192.168.1.110");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
