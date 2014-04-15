package course.cs5300.project1a.rpc.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.gossip.GossipService;
import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;
import course.cs5300.project1a.pojo.View;
import course.cs5300.project1a.rpc.lib.RPCOperationCode;
import course.cs5300.project1a.rpc.lib.service.RPCBufferService;
import course.cs5300.project1a.service.LocalSessionTableManager;
import course.cs5300.project1a.service.VersionManager;

@Named
@Scope("prototype")
public class RPCServiceRunnable implements Runnable {

	private byte[] inBuf;
	private DatagramPacket requestPacket;
	private DatagramSocket udpSocket;

	@Inject
	private RPCBufferService rpcBufferService;

	@Inject
	private LocalSessionTableManager localSessionTableManager;

	@Inject
	private GossipService gossipService;
	
	@Inject
	private VersionManager versionManager;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("packet received from "
				+ this.requestPacket.getAddress().toString() + ":"
				+ this.requestPacket.getPort());
		int callId = this.rpcBufferService.getCallIdFromReplyBuffer(inBuf);
		int opCode = this.rpcBufferService.getOpCodeFromReplyBuffer(inBuf);
		byte[] outBuf = new byte[RPCServerListener.RPC_PORT];
		int replyPacktLength = -1;
		switch (opCode) {
		case RPCOperationCode.SESSION_READ: {
			SessionID sessionId = this.rpcBufferService
					.getSessionIDFromReadSessionBuffer(this.inBuf,
							this.inBuf.length);
			long versionNum = this.rpcBufferService
					.getVersionNumFromReadSessionBuffer(this.inBuf,
							this.inBuf.length);
			SessionContent returnSession = this.localSessionTableManager
					.getSession(sessionId);
			if (returnSession != null
					&& returnSession.getVersion() < versionNum) {
				returnSession = null;
			}
			replyPacktLength = this.rpcBufferService
					.sendRepleyOfReadSessionBuffer(outBuf, callId,
							returnSession);
			break;
		}
		case RPCOperationCode.SESSION_WRITE: {
			SessionID sessionId = this.rpcBufferService
					.getSessionIdFromWriteSessionBuffer(inBuf, inBuf.length);
			SessionContent sessionContent = this.rpcBufferService
					.getSessionContentFromWriteSessionBuffer(inBuf,
							inBuf.length);
			this.versionManager.updateVersion(sessionContent.getVersion());
			this.localSessionTableManager.storeSessionLocally(sessionId,
					sessionContent);
			replyPacktLength = this.rpcBufferService
					.sendReplyOfWriteSessionBuffer(outBuf, callId);
			break;
		}
		case RPCOperationCode.GET_VIEW: {
			View view = this.gossipService.getView();
			replyPacktLength = this.rpcBufferService.sendReplyOfGetViewBuffer(
					outBuf, callId, view);
			break;
		}
		}

		DatagramPacket replyPkt = new DatagramPacket(outBuf, replyPacktLength,
				requestPacket.getAddress(), requestPacket.getPort());
		try {
			this.udpSocket.send(replyPkt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public byte[] getInBuf() {
		return inBuf;
	}

	public void setInBuf(byte[] inBuf) {
		this.inBuf = inBuf;
	}

	public DatagramPacket getUdpPacket() {
		return requestPacket;
	}

	public void setUdpPacket(DatagramPacket udpPacket) {
		this.requestPacket = udpPacket;
	}

	public DatagramSocket getUdpSocket() {
		return udpSocket;
	}

	public void setUdpSocket(DatagramSocket udpSocket) {
		this.udpSocket = udpSocket;
	}

}
