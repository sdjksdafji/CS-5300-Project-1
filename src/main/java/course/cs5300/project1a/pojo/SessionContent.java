package course.cs5300.project1a.pojo;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.List;

public class SessionContent {
	private String message; // 512 - 8 - 8 = 496 bytes. internal encoding is 2
							// bytes for each char
	private long version; // 64 bit = 8 bytes
	private Timestamp expirationTimestamp; // getTime() is a long; 64 bits = 8
											// bytes

	private static int STRING_SIZE_LIMIT = 248;

	public SessionContent() {
		super();
	}

	public SessionContent(String message, long version,
			Timestamp expirationTimestamp) {
		super();
		this.message = message;
		this.version = version;
		this.expirationTimestamp = expirationTimestamp;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		SessionContent clonedSessionContent = new SessionContent(this.message,
				this.version, (Timestamp) this.expirationTimestamp.clone());
		return clonedSessionContent;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if (message.length() <= STRING_SIZE_LIMIT) {
			this.message = message;
		} else {
			this.message = message.substring(0, STRING_SIZE_LIMIT);
		}
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Timestamp getExpirationTimestamp() {
		return expirationTimestamp;
	}

	public void setExpirationTimestamp(Timestamp expirationTimestamp) {
		this.expirationTimestamp = expirationTimestamp;
		System.out.println("this.Time2: "+this.expirationTimestamp.toString());
	}

	@Override
	public String toString() {
		return "SessionContent [message=" + message + ", version=" + version
				+ ", expirationTimestamp=" + expirationTimestamp.toString()
				+ "]";
	}

}
