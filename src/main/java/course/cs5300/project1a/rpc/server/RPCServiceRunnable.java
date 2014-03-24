package course.cs5300.project1a.rpc.server;

import java.net.DatagramPacket;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

@Named
@Scope("prototype")
public class RPCServiceRunnable implements Runnable {

	private byte[] inBuf;
	private DatagramPacket udpPacket;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("packet received");
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

}
