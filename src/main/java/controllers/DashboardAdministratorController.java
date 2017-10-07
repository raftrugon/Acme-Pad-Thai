package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BillService;
import services.ContestService;
import services.CookService;
import services.LearningMaterialService;
import services.MasterClassService;
import services.RecipeService;
import services.SponsorService;
import services.UserService;
import domain.Contest;
import domain.Cook;
import domain.Sponsor;
import domain.User;

@Controller
@RequestMapping("/dashboard/admin")
public class DashboardAdministratorController extends AbstractController {
	
	//Services -----------------------------
	@Autowired
	private UserService userService;
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private BillService billService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private CookService cookService;
	@Autowired
	private MasterClassService masterClassService;
	@Autowired
	private LearningMaterialService learningMaterialService;
	
	//Constructor --------------------------
	public DashboardAdministratorController() {
		super();
	}
	
	//Show dashboard -----------------------
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;	
		
		//C1
		Double[] minAvgMaxNumberOfRecipes = userService.minAvgMaxNumberOfRecipes();
		//C2
		Collection<User> userWithMoreRecipes = userService.userWithMoreRecipes();
		//C3
		Double[] minAvgMaxRecipesQualified = contestService.minAvgMaxRecipesQualified();
		//C4
		Collection<Contest> contestsWithMoreRecipes = contestService.contestsWithMoreRecipes();
		//C5
		Double[] avgStddevNumberOfSteps = recipeService.avgStddevNumberOfSteps();
		//C6
		Double[] avgStddevNumberOfIngredients = recipeService.avgStddevNumberOfIngredients();
		//C7
		Collection<User> findByPopularity = userService.findByPopularity();
		//C8
		Collection<User> findByLikesAndDislikes = userService.findByLikesAndDislikes();
		
		//B1
		Double[] minAvgMaxCampaignPerSponsor = sponsorService.minAvgMaxCampaignPerSponsor();
		//B2
		Double[] minAvgMaxOfCampaignActivesPerSponsor = sponsorService.minAvgMaxOfCampaignActivesPerSponsor();
		//B3
		Collection<String> rankingCompaniesPerNumCampaign = sponsorService.rankingCompaniesPerNumCampaign();
		//B4
		Collection<String> rankingCompaniesPerBills = sponsorService.rankingCompaniesPerBills();
		//B5
		Double[] avgStdBillPaidandNoPaid = billService.avgStdBillPaidandNoPaid();
		//B6
		Collection<Sponsor> sponsorWithActiveCampaigns = sponsorService.sponsorWithActiveCampaigns();
		//B7
		Collection<String> companiesBelowAvg = sponsorService.companiesBelowAvg();
		//B8
		Collection<String> mostExpensiveCompanies = sponsorService.mostExpensiveCompanies();
	
		//A1
		Double[] minMaxAvgStddevMasterClassPerCook = masterClassService.minMaxAvgStddevMasterClassPerCook();
		//A2
		Collection<Double> averageByMasterClassGroupByTypes = learningMaterialService.averageByMasterClassGroupByTypes();
		//A3
		Integer numberOfPromoted = masterClassService.numberOfPromoted();
		//A4
		Collection<Cook> findAllOrderedByMasterClassesPromoted = cookService.findAllOrderedByMasterClassesPromoted();
		//A5
		Collection<Double> averagePromotedAndDemoted = masterClassService.averagePromotedAndDemoted();
		
		result = new ModelAndView("admin/dashboard/list");
		
		//C1
		result.addObject("minNumberOfRecipes", minAvgMaxNumberOfRecipes[0]);
		result.addObject("avgNumberOfRecipes", minAvgMaxNumberOfRecipes[1]);
		result.addObject("maxNumberOfRecipes", minAvgMaxNumberOfRecipes[2]);
		//C2
		result.addObject("userWithMoreRecipes", userWithMoreRecipes);
		//C3
		result.addObject("minRecipesQualified", minAvgMaxRecipesQualified[0]);
		result.addObject("avgRecipesQualified", minAvgMaxRecipesQualified[1]);
		result.addObject("maxRecipesQualified", minAvgMaxRecipesQualified[2]);
		//C4
		result.addObject("contestsWithMoreRecipes", contestsWithMoreRecipes);
		//C5
		result.addObject("avgNumberOfSteps", avgStddevNumberOfSteps[0]);
		result.addObject("stdNumberOfSteps", avgStddevNumberOfSteps[1]);
		//C6
		result.addObject("avgNumberOfIngredients", avgStddevNumberOfIngredients[0]);
		result.addObject("stdNumberOfIngredients", avgStddevNumberOfIngredients[0]);
		//C7
		result.addObject("findByPopularity", findByPopularity);
		//C8
		result.addObject("findByLikesAndDislikes", findByLikesAndDislikes);
		
		//B1
		result.addObject("minCampaignPerSponsor", minAvgMaxCampaignPerSponsor[0]);
		result.addObject("avgCampaignPerSponsor", minAvgMaxCampaignPerSponsor[1]);
		result.addObject("maxCampaignPerSponsor", minAvgMaxCampaignPerSponsor[2]);
		//B2
		result.addObject("minOfCampaignActivesPerSponsor", minAvgMaxOfCampaignActivesPerSponsor[0]);
		result.addObject("avgOfCampaignActivesPerSponsor", minAvgMaxOfCampaignActivesPerSponsor[1]);
		result.addObject("maxOfCampaignActivesPerSponsor", minAvgMaxOfCampaignActivesPerSponsor[2]);
		//B3
		result.addObject("rankingCompaniesPerNumCampaign", rankingCompaniesPerNumCampaign);
		//B4
		result.addObject("rankingCompaniesPerBills", rankingCompaniesPerBills);
		//B5
		result.addObject("avgBillPaidandNoPaid", avgStdBillPaidandNoPaid[0]);
		result.addObject("stdBillPaidandNoPaid", avgStdBillPaidandNoPaid[1]);
		//B6
		result.addObject("sponsorWithActiveCampaigns", sponsorWithActiveCampaigns);
		//B7
		result.addObject("companiesBelowAvg", companiesBelowAvg);
		//B8
		result.addObject("mostExpensiveCompanies", mostExpensiveCompanies);
			
		//A1
		result.addObject("minMasterClassPerCook",minMaxAvgStddevMasterClassPerCook[0]);
		result.addObject("maxMasterClassPerCook",minMaxAvgStddevMasterClassPerCook[1]);
		result.addObject("avgMasterClassPerCook",minMaxAvgStddevMasterClassPerCook[2]);
		result.addObject("stdMasterClassPerCook",minMaxAvgStddevMasterClassPerCook[3]);
		
		//A2
		int cont = 1;
		for(Double d: averageByMasterClassGroupByTypes){
			result.addObject("averageByMasterClassGroupBy"+cont,d);
			cont++;
		}
		//A3
		result.addObject("numberOfPromoted",numberOfPromoted);
		//A4
		result.addObject("findAllOrderedByMasterClassesPromoted",findAllOrderedByMasterClassesPromoted);
		//A5
		result.addObject("averagePromotedAndDemoted",averagePromotedAndDemoted);
		cont = 1;
		for(Double d: averageByMasterClassGroupByTypes){
			result.addObject("averagePromotedAndDemoted"+cont,d);
			cont++;
		}
	
		return result;
	}

}
