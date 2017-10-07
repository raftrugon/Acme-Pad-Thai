package services;


import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Admin;
import domain.Cook;
import domain.Folder;
import domain.ForbiddenWord;

import domain.Message;
import domain.Priority;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
	public class MessageServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private MessageService messageService;
		
	//Supporting services --------------------
	
	@Autowired
	private FolderService folderService;
	@Autowired
	private CookService cookService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private ForbiddenWordService forbiddenWordService;
	
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE MASTERCLASS ==");
		System.out.println("========================");
		authenticate("cook2");
		Message message = messageService.create();
		Assert.notNull(message);
		authenticate(null);
	}
	
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE MESSAGE ==");
		System.out.println("========================");
		authenticate("cook2");
		Message m = messageService.findOne(156);
		Assert.notNull(m);
		System.out.println(m.getSubject() + "(" + m.getDeliveryDate() + ")");
		}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE MESSAGE ==");
		System.out.println("========================");
		authenticate("cook2");
		Cook c = cookService.findByPrincipal();
		Assert.notNull(c);	
		Folder f = folderService.findInboxFrom(c.getId());
		Assert.notNull(f);
		Cook c2 = cookService.findOne(195);
		System.out.println("Number of messages " + f.getMessages().size());
		Message m = messageService.create();
		m.setActorFrom(c2);
		m.setActorTo(c);
		m.setBody("hi");
		m.setFolder(f);
		m.setPriority(Priority.HIGH);
		m.setSubject("jgdias");
		messageService.save(m);
		System.out.println("Number of messages " + f.getMessages().size());
		authenticate(null);
	}
	
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE MESSAGE ==");
		System.out.println("========================");
		authenticate("admin2");
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Folder f = folderService.findInboxFrom(a.getId());
		Folder f2 = folderService.findTrashboxFrom(a.getId());
		Message m = messageService.findOne(156);
		
		int num = f.getMessages().size();
		int num2 = f2.getMessages().size();
		System.out.println("Number of messages (inbox) [BEFORE]: "+ num);
		System.out.println("Number of messages (trashbox) [BEFORE]: "+ num2);
		if(m!=null)
			System.out.println("Still being in database.");
		messageService.delete(m);
		Assert.notNull(m);
	
		num= f.getMessages().size();
		num2 = f2.getMessages().size();
		
		System.out.println("Number of messages (inbox) [AFTER]: " +num);
		System.out.println("Number of messages (trashbox) [AFTER]: "+ num2);
		
		if(m!=null)
			System.out.println("Still being in database.");
		
		messageService.delete(m);

		num= f.getMessages().size();
		num2 = f2.getMessages().size();
		
		System.out.println("Number of messages (inbox) [DELETED]: " +num);
		System.out.println("Number of messages (trashbox) [DELETED]: "+ num2);
		
		try{
			messageService.findOne(148);		
		}
		catch(IllegalArgumentException i){
			System.out.println("Deleted from database");
		}
			
		

		authenticate(null);
		
		
	}
	
	@Test
	public void testIsSpam(){
		System.out.println("========================");
		System.out.println("== IS IT SPAM? ==");
		System.out.println("========================");
		Message m = messageService.findOne(155);
		Assert.notNull(m);
		
		Collection<ForbiddenWord> res = forbiddenWordService.findAll();
		Assert.notNull(res);
		
		for(ForbiddenWord fw:res)
			Assert.isTrue(!messageService.isSpam(m.getId(), fw.getName()));

		System.out.println("Message with subject: "+m.getSubject() + " is not spam.");
		
		Assert.notNull(res);
	}
}
