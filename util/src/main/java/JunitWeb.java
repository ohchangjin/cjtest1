
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = 
	{ 
		"file:WebContent/junit/playybook-config.xml",
		"file:WebContent/junit/playybook.xml",
		"file:WebContent/junit/playybook-dao.xml"
	}
)

public class JunitWeb {
	MockHttpServletRequest request;
	MockHttpServletResponse response;
	
	@Autowired
		
	@Before
	public void setUp() {
		// REQUEST/RESPONSE
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}
	@Test
	public void webTest() {
		
	}
}
