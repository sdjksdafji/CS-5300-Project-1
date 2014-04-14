package course.cs5300.project1a.service.implementation;

import javax.inject.Named;

import course.cs5300.project1a.service.SessionNumberManager;

@Named
public class SessionNumberManagerImpl implements SessionNumberManager {

	private static long currentNum = 0;

	@Override
	public synchronized long getSessionNum() {
		long returnVal = currentNum++;
		return returnVal;
	}

}
