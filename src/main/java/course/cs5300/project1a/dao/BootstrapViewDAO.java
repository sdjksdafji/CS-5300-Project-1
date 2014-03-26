package course.cs5300.project1a.dao;

import java.net.InetAddress;
import java.util.List;

public interface BootstrapViewDAO {
	public void setBootstrapView(List<InetAddress> view);

	public List<InetAddress> getBootstrapView();
}
