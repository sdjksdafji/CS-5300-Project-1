package course.cs5300.project1a.rpc.lib.service;

import java.net.InetAddress;
import java.util.List;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;
import course.cs5300.project1a.pojo.View;

public interface RPCBufferService {

	// fill outBuf with [ callID, operationSESSIONREAD, sessionID,
	// sessionVersionNum ]
	// return the length of buffer
	// input:[callId, sessionID, versionNumber]
	// output:[outBuf, length]
	public int sendReadSessionBuffer(byte[] outBuf, int callId,
			SessionID sessionId, long versionNumber);

	// reverse of the previous function
	public SessionID getSessionIDFromReadSessionBuffer(byte[] inBuf,
			int bufferLength);

	public long getVersionNumFromReadSessionBuffer(byte[] inBuf,
			int bufferLength);

	// fill outBuf with [ callID, operationSESSIONREAD, session content]
	// return the length of buffer
	// input:[callId, sessionContent]
	// output:[outBuf, length]
	public int sendRepleyOfReadSessionBuffer(byte[] outBuf, int callId,
			SessionContent sessionContent);

	// reverse of the previous function
	public SessionContent getSessionContentFromReplyOfReadSessionBuffer(
			byte[] inBuf, int bufferLength);

	// fill outBuf with [ callID, operationSESSIONWRITE, sessionID,
	// sessionContent ]
	// return the length of buffer
	// input:[callId, sessionID, sessionContent]
	// output:[outBuf, length]
	public int sendWriteSessionBuffer(byte[] outBuf, int callId,
			SessionID sessionId, SessionContent sessionContent);

	// reverse of the previous function
	public SessionID getSessionIdFromWriteSessionBuffer(byte[] inBuf,
			int bufferLength);

	public SessionContent getSessionContentFromWriteSessionBuffer(byte[] inBuf,
			int bufferLength);

	// fill outBuf with [ callID, operationSESSIONWRITE ]
	// return the length of buffer
	// input:[callId]
	// output:[outBuf, length]
	public int sendReplyOfWriteSessionBuffer(byte[] outBuf, int callId);

	// fill outBuf with [ callID, operationSESSIONWRITE ]
	// return the length of buffer
	// input:[callId]
	// output:[outBuf, length]
	public int sendGetViewBuffer(byte[] outBuf, int callId);

	// fill outBuf with [ callID, operationSESSIONWRITE, {ip1, ip2, ip3, ... ,
	// ipn } ]
	// return the length of buffer
	// input:[callId, view]
	// output:[outBuf, length]
	public int sendReplyOfGetViewBuffer(byte[] outBuf, int callId, View view);

	// reverse of previous function
	public List<InetAddress> getViewFromReplyOfGetViewBuffer(byte[] inBuf,
			int bufferLength);

	public int getCallIdFromReplyBuffer(byte[] buf);

	public byte getOpCodeFromReplyBuffer(byte[] buf);
}
