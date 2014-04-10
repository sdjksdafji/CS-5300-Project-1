package course.cs5300.project1a.rpc.client.service.impl;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.rpc.client.service.RPCClientService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class RPCClientServiceImplTest extends RPCClientServiceImpl {

	@Inject
	RPCClientService rpcClientService;

	public static String server = "localhost";

	@Test
	public void testReadSession() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("RPCClientServiceImpl readSession testing");
		InetAddress testServer = null;
		try {
			testServer = InetAddress.getByName(server);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert (testServer != null);

		SessionContent returnVal = this.rpcClientService.readSession(
				testServer, null, 0);
		System.out.println("RPCClientServiceImpl readSession returns: "
				+ (returnVal == null ? "null" : returnVal.toString()));
	}

	@Test
	public void testWriteSession() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("RPCClientServiceImpl writeSession testing");
		InetAddress testServer = null;
		try {
			testServer = InetAddress.getByName(server);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert (testServer != null);

		boolean returnVal = this.writeSession(testServer, null, null);
		System.out.println("RPCClientServiceImpl writeSession returns: "
				+ returnVal);
	}

	@Test
	public void testGetView() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("RPCClientServiceImpl getView testing");
		InetAddress testServer = null;
		try {
			testServer = InetAddress.getByName(server);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert (testServer != null);

		List<InetAddress> returnVal = this.getView(testServer);
		System.out.println("RPCClientServiceImpl getView returns: "
				+ (returnVal == null ? "null" : returnVal.toString()));
	}

}
