package course.cs5300.project1a.dao.impl;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import course.cs5300.project1a.dao.BootstrapViewDAO;
import course.cs5300.project1a.pojo.View;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml" })

public class BootstrapViewDAOImplTest {

	@Inject
	BootstrapViewDAO bootstrapViewDAO;
	@Test
	public void test() {
		View view = new View();
		InetAddress a = null;
		try {
			a = InetAddress.getByName("192.1.1.1");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<InetAddress> s = new HashSet<InetAddress>();
		s.add(a);
		view.setIpAddresses(s);
		bootstrapViewDAO.setBootstrapView(view);
		View returnedView = bootstrapViewDAO.getBootstrapView();
		for(InetAddress ip:returnedView.getIpAddresses()){
			System.out.println(ip.toString());
		}
		
	}

}
