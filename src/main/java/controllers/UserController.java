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

import services.UserService;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

	//Services -----------------------------------
	@Autowired
	private UserService userService;
	
	//Constructor ---------------------------------
	public UserController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
	
		ModelAndView result;
		Collection<User> users;

		users = userService.findAll();
		result = new ModelAndView("user/list");
		result.addObject("users", users);

		return result;
	}
	
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public ModelAndView listByKeyword(@RequestParam("keyword") String keyword) {
		ModelAndView result;
		Collection<User> search;
		search = userService.findUserByKeyword(keyword);
		
		result = new ModelAndView("user/list");
		result.addObject("users", search);
		result.addObject("search", search);
		result.addObject("keyword", keyword);
		return result;
	}
	
	//Creation ------------------------------------
	@RequestMapping(value="/register", method= RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		User user;
		
		user= userService.create();
		result= createEditModelAndView(user);
		return result;
	}
	
	//Edition --------------------------------------
	@RequestMapping(value="/edit", method= RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		
		
		User user=userService.findByPrincipal();
		Assert.notNull(user);
		result= createEditModelAndView(user);
	
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView saveEdit(@Valid User user, BindingResult binding, RedirectAttributes redirectAttrs) {
	ModelAndView result;
	
	if(binding.hasErrors()){ //si hay errores de validación en el formulario
//		for(ObjectError o: binding.getAllErrors()){
//		System.out.println("ARGUMENT: "+o.getArguments());
//		System.out.println("CODE: "+o.getCode());
//		System.out.println("MESSAGE: "+o.getDefaultMessage());
//		}
//		System.out.println(binding.getAllErrors());
		result= createEditModelAndView(user);
		
	}
	else{
		try {
			
			userService.saveUpdate(user);
		
			
			redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
			result= new ModelAndView("redirect:/welcome/index.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(user, "actor.commit.error", "danger");
		}
	}
	return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid User user, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		
		if(binding.hasErrors()){ //si hay errores de validación en el formulario
//			for(ObjectError o: binding.getAllErrors()){
//			System.out.println("ARGUMENT: "+o.getArguments());
//			System.out.println("CODE: "+o.getCode());
//			System.out.println("MESSAGE: "+o.getDefaultMessage());
//			}
//			System.out.println(binding.getAllErrors());
			result= createEditModelAndView(user, "actor.commit.error", "danger");
			
		}
		else{
			try {
				
				userService.save(user);			
				
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result= new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(user, "actor.commit.error", "danger");
			}
		}
		return result;
	}
	
	//Ancillary methods -----------------------------
	
	protected ModelAndView createEditModelAndView(User user){
		ModelAndView result;
		result= createEditModelAndView(user, null, null);
		return result;
				
	}

	private ModelAndView createEditModelAndView(User user, String message, String msgType) {
		ModelAndView result;
		
		if(user.getId()==0)
			result= new ModelAndView("user/register");
		else
			result= new ModelAndView("user/edit");
		result.addObject("user", user);
		result.addObject("message", message);
		result.addObject("msgType", msgType);
		
		return result;
	}
}