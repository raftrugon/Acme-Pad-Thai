package services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curricula;
import domain.Endorsement;
import domain.Nutritionist;
import repositories.CurriculaRepository;

@Service
@Transactional
public class CurriculaService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CurriculaRepository curriculaRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private NutritionistService nutritionistService;
	@Autowired
	private EndorsementService endorsementService;
	
	// Constructors -----------------------------------------------------------
	public CurriculaService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Curricula create() {	
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Curricula res;
		res = new Curricula();
		res.setEndorsements(new ArrayList<Endorsement>());
		

		return res;
	}

	public Curricula findOne(int curriculaId) {
		Assert.isTrue(curriculaId != 0);		
		Curricula res = curriculaRepository.findOne(curriculaId);
		Assert.notNull(res);
		return res;
	}

	public void save(Curricula curricula) {
		Assert.notNull(curricula);
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Curricula res = curriculaRepository.save(curricula);
		if(curricula.getId()==0)
			n.setCurricula(res);
	}

	public void delete(Curricula curricula) {
		Assert.notNull(curricula);
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Assert.isTrue(curricula.getId() != 0);
		Assert.isTrue(curriculaRepository.exists(curricula.getId()));
				
		for(Endorsement e:curricula.getEndorsements())
			endorsementService.delete(e);
		n.setCurricula(null);
		
		curriculaRepository.delete(curricula.getId());
	}

	// Other business methods -------------------------------------------------

}
