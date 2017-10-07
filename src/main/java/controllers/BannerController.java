package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import services.BannerService;
import services.CampaignService;
import services.SponsorService;
import domain.Actor;
import domain.Admin;
import domain.Banner;
import domain.Campaign;
import domain.Sponsor;



@Controller
@RequestMapping("/banner")
public class BannerController extends AbstractController {

	//Services -----------------------------------
	@Autowired
	private BannerService bannerService;
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private ActorService actorService;
	
	//Constructor ---------------------------------
	public BannerController() {
		super();
	}
	
	//Listing -------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) Integer campaignId) {
		ModelAndView result;
		Boolean admin = false;
		Boolean owner = false;
		
		Actor a = actorService.findByPrincipal();
		if(a instanceof Admin)
			admin = true;
		Collection<Banner> banners;
		banners = bannerService.findAll();
		Assert.notNull(banners);
		Campaign c = null;
		
		
		if(campaignId!=null){
			c=campaignService.findOne(campaignId);
			if(a instanceof Sponsor && c.getSponsor().equals(a))
				owner = true;
		}
		
		result = new ModelAndView("banner/list");
		String id = "";		
		Sponsor s = sponsorService.findByPrincipal();
		
		if(s!=null){
			id = campaignId.toString();	
			result.addObject("banners", c.getBanners());
			result.addObject("campaignId", id);
			result.addObject("uri", "banner/list.do?campaignId="+id);
		}
		else{
			result.addObject("banners", banners);
			result.addObject("uri", "banner/list.do");
		}
		
		result.addObject("admin", admin);	
		result.addObject("owner", owner);	

		
		return result;	
	}
			
	//Creating ------------------------------------
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(int campaignId){
		ModelAndView result;
		Banner banner;
		
		Campaign c = campaignService.findOne(campaignId);
		banner = bannerService.create();
		banner.setCampaign(c);
		result= createEditModelAndView(banner);
		return result;
	}
			
	
	
	//Edition -------------------------------------
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int bannerId) {
		ModelAndView result;
		Banner banner;
		
		banner = bannerService.findOne(bannerId);
		Assert.notNull(banner);
		result = createEditModelAndView(banner);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Banner banner, BindingResult binding,RedirectAttributes redirectAttrs) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(banner);
//			for(ObjectError o: binding.getAllErrors()){
//			System.out.println("ARGUMENT: "+o.getArguments());
//			System.out.println("CODE: "+o.getCode());
//			System.out.println("MESSAGE: "+o.getDefaultMessage());
//			}
//			System.out.println(binding.getAllErrors());
		} else {
			try {
				bannerService.save(banner);
				redirectAttrs.addFlashAttribute("message", "banner.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				Integer n = banner.getCampaign().getId();
				String num = n.toString();
				result= new ModelAndView("redirect:/banner/list.do?campaignId="+num);
			} catch (Throwable oops) {
				result = createEditModelAndView(banner, "banner.commit.error");
			}
		}
		return result;
		
	}
	
	@RequestMapping(value="/edit", method= RequestMethod.POST, params="delete")
	public ModelAndView delete(Banner banner, BindingResult binding){
		ModelAndView result;
	
		try{
			bannerService.delete(banner);
			Integer n = banner.getCampaign().getId();
			String num = n.toString();
			result= new ModelAndView("redirect:/banner/list.do?campaignId="+num);
		}catch (Throwable oops){
			result= createEditModelAndView(banner, "banner.commit.error");
		}
		return result;
	}
	
	//Ancillary methods -----------------------------
	
	protected ModelAndView createEditModelAndView(Banner banner) {
		ModelAndView result;
		result = createEditModelAndView(banner, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Banner banner, String message) {
		ModelAndView result;
		Boolean admin = false;
		Boolean owner = false;
		
		Actor a = actorService.findByPrincipal();
		if(a instanceof Admin)
			admin = true;
	
		if(a instanceof Sponsor && banner.getCampaign().getSponsor().equals(a))
			owner = true;	
		
		result = new ModelAndView("banner/edit");
		result.addObject("admin", admin);
		result.addObject("owner", owner);
		result.addObject("banner", banner);
		result.addObject("message", message);
		

		return result;
	}

}
