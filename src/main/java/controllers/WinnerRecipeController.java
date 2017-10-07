package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BannerService;
import services.BillService;
import services.ContestService;
import domain.Actor;
import domain.Banner;
import domain.Contest;
import domain.Nutritionist;
import domain.User;
import domain.WinnerRecipe;

@Controller
@RequestMapping("/winnerRecipe")
public class WinnerRecipeController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private BannerService bannerService;
	@Autowired
	private BillService billService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private ContestService contestService;
	
	//Constructor ---------------------------
	public WinnerRecipeController() {
		super();
	}
	
	//Listing -------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int contestId) {
		Boolean noAdmin = false;
		
		try{
			Actor a = actorService.findByPrincipal();
			if(a instanceof User || a instanceof Nutritionist)
				noAdmin = true;
			}
		catch(Throwable oops){
		}		
		
		ModelAndView result;
		Contest contest;
		Collection<WinnerRecipe> winnerRecipes;

		contest = contestService.findOne(contestId);
		winnerRecipes = contest.getWinnerRecipes();
		
		Collection<Banner> banners;
		banners = bannerService.findByActiveCampaigns();
		Assert.notNull(banners);
		Banner banner = null;
		if(!banners.isEmpty()){
			banner = bannerService.showRandomBanner(banners);
			Assert.notNull(banner);
			billService.updateBill(banner);
		}
		
		result = new ModelAndView("winnerRecipe/list");
		result.addObject("noAdmin", noAdmin);
		result.addObject("banner", banner);
		result.addObject("winnerRecipes", winnerRecipes);
		return result;	
	}
		
}
