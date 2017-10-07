package services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.NoAdmin;
import repositories.NoAdminRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class NoAdminService {
	
	// Managed repository
	@Autowired
	private NoAdminRepository noAdminRepository;
	
	// Supporting services ----------------------------------------------------

	
	
	// Constructors -----------------------------------------------------------
	
	public NoAdminService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	//Other business methods----------------------------------------

	public NoAdmin findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		NoAdmin res;
		res = noAdminRepository.findByUserAccount(userAccount.getId());
		return res;
	}
	
	public NoAdmin findByPrincipal() {
		NoAdmin res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		return res;
	}
	
	public void follow(NoAdmin na){
		NoAdmin napr = findByPrincipal();
		Assert.notNull(napr);
		napr.getNoAdmins().add(na);
	}
	
	public void unfollow(NoAdmin na){
		NoAdmin napr = findByPrincipal();
		Assert.notNull(napr);
		napr.getNoAdmins().remove(na);
	}


}
