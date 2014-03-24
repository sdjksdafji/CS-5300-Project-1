package course.cs5300.project1a.jsfbean;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.service.GetLocalIPService;

@Named
@Scope("request")
public class ServerInfoBean {
	String ipAddress;

	@Inject
	private GetLocalIPService getLocalIpService;

	public String getIpAddress() {
		this.ipAddress = this.getLocalIpService.getLocalIP().toString();
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
