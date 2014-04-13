package course.cs5300.project1a.jsfbean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.gossip.GossipService;
import course.cs5300.project1a.pojo.View;
import course.cs5300.project1a.service.GetLocalIPService;

@Named
@Scope("request")
public class ServerInfoBean {
	private String ipAddress;
	private List<String> peerIpList;

	@Inject
	private GetLocalIPService getLocalIpService;
	
	@Inject
	private GossipService gossipService;
	
	@PostConstruct
	public void init(){
		View view = this.gossipService.getView();
		peerIpList = new ArrayList<String>();
		for(InetAddress ip:view.getIpAddresses()){
			peerIpList.add(ip.toString());
		}
	}

	public String getIpAddress() {
		this.ipAddress = this.getLocalIpService.getLocalIP().toString();
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public List<String> getPeerIpList() {
		return peerIpList;
	}

	public void setPeerIpList(List<String> peerIpList) {
		this.peerIpList = peerIpList;
	}
	
	
}
