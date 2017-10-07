package controllers;

import java.util.ArrayList;
import java.util.List;

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

import services.CommentService;
import services.NoAdminService;
import services.RecipeService;
import domain.Comment;
import domain.NoAdmin;
import domain.Recipe;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private NoAdminService noAdminService;
	@Autowired
	private CommentService commentService;
	
	//Constructor ---------------------------
	public CommentController() {
		super();
	}
	
	//Listing -------------------------------------
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list(@RequestParam int recipeId) {
			ModelAndView result;
			Boolean noAdmin = false;
			
			try{
				NoAdmin na = noAdminService.findByPrincipal();
				if(na!=null)
					noAdmin=true;
				}
			catch(Throwable oops){			
			}
			
			List<Comment> comments = new ArrayList<Comment>();
			comments = commentService.findCommentsOrderByDate(recipeId);
			Assert.notNull(comments);
			
			
				
			result = new ModelAndView("comment/list");
			result.addObject("recipeId", recipeId);
			result.addObject("comments", comments);
			result.addObject("noAdmin", noAdmin);

			return result;	
		}
		
		//Creation --------------------------------------
		@RequestMapping(value="/create", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam int recipeId) {
			ModelAndView result;		
			Boolean noAdmin = false;
			
			Recipe r = recipeService.findOne(recipeId);
			Comment c;
			c = commentService.create();
			c.setRecipe(r);
			
			NoAdmin na = noAdminService.findByPrincipal();
			if(na!=null)
				noAdmin=true;
			
			result = createEditModelAndView(c);
			result.addObject("noAdmin",noAdmin);
			result.addObject("recipeId",recipeId);

			return result;
		}
		
		@RequestMapping(value="/create", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid Comment c, BindingResult binding, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			if (binding.hasErrors()) {
//				for(ObjectError o: binding.getAllErrors()){
//					System.out.println("ARGUMENT: "+o.getArguments());
//					System.out.println("CODE: "+o.getCode());
//					System.out.println("MESSAGE: "+o.getDefaultMessage());
//				}
//				System.out.println(binding.getAllErrors());
				result = createEditModelAndView(c);
			} else {
				try {
					Assert.isTrue(!c.getRecipe().getReadOnly());
					commentService.save(c);
					redirectAttrs.addFlashAttribute("message", "comment.commit.ok");
					redirectAttrs.addFlashAttribute("msgType", "success");
					result = new ModelAndView("redirect:list.do?recipeId="+c.getRecipe().getId());
				} catch (Throwable oops) {
					result = createEditModelAndView(c, "comment.commit.error", "danger");
				}
			}
			return result;
		}
		
		//Ancillary methods ------------------------------
		
		protected ModelAndView createEditModelAndView(Comment c) {
			ModelAndView result;
			result = createEditModelAndView(c, null, null);
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Comment c, String message, String msgType) {
			ModelAndView result;
			Boolean noAdmin = false;
			
			NoAdmin na = noAdminService.findByPrincipal();
			if(na!=null)
				noAdmin=true;
			
			result = new ModelAndView("comment/create");
			result.addObject("noAdmin", noAdmin);
			result.addObject("comment", c);
			result.addObject("message", message);
			result.addObject("msgType", msgType);

			return result;
		}
		

}
