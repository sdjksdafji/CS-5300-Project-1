package course.cs5300.project1a.rpc.lib.service.impl;

import java.net.InetAddress;
import java.util.List;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;
import course.cs5300.project1a.rpc.lib.service.RPCBufferService;

public class RPCBufferServiceImpl implements RPCBufferService {

	@Override
	public int sendReadSessionBuffer(byte[] outBuf, int callId,
			SessionID sessionId, long versionNumber) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SessionID getSessionIDFromReadSessionBuffer(byte[] inBuf,
			int bufferLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getVersionNumFromReadSessionBuffer(byte[] inBuf,
			int bufferLength) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sendRepleyOfReadSessionBuffer(byte[] outBuf, int callId,
			SessionContent sessionContent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SessionContent getSessionContentFromReplyOfReadSessionBuffer(
			byte[] inBuf, int bufferLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int sendWriteSessionBuffer(byte[] outBuf, int callId,
			SessionID sessionId, SessionContent sessionContent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SessionID getSessionIdFromWriteSessionBuffer(byte[] inBuf,
			int bufferLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionContent getSessionContentFromWriteSessionBuffer(byte[] inBuf,
			int bufferLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int sendReplyOfWriteSessionBuffer(byte[] outBuf, int callId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sendGetViewBuffer(byte[] outBuf, int callId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sendReplyOfGetViewBuffer(byte[] outBuf, int callId,
			View view) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<InetAddress> getViewFromReplyOfGetViewBuffer(byte[] inBuf,
			int bufferLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCallIdFromReplyBuffer(byte[] buf) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getOpCodeFromReplyBuffer(byte[] buf) {
		// TODO Auto-generated method stub
		return 0;
	}

}
