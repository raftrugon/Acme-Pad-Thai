package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Admin;

import domain.Cook;
import domain.Folder;
import domain.MasterClass;
import repositories.CookRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class CookService {
	
	// Managed Repository ------------------------------------------
	
	@Autowired
	private CookRepository cookRepository;
			
	//Supporting services ------------------------------------------
			
	@Autowired
	private AdminService adminService;
	@Autowired
	private FolderService folderService;
	
	//Constructors -------------------------------------------------
	
	public CookService() {
		super();
	}
	
	//Simple CRUD methods ------------------------------------------
	
	public Cook create() {
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);	
		Cook res;
		res = new Cook();
		res.setMasterClasses(new ArrayList<MasterClass>());
		res.setFolders(new ArrayList<Folder>());

		Authority auth = new Authority();
		auth.setAuthority("COOK");
		Collection<Authority> lia = new ArrayList<Authority>();
		lia.add(auth);
		UserAccount ua = new UserAccount();
		ua.setAuthorities(lia);
		res.setUserAccount(ua);
		return res;
	}
	
	public Collection<Cook> findAll() {
		Collection<Cook> res;
		res = cookRepository.findAll();
		Assert.notNull(res);		
		return res;
	}

	public Cook findOne(int cookId) {
		Assert.isTrue(cookId != 0);		
		Cook res = cookRepository.findOne(cookId);
		Assert.notNull(res);
		return res;
	}
	
	public void save(Cook cook) {
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		
		if(cook.getId()==0){
			Folder inbox = folderService.create();
			inbox.setName("Inbox");
			inbox.setSystem(true);
			Folder outbox = folderService.create();
			outbox.setName("Outbox");
			outbox.setSystem(true);
			Folder trashbox = folderService.create();
			trashbox.setName("Trashbox");
			trashbox.setSystem(true);
			Folder spambox = folderService.create();
			spambox.setName("Spambox");
			spambox.setSystem(true);
			Folder inbox2 = folderService.save(inbox, cook);
			Folder outbox2 = folderService.save(outbox, cook);
			Folder trashbox2 = folderService.save(trashbox, cook);
			Folder spambox2 = folderService.save(spambox, cook);
			cook.getFolders().add(inbox2);
			cook.getFolders().add(outbox2);
			cook.getFolders().add(trashbox2);
			cook.getFolders().add(spambox2);
		}
		
		Md5PasswordEncoder password = new Md5PasswordEncoder();
		String encodedPassword = password.encodePassword(cook.getUserAccount().getPassword(), null);
		cook.getUserAccount().setPassword(encodedPassword);
		Assert.notNull(cook);
		cookRepository.save(cook);
	}
	
	//Para actualizar los datos personales
	public void saveUpdate(Cook cook) {
		Assert.notNull(cook);
		cookRepository.save(cook);
	}
	
	//Other business methods --------------------------------------
	
	public Cook findByPrincipal() {
		Cook res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		return res;
	}
	
	public Cook findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Cook res;
		res = cookRepository.findByUserAccount(userAccount.getId());
		return res;
	}
	
	public Collection<Cook> findAllOrderedByMasterClassesPromoted(){
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Collection<Cook> res;
		res = cookRepository.findAllOrderedByMasterClassesPromoted();
		Assert.notNull(res);
		return res;	
	}
	
}
