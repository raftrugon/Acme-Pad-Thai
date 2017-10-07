package services;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Cook;
import domain.Folder;
import domain.MasterClass;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional

public class CookServiceTest extends AbstractTest {
	
	//Service under test ---------------------
		@Autowired
		private CookService cookService;
			
		//Supporting services --------------------
		
		@Autowired
		private UserAccountService userAccountService;
		
		//Tests ----------------------------------
		
		@Test
		public void testCreate(){
			System.out.println("========================");
			System.out.println("== CREATE COOK ==");
			System.out.println("========================");
			authenticate("admin1");
			Cook cook = cookService.create();
			Assert.notNull(cook);
			authenticate(null);
		}
		
		@Test
		public void testFindAll(){
			System.out.println("========================");
			System.out.println("== FIND ALL COOK ==");
			System.out.println("========================");
			Collection<Cook> all;
			all = cookService.findAll();
			Assert.notNull(all);
			for (Cook c: all) {
				System.out.println(c.getName() + " " + c.getSurname());
			}
		}
		
		@Test
		public void testFindOne(){
			System.out.println("========================");
			System.out.println("== FIND ONE COOK ==");
			System.out.println("========================");
			Cook cook = cookService.findOne(193);
			Assert.notNull(cook);
			System.out.println(cook.getName() + " "  + cook.getSurname());
		}
		
		@Test
		public void testSave(){
			System.out.println("========================");
			System.out.println("== SAVE COOK ==");
			System.out.println("========================");
			authenticate("admin1");
			
			Collection<Cook> all;
			all = cookService.findAll();
			Assert.notNull(all);
			System.out.println("BEFORE: \n");
			for (Cook c: all) {
				System.out.println(c.getName() + "-" + c.getSurname());
			}
			Cook cook = cookService.create();
			cook.setName("Peter");
			cook.setSurname("Griffin");
			cook.setAddress("Libertinaje 288ºP");
			cook.setEmail("oyetubasta@us.es");
			cook.setPhone("654493928");
			Collection<Folder> folders = new HashSet<Folder>();
			cook.setFolders(folders);
			Collection<MasterClass> masterclasses = new HashSet<MasterClass>();
			cook.setMasterClasses(masterclasses);
			
			UserAccount uA = new UserAccount();
			Authority a = new Authority();
			a.setAuthority("COOK");
			Collection<Authority> authority = new HashSet<Authority>();
			authority.add(a);
			uA.setAuthorities(authority);
			uA.setPassword("cook");
			uA.setUsername("cook9");
			cook.setUserAccount(uA);
			
			cookService.save(cook);
			all = cookService.findAll();
			Assert.notNull(all);
			System.out.println("\nAFTER: \n");
			for (Cook c: all) {
				System.out.println(c.getName() + "-" + c.getSurname());
			}
			authenticate(null);
		}
		
		@Test
		public void testFindByPrincipal(){
			System.out.println("========================");
			System.out.println("== FIND PRINCIPAL ==");
			System.out.println("========================");
			authenticate("cook1");
			Cook cook = cookService.findByPrincipal();
			Assert.notNull(cook);
			System.out.println(cook.getName() + "-" + cook.getSurname());
			authenticate(null);
		}
		
		
		@Test
		public void testFindByUserAccount(){
			System.out.println("========================");
			System.out.println("== FIND BY USERACCOUNT ==");
			System.out.println("========================");
			UserAccount userAccount = userAccountService.findByUserName("cook2");
			Cook cook = cookService.findByUserAccount(userAccount);
			Assert.notNull(cook);
			System.out.println(cook.getName() + " " + cook.getSurname());
		}
		
		
		@Test
		public void testFindAllOrderedByMasterClassesPromoted(){
			System.out.println("========================");
			System.out.println("== FindAllordered ==");
			System.out.println("========================");
			authenticate("admin1");
			Collection<Cook> res = cookService.findAllOrderedByMasterClassesPromoted();
			Assert.notNull(res);
			  for(Cook c: res){
				  System.out.println(c.getName() + "-" + c.getMasterClasses().size());
			  }
			authenticate(null);
		}
		
			

}
