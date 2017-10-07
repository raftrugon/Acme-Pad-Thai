package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NutritionistRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.Folder;
import domain.Nutritionist;

@Service
@Transactional
public class NutritionistService {
	
	// Managed Repository ------------------------------------------
	@Autowired
	private NutritionistRepository nutritionistRepository;
			
	//Supporting services ------------------------------------------
	@Autowired
	private FolderService folderService;
			
	
	//Constructors -------------------------------------------------
	
	public NutritionistService() {
		super();
	}
	
	//Simple CRUD methods ------------------------------------------
	
    public Nutritionist create() {
    	Nutritionist res;
		res = new Nutritionist();
		Authority auth = new Authority();
		auth.setAuthority("NUTRITIONIST");
		Collection<Authority> lia = new ArrayList<Authority>();		
		res.setFolders(new ArrayList<Folder>());
		
		lia.add(auth);
		UserAccount ua = new UserAccount();
		ua.setAuthorities(lia);
		res.setUserAccount(ua);
		Assert.notNull(res);
		return res;
	}
	
	public Collection<Nutritionist> findAll() {
		Collection<Nutritionist> res;
		res = nutritionistRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Nutritionist findOne(int nutritionistId) {
		Assert.isTrue(nutritionistId != 0);		
		Nutritionist res = nutritionistRepository.findOne(nutritionistId);
		Assert.notNull(res);
		return res;
	}
	
	public void save(Nutritionist nutritionist) {
		if(nutritionist.getId()==0){
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
			Folder inbox2 = folderService.save(inbox, nutritionist);
			Folder outbox2 = folderService.save(outbox, nutritionist);
			Folder trashbox2 = folderService.save(trashbox, nutritionist);
			Folder spambox2 = folderService.save(spambox, nutritionist);
			nutritionist.getFolders().add(inbox2);
			nutritionist.getFolders().add(outbox2);
			nutritionist.getFolders().add(trashbox2);
			nutritionist.getFolders().add(spambox2);
		}
		
		Md5PasswordEncoder password = new Md5PasswordEncoder();
		String encodedPassword = password.encodePassword(nutritionist.getUserAccount().getPassword(), null);
		nutritionist.getUserAccount().setPassword(encodedPassword);
		Assert.notNull(nutritionist);
		nutritionistRepository.save(nutritionist);
	}
	
	//Para actualizar los datos personales
	public void saveUpdate(Nutritionist nutritionist) {
		Assert.notNull(nutritionist);
		nutritionistRepository.save(nutritionist);
	}
	
	//Other business methods----------------------------------------
	
	public Nutritionist findByPrincipal() {
		Nutritionist res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res=findByUserAccount(userAccount);
		return res;
	}

	public Nutritionist findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Nutritionist res;
		res=nutritionistRepository.findByUserAccount(userAccount.getId());
		return res;
	}
	
	public Nutritionist findByCurricula(Curricula curricula) {
		Assert.notNull(curricula);
		Nutritionist res;
		res=nutritionistRepository.findByCurricula(curricula.getId());
		return res;
	}
}
