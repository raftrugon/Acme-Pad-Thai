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
import domain.ForbiddenWord;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional

public class ForbiddenWordServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private ForbiddenWordService forbiddenWordService;
			
	//Supporting services --------------------
	@Autowired
	private AdminService adminService;
	//Tests ----------------------------------

	@Test
	public void testCreate() {
		System.out.println("========================");
		System.out.println("== CREATE FORBIDDENWORD ==");
		System.out.println("========================");
		authenticate("admin1");
		ForbiddenWord fw = forbiddenWordService.create();
		Assert.notNull(fw);
		authenticate(null);
		
	}
	
	@Test
	public void testFindOne() {
		System.out.println("========================");
		System.out.println("== FIND ONE FORBIDDENWORD ==");
		System.out.println("========================");
		ForbiddenWord fw;
		fw = forbiddenWordService.findOne(36);
		Assert.notNull(fw);
		System.out.println("NOMBRE = "+ fw.getName()+" ID= " + fw.getId());
	}
	
	@Test
	public void testFindAll() {
		System.out.println("========================");
		System.out.println("== FIND ALL FORBIDDENWORD ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<ForbiddenWord> fws;
		fws = forbiddenWordService.findAll();
		Assert.notNull(fws);
		for (ForbiddenWord fw: fws) {
			System.out.println(fw.getName());
		}
		
		authenticate(null);

	}
	
	@Test
	public void testSave() {
		System.out.println("========================");
		System.out.println("== SAVE FORBIDDENWORD ==");
		System.out.println("========================");
		authenticate("admin1");
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);	
		Collection<ForbiddenWord> fws;
		fws = forbiddenWordService.findAll();
		Assert.notNull(fws);
		for (ForbiddenWord fw: fws) {
			System.out.println(fw.getName());
		}
		ForbiddenWord fw = forbiddenWordService.create();
		fw.setName("consolador");
		Assert.notNull(fw);	
		forbiddenWordService.save(fw);
		fws = forbiddenWordService.findAll();
		System.out.println("DESPUÉS DE GUARDAR");
		for (ForbiddenWord fw1: fws) {
			System.out.println(fw1.getName());
		}			
		authenticate(null);
	}
	
	@Test
	public void testDelete() {
		System.out.println("========================");
		System.out.println("== DELETE FORBIDDENWORD ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<ForbiddenWord> fws = forbiddenWordService.findAll();
		System.out.println("Number of ForbiddenWords (BEFORE): " + fws.size());
		ForbiddenWord fw = forbiddenWordService.findOne(36);
		forbiddenWordService.delete(fw);
		fws = forbiddenWordService.findAll();
		System.out.println("Number of ForbiddenWords (AFTER): " + fws.size());				
		authenticate(null);
		
	}
			
			
}
