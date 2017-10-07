/* WelcomeController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BannerService;
import services.BillService;
import services.MasterClassService;
import domain.Banner;
import domain.MasterClass;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	//Services ------------------------------
	@Autowired
	private MasterClassService masterClassService;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private BillService billService;
	@Autowired
	private ActorService actorService;
	

	// Constructors -----------------------------------------------------------
	
	public WelcomeController() {
		super();
	}
		
	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required=false, defaultValue="anonymous") String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		
		Collection<MasterClass> masterClasses;
		masterClasses = masterClassService.findOnlyPromoted();
		Assert.notNull(masterClasses);
		Boolean registered = false;
		
		Collection<Banner> banners;
		banners = bannerService.findByActiveStarCampaigns();
		Assert.notNull(banners);
		Banner banner = null;
		if(!banners.isEmpty()){
			banner = bannerService.showRandomBanner(banners);
			Assert.notNull(banner);
			billService.updateBill(banner);
		}
		
		
		try{
		actorService.findByPrincipal();
		registered = masterClassService.isRegistered();
		}
		catch(IllegalArgumentException e){
			
		}

		result = new ModelAndView("welcome/index");

		result.addObject("banner", banner);
		result.addObject("masterClasses", masterClasses);
		result.addObject("registered", registered);
		
		result.addObject("name", name);
		result.addObject("moment", moment);

		return result;
	}
}