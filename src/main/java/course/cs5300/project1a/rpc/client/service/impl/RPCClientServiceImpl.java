package course.cs5300.project1a.rpc.client.service.impl;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import course.cs5300.project1a.gossip.GossipService;
import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;
import course.cs5300.project1a.rpc.client.service.RPCClientService;
import course.cs5300.project1a.rpc.lib.service.CallIdService;
import course.cs5300.project1a.rpc.lib.service.RPCBufferService;
import course.cs5300.project1a.rpc.server.RPCServerListener;

@Named
public class RPCClientServiceImpl implements RPCClientService {

	@Inject
	private CallIdService callIdService;

	@Inject
	private RPCBufferService rpcBufferService;

	@Inject
	private GossipService gossipService;

	@Override
	public SessionContent readSession(InetAddress serverId,
			SessionID sessionId, long version) {
		DatagramSocket udpSocket = null;
		try {
			udpSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		}

		int callId = this.callIdService.getCallID();
		byte[] outBuf = new byte[RPCServerListener.BUFFER_SIZE];
		int packetLength = this.rpcBufferService.sendReadSessionBuffer(outBuf,
				callId, sessionId, version);

		// send packet for one address, in project description it send to a
		// group of addresses
		DatagramPacket sendPkt = new DatagramPacket(outBuf, packetLength,
				serverId, RPCServerListener.RPC_PORT);
		try {
			udpSocket.send(sendPkt);
			System.out.println("read session rpc packet sent !!!");
		} catch (IOException e) {
			e.printStackTrace();
			udpSocket.close();
			return null;
		}

		try {
			udpSocket.setSoTimeout(RPCServerListener.SOCKET_TIMEOUT);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			udpSocket.close();
			return null;
		}
		byte[] inBuf = new byte[RPCServerListener.BUFFER_SIZE];
		DatagramPacket recvPkt = new DatagramPacket(inBuf, inBuf.length);
		try {
			do {
				recvPkt.setLength(inBuf.length);
				udpSocket.receive(recvPkt);

			} while (this.rpcBufferService.getCallIdFromReplyBuffer(inBuf) != callId);
		} catch (InterruptedIOException iioe) {
			// timeout
			this.gossipService.remove(serverId);
			System.out.println("time out !!");
			recvPkt = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		udpSocket.close();

		if (recvPkt != null) {
			return this.rpcBufferService
					.getSessionContentFromReplyOfReadSessionBuffer(inBuf,
							inBuf.length);
		} else {

			return null;
		}
	}

	@Override
	public boolean writeSession(InetAddress serverId, SessionID sessionId,
			SessionContent sessionContent) {
		DatagramSocket udpSocket = null;
		try {
			udpSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}

		int callId = this.callIdService.getCallID();
		byte[] outBuf = new byte[RPCServerListener.BUFFER_SIZE];
		int packetLength = this.rpcBufferService.sendWriteSessionBuffer(outBuf,
				callId, sessionId, sessionContent);

		DatagramPacket sendPkt = new DatagramPacket(outBuf, packetLength,
				serverId, RPCServerListener.RPC_PORT);
		try {
			udpSocket.send(sendPkt);
			System.out.println("write session rpc packet sent !!!");
		} catch (IOException e) {
			e.printStackTrace();
			udpSocket.close();
			return false;
		}

		try {
			udpSocket.setSoTimeout(RPCServerListener.SOCKET_TIMEOUT);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			udpSocket.close();
			return false;
		}
		byte[] inBuf = new byte[RPCServerListener.BUFFER_SIZE];
		DatagramPacket recvPkt = new DatagramPacket(inBuf, inBuf.length);
		try {
			do {
				recvPkt.setLength(inBuf.length);
				udpSocket.receive(recvPkt);
			} while (this.rpcBufferService.getCallIdFromReplyBuffer(inBuf) != callId);
		} catch (InterruptedIOException iioe) {
			// timeout
			this.gossipService.remove(serverId);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		udpSocket.close();
		return true;
	}

	@Override
	public List<InetAddress> getView(InetAddress serverId) {
		DatagramSocket udpSocket = null;
		try {
			udpSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		}

		int callId = this.callIdService.getCallID();
		byte[] outBuf = new byte[RPCServerListener.BUFFER_SIZE];
		int packetLength = this.rpcBufferService.sendGetViewBuffer(outBuf,
				callId);

		DatagramPacket sendPkt = new DatagramPacket(outBuf, packetLength,
				serverId, RPCServerListener.RPC_PORT);
		try {
			udpSocket.send(sendPkt);
			System.out.println("read session rpc packet sent !!!");
		} catch (IOException e) {
			e.printStackTrace();
			udpSocket.close();
			return null;
		}
		try {
			udpSocket.setSoTimeout(RPCServerListener.SOCKET_TIMEOUT);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			udpSocket.close();
			return null;
		}
		byte[] inBuf = new byte[RPCServerListener.BUFFER_SIZE];
		DatagramPacket recvPkt = new DatagramPacket(inBuf, inBuf.length);
		try {
			do {
				recvPkt.setLength(inBuf.length);
				udpSocket.receive(recvPkt);
			} while (this.rpcBufferService.getCallIdFromReplyBuffer(inBuf) != callId);
		} catch (InterruptedIOException iioe) {
			// timeout
			this.gossipService.remove(serverId);
			recvPkt = null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		udpSocket.close();

		if (recvPkt != null) {
			return this.rpcBufferService.getViewFromReplyOfGetViewBuffer(inBuf,
					inBuf.length);
		} else {

			return null;
		}
	}

}
