package course.cs5300.project1a.pojo;

import java.sql.Timestamp;

public class SessionContent {
	private String message;
	private long version;
	private Timestamp expirationTimestamp;

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
		this.message = message;
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
	}

}
