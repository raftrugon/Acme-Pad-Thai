package controllers;



import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import services.CurriculaService;
import services.EndorsementService;
import services.NoAdminService;
import services.NutritionistService;
import services.SocialIdentityService;
import domain.Actor;
import domain.Admin;
import domain.Cook;
import domain.Curricula;
import domain.Endorsement;
import domain.NoAdmin;
import domain.Nutritionist;
import domain.SocialIdentity;
import domain.Sponsor;
import domain.User;

@Controller
@RequestMapping("/profile")
public class SocialIdentityController extends AbstractController{

	//Services -----------------------------------
	@Autowired
	private SocialIdentityService socialIdentityService;	
	@Autowired
	private ActorService actorService;
	@Autowired
	private NoAdminService noAdminService;
	@Autowired
	private CurriculaService curriculaService;
	@Autowired
	private NutritionistService nutritionistService;
	@Autowired
	private EndorsementService endorsementService;
	
	//Constructor ---------------------------------
	public SocialIdentityController() {
		super();
	}
	
	//Listing--------------------------------------------
	
	@RequestMapping(value="/personalData/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) Integer actorId) {
		ModelAndView result;
		Boolean esNoAdmin = false;
		Boolean follow = false;
		Boolean endorse = true;
		Boolean nutritionist = false;
		NoAdmin napr;
		NoAdmin na;
		Actor a;
		Actor pr = null;
		int rol = -1;

		
		try{//El usuario ha realizado login
			pr= actorService.findByPrincipal();
			if(actorId == null)
				a = pr;
			else
				a= actorService.findOne(actorId);
			
			Assert.notNull(pr);
			Assert.notNull(a);
			
			if((a instanceof NoAdmin && pr instanceof NoAdmin) && !pr.equals(a)){
				esNoAdmin = true;
				
				napr = (NoAdmin) pr;
				na = (NoAdmin) a;
				
				follow = !napr.getNoAdmins().contains(na);
			}
			
			if(a instanceof Nutritionist)
				nutritionist = true;
			
			if(pr instanceof Admin)
				rol = 0;
			else if(pr instanceof User)
				rol = 1;
			else if(pr instanceof Nutritionist)
				rol = 2;
			else if(pr instanceof Sponsor)
				rol = 3;
			else if(pr instanceof Cook)
				rol = 4;
			
			result = new ModelAndView("profile/personalData/list");

		}
		catch(Throwable oops){ //El usuario no ha realizado login
			a= actorService.findOne(actorId);
			result = new ModelAndView("profile/personalData/list");

		}
		
		Curricula curricula = null;
		Collection<Endorsement> endorsements = null;
		if(nutritionist){
			Nutritionist n = (Nutritionist) a;
			curricula = n.getCurricula();
			if(curricula!=null){
				endorsements = curricula.getEndorsements();
				for(Endorsement e: endorsements)
						if(e.getActor().equals(pr))
							endorse = false;
			}
		}
		
		result.addObject("endorsements", endorsements);
		result.addObject("curricula", curricula);
		result.addObject("rol", rol);
		result.addObject("socialIdentity", a.getSocialIdentity());
		result.addObject("actor", a);
		result.addObject("esNoAdmin", esNoAdmin);
		result.addObject("follow", follow);
		result.addObject("endorse", endorse);
		result.addObject("nutritionist", nutritionist);
		
		return result;	
	}
	
	
	//Creation ------------------------------------
	@RequestMapping(value="/socialIdentity/register", method= RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		SocialIdentity si;
		
		si= socialIdentityService.create();
		result= createEditModelAndView(si);
		return result;
	}
	
	//Edition --------------------------------------
	@RequestMapping(value="/socialIdentity/edit", method= RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		Actor a = actorService.findByPrincipal();
		SocialIdentity si;
		si = a.getSocialIdentity();
		result= createEditModelAndView(si);
		
		return result;
	}
	
	@RequestMapping(value="/socialIdentity/register", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid SocialIdentity si, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		
		if(binding.hasErrors()){ 
			result= createEditModelAndView(si);
			
		}
		else{
			try {
				socialIdentityService.save(si);
				Actor a = actorService.findByPrincipal();
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/profile/personalData/list.do?actorId="+a.getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(si, "actor.commit.error", "danger");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/socialIdentity/register", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(SocialIdentity si, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			socialIdentityService.delete(si);
			Actor a = actorService.findByPrincipal();
			redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
			result = new ModelAndView("redirect:/profile/personalData/list.do?actorId="+a.getId());
		} catch (Throwable oops) {
			result = createEditModelAndView(si, "actor.commit.error", "danger");
		}
		return result;
	}
	
	@RequestMapping(value="/personalData/follow", method=RequestMethod.GET)
	public ModelAndView follow(@RequestParam int actorId, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Actor a = actorService.findOne(actorId);
		try {
			NoAdmin na = (NoAdmin) a;
			noAdminService.follow(na);

			redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (Throwable oops) {

			redirectAttrs.addFlashAttribute("message", "actor.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		result = new ModelAndView("redirect:/profile/personalData/list.do?actorId="+a.getId());
		return result;
	}
	
	@RequestMapping(value="/personalData/unfollow", method=RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam int actorId, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Actor a = actorService.findOne(actorId);
		try {
			NoAdmin na = (NoAdmin) a;
			noAdminService.unfollow(na);

			redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (Throwable oops) {

			redirectAttrs.addFlashAttribute("message", "actor.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		result = new ModelAndView("redirect:/profile/personalData/list.do?actorId="+a.getId());
		return result;
	}
	
	//Creation ------------------------------------
	@RequestMapping(value="/curricula/create", method= RequestMethod.GET)
	public ModelAndView createCurricula(){
		ModelAndView result;
		Curricula c;
		
		c= curriculaService.create();
		result= createEditModelAndView(c);
		return result;
	}
	
	//Edition --------------------------------------
	@RequestMapping(value="/curricula/edit", method= RequestMethod.GET)
	public ModelAndView editCurricula(){
		ModelAndView result;
		Nutritionist n = nutritionistService.findByPrincipal();
		Curricula c;
		c = n.getCurricula();
		result= createEditModelAndView(c);
		
		return result;
	}
	
	@RequestMapping(value="/curricula/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Curricula c, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		
		if(binding.hasErrors()){ 
			result= createEditModelAndView(c);
			for(ObjectError o: binding.getAllErrors()){
			System.out.println("ARGUMENT: "+o.getArguments());
			System.out.println("CODE: "+o.getCode());
			System.out.println("MESSAGE: "+o.getDefaultMessage());
		}
		System.out.println(binding.getAllErrors());
			
		}
		else{
			try {
				curriculaService.save(c);
				Actor a = actorService.findByPrincipal();
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/profile/personalData/list.do?actorId="+a.getId());
			} catch (Throwable oops) {
				redirectAttrs.addFlashAttribute("message", "actor.commit.error");
				redirectAttrs.addFlashAttribute("msgType", "danger");
				result = createEditModelAndView(c, "actor.commit.error", "danger");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/curricula/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Curricula c, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			curriculaService.delete(c);
			Actor a = actorService.findByPrincipal();
			redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
			result = new ModelAndView("redirect:/profile/personalData/list.do?actorId="+a.getId());
		} catch (Throwable oops) {
			redirectAttrs.addFlashAttribute("message", "actor.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
			result = createEditModelAndView(c, "actor.commit.error", "danger");
		}
		return result;
	}
	
	@RequestMapping(value="/personalData/endorse", method=RequestMethod.GET)
	public ModelAndView endorse(@RequestParam int curriculaId, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Curricula c = curriculaService.findOne(curriculaId);
		try {
			endorsementService.endorse(c);
			redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (Throwable oops) {

			redirectAttrs.addFlashAttribute("message", "actor.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		Nutritionist n = nutritionistService.findByCurricula(c);
		result = new ModelAndView("redirect:/profile/personalData/list.do?actorId="+n.getId());
		return result;
	}
	
	@RequestMapping(value="/personalData/noEndorse", method=RequestMethod.GET)
	public ModelAndView noEndorse(@RequestParam int curriculaId, RedirectAttributes redirectAttrs) {
		
		ModelAndView result;
		Curricula c = curriculaService.findOne(curriculaId);
		Endorsement e = null;
		Actor a = null;
		
		try {
			a = actorService.findByPrincipal();
			e = endorsementService.findByActor(c, a);
			endorsementService.delete(e);			
			redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (Throwable oops) {
			redirectAttrs.addFlashAttribute("message", "actor.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}
		
		Nutritionist n = nutritionistService.findByCurricula(c);
		result = new ModelAndView("redirect:/profile/personalData/list.do?actorId="+n.getId());

		return result;	
	}
	
	//Ancillary methods -----------------------------
	
	protected ModelAndView createEditModelAndView(SocialIdentity si){
		ModelAndView result;
		result= createEditModelAndView(si, null, null);
		return result;
				
	}

	private ModelAndView createEditModelAndView(SocialIdentity si, String message, String msgType) {
		ModelAndView result;
		
		result= new ModelAndView("profile/socialIdentity/register");
		result.addObject("socialIdentity", si);
		result.addObject("message", message);
		result.addObject("msgType", msgType);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Curricula c){
		ModelAndView result;
		result= createEditModelAndView(c, null, null);
		return result;
				
	}

	private ModelAndView createEditModelAndView(Curricula c, String message, String msgType) {
		ModelAndView result;
		
		result= new ModelAndView("profile/curricula/edit");
		result.addObject("curricula", c);
		result.addObject("message", message);
		result.addObject("msgType", msgType);
		
		return result;
	}
}
