package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.LoginService;
import security.UserAccount;
import domain.Admin;

@Service
@Transactional
public class AdminService {
	
	// Managed Repository ------------------------------------------
	
	@Autowired
	private AdminRepository adminRepository;
			
	//Supporting services ------------------------------------------
		
	
	//Constructors -------------------------------------------------
	
	public AdminService() {
		super();
	}
	
	//Simple CRUD methods ------------------------------------------
	
	public void save(Admin admin) { //Actualizar datos de Admin
		Admin a = findByPrincipal();
		Assert.notNull(a);
		Assert.notNull(admin);
		adminRepository.save(admin);
	}
	
	
	//Other business methods ---------------------------
	
	public Admin findByPrincipal() {
		Admin res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		return res;
	}
	
	public Admin findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Admin res;
		res = adminRepository.findOneByAdminId(userAccount.getId());
		return res;
	}
	
	public Admin findByUserAccountId(int userAccountId) {
		Assert.notNull(userAccountId);
		Admin res;
		res = adminRepository.findOneByAdminId(userAccountId);
		return res;
	}
}
