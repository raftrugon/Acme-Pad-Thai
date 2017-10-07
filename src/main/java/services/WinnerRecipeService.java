package services;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Admin;
import domain.WinnerRecipe;
import repositories.WinnerRecipeRepository;

@Service
@Transactional
public class WinnerRecipeService {
	
	// Managed repository -----------------------------------------------------
	@Autowired
	private WinnerRecipeRepository winnerRecipeRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private AdminService adminService;
	
	
	
	// Constructors -----------------------------------------------------------

	public WinnerRecipeService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	public WinnerRecipe create() {
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		WinnerRecipe res;
		res = new WinnerRecipe();

		return res;
	}
	
	public WinnerRecipe findOne(int winnerRecipeId) {
		Assert.isTrue(winnerRecipeId != 0);		
		WinnerRecipe res = winnerRecipeRepository.findOne(winnerRecipeId);
		Assert.notNull(res);
		return res;
	}

	public void save(WinnerRecipe winnerRecipe) {
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Assert.notNull(winnerRecipe);
		winnerRecipeRepository.save(winnerRecipe);
	}

	// Other business methods -------------------------------------------------

}
