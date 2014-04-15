package course.cs5300.project1a.dao.impl;

import javax.inject.Named;

import course.cs5300.project1a.dao.BootstrapViewDAO;
import course.cs5300.project1a.pojo.View;

//@Named
public class BoostrapViewDAOMock implements BootstrapViewDAO {

	@Override
	public void setBootstrapView(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public View getBootstrapView() {
		// TODO Auto-generated method stub
		return new View();
	}

}
