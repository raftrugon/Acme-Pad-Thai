package controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.SponsorService;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController{

	//Services -----------------------------------
	@Autowired
	private SponsorService sponsorService;
	
	//Constructor ---------------------------------
	public SponsorController() {
		super();
	}
	
	//Creation ------------------------------------
	@RequestMapping(value="/register", method= RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Sponsor sponsor;
		
		sponsor= sponsorService.create();
		result= createEditModelAndView(sponsor);
		return result;
	}
	
	//Edition --------------------------------------
	@RequestMapping(value="/edit", method= RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		
		Sponsor sponsor=sponsorService.findByPrincipal();
		Assert.notNull(sponsor);
		result= createEditModelAndView(sponsor);
	
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView saveEdit(@Valid Sponsor sponsor, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		
		if(binding.hasErrors()){ //si hay errores de validación en el formulario
//			for(ObjectError o: binding.getAllErrors()){
//			System.out.println("ARGUMENT: "+o.getArguments());
//			System.out.println("CODE: "+o.getCode());
//			System.out.println("MESSAGE: "+o.getDefaultMessage());
//			}
//			System.out.println(binding.getAllErrors());
			result= createEditModelAndView(sponsor);
			
		}
		else{
			try {		
				sponsorService.saveUpdate(sponsor);
				
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(sponsor, "actor.commit.error", "danger");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Sponsor sponsor, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		
		if(binding.hasErrors()){ 
			for(ObjectError o: binding.getAllErrors()){
			System.out.println("ARGUMENT: "+o.getArguments());
			System.out.println("CODE: "+o.getCode());
			System.out.println("MESSAGE: "+o.getDefaultMessage());
			}
			System.out.println(binding.getAllErrors());
			result= createEditModelAndView(sponsor);
			
		}
		else{
			try {
				sponsorService.save(sponsor);
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(sponsor, "actor.commit.error", "danger");
			}
		}
		return result;
	}
	
	//Ancillary methods -----------------------------
	
	protected ModelAndView createEditModelAndView(Sponsor sponsor){
		ModelAndView result;
		result= createEditModelAndView(sponsor, null, null);
		return result;
				
	}

	private ModelAndView createEditModelAndView(Sponsor sponsor, String message, String msgType) {
		ModelAndView result;
		
		if(sponsor.getId()==0)
			result= new ModelAndView("sponsor/register");
		else
			result= new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);
		result.addObject("message", message);
		result.addObject("msgType", msgType);
		
		return result;
	}
}
