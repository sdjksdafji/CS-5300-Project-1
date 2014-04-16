package course.cs5300.project1a.rpc.lib.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import course.cs5300.project1a.pojo.SessionContent;
import course.cs5300.project1a.pojo.SessionID;
import course.cs5300.project1a.pojo.View;
import course.cs5300.project1a.rpc.lib.RPCOperationCode;
import course.cs5300.project1a.rpc.lib.service.RPCBufferService;

@Named
public class RPCBufferServiceImpl implements RPCBufferService {

	@Override
	public int sendReadSessionBuffer(byte[] outBuf, int callId,
			SessionID sessionId, long versionNumber) {
		// TODO Auto-generated method stub

		byte[] cid = int2byte(callId);
		byte2byte(0, outBuf, cid);

		outBuf[4] = RPCOperationCode.SESSION_READ;

		byte[] snum = long2byte(sessionId.getSessionNumber());
		byte2byte(5, outBuf, snum);

		byte[] sid = sessionId.getServerID().getAddress(); // - Yaolin
		byte2byte(13, outBuf, sid);

		byte[] vnum = long2byte(versionNumber);
		byte2byte(17, outBuf, vnum);

		return 25;

	}

	@Override
	public SessionID getSessionIDFromReadSessionBuffer(byte[] inBuf,
			int bufferLength) {
		// TODO Auto-generated method stub
		byte[] temp = getByteArray(inBuf, 5, 8);
		long snum = byte2long(temp);
		byte[] stemp = getByteArray(inBuf, 13, 4);
		InetAddress sid = null;
		try {
			sid = InetAddress.getByAddress(stemp);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SessionID id = new SessionID(snum, sid);

		return id;
	}

	@Override
	public long getVersionNumFromReadSessionBuffer(byte[] inBuf,
			int bufferLength) {
		// TODO Auto-generated method stub
		byte[] temp = getByteArray(inBuf, 17, 8);
		return byte2long(temp);
	}

	/** 2 */
	@Override
	public int sendRepleyOfReadSessionBuffer(byte[] outBuf, int callId,
			SessionContent sessionContent) {
		// TODO Auto-generated method stub
		byte[] cid = int2byte(callId);
		byte2byte(0, outBuf, cid);

		outBuf[4] = RPCOperationCode.SESSION_READ;

		if(sessionContent ==null){
			sessionContent = new SessionContent();
			sessionContent.setMessage("");
			sessionContent.setVersion(-1);
			sessionContent.setExpirationTimestamp(new Timestamp(1));
		}
		long ver = sessionContent.getVersion();
		byte[] verArray = long2byte(ver);
		byte2byte(5, outBuf, verArray);

		// timestamp -> long -> byte array
		// String time = sessionContent.getExpirationTimestamp().toString();
		// byte[] timeArray = string2byte(time, 8);
		// byte2byte(13, outBuf, timeArray);
		long time = sessionContent.getExpirationTimestamp().getTime();
		byte[] timeArray = long2byte(time);
		byte2byte(13, outBuf, timeArray);

		String mes = sessionContent.getMessage();
		int mesSize = mes.length() * 2;
		byte[] mesArray;
		if (mesSize > 490) {
			String str = mes.substring(0, 490 / 2);
			mesArray = string2byte(str);
			byte2byte(21, outBuf, mesArray);
			return 21 + 490;
		} else {
			mesArray = string2byte(mes);
			byte2byte(21, outBuf, mesArray);
			return 21 + mesSize;
		}

	}

	@Override
	public SessionContent getSessionContentFromReplyOfReadSessionBuffer(
			byte[] inBuf, int bufferLength) {
		// TODO Auto-generated method stub
		byte[] temp = getByteArray(inBuf, 5, 8);
		long version = byte2long(temp);
		// Timestamp timestamp = Timestamp.valueOf(byte2string(inBuf, 13, 8));
		byte[] temp2 = getByteArray(inBuf, 13, 8);
		long ts = byte2long(temp2);
		Timestamp timestamp = new Timestamp(ts);
		byte[] mess = getByteArray(inBuf, 21, bufferLength - 21);
		String message = byte2string(mess);

		SessionContent content = null;
		if(version >= 0){
			content = new SessionContent(message, version, timestamp);
		}
		return content;
	}

	/** 3 */
	@Override
	public int sendWriteSessionBuffer(byte[] outBuf, int callId,
			SessionID sessionId, SessionContent sessionContent) {
		// TODO Auto-generated method stub
		byte[] cid = int2byte(callId);
		byte2byte(0, outBuf, cid);

		outBuf[4] = RPCOperationCode.SESSION_WRITE;

		byte[] snum = long2byte(sessionId.getSessionNumber());
		byte2byte(5, outBuf, snum);

		byte[] sid = sessionId.getServerID().getAddress(); // - Yaolin
		byte2byte(13, outBuf, sid);

		long ver = sessionContent.getVersion();
		byte[] verArray = long2byte(ver);
		byte2byte(17, outBuf, verArray);

		// String time = sessionContent.getExpirationTimestamp().toString();
		// byte[] timeArray = string2byte(time, 8);
		// byte2byte(25, outBuf, timeArray);
		long time = Long.MAX_VALUE;
		if (sessionContent.getExpirationTimestamp() != null) {
			time = sessionContent.getExpirationTimestamp().getTime();
		}
		byte[] timeArray = long2byte(time);
		byte2byte(25, outBuf, timeArray);

		String mes = sessionContent.getMessage();
		int mesSize = mes.length() * 2;
		byte[] mesArray;
		if (mesSize > 470) {
			String str = mes.substring(0, 470 / 2);
			mesArray = string2byte(str);
			byte2byte(33, outBuf, mesArray);
			return 33 + 470;
		} else {
			mesArray = string2byte(mes);
			byte2byte(33, outBuf, mesArray);
			return 33 + mesSize;
		}

	}

	@Override
	public SessionID getSessionIdFromWriteSessionBuffer(byte[] inBuf,
			int bufferLength) {
		// TODO Auto-generated method stub
		byte[] temp = getByteArray(inBuf, 5, 8);
		long snum = byte2long(temp);
		byte[] stemp = getByteArray(inBuf, 13, 4);
		InetAddress sid = null;
		try {
			sid = InetAddress.getByAddress(stemp);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SessionID id = new SessionID(snum, sid);

		return id;
	}

	@Override
	public SessionContent getSessionContentFromWriteSessionBuffer(byte[] inBuf,
			int bufferLength) {
		// TODO Auto-generated method stub
		byte[] temp = getByteArray(inBuf, 17, 8);
		long version = byte2long(temp);
		// Timestamp timestamp = Timestamp.valueOf(byte2string(inBuf, 25, 8));
		byte[] temp2 = getByteArray(inBuf, 25, 8);
		long ts = byte2long(temp2);
		Timestamp timestamp = new Timestamp(ts);
		byte[] mess = getByteArray(inBuf, 33, bufferLength - 33);
		String message = byte2string(mess);

		SessionContent content = new SessionContent(message, version, timestamp);
		return content;
	}

	@Override
	public int sendReplyOfWriteSessionBuffer(byte[] outBuf, int callId) {
		// TODO Auto-generated method stub
		byte[] cid = int2byte(callId);
		byte2byte(0, outBuf, cid);

		outBuf[4] = RPCOperationCode.SESSION_WRITE;
		return 5;
	}

	@Override
	public int sendGetViewBuffer(byte[] outBuf, int callId) {
		// TODO Auto-generated method stub
		byte[] cid = int2byte(callId);
		byte2byte(0, outBuf, cid);

		outBuf[4] = RPCOperationCode.GET_VIEW;
		return 5;
	}

	@Override
	public int sendReplyOfGetViewBuffer(byte[] outBuf, int callId, View view) {
		// TODO Auto-generated method stub
		byte[] cid = int2byte(callId);
		byte2byte(0, outBuf, cid);

		outBuf[4] = RPCOperationCode.SESSION_WRITE;

		// byte[] viewArray = new byte[508];
		Set<InetAddress> ip = view.getIpAddresses();
		Iterator<InetAddress> iterator = ip.iterator();
		int offset = 0;
		int count = 0;
		while (iterator.hasNext()) {
			count++;
			InetAddress i = iterator.next();
			byte[] temp = i.getAddress();
			byte2byte(5 + offset, outBuf, temp);
			offset += 4;
		}
		return 5 + count * 4;
	}

	@Override
	public List<InetAddress> getViewFromReplyOfGetViewBuffer(byte[] inBuf,
			int bufferLength) {
		// TODO Auto-generated method stub
		byte[] ip = getByteArray(inBuf, 5, inBuf.length - 5);
		List<InetAddress> res = new ArrayList<InetAddress>();
		int i = 0;
		while (i < ip.length) {
			byte[] temp = getByteArray(ip, i, 4);
			InetAddress tmp = null;
			try {
				tmp = InetAddress.getByAddress(temp);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (tmp == null) {
				i += 4;
				continue;
			} else {
				res.add(tmp);
			}
			i += 4;
		}

		return res;
	}

	@Override
	public int getCallIdFromReplyBuffer(byte[] buf) {
		// TODO Auto-generated method stub
		byte[] temp = getByteArray(buf, 0, 4);
		return byte2int(temp);

	}

	@Override
	public byte getOpCodeFromReplyBuffer(byte[] buf) {
		// TODO Auto-generated method stub
		return buf[4];
	}

	/** helper functions start here */
	/** add son array to the mom array at exact location */
	public void byte2byte(int index, byte[] mom, byte[] son) {
		int i = index;
		for (byte j : son) {
			mom[i++] = j;
		}
	}

	/** convert value to byte array */
	public byte[] int2byte(int num) {
		byte[] res = new byte[4];
		res[0] = (byte) ((num >> 24) & 0xFF);
		res[1] = (byte) ((num >> 16) & 0xFF);
		res[2] = (byte) ((num >> 8) & 0xFF);
		res[3] = (byte) (num & 0xFF);
		return res;
	}

	public byte[] long2byte(long num) {
		byte[] res = new byte[8];
		res[0] = (byte) ((num >> 56) & 0xFF);
		res[1] = (byte) ((num >> 48) & 0xFF);
		res[2] = (byte) ((num >> 40) & 0xFF);
		res[3] = (byte) ((num >> 32) & 0xFF);
		res[4] = (byte) ((num >> 24) & 0xFF);
		res[5] = (byte) ((num >> 16) & 0xFF);
		res[6] = (byte) ((num >> 8) & 0xFF);
		res[7] = (byte) (num & 0xFF);

		return res;
	}

	public byte[] string2byte(String s) { // //String -> char array -> byte
											// array
	// byte[] res = new byte[len]; // len is the length of the output byte
	// array, limit the length
	// char[] tmp = s.toCharArray();
		return s.getBytes();
		// return res;
	}

	/** get son byte array from mom array */
	public byte[] getByteArray(byte[] mom, int index, int len) {
		byte[] son = new byte[len];
		for (int i = 0; i < len; i++) {
			son[i] = mom[index + i];
		}

		return son;
	}

	/** convert byte array to value */
	public int byte2int(byte[] input) {
		int res = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			res += (input[i] & 0x000000FF) << shift;
		}
		return res;
	}

	public long byte2long(byte[] input) {
		long res = 0;
		for (int i = 0; i < 8; i++) {
			long shift = (8 - 1 - i) * 8;
			long temp = input[i] & 0x000000FF;
			res += (temp) << shift;
		}

		return res;
	}

	public String byte2string(byte[] input) {
		// String res = "";
		return new String(input);
		// return res;
	}

	public int getString2byteSize(String mes) {
		// TODO Auto-generated method stub
		int size = mes.length();
		return 2 * size;
	}

}
