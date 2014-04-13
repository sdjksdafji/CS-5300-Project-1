package course.cs5300.project1a.rpc.lib.service.impl;

import java.util.Random;

import javax.inject.Named;

import course.cs5300.project1a.rpc.lib.service.CallIdService;

@Named
public class CallIdServiceImpl implements CallIdService {

	private Random generator = new Random();

	@Override
	public int getCallID() {
		return generator.nextInt(Integer.MAX_VALUE);
	}

}
