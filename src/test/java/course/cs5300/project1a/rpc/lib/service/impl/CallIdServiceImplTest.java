package course.cs5300.project1a.rpc.lib.service.impl;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import course.cs5300.project1a.rpc.lib.service.CallIdService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class CallIdServiceImplTest {

	@Inject
	private CallIdService callIdService;

	@Test
	public void test() {
		for (int i = 0; i < 100; i++)
			System.out.println(this.callIdService.getCallID());
	}

}
