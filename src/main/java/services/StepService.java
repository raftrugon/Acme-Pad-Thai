package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StepRepository;
import domain.Recipe;
import domain.Step;
import domain.User;

@Service
@Transactional
public class StepService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private StepRepository stepRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService userService;

	
	// Constructors -----------------------------------------------------------
	public StepService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	public Step create() {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Step res;
		res = new Step();
		res.setHints(new ArrayList<String>());
	
		return res;
	}
	
	public Step findOne(int stepId) {
		Assert.isTrue(stepId != 0);		
		Step res = stepRepository.findOne(stepId);
		Assert.notNull(res);
		return res;
	}


	public void save(Step step) {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Assert.notNull(step);
		stepRepository.save(step);
	}
	
	public void delete(Step step) {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Assert.notNull(step);
		Assert.isTrue(step.getId() != 0);
		Assert.isTrue(stepRepository.exists(step.getId()));
		step.getRecipe().getSteps().remove(step);
				
		stepRepository.delete(step.getId());
	}

	// Other business methods -------------------------------------------------
	public Integer lastNumberOfStep(Recipe recipe){
		Integer res;
		res = stepRepository.lastNumberOfStep(recipe.getId());
		if(res==null)//No hay ningún paso en la receta
			res=0;
	
		return res;
	}
	
	public Collection<Integer> getNumberOfSteps(Recipe recipe){
		Collection<Integer> res;
		res = stepRepository.getNumberOfSteps(recipe.getId());
		Assert.notNull(res);
	
		return res;
	}
	
	public List<Step> findStepsOrderByNumber(int recipeId){
		List<Step> res;
		res = stepRepository.findStepsOrderByNumber(recipeId);
		return res;
	}

}
