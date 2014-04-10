package course.cs5300.project1a.rpc.lib.service.impl;

import javax.inject.Named;

import course.cs5300.project1a.rpc.lib.service.CallIdService;

@Named
public class CallIdServiceMock implements CallIdService {

	@Override
	public int getCallID() {
		// TODO Auto-generated method stub
		return 20;
	}

}
