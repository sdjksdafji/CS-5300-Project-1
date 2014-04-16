package course.cs5300.project1a.dao.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.ListDomainsResult;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import course.cs5300.project1a.dao.BootstrapViewDAO;
import course.cs5300.project1a.pojo.*;
import course.cs5300.project1a.rpc.client.service.RPCClientService;
import course.cs5300.project1a.service.GetLocalIPService;
@Named
@Scope("singleton")
public class BootstrapViewDAOImpl implements BootstrapViewDAO {
	View view;
	private static AmazonSimpleDBClient client;
	@Inject
	private GetLocalIPService getLocalIpService;
	@Inject
	private RPCClientService rPCClientService;
	
	public BootstrapViewDAOImpl(){
		Properties properties = new Properties();
		try {
			properties.load(BootstrapViewDAOImpl.class.getResourceAsStream("AwsCredentials.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BasicAWSCredentials bawsc = new BasicAWSCredentials(properties.getProperty("accessKey"), properties.getProperty("secretKey"));
		client = new AmazonSimpleDBClient(bawsc);
	}
	
	public void createTable(String TableName) {
		// Create a table
		boolean found = false;
		ListDomainsResult response = client.listDomains();
		for (String domain:response.getDomainNames())
		{
			System.out.println("It has domains: " + domain);
		    if(domain.equals(TableName)) found = true;
		} 

		if(!found)
		{
			client.createDomain( new CreateDomainRequest(TableName) );
			System.out.println("Create new domain: "+TableName);
		}
				
	}
	
	public void deleteTable(String TableName) {
		client.deleteDomain(new DeleteDomainRequest(TableName));
		System.out.println("Delete domain: "+TableName);
	}
	
	@Override
	public void setBootstrapView(View view) {
//		System.out.println("We are setting views");
//		for(InetAddress ip : view.getIpAddresses()){
//			System.out.println("We are setting "+ip.toString());
//		}
		
		// TODO Auto-generated method stub
		String TableName = "Table";
		String ItemName = "item";
		StringBuilder sb = new StringBuilder();
		List<InetAddress> newViews = new ArrayList<InetAddress>();
		newViews.addAll(view.getIpAddresses());
		sb.append(newViews.get(0));
		for (int i = 1; i < newViews.size(); i ++) {
			sb.append("_");
			sb.append(newViews.get(i));
		}
		ReplaceableAttribute replaceAttribute = new ReplaceableAttribute("views",sb.toString(), true) ;
		
		List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();
		attributes.add(replaceAttribute);
		client.putAttributes(new PutAttributesRequest(TableName, ItemName, attributes));
	}

	@Override
	public View getBootstrapView() {
		// TODO Auto-generated method stub
		String result = "";
		String TableName = "Table";
		String ItemName = "item";

		GetAttributesResult response = client.getAttributes( new GetAttributesRequest(TableName, ItemName) );
		for (Attribute attribute : response.getAttributes())
		{
		    if (attribute.getName().equals("views")) { 
		    	result = attribute.getValue(); 
		    }
		}

		List<String> temp = (Arrays.asList(result.split("_/")));
		List<InetAddress> resultList = new ArrayList<InetAddress>();
		for(String ip:temp){
			try {
				resultList.add(InetAddress.getByName(this.getLocalIpService.moveFirstSlash(ip)));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		View view = new View();
		view.setIpAddresses(new HashSet(resultList));
		return view;
	}

}
