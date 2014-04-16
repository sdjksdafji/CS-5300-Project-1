package course.cs5300.project1a.rpc.lib.service;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class RPCBufferServiceTest {
	
	@Inject
	private RPCBufferService rpcBufferService;

	@Test
	public void test() throws UnknownHostException {
		SessionID sessionId = new SessionID(2,InetAddress.getLocalHost());
		SessionContent sessionContent = new SessionContent();
		sessionContent.setMessage("testMessage");
		sessionContent.setVersion(891);
		java.util.Date date = new java.util.Date();
		Timestamp ts = new Timestamp(date.getTime());
		sessionContent.setExpirationTimestamp(ts);
		System.out.println(ts.toString());
		System.out.println(ts.getTime());
		byte[] inBuf = new byte[1024];
		int l1 = this.rpcBufferService.sendRepleyOfReadSessionBuffer(inBuf, 1, sessionContent);
		SessionContent sc = this.rpcBufferService.getSessionContentFromReplyOfReadSessionBuffer(inBuf,l1 );
		assert(sc.getExpirationTimestamp().getTime() == sessionContent.getExpirationTimestamp().getTime());
		System.out.println(sc.getExpirationTimestamp().toString());
		System.out.println(sc.getExpirationTimestamp().getTime());
		
		int l2 = this.rpcBufferService.sendWriteSessionBuffer(inBuf, 234, sessionId, sessionContent);
		SessionContent sc2 = this.rpcBufferService.getSessionContentFromWriteSessionBuffer(inBuf, l2);
		assert(sc2.getExpirationTimestamp().getTime() == sessionContent.getExpirationTimestamp().getTime());
		System.out.println(sc2.getExpirationTimestamp().toString());
		System.out.println(sc.getExpirationTimestamp().getTime());
	}

}
