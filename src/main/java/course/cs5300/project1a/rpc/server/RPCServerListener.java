package course.cs5300.project1a.rpc.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Named
public class RPCServerListener {

	public static final int RPC_PORT = 5301;
	public static final int BUFFER_SIZE = 1024;
	public static final int SOCKET_TIMEOUT = 5 * 1000;

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	@Named("myExecutor")
	private TaskExecutor taskExecutor;

	@Scheduled(initialDelay = 500, fixedRate = java.lang.Long.MAX_VALUE)
	@Async
	public void RPCServer() {
		DatagramSocket udpSocket = null;
		try {
			udpSocket = new DatagramSocket(RPC_PORT);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (udpSocket != null) {
			System.out.println("UDP socket start listening");
			while (true) {
				byte[] inBuf = new byte[BUFFER_SIZE];
				DatagramPacket udpPacket = new DatagramPacket(inBuf,
						inBuf.length);
				try {
					udpSocket.receive(udpPacket);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				RPCServiceRunnable thread = this.applicationContext
						.getBean(RPCServiceRunnable.class);
				thread.setInBuf(inBuf);
				thread.setUdpPacket(udpPacket);
				thread.setUdpSocket(udpSocket);
				this.taskExecutor.execute(thread);
			}
		} else {
			System.err.println("UDP socket failed to initialize");
		}

	}
}
