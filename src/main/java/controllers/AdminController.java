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

import services.AdminService;
import domain.Admin;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController{

	//Services -----------------------------------
	@Autowired
	private AdminService adminService;
	
	//Constructor ---------------------------------
	public AdminController() {
		super();
	}
	
	//Edition --------------------------------------
	@RequestMapping(value="/edit", method= RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		
		Admin admin=adminService.findByPrincipal();
		Assert.notNull(admin);
		result= createEditModelAndView(admin);
	
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView saveEdit(@Valid Admin admin, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		
		if(binding.hasErrors()){ //si hay errores de validación en el formulario
//				for(ObjectError o: binding.getAllErrors()){
//				System.out.println("ARGUMENT: "+o.getArguments());
//				System.out.println("CODE: "+o.getCode());
//				System.out.println("MESSAGE: "+o.getDefaultMessage());
//				}
//				System.out.println(binding.getAllErrors());
			result= createEditModelAndView(admin);	
		}
		else{
			try {
				
				adminService.save(admin);
	
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(admin, "actor.commit.error", "danger");
			}
		}
		return result;
	}
	
	//Ancillary methods -----------------------------
	
	protected ModelAndView createEditModelAndView(Admin admin){
		ModelAndView result;
		result= createEditModelAndView(admin, null, null);
		return result;
				
	}

	private ModelAndView createEditModelAndView(Admin admin, String message, String msgType) {
		ModelAndView result;

		result= new ModelAndView("admin/edit");
		
		result.addObject("admin", admin);
		result.addObject("message", message);
		result.addObject("msgType", msgType);
		
		return result;
	}
}
