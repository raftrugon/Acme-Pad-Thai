package controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CookService;
import domain.Cook;

@Controller
@RequestMapping("/cook")
public class CookController extends AbstractController{

	//Services -----------------------------------
	@Autowired
	private CookService cookService;
	
	//Constructor ---------------------------------
	public CookController() {
		super();
	}
	
	//Creation ------------------------------------
	@RequestMapping(value="/register", method= RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Cook cook;
		
		cook= cookService.create();
		result= createEditModelAndView(cook);
		return result;
	}
	
	//Edition --------------------------------------
	@RequestMapping(value="/edit", method= RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		
		Cook cook=cookService.findByPrincipal();
		Assert.notNull(cook);
		result= createEditModelAndView(cook);
	
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView saveEdit(@Valid Cook cook, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		
		if(binding.hasErrors()){ //si hay errores de validación en el formulario
//				for(ObjectError o: binding.getAllErrors()){
//				System.out.println("ARGUMENT: "+o.getArguments());
//				System.out.println("CODE: "+o.getCode());
//				System.out.println("MESSAGE: "+o.getDefaultMessage());
//				}
//				System.out.println(binding.getAllErrors());
			result= createEditModelAndView(cook);	
		}
		else{
			try {
				
				cookService.saveUpdate(cook);
	
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(cook, "actor.commit.error", "danger");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Cook cook, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		
		if(binding.hasErrors()){ //si hay errores de validación en el formulario
//			for(ObjectError o: binding.getAllErrors()){
//			System.out.println("ARGUMENT: "+o.getArguments());
//			System.out.println("CODE: "+o.getCode());
//			System.out.println("MESSAGE: "+o.getDefaultMessage());
//			}
//			System.out.println(binding.getAllErrors());
			result= createEditModelAndView(cook);
			
		}
		else{
			try {
				cookService.save(cook);
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(cook, "actor.commit.error", "danger");
			}
		}
		return result;
	}
	
	
	
	//Ancillary methods -----------------------------
	
	protected ModelAndView createEditModelAndView(Cook cook){
		ModelAndView result;
		result= createEditModelAndView(cook, null, null);
		return result;
				
	}

	private ModelAndView createEditModelAndView(Cook cook, String message, String msgType) {
		ModelAndView result;
		
		if(cook.getId()==0)
			result= new ModelAndView("cook/register");
		else
			result= new ModelAndView("cook/edit");
		result.addObject("cook", cook);
		result.addObject("message", message);
		result.addObject("msgType", msgType);
		
		return result;
	}
}
