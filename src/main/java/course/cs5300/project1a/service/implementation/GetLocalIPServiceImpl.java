package course.cs5300.project1a.service.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import course.cs5300.project1a.service.GetLocalIPService;

public class GetLocalIPServiceImpl implements GetLocalIPService {

	@Override
	public InetAddress getLocalIP() {
		try {
			return InetAddress.getByName(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String command = "/opt/aws/bin/ec2-metadata --public-ipv4";
		try {
			Process a = Runtime.getRuntime().exec(command);
			InputStreamReader read = new InputStreamReader(a.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineText = null;
			if((lineText=bufferedReader.readLine())!=null){
				String[] strings = lineText.split("\\s");
				//System.out.println(strings[0]);
				//System.out.println(strings[1]);
				return InetAddress.getByName(strings[1]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
