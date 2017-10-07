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

import services.ActorService;
import services.FolderService;
import domain.Actor;
import domain.Folder;

@Controller
@RequestMapping("/profile/folder")
public class FolderController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private FolderService folderService;
	@Autowired
	private ActorService actorService;

	
	//Constructor ---------------------------
	public FolderController() {
		super();
	}
	
	//Listing -------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list() {
		Actor a;
		a = actorService.findByPrincipal();
		Assert.notNull(a);
		
		ModelAndView result;
		Collection<Folder> folders;
		folders = a.getFolders();
		Assert.notNull(folders);
			
		result = new ModelAndView("profile/folder/list");
		result.addObject("folders", folders);

		return result;	
	}
	
	//Creation --------------------------------------
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Folder f;
		f = folderService.create();
		result = createEditModelAndView(f);
		return result;
	}
	
	//Edition ----------------------------------------
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int folderId) {
		ModelAndView result;
		Folder f;
		f = folderService.findOne(folderId);
		Assert.notNull(f);

		result = createEditModelAndView(f);
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Folder f, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		if (binding.hasErrors()) {
//				for(ObjectError o: binding.getAllErrors()){
//					System.out.println("ARGUMENT: "+o.getArguments());
//					System.out.println("CODE: "+o.getCode());
//					System.out.println("MESSAGE: "+o.getDefaultMessage());
//				}
//				System.out.println(binding.getAllErrors());
			result = createEditModelAndView(f);
		} else {
			try {

				if(f.getId()==0){
					Actor a;
					a = actorService.findByPrincipal();
					Assert.notNull(a);
					folderService.save(f,a);
				}
				else
					folderService.save(f);

				redirectAttrs.addFlashAttribute("message", "folder.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(f, "folder.commit.error", "danger");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Folder f, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			folderService.delete(f);
			redirectAttrs.addFlashAttribute("message", "folder.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(f, "folder.commit.error", "danger");
		}
		return result;
	}
	
	//Ancillary methods ------------------------------
	
	protected ModelAndView createEditModelAndView(Folder f) {
		ModelAndView result;
		result = createEditModelAndView(f, null, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Folder f, String message, String msgType) {
		ModelAndView result;
		
		Boolean owner = false;
		
		Actor a;
		a = actorService.findByPrincipal();
		if(a.getFolders().contains(f) || f.getId()==0)
			owner = true;
		
		result = new ModelAndView("profile/folder/edit");
		result.addObject("owner",owner);
		result.addObject("folder", f);
		result.addObject("message", message);
		result.addObject("msgType", msgType);

		return result;
	}
		

}
