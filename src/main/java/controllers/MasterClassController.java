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

import domain.Cook;
import domain.MasterClass;

import services.ActorService;
import services.CookService;
import services.MasterClassService;

@Controller
@RequestMapping("/masterClass")
public class MasterClassController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private MasterClassService masterClassService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private CookService cookService;
	
	//Constructor ---------------------------
	public MasterClassController() {
		super();
	}
	
	//Listing -------------------------------------
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<MasterClass> masterClasses;
			masterClasses = masterClassService.findAll();
			Assert.notNull(masterClasses);
			Boolean registered = false;
			
			try{
			actorService.findByPrincipal();
			registered = masterClassService.isRegistered();
			}
			catch(IllegalArgumentException e){
				
			}
				
			result = new ModelAndView("masterClass/list");
			result.addObject("masterClasses", masterClasses);
			result.addObject("registered", registered);

			return result;	
		}
		
		@RequestMapping(value="/actor/register", method=RequestMethod.GET)
		public ModelAndView register(@RequestParam int masterClassId, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			try {
				masterClassService.register(masterClassId);

				redirectAttrs.addFlashAttribute("message", "masterClass.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
			} catch (Throwable oops) {

				redirectAttrs.addFlashAttribute("message", "masterClass.commit.error");
				redirectAttrs.addFlashAttribute("msgType", "danger");
			}

			result = new ModelAndView("redirect:/masterClass/list.do");
			return result;
		}
		
		@RequestMapping(value="/actor/deregister", method=RequestMethod.GET)
		public ModelAndView deregister(@RequestParam int masterClassId, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			try {
				masterClassService.deregister(masterClassId);

				redirectAttrs.addFlashAttribute("message", "masterClass.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
			} catch (Throwable oops) {

				redirectAttrs.addFlashAttribute("message", "masterClass.commit.error");
				redirectAttrs.addFlashAttribute("msgType", "danger");
			}

			result = new ModelAndView("redirect:/masterClass/list.do");
			return result;
		}
		
		@RequestMapping(value="/admin/promote", method=RequestMethod.GET)
		public ModelAndView promote(@RequestParam int masterClassId, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			try {
				masterClassService.promote(masterClassId);

				redirectAttrs.addFlashAttribute("message", "masterClass.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
			} catch (Throwable oops) {

				redirectAttrs.addFlashAttribute("message", "masterClass.commit.error");
				redirectAttrs.addFlashAttribute("msgType", "danger");
			}

			result = new ModelAndView("redirect:/masterClass/list.do");
			return result;
		}
		
		@RequestMapping(value="/admin/demote", method=RequestMethod.GET)
		public ModelAndView demote(@RequestParam int masterClassId, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			try {
				masterClassService.demote(masterClassId);

				redirectAttrs.addFlashAttribute("message", "masterClass.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
			} catch (Throwable oops) {

				redirectAttrs.addFlashAttribute("message", "masterClass.commit.error");
				redirectAttrs.addFlashAttribute("msgType", "danger");
			}

			result = new ModelAndView("redirect:/masterClass/list.do");
			return result;
		}
		
		@RequestMapping(value="/cook/list", method = RequestMethod.GET)
		public ModelAndView listMyMasterClasses() {
			ModelAndView result;
			Collection<MasterClass> masterClasses;
			Cook c;
			c = cookService.findByPrincipal();
			Assert.notNull(c);
			masterClasses = c.getMasterClasses();
			Assert.notNull(masterClasses);
			
				
			result = new ModelAndView("masterClass/cook/list");
			result.addObject("masterClasses", masterClasses);

			return result;	
		}
		
		//Creation --------------------------------------
		@RequestMapping(value="/cook/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			MasterClass mc;
			mc = masterClassService.create();
			result = createEditModelAndView(mc);

			return result;
		}
		
		//Edition ----------------------------------------
		@RequestMapping(value="/cook/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int masterClassId) {
			ModelAndView result;
			MasterClass mc;
			mc = masterClassService.findOne(masterClassId);
			Assert.notNull(mc);

			
			result = createEditModelAndView(mc);
			return result;
		}
		
		@RequestMapping(value="/cook/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid MasterClass mc, BindingResult binding, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			if (binding.hasErrors()) {
//				for(ObjectError o: binding.getAllErrors()){
//					System.out.println("ARGUMENT: "+o.getArguments());
//					System.out.println("CODE: "+o.getCode());
//					System.out.println("MESSAGE: "+o.getDefaultMessage());
//				}
//				System.out.println(binding.getAllErrors());
				result = createEditModelAndView(mc);
			} else {
				try {
					masterClassService.save(mc);
					redirectAttrs.addFlashAttribute("message", "masterClass.commit.ok");
					redirectAttrs.addFlashAttribute("msgType", "success");
					result = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(mc, "masterClass.commit.error", "danger");
				}
			}
			return result;
		}
		
		@RequestMapping(value="/cook/edit", method=RequestMethod.POST, params="delete")
		public ModelAndView delete(MasterClass mc, BindingResult binding, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			try {
				masterClassService.delete(mc);
				redirectAttrs.addFlashAttribute("message", "masterClass.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(mc, "masterClass.commit.error", "danger");
			}
			return result;
		}
		
		//Ancillary methods ------------------------------
		
		protected ModelAndView createEditModelAndView(MasterClass mc) {
			ModelAndView result;
			result = createEditModelAndView(mc, null, null);
			return result;
		}
		
		protected ModelAndView createEditModelAndView(MasterClass mc, String message, String msgType) {
			ModelAndView result;
			Boolean owner = false;
			
			Cook c;
			c = cookService.findByPrincipal();
			if(mc.getCook().equals(c))
				owner = true;
			
			result = new ModelAndView("masterClass/cook/edit");
			result.addObject("masterClass", mc);
			result.addObject("owner",owner);
			result.addObject("message", message);
			result.addObject("msgType", msgType);

			return result;
		}
		

}
