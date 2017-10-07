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

import services.NutritionistService;
import domain.Nutritionist;

@Controller
@RequestMapping("/nutritionist")
public class NutritionistController extends AbstractController{

	//Services -----------------------------------
	@Autowired
	private NutritionistService nutritionistService;
	
	//Constructor ---------------------------------
	public NutritionistController() {
		super();
	}
	
	//Creation ------------------------------------
	@RequestMapping(value="/register", method= RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Nutritionist nutritionist;
		
		nutritionist= nutritionistService.create();
		result= createEditModelAndView(nutritionist);
		return result;
	}
	
	//Edition --------------------------------------
		@RequestMapping(value="/edit", method= RequestMethod.GET)
		public ModelAndView edit(){
			ModelAndView result;
			
			
			Nutritionist nutritionist=nutritionistService.findByPrincipal();
			Assert.notNull(nutritionist);
			result= createEditModelAndView(nutritionist);
		
			return result;
		}
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
		public ModelAndView saveEdit(@Valid Nutritionist nutritionist, BindingResult binding, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			
			if(binding.hasErrors()){ //si hay errores de validación en el formulario
//				for(ObjectError o: binding.getAllErrors()){
//				System.out.println("ARGUMENT: "+o.getArguments());
//				System.out.println("CODE: "+o.getCode());
//				System.out.println("MESSAGE: "+o.getDefaultMessage());
//				}
//				System.out.println(binding.getAllErrors());
				result= createEditModelAndView(nutritionist);
				
			}
			else{
				try {					
					nutritionistService.saveUpdate(nutritionist);
		
					redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
					redirectAttrs.addFlashAttribute("msgType", "success");
					result= new ModelAndView("redirect:/welcome/index.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(nutritionist, "actor.commit.error", "danger");
				}
			}
			return result;
		}
	
	@RequestMapping(value="/register", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Nutritionist nutritionist, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		
		if(binding.hasErrors()){ 
			result= createEditModelAndView(nutritionist);
			
		}
		else{
			try {
				nutritionistService.save(nutritionist);
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(nutritionist, "actor.commit.error", "danger");
			}
		}
		return result;
	}
	
	//Ancillary methods -----------------------------
	
	protected ModelAndView createEditModelAndView(Nutritionist nutritionist){
		ModelAndView result;
		result= createEditModelAndView(nutritionist, null, null);
		return result;
				
	}

	private ModelAndView createEditModelAndView(Nutritionist nutritionist, String message, String msgType) {
		ModelAndView result;
		
		if(nutritionist.getId()==0)
			result= new ModelAndView("nutritionist/register");
		else
			result= new ModelAndView("nutritionist/edit");
		result.addObject("nutritionist", nutritionist);
		result.addObject("message", message);
		result.addObject("msgType", msgType);
		
		return result;
	}
}
