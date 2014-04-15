package course.cs5300.project1a.service.implementation;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import course.cs5300.project1a.service.VersionManager;

@Named
@Scope("singleton")
public class VersionManagerImpl implements VersionManager {
	
	private static long currentVersion = 0;;

	@Override
	public synchronized long getVersionNumber() {
		long returnVal = currentVersion++;
		return returnVal;
	}

	@Override
	public synchronized void updateVersion(long kownVersion) {
		if(kownVersion >= currentVersion){
			currentVersion = kownVersion + 1;
		}
	}

	
}
