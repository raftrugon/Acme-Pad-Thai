package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Cook;
import domain.MasterClass;


@Service
@Transactional
public class ActorService {
	
	@Autowired
	private ActorRepository actorRepository;

	// Supporting services
	@Autowired
	private CookService cookService;

	// Constructors
	public ActorService() {
		super();
	}

	// Simple CRUD methods
	
	public Collection<Actor> findAll() {
		Actor a = findByPrincipal();
		Assert.notNull(a);
		Collection<Actor> res;
		res=actorRepository.findAll();
		Assert.notNull(res);

		return res;
	}
	
	public Actor findOne(int actorId) {
		Assert.isTrue(actorId != 0);		
		Actor res = actorRepository.findOne(actorId);
		Assert.notNull(res);
		return res;
	}

	
	//Other business methods----------------------------------------
	
	public Actor findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor res;
		res = actorRepository.findByUserAccount(userAccount.getId());
		return res;
	}
	
	public Actor findByPrincipal() {
		Actor res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Collection<Actor> actorsRegisteredInAMasterClass(MasterClass masterClass){
		Cook c = cookService.findByPrincipal();
		Assert.notNull(c);
		Collection<Actor> res;
		res = actorRepository.actorsRegisteredInAMasterClass(masterClass.getId());
		Assert.notNull(res);
		return res;
	}
	
	public Actor findBySocialIdentity(int socialIdentityId) {
		Assert.notNull(socialIdentityId);
		Actor res;
		res = actorRepository.findBySocialIdentity(socialIdentityId);
		return res;
	}

}
