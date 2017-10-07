package controllers;

import java.util.ArrayList;
import java.util.Collection;


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
import services.MessageService;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/profile/message")
public class MessageController extends AbstractController {
	//Services ------------------------------
	@Autowired
	private MessageService messageService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private ActorService actorService;

	
	//Constructor ---------------------------
	public MessageController() {
		super();
	}
	
	//Listing -------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int folderId) {
		Actor a;
		a = actorService.findByPrincipal();
		Assert.notNull(a);
		
		Folder folder;
		folder = folderService.findOne(folderId);
		Assert.notNull(folder);
		
		boolean owner = true;
		
		if(!a.getFolders().contains(folder)){
			owner = false;
		}
		
		Collection<Message> messages;
		messages = folder.getMessages();
		Assert.notNull(messages);
		
		ModelAndView result;
			
		result = new ModelAndView("profile/message/list");
		result.addObject("messages", messages);
		result.addObject("folder", folder);
		result.addObject("owner", owner);

		return result;	
	}
	
	//Creation --------------------------------------
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message m;
		m = messageService.create();
		result = createEditModelAndView(m);
		
		Collection<Actor> actors;
		actors = actorService.findAll();
		result.addObject("actors",actors);
		return result;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, params="save")
	public ModelAndView save(Message m, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		if (binding.hasErrors()) {
//				for(ObjectError o: binding.getAllErrors()){
//					System.out.println("ARGUMENT: "+o.getArguments());
//					System.out.println("CODE: "+o.getCode());
//					System.out.println("MESSAGE: "+o.getDefaultMessage());
//				}
//				System.out.println(binding.getAllErrors());
			result = createEditModelAndView(m);
		} else {
			try {
				messageService.save(m);

				redirectAttrs.addFlashAttribute("message", "message.commit.ok");
				redirectAttrs.addFlashAttribute("msgType", "success");
				
				Actor a = actorService.findByPrincipal();
				Folder f;
				f = folderService.findOutboxFrom(a.getId());
				result = new ModelAndView("redirect:list.do?folderId="+f.getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(m, "message.commit.error", "danger");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int messageId, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Message message = messageService.findOne(messageId);
		Assert.notNull(message);
		try {
			Actor a = actorService.findByPrincipal();
			Assert.isTrue(a.getFolders().contains(message.getFolder()));
			messageService.delete(message);
			redirectAttrs.addFlashAttribute("message", "message.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
			Folder f;
			f = folderService.findTrashboxFrom(a.getId());
			result = new ModelAndView("redirect:list.do?folderId="+f.getId());
		} catch (Throwable oops) {
			result = createEditModelAndView(message, "message.commit.error", "danger");
		}
		return result;
	}
	
	//Move --------------------------------------
	@RequestMapping(value="/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam int messageId, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Boolean owner = false;
		Message mensaje = new Message();
		
		try{
			mensaje = messageService.findOne(messageId);
			Assert.notNull(mensaje);
			Actor a = actorService.findByPrincipal();
			if(a.getFolders().contains(mensaje.getFolder()))
				owner = true;
			Collection<Folder> folders;
			folders = new ArrayList<Folder>(a.getFolders());
			folders.remove(mensaje.getFolder());
			result = new ModelAndView("profile/message/move");
			result.addObject("owner",owner);
			result.addObject("messageId",mensaje.getId());
			result.addObject("folders",folders);
		}
		catch(Throwable oops){
			result = createEditModelAndView(mensaje, "message.commit.error", "danger");
		}
		return result;
	}
	
	@RequestMapping(value="/moveAction", method = RequestMethod.GET)
	public ModelAndView moveSave(@RequestParam int messageId, @RequestParam int folderId, RedirectAttributes redirectAttrs) {

		ModelAndView result;
		Message mensaje = messageService.findOne(messageId);
		Folder folder = folderService.findOne(folderId);

		try {
			messageService.move(mensaje, folder);

			redirectAttrs.addFlashAttribute("message", "message.commit.ok");
			redirectAttrs.addFlashAttribute("msgType", "success");
			
			result = new ModelAndView("redirect:list.do?folderId="+folderId);
		} catch (Throwable oops) {
			result = createEditModelAndView(mensaje, "message.commit.error", "danger");
		}
		return result;
	}
	
	//Ancillary methods ------------------------------
	
	protected ModelAndView createEditModelAndView(Message m) {
		ModelAndView result;
		result = createEditModelAndView(m, null, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Message m, String message, String msgType) {
		ModelAndView result;
		
		if(m.getId()==0)
			result = new ModelAndView("profile/message/create");
		else
			result = new ModelAndView("profile/message/move");
		result.addObject("m", m);
		result.addObject("message", message);
		result.addObject("msgType", msgType);

		return result;
	}
		

}
