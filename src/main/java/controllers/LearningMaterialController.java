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

import domain.Actor;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;

import services.ActorService;
import services.CookService;
import services.LearningMaterialService;
import services.MasterClassService;

@Controller
@RequestMapping("/learningMaterial")
public class LearningMaterialController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private LearningMaterialService learningMaterialService;
	@Autowired
	private MasterClassService masterClassService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private CookService cookService;

	
	//Constructor ---------------------------
	public LearningMaterialController() {
		super();
	}
	
	//Listing -------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int masterClassId) {
		ModelAndView result;
		Collection<LearningMaterial> learningMaterials;
		Boolean registered = false;
		Boolean owner = false;
		MasterClass masterClass;
		masterClass = masterClassService.findOne(masterClassId);
		Assert.notNull(masterClass);
		Actor a;
		a= actorService.findByPrincipal();
		Assert.notNull(a);
		if(masterClass.getActors().contains(a) || masterClass.getCook().equals(a))
			registered = true; //Sólo pueden acceder registrados y el propietario.
		if(masterClass.getCook().equals(a))
			owner = true; //Sólo puede acceder el propietario.
			
	
		learningMaterials = masterClass.getLearningMaterials();
		Assert.notNull(learningMaterials);
		result = new ModelAndView("learningMaterial/list");
		result.addObject("registered", registered);
		result.addObject("owner", owner);
		result.addObject("masterClassId", masterClass.getId());
		result.addObject("learningMaterials", learningMaterials);
		return result;	
	}
	
	@RequestMapping(value="/cook/list", method = RequestMethod.GET)
	public ModelAndView listMyLearningMaterials(@RequestParam int masterClassId) {
		ModelAndView result;
		Collection<LearningMaterial> learningMaterials;
		Boolean registered = false;
		MasterClass masterClass;
		masterClass = masterClassService.findOne(masterClassId);
		Assert.notNull(masterClass);
		Actor a;
		a= actorService.findByPrincipal();
		Assert.notNull(a);
		if(masterClass.getCook().equals(a))
			registered = true;
			
	
		learningMaterials = masterClass.getLearningMaterials();
		Assert.notNull(learningMaterials);
		result = new ModelAndView("learningMaterial/cook/list");
		result.addObject("registered", registered);
		result.addObject("masterClassId", masterClass.getId());
		result.addObject("learningMaterials", learningMaterials);
		return result;	
	}
	
	//Creation --------------------------------------
	@RequestMapping(value="/cook/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int masterClassId) {	
		ModelAndView result;
		Boolean registered = false;
		MasterClass mc;
		mc = masterClassService.findOne(masterClassId);
		Assert.notNull(mc);
		LearningMaterial l;
		l = learningMaterialService.create(mc);
		
		Cook c;
		c = cookService.findByPrincipal();
		if(mc.getCook().equals(c))
			registered = true; //Sólo puede acceder el propietario.
		
		result = createEditModelAndView(l);
		result.addObject("registered",registered);
		return result;
	}
	
	//Edition ----------------------------------------
	@RequestMapping(value="/cook/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int learningMaterialId) {
		ModelAndView result;
		Boolean registered = false;
		LearningMaterial l;
		l = learningMaterialService.findOne(learningMaterialId);
		Assert.notNull(l);
		
		Cook c;
		c = cookService.findByPrincipal();
		if(l.getMasterClass().getCook().equals(c))
			registered = true; //Sólo puede acceder el propietario.
		
		
		result = createEditModelAndView(l);
		
		result.addObject("registered",registered);
		return result;
	}
	
	@RequestMapping(value="/cook/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid LearningMaterial l, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		if (binding.hasErrors()) {
//						for(ObjectError o: binding.getAllErrors()){
//							System.out.println("ARGUMENT: "+o.getArguments());
//							System.out.println("CODE: "+o.getCode());
//							System.out.println("MESSAGE: "+o.getDefaultMessage());
//						}
//						System.out.println(binding.getAllErrors());
			result = createEditModelAndView(l);
		} else {
			try {
				learningMaterialService.save(l);
				redirectAttrs.addFlashAttribute("message", "learningMaterial.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result = new ModelAndView("redirect:list.do?masterClassId="+l.getMasterClass().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(l, "learningMaterial.commit.error", "danger");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/cook/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(LearningMaterial l, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			learningMaterialService.delete(l);
			redirectAttrs.addFlashAttribute("message", "learningMaterial.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
			result = new ModelAndView("redirect:list.do?masterClassId="+l.getMasterClass().getId());
		} catch (Throwable oops) {
			result = createEditModelAndView(l, "learningMaterial.commit.error", "danger");
		}
		return result;
	}
	
	//Ancillary methods ------------------------------
	
	protected ModelAndView createEditModelAndView(LearningMaterial l) {
		ModelAndView result;
		result = createEditModelAndView(l, null, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(LearningMaterial l, String message, String msgType) {
		ModelAndView result;
		result = new ModelAndView("learningMaterial/cook/edit");
		result.addObject("learningMaterial", l);
		result.addObject("masterClassId", l.getMasterClass().getId());
		result.addObject("message", message);
		result.addObject("msgType", msgType);

		return result;
	}
		

}
