package utilities;


import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import security.LoginService;

public class AbstractTest {

	// Supporting services --------------------------------
	@Autowired
	private LoginService loginService;

	// Set up and tear down -------------------------------
	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}

	// Supporting methods ---------------------------------
	public void authenticate(String username) {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;	

		if (username == null)
			authenticationToken = null;
		else {
			userDetails = loginService.loadUserByUsername(username);
			authenticationToken = new TestingAuthenticationToken(userDetails, null);		    
		}
		
		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);
	}

}