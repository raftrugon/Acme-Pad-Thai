package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import domain.Actor;
import domain.Curricula;
import domain.Endorsement;

@Service
@Transactional
public class EndorsementService {
	
	
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private EndorsementRepository endorsementRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;

	
	// Constructors -----------------------------------------------------------

	public EndorsementService() {
		super();
		}

	
	// Simple CRUD methods ----------------------------------------------------

	public Endorsement create() {	
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		Endorsement res;
		res = new Endorsement();
	
		return res;
	}
	
	public Endorsement findOne(int endorsementId) {
		Assert.isTrue(endorsementId != 0);		
		Endorsement res = endorsementRepository.findOne(endorsementId);
		Assert.notNull(res);
		return res;
	}
	
	public void save(Endorsement endorsement) {
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		Assert.notNull(endorsement);
		endorsement.getCurricula().getEndorsements().add(endorsement);
		endorsementRepository.save(endorsement);		
	}

	public void delete(Endorsement endorsement) {
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);
		Assert.isTrue(endorsementRepository.exists(endorsement.getId()));
		
		endorsement.getCurricula().getEndorsements().remove(endorsement);
		
		endorsementRepository.delete(endorsement.getId());
	}

	// Other business methods -------------------------------------------------
	public Endorsement findByActor(Curricula curricula, Actor actor) {
		Assert.notNull(curricula);
		Assert.notNull(actor);
		Endorsement res;
		res=endorsementRepository.findByActor(curricula.getId(),actor.getId());
		return res;
	}
	
	public void endorse(Curricula c){
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		Endorsement e = create();
		e.setCurricula(c);
		e.setActor(a);
//		e.setName(e.getActor().getName()+" "+e.getActor().getSurname());
//		e.setHomePage("profile/personalData/list.do?actorId="+e.getActor().getId());
		save(e);	
	}
	
}
