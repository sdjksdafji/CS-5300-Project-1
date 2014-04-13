package course.cs5300.project1a.pojo;

import java.net.InetAddress;

public class SessionID {
	private long sessionNumber;
	private InetAddress serverID;

	public SessionID(long sessionNumber, InetAddress serverID) {
		super();
		this.sessionNumber = sessionNumber;
		this.serverID = serverID;
	}

	public long getSessionNumber() {
		return sessionNumber;
	}

	public void setSessionNumber(long sessionNumber) {
		this.sessionNumber = sessionNumber;
	}

	public InetAddress getServerID() {
		return serverID;
	}

	public void setServerID(InetAddress serverID) {
		this.serverID = serverID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serverID == null) ? 0 : serverID.hashCode());
		result = prime * result
				+ (int) (sessionNumber ^ (sessionNumber >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SessionID other = (SessionID) obj;
		if (serverID == null) {
			if (other.serverID != null)
				return false;
		} else if (!serverID.equals(other.serverID))
			return false;
		if (sessionNumber != other.sessionNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SessionID [sessionNumber=" + sessionNumber + ", serverID="
				+ serverID.toString() + "]";
	}

}
