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


import domain.Folder;
import domain.NoAdmin;
import domain.Nutritionist;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class NutritionistServiceTest extends AbstractTest {
	//Service under test ---------------------
			@Autowired
			private NutritionistService nutritionistService;
			
		//Supporting services --------------------
			@Autowired
			private UserAccountService userAccountService;
		//Tests ----------------------------------
			
			@Test
			public void testCreate(){
				System.out.println("========================");
				System.out.println("== CREATE NUTRITIONIST ==");
				System.out.println("========================");
				Nutritionist n = nutritionistService.create();
				Assert.notNull(n);
				authenticate(null);
				}
			
			@Test
			public void testFindAll(){
				System.out.println("========================");
				System.out.println("== FIND ALL NUTRITIONIST ==");
				System.out.println("========================");
				Collection<Nutritionist> all;
				all = nutritionistService.findAll();
				Assert.notNull(all);
				for (Nutritionist n: all) {
					System.out.println(n.getName() + "-" + n.getSurname());
				}
			}
			
			@Test
			public void testFindOne(){
				System.out.println("========================");
				System.out.println("== FIND ONE NUTRITIONIST ==");
				System.out.println("========================");
				Nutritionist n;
				n = nutritionistService.findOne(172);
				Assert.notNull(n);
				System.out.println(n.getName() + "-" + n.getSurname());
			}
			
			@Test
			public void testSave(){
				System.out.println("========================");
				System.out.println("== SAVE NUTRITIONIST ==");
				System.out.println("========================");
				
				Collection<Nutritionist> all;
				all = nutritionistService.findAll();
				Assert.notNull(all);
				System.out.println("BEFORE: \n");
				for (Nutritionist n: all) {
					System.out.println(n.getName() + "-" + n.getSurname());
				}
				
				Nutritionist n= nutritionistService.create();
				n.setAddress("calle bgd");
				n.setEmail("ff@yahoo.com");
				Collection<Folder> folders = new HashSet<Folder>();
				n.setFolders(folders);
				n.setName("Peter");
				Collection<NoAdmin> noAdmin = new HashSet<NoAdmin>();
				n.setNoAdmins(noAdmin);
				n.setPhone("654508839");
				n.setSurname("Perter");
				
				UserAccount uA = new UserAccount();
				Authority a = new Authority();
				a.setAuthority("NUTRITIONIST");
				Collection<Authority> authority = new HashSet<Authority>();
				authority.add(a);
				uA.setAuthorities(authority);
				uA.setPassword("nutritionist");
				uA.setUsername("nutritionist9");
				n.setUserAccount(uA);
				
				
				nutritionistService.save(n);
				all = nutritionistService.findAll();
				Assert.notNull(all);
				System.out.println("\nAFTER: \n");
				for (Nutritionist na: all) {
					System.out.println(na.getName() + "-" + na.getSurname());
				}
				authenticate(null);
			}
			
			@Test
			public void testFindByPrincipal(){
			System.out.println("========================");
			System.out.println("== FIND PRINCIPAL ==");
			System.out.println("========================");
			authenticate("nutritionist3");
			Nutritionist n = nutritionistService.findByPrincipal();
			Assert.notNull(n);
			System.out.println(n.getName() + "-" + n.getSurname());
			authenticate(null);
			}
			
			
			@Test
			public void testFindByUserAccount(){
				System.out.println("========================");
				System.out.println("== FIND BY USERACCOUNT ==");
				System.out.println("========================");
				Nutritionist n;
				UserAccount userAccount = userAccountService.findByUserName("nutritionist1");
				n = nutritionistService.findByUserAccount(userAccount);
				Assert.notNull(n);
				System.out.println(n.getName() + "-" + n.getSurname());
			}

}
