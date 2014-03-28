package course.cs5300.project1a.rpc.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.rpc.lib.RPCOperationCode;
import course.cs5300.project1a.rpc.lib.service.RPCBufferService;

@Named
@Scope("prototype")
public class RPCServiceRunnable implements Runnable {

	private byte[] inBuf;
	private DatagramPacket udpPacket;
	private DatagramSocket udpSocket;

	@Inject
	private RPCBufferService rpcBufferService;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("packet received from "
				+ this.udpPacket.getAddress().toString() + ":"
				+ this.udpPacket.getPort());
		int opCode = this.rpcBufferService.getOpCodeFromReplyBuffer(inBuf);
		byte[] outBuf = new byte[RPCServerListener.RPC_PORT];
		switch (opCode) {
		case RPCOperationCode.SESSION_READ:

			break;
		case RPCOperationCode.SESSION_WRITE:

			break;

		case RPCOperationCode.GET_VIEW:

			break;
		}
	}

	public byte[] getInBuf() {
		return inBuf;
	}

	public void setInBuf(byte[] inBuf) {
		this.inBuf = inBuf;
	}

	public DatagramPacket getUdpPacket() {
		return udpPacket;
	}

	public void setUdpPacket(DatagramPacket udpPacket) {
		this.udpPacket = udpPacket;
	}

	public DatagramSocket getUdpSocket() {
		return udpSocket;
	}

	public void setUdpSocket(DatagramSocket udpSocket) {
		this.udpSocket = udpSocket;
	}

}
