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

import services.ForbiddenWordService;
import domain.ForbiddenWord;


@Controller
@RequestMapping("/forbiddenWord")
public class ForbiddenWordController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private ForbiddenWordService fwService;

	
	//Constructor ---------------------------
	public ForbiddenWordController() {
		super();
	}
	
	//Creation---------------------------------------------------
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ForbiddenWord fw;
		fw = fwService.create();
		result = createEditModelAndView(fw);
		return result;
	}
	
	//Listing -------------------------------------
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<ForbiddenWord> fw;

			fw = fwService.findAll();
			result = new ModelAndView("forbiddenWord/list");
			result.addObject("forbiddenWord", fw);
			
			return result;	
		}
		
		
		//Edition -------------------------------------
		@RequestMapping(value="/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int fwId) {
			ModelAndView result;
			ForbiddenWord fw;
			
			fw = fwService.findOne(fwId);
			Assert.notNull(fw);
			result = createEditModelAndView(fw);
			
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid ForbiddenWord fw, BindingResult binding,RedirectAttributes redirectAttrs) {
			ModelAndView result;
			if (binding.hasErrors()) {
				result = createEditModelAndView(fw);
			} else {
				try {
					fwService.save(fw);
					redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
					redirectAttrs.addFlashAttribute("msgType", "success");
					result= new ModelAndView("redirect:/forbiddenWord/list.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(fw, "forbiddenWord.commit.error", "danger");
				}
			}
			return result;
			
		}
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
		public ModelAndView delete(ForbiddenWord fw, BindingResult binding) {
			ModelAndView result;
			try {
				fwService.delete(fw);
				result = list();
				result.addObject("message", "forbiddenWord.commit.ok");
				result.addObject("msgType", "success");
			} catch (Throwable oops) {
				result = createEditModelAndView(fw, "forbiddenWord.commit.error", "danger");
			}
			return result;
		}
		
		//Ancillary methods -----------------------------
		
		protected ModelAndView createEditModelAndView(ForbiddenWord fw) {
			ModelAndView result;
			result = createEditModelAndView(fw, null, null);
			return result;
		}
		
		protected ModelAndView createEditModelAndView(ForbiddenWord fw, String message, String msgType) {
			ModelAndView result;
			
			result = new ModelAndView("forbiddenWord/edit");
			result.addObject("forbiddenWord", fw);
			result.addObject("message", message);
			result.addObject("msgType", msgType);

			return result;
		}
		
}