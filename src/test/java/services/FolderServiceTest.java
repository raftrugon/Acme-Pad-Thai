package services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Admin;
import domain.Folder;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional

public class FolderServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private FolderService folderService;
			
	//Supporting services --------------------
	@Autowired
	private AdminService adminService;
	
	//Tests ----------------------------------

	@Test
	public void testCreate() {
		System.out.println("========================");
		System.out.println("== CREATE FOLDER ==");
		System.out.println("========================");
		authenticate("admin1");
		Folder folder = folderService.create();
		Assert.notNull(folder);
		authenticate(null);
		
	}
	
	@Test
	public void testFindOne() {
		System.out.println("========================");
		System.out.println("== FIND ONE FOLDER ==");
		System.out.println("========================");
		Folder folder;
		folder = folderService.findOne(115);
		Assert.notNull(folder);
		System.out.println("NOMBRE CARPETA= "+ folder.getName()+" ID= " + folder.getId());
	}
	
	@Test
	public void testSave() {
		System.out.println("========================");
		System.out.println("== SAVE FOLDER ==");
		System.out.println("========================");
		authenticate("admin1");
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);	
		System.out.println("Nº de folders del admin "+a.getName()+": "+a.getFolders().size());
		Folder f = folderService.create();
		f.setName("Carpeta");
		Assert.notNull(f);
		a.getFolders().add(f);	
		folderService.save(f, a);
		System.out.println("DESPUÉS DE GUARDAR");
		System.out.println("Nº de folders del admin "+a.getName()+": "+a.getFolders().size());
	
		authenticate(null);
	}
	
	@Test
	public void testDelete() {
		System.out.println("========================");
		System.out.println("== DELETE FOLDER ==");
		System.out.println("========================");
		authenticate("admin3");
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		System.out.println("FOLDERS(BEFORE):");
		for(Folder f: a.getFolders())
			System.out.println(f.getName());
		Folder f = folderService.findOne(154);
		folderService.delete(f);
		System.out.println("FOLDERS(AFTER):");
		for(Folder f2: a.getFolders())
			System.out.println(f2.getName());
		
		
		authenticate(null);
	}
	
	
	
	@Test
	public void testFindInboxFrom() {
		System.out.println("========================");
		System.out.println("== FIND INBOX FOLDER ==");
		System.out.println("========================");
		authenticate("admin1");
		Admin a=adminService.findByPrincipal();
		Folder f = folderService.findInboxFrom(a.getId());
		authenticate(null);
		
		System.out.println("FOLDER= "+ f.getName());
	}
	
	@Test
	public void testFindSpamboxFrom() {
		System.out.println("========================");
		System.out.println("== FIND SPAM FOLDER ==");
		System.out.println("========================");
		authenticate("admin1");
		Admin a=adminService.findByPrincipal();
		Folder f = folderService.findSpamboxFrom(a.getId());
		authenticate(null);
		
		System.out.println("FOLDER= "+ f.getName());
	}
	
	@Test
	public void testFindOutboxFrom() {
		System.out.println("========================");
		System.out.println("== FIND OUT FOLDER ==");
		System.out.println("========================");
		authenticate("admin1");
		Admin a=adminService.findByPrincipal();
		Folder f = folderService.findOutboxFrom(a.getId());
		authenticate(null);
		
		System.out.println("FOLDER= "+ f.getName());
	}
	
	@Test
	public void testTrashboxFrom() {
		System.out.println("========================");
		System.out.println("== FIND TRASH FOLDER ==");
		System.out.println("========================");
		authenticate("admin1");
		Admin a=adminService.findByPrincipal();
		Folder f = folderService.findTrashboxFrom(a.getId());
		authenticate(null);
		
		System.out.println("FOLDER= "+ f.getName());
	}
			
			
}
