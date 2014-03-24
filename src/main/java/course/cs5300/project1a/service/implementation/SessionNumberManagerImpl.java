package course.cs5300.project1a.service.implementation;

import course.cs5300.project1a.service.SessionNumberManager;

public class SessionNumberManagerImpl implements SessionNumberManager {

	private static long currentNum = 0;

	@Override
	public synchronized long getSessionNum() {
		long returnVal = currentNum++;
		return returnVal;
	}

}
