package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CampaignService;
import services.SponsorService;
import domain.Campaign;
import domain.Sponsor;

@Controller
@RequestMapping("/campaign")
public class CampaignController extends AbstractController {
	
	//Services ------------------------------
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private SponsorService sponsorService;

	//Constructor ---------------------------
	public CampaignController(){
		super();
	}
	
	//Listing -------------------------------------
	@RequestMapping(value="/list", method= RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Sponsor sponsor = sponsorService.findByPrincipal();
		Collection<Campaign> campaigns = sponsor.getCampaigns();
		
		result= new ModelAndView("campaign/list");
		result.addObject("campaigns", campaigns);
		result.addObject("actualDate", new Date());	
		
		return result;
	}
	
	//Create -------------------------------------
	@RequestMapping(value="/create", method= RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Campaign campaign;
		
		campaign = campaignService.create();
		result= createEditModelAndView(campaign);
		return result;
	}
	
	//Edit -------------------------------------
	@RequestMapping(value="/edit", method= RequestMethod.GET)
	public ModelAndView edit(@RequestParam int campaignId){
		ModelAndView result;
		
		Campaign campaign;
		campaign= campaignService.findOne(campaignId);
		Assert.notNull(campaign);
		result= createEditModelAndView(campaign);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method= RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Campaign campaign, BindingResult binding){
		ModelAndView result;
		
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(campaign);
//			for(ObjectError o: binding.getAllErrors()){
//			System.out.println("ARGUMENT: "+o.getArguments());
//			System.out.println("CODE: "+o.getCode());
//			System.out.println("MESSAGE: "+o.getDefaultMessage());
//			}
//			System.out.println(binding.getAllErrors());
		} else {
			try {
				
				campaignService.save(campaign);				
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(campaign, "campaign.commit.error");				
			}
		}

		return result;
	}
	
	@RequestMapping(value="/edit", method= RequestMethod.POST, params="delete")
	public ModelAndView delete(@Valid Campaign campaign, BindingResult binding){
		ModelAndView result;
		
		
		try{
			campaignService.delete(campaign);
			result= new ModelAndView("redirect:list.do");
		}catch (Throwable oops){
			result= createEditModelAndView(campaign, "campaign.commit.error");
		}
		return result;
	}
	
	//Ancillary methods ------------------------------
	
		protected ModelAndView createEditModelAndView(Campaign campaign) {
			ModelAndView result;
			result = createEditModelAndView(campaign,null);
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Campaign campaign, String message) {
			
			ModelAndView result;
			
			result = new ModelAndView("campaign/edit");
			result.addObject("campaign", campaign);
			result.addObject("message", message);
			

			return result;
		}
	

}
