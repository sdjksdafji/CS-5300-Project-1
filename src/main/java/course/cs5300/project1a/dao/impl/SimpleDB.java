package course.cs5300.project1a.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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

public class SimpleDB {
	private static AmazonSimpleDBClient client;
	
	public SimpleDB (){
		Properties properties = new Properties();
		try {
			properties.load(SimpleDB.class.getResourceAsStream("/AwsCredentials.properties"));
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
	
/*	public static void main(String[] args) {
		SimpleDB client = new SimpleDB();
		client.createTable("Table");
		
		List<String> a = new ArrayList<String>(0);
		a.add("aaaa");
		a.add("bbbb");
		a.add("cccc");
		a.add("dddd");
		a.add("eeee");
		///////
		client.setBootstrapeViews(a);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// solve the delay on simpleDB

		client.getBootstrapeViews();
		client.deleteTable("Table");
	}*/
//	public static void main(String[] args) throws IOException, InterruptedException{
//		//Load the Properties File with AWS Credentials
//		Properties properties = new Properties();
//		properties.load(newInstance.class.getResourceAsStream("/AwsCredentials.properties"));
//		
//		BasicAWSCredentials bawsc = new BasicAWSCredentials(properties.getProperty("accessKey"), properties.getProperty("secretKey"));
//		client = new AmazonSimpleDBClient(bawsc);
//		
//		// Create a table
//		boolean found = false;
//		String TableName = "Table";
//		ListDomainsResult response = client.listDomains();
//		for (String domain:response.getDomainNames())
//		{
//			System.out.println("It has domains: " + domain);
//		    if(domain.equals(TableName)) found = true;
//		} 
//
//		if(!found)
//		{
//			client.createDomain( new CreateDomainRequest(TableName) );
//			System.out.println("Create new domain: "+TableName);
//		}
//		
//		//delete domain
//		//client.deleteDomain(new DeleteDomainRequest(TableName));
//		List<String> attributes = new ArrayList<String>();
//		attributes.add("1111");
//		attributes.add("2222");
//		attributes.add("3333");
//		setBootstrapeViews(attributes);
//		
//		Thread.sleep(5000);// solve the delay on simpleDB
//		List<String> a = getBootstrapeViews();
//		System.out.println("value is stored as " + a.toString());
//
//		System.out.println("finished");
//	}
	
	public void setBootstrapeViews(List<String> newViews) {
		String TableName = "Table";
		String ItemName = "item";
		
		StringBuilder sb = new StringBuilder();
		sb.append(newViews.get(0));
		for (int i = 1; i < newViews.size(); i ++) {
			sb.append("_");
			sb.append(newViews.get(i));
		}

		ReplaceableAttribute replaceAttribute = new ReplaceableAttribute("views",sb.toString(), true) ;
		
		List<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();
		attributes.add(replaceAttribute);
		client.putAttributes(new PutAttributesRequest(TableName, ItemName, attributes));
		//System.out.println("set New BootstrapeViews as: " + sb.toString());
	}
	
	public List<String> getBootstrapeViews() {
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

		List<String> resultList = new ArrayList<String>(Arrays.asList(result.split("_")));
		//System.out.println("get BootstrapeViews are: " + resultList.toString());
		return resultList;
	}

}
