package course.cs5300.project1a.dao.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.dao.BootstrapViewDAO;
import course.cs5300.project1a.pojo.*;
import course.cs5300.project1a.rpc.client.service.RPCClientService;
import course.cs5300.project1a.service.GetLocalIPService;
@Named
@Scope("singleton")
public class BootstrapViewDAOImpl implements BootstrapViewDAO {
	View view;
	@Inject
	private GetLocalIPService getLocalIpService;
	@Inject
	private RPCClientService rPCClientService;
	@Override
	public void setBootstrapView(View view) {
		// TODO Auto-generated method stub
		InetAddress serverId = null;
		try {
			serverId = InetAddress.getByName(getLocalIpService.getLocalIP().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(InetAddress ip:view.getIpAddresses())
			rPCClientService.getView(serverId).add(ip);
	}

	@Override
	public View getBootstrapView() {
		// TODO Auto-generated method stub
		InetAddress serverId = null;
		try {
			serverId = InetAddress.getByName(getLocalIpService.getLocalIP().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (View) rPCClientService.getView(serverId);
	}

}
