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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ContestService;
import services.RecipeService;
import services.UserService;
import domain.Contest;
import domain.Recipe;
import domain.User;

@Controller
@RequestMapping("/contest")
public class ContestController extends AbstractController{
	
	//Services ------------------------------
	@Autowired
	private ContestService contestService;
	@Autowired
	private UserService userService;
	@Autowired
	private RecipeService recipeService;
	
	//Constructor ---------------------------
	public ContestController() {
		super();
	}
		
	//Listing ----------------------------------
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public ModelAndView listUser() {
		ModelAndView result;
	    Collection<Contest> contests;
		
		contests = contestService.findAll();
		
		result = new ModelAndView("contest/user/list");
		result.addObject("contests", contests);
		result.addObject("actualDate",new Date());
		result.addObject("requestURI", "contest/user/list.do");
		
		return result;	
	}
	
	//Creation --------------------------------------
	@RequestMapping(value="/admin/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Contest contest;
		
		contest = contestService.create();
		
		result = createEditModelAndView(contest);
		
		return result;
	}
	
	//Listing ----------------------------------------
	@RequestMapping(value="/admin/list", method=RequestMethod.GET)
	public ModelAndView listAdmin() {
		ModelAndView result;
		Collection<Contest> contests;
		
		contests = contestService.findAll();
		
		result = new ModelAndView("contest/admin/list");
		result.addObject("contests", contests);
		result.addObject("actualDate",new Date());
		result.addObject("requestURI", "contest/admin/list.do");
		
		return result;
	}
	
	//Edition ----------------------------------------
	@RequestMapping(value="/admin/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int contestId) {
		ModelAndView result;
		Contest contest;
		
		contest = contestService.findOne(contestId);
		Assert.notNull(contest);
		result = createEditModelAndView(contest);
		
		return result;
	}
	
	@RequestMapping(value="/admin/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Contest contest, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		
			if (binding.hasErrors()) {
//				for(ObjectError o: binding.getAllErrors()){
//					System.out.println("ARGUMENT: "+o.getArguments());
//					System.out.println("CODE: "+o.getCode());
//					System.out.println("MESSAGE: "+o.getDefaultMessage());
//				}
//					System.out.println(binding.getAllErrors());
					
				result = createEditModelAndView(contest);
			} else {
				try {
					Assert.isTrue(contest.getRecipes().isEmpty());
					contestService.save(contest);
					redirectAttrs.addFlashAttribute("message", "contest.commit.ok");
					redirectAttrs.addFlashAttribute("msgType", "success");
					result= new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(contest, "contest.commit.error", "danger");
				}
			}
		
		
		return result;
	}
	
	@RequestMapping(value="/admin/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Contest contest, BindingResult binding) {
		ModelAndView result;
		Collection<Recipe> recipes= contest.getRecipes();
		
		if(recipes.isEmpty()){
			try {
				Assert.isTrue(contest.getRecipes().isEmpty());
				contestService.delete(contest);
				result = listAdmin();
				result.addObject("message", "contest.commit.ok");
				result.addObject("msgType", "success");
			} catch (Throwable oops) {
				result = createEditModelAndView(contest, "contest.commit.error", "danger");
			}
		}else{
			result = createEditModelAndView(contest, "contest.commit.error", "danger");
		}
		
		return result;
	}
	
	//Qualify ----------------------------------------
	@RequestMapping(value="/user/recipes", method = RequestMethod.GET)
	public ModelAndView recipes(@RequestParam int contestId) {
		ModelAndView result;
		Contest contest;
		
		contest = contestService.findOne(contestId);
		Assert.notNull(contest);
		
		Collection<Recipe> recipes;
		User u;
		u = userService.findByPrincipal();
		Assert.notNull(u);
		recipes = recipeService.showNoReadOnlyRecipes(u.getId());
		Assert.notNull(recipes);
		
		Date actualDate = new Date();
		
		result = new ModelAndView("contest/user/recipes");
		result.addObject("contest", contest);
		result.addObject("contestId", contestId);
		result.addObject("userId", u.getId());
		result.addObject("recipes", recipes);
		result.addObject("actualDate", actualDate);
		
		return result;
	}
	
	@RequestMapping(value="/user/qualify", method=RequestMethod.GET)
	public ModelAndView qualify(@RequestParam int contestId, @RequestParam int recipeId, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Contest c = null;
		try {
			Recipe r;
			
			c = contestService.findOne(contestId);
			Assert.notNull(c);
			r = recipeService.findOne(recipeId);
			Assert.notNull(r);
			Assert.isTrue(!contestService.containsCopy(c, r));
			recipeService.registerInAContest(r, c);

			redirectAttrs.addFlashAttribute("message", "contest.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
		} catch (Throwable oops) {
			redirectAttrs.addFlashAttribute("message", "contest.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		result = new ModelAndView("redirect:recipes.do?contestId="+c.getId());
		return result;
	}
	
	@RequestMapping(value="/admin/selectWinners", method=RequestMethod.GET)
	public ModelAndView selectWinners(@RequestParam int contestId, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Contest c = null;
		try {
			c = contestService.findOne(contestId);
			Assert.notNull(c);
			contestService.selectWinnerRecipes(c);

			redirectAttrs.addFlashAttribute("message", "contest.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
			
		} catch (Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getLocalizedMessage());
			redirectAttrs.addFlashAttribute("message", "contest.commit.error");
			redirectAttrs.addFlashAttribute("msgType", "danger");
		}

		result = new ModelAndView("redirect:list.do");
		return result;
	}
	
	//Ancillary methods ------------------------------
	protected ModelAndView createEditModelAndView(Contest contest) {
		ModelAndView result;
			
		result = createEditModelAndView(contest, null, null);
			
		return result;
	}
		
	protected ModelAndView createEditModelAndView(Contest contest, String message, String msgType) {
		ModelAndView result;
		Date d=new Date();
		result = new ModelAndView("contest/admin/edit");
		Integer recipes= contest.getRecipes().size();
		
		result.addObject("contest", contest);
		result.addObject("message", message);
		result.addObject("msgType", msgType);
		result.addObject("fecha", d);
		result.addObject("tamRecipes", recipes);

		return result;
	}
}
