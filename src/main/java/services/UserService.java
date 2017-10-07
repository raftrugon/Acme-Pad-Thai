package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Folder;
import domain.Recipe;
import domain.User;

@Service
@Transactional
public class UserService {
	
	// Managed Repository ------------------------------------------
	@Autowired
	private UserRepository userRepository;
			
	//Supporting services ------------------------------------------
	@Autowired
	private FolderService folderService;
	
	
	//Constructors -------------------------------------------------
	
	public UserService() {
		super();
	}
	
	//Simple CRUD methods ------------------------------------------
	
	public User create() {
		User res;
		res = new User();
		res.setRecipes(new ArrayList<Recipe>());
		Authority auth = new Authority();
		auth.setAuthority("USER");
		Collection<Authority> lia = new ArrayList<Authority>();
		res.setFolders(new ArrayList<Folder>());	

		lia.add(auth);
		UserAccount ua = new UserAccount();
		ua.setAuthorities(lia);
		res.setUserAccount(ua);
		Assert.notNull(res);
		return res;
	}
	
	public Collection<User> findAll() {
		Collection<User> res;
		res = userRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public User findOne(int userId) {
		Assert.isTrue(userId != 0);		
		User res = userRepository.findOne(userId);
		Assert.notNull(res);
		return res;
	}
	
	public void save(User user) {
		if(user.getId()==0){
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
			Folder inbox2 = folderService.save(inbox, user);
			Folder outbox2 = folderService.save(outbox, user);
			Folder trashbox2 = folderService.save(trashbox, user);
			Folder spambox2 = folderService.save(spambox, user);
			user.getFolders().add(inbox2);
			user.getFolders().add(outbox2);
			user.getFolders().add(trashbox2);
			user.getFolders().add(spambox2);
		}
		Md5PasswordEncoder password = new Md5PasswordEncoder();
		String encodedPassword = password.encodePassword(user.getUserAccount().getPassword(), null);
		user.getUserAccount().setPassword(encodedPassword);
		Assert.notNull(user);
		userRepository.save(user);
	}
	
	//Para actualizar los datos personales
	public void saveUpdate(User user) {
		Assert.notNull(user);
		userRepository.save(user);
	}
	
	//Other business methods----------------------------------------
	
	public User findByPrincipal() {
		User res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res=findByUserAccount(userAccount);
		return res;
	}
	
	public Double[] minAvgMaxNumberOfRecipes(){
		Double[] res = null;
		res = userRepository.minAvgMaxNumberOfRecipes();
		Assert.notNull(res);
		for(int i=0;i<res.length;i++){
			if(res[i]==null)
				res[i]=0.0;
		}
		return res;
	}
	
	public Collection<User> userWithMoreRecipes(){
		Collection<User> res;
		res = userRepository.userWithMoreRecipes();
		Assert.notNull(res);
		return res;
	}
	
	public Collection<User> findByPopularity(){
		Collection<User> res;
		res = userRepository.findByPopularity();
		Assert.notNull(res);
		return res;
	}
	
	public Collection<User> findByLikesAndDislikes(){
		Collection<User> res;
		res = userRepository.findByLikesAndDislikes();
		Assert.notNull(res);
		return res;
	}
	
	public User findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		User res;
		res = userRepository.findByUserAccount(userAccount.getId());
		return res;
	}
	
	public Collection<User> findUserByKeyword(String keyword){
		Collection<User> res;
		res = userRepository.findUserByKeyword(keyword);
		Assert.notNull(res);
		return res;
	}
	
}
