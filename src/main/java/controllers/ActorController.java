package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.SponsorService;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor/admin")
public class ActorController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private SponsorService sponsorService;

	//Constructor ---------------------------
	public ActorController() {
		super();
	}
	
	//Listing -------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Sponsor> sponsors;
		sponsors = sponsorService.findDefaultingSponsors();
		Assert.notNull(sponsors);
			
		result = new ModelAndView("sponsor/admin/list");
		result.addObject("sponsors", sponsors);
	
		return result;	
	}
	
	@RequestMapping(value="/message", method=RequestMethod.GET)
	public ModelAndView register(RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			sponsorService.sendAdvertisementToDefaultingSponsors();

			redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (Throwable oops) {

			redirectAttrs.addFlashAttribute("message", "actor.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		result = new ModelAndView("redirect:/sponsor/admin/list.do");
		return result;
	}
}
