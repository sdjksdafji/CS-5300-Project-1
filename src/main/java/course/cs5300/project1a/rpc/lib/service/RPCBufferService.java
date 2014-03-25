package course.cs5300.project1a.rpc.lib.service;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;

public interface RPCBufferService {

	// fill outBuf with [ callID, operationSESSIONREAD, sessionID,
	// sessionVersionNum ]
	// return the length of buffer
	public int sendReadSessionBuffer(byte[] outBuf, int callId,
			SessionID sessionId, long versionNumber);

	// reverse of the previous function
	public void receiveReadSessionBuffer(byte[] inBuf, int bufferLength,
			Integer callId, SessionID sessionId, Long versionNumber);
	
	public int sendRepleyReadSessionBuffer();
	public int receiveRepleyReadSessionBuffer();
	
	// fill outBuf with [ callID, operationSESSIONWRITE, sessionID,
	// sessionContent ]
	// return the length of buffer
	public int sendWriteSessionBuffer(byte[] outBuf, int callId,
			SessionID sessionId, SessionContent sessionContent);
	
	public int receiveWriteSessionBuffer(byte[] outBuf, int callId,
			SessionID sessionId, SessionContent sessionContent);
	
	public int sendRepleyWriteSessionBuffer();
	public int receiveRepleyWriteSessionBuffer();
}
