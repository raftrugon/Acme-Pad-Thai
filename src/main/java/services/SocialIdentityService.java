package services;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.SocialIdentity;

import repositories.SocialIdentityRepository;

@Service
@Transactional
public class SocialIdentityService {
	
	// Managed Repository ------------------------------------------
	
	@Autowired
	private SocialIdentityRepository socialIdentityRepository;
		
	//Supporting services ------------------------------------------
	@Autowired
	private ActorService actorService;
	
	//Constructors -------------------------------------------------
	
	public SocialIdentityService() {
		super();
	}
	
	//Simple CRUD methods ------------------------------------------
	
	public SocialIdentity create() {
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		SocialIdentity res;
		res = new SocialIdentity();
		return res;
	}

	public SocialIdentity findOne(int socialIdentityId) {
		Assert.isTrue(socialIdentityId != 0);		
		SocialIdentity res = socialIdentityRepository.findOne(socialIdentityId);
		Assert.notNull(res);
		return res;
	}
	
	public void save(SocialIdentity socialIdentity) {
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		Assert.notNull(socialIdentity); 
		SocialIdentity res = socialIdentityRepository.save(socialIdentity);
		a.setSocialIdentity(res);
	}
	
	public void delete(SocialIdentity socialIdentity) {
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		Assert.notNull(socialIdentity);
		Assert.isTrue(socialIdentity.getId() != 0);
		Assert.isTrue(socialIdentityRepository.exists(socialIdentity.getId()));
		
		Actor res = actorService.findBySocialIdentity(socialIdentity.getId());
		res.setSocialIdentity(null);
		socialIdentityRepository.delete(socialIdentity);
	}
	
	//Other business methods----------------------------------------
}
