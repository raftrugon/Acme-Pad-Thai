package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Admin;
import domain.Contest;
import domain.Recipe;
import domain.WinnerRecipe;
import repositories.ContestRepository;

@Service
@Transactional
public class ContestService  {
	
	// Managed repository -----------------------------------------------------
	@Autowired
	private ContestRepository contestRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private AdminService adminService;
	@Autowired
	private WinnerRecipeService winnerRecipeService;
	
	// Constructors -----------------------------------------------------------
	public ContestService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	public Contest create() {	
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Contest res;
		res = new Contest();	
		
		Date openigDate = new Date();
		Date closingDate = new Date();
		Collection<Recipe> recipes = new ArrayList<Recipe>();
		Collection<WinnerRecipe> winnerRecipes = new ArrayList<WinnerRecipe>();
		
		res.setOpeningTime(openigDate);
		res.setClosingTime(closingDate);
		res.setRecipes(recipes);
		res.setWinnerRecipes(winnerRecipes);
	
		return res;
	}
	
	public Collection<Contest> findAll() {
		Collection<Contest> res;
		res=contestRepository.findAll();
		Assert.notNull(res);

		return res;
	}
	
	public Contest findOne(int contestId) {
		Assert.isTrue(contestId != 0);		
		Contest res = contestRepository.findOne(contestId);
		Assert.notNull(res);
		return res;
	}

	public void save(Contest contest) {
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Assert.notNull(contest);
		Assert.isTrue(contest.getOpeningTime().before(contest.getClosingTime()));
		Assert.isTrue(contest.getRecipes().isEmpty());
		contestRepository.save(contest);
	}

	public void delete(Contest contest) {
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Assert.notNull(contest);
		Assert.isTrue(contest.getId() != 0);
		Assert.isTrue(contestRepository.exists(contest.getId()));
		Assert.isTrue(contest.getRecipes().isEmpty());
		
		contestRepository.delete(contest.getId());
	}
	
	// Other business methods -------------------------------------------------

	public Double[] minAvgMaxRecipesQualified(){
		Double[] res = null;
		res = contestRepository.minAvgMaxRecipesQualified();
		Assert.notNull(res);
		for(int i=0;i<res.length;i++){
			if(res[i]==null)
				res[i]=0.0;
		}
		return res;
	}
	
	public Collection<Contest> contestsWithMoreRecipes(){
		Collection<Contest> res = null;
		res = contestRepository.contestsWithMoreRecipes();
		Assert.notNull(res);	
		return res;
	}
	
	public List<Recipe> recipesWithMoreLikes(int contestId){
		List<Recipe> res = null;
		res = contestRepository.recipesWithMoreLikes(contestId);
		Assert.notNull(res);
		
		return res;
	}
	
	public Collection<Recipe> selectWinnerRecipes(Contest contest){
		Assert.notNull(contest);
		List<Recipe> res = new ArrayList<Recipe>();
		List<Recipe> recipes = recipesWithMoreLikes(contest.getId());
		Assert.notNull(recipes);
		Assert.isTrue(contest.getWinnerRecipes().isEmpty());
		Date actualDate = new Date();
		Assert.isTrue(actualDate.after(contest.getClosingTime()));

		for(int i=0;i<3;i++){
			if(i>=recipes.size())
				break;
			WinnerRecipe wr = winnerRecipeService.create();
			Assert.notNull(wr);
			wr.setRecipe(recipes.get(i));
			recipes.get(i).setWinnerRecipe(wr);
			winnerRecipeService.save(wr);
			contest.getWinnerRecipes().add(wr);
			res.add(recipes.get(i));
		}	
		
		return res;	
	}
	
	public boolean containsCopy(Contest contest, Recipe recipe){
		boolean res;
		Assert.notNull(contest);
		Assert.notNull(recipe);
		res = contestRepository.containsCopy(contest.getId(), recipe.getTicker());
		Assert.notNull(res);
		
		return res;
	}
	

}
