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

import domain.Admin;
import domain.Folder;
import domain.NoAdmin;
import domain.Recipe;
import domain.User;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional

public class UserServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private UserService userService;
			
	//Supporting services --------------------
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private AdminService adminService;
	
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE USER ==");
		System.out.println("========================");
		authenticate("admin1");
		User u = userService.create();
		Assert.notNull(u);
		authenticate(null);
	}
	
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE USER ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<User> users;
		users = userService.findAll();
		Assert.notNull(users);
		System.out.println("BEFORE: \n");
		for (User us: users) {
			System.out.println(us.getName() + "-" + us.getSurname());
		}
		User u = userService.create();
		u.setName("Rafa");
		u.setSurname("Trujillo");
		u.setAddress("sinley 288");
		u.setEmail("editorial@us.es");
		u.setPhone("654693928");
		Collection<Folder> folders = new HashSet<Folder>();
		Collection<Recipe> recipes = new HashSet<Recipe>();
		u.setFolders(folders);
		u.setRecipes(recipes);
		Collection<NoAdmin> noAdmins = new HashSet<NoAdmin>();
		u.setNoAdmins(noAdmins);

		UserAccount uA = new UserAccount();
		Authority a = new Authority();
		a.setAuthority("USER");
		Collection<Authority> authority = new HashSet<Authority>();
		authority.add(a);
		uA.setAuthorities(authority);
		uA.setPassword("user");
		uA.setUsername("user12");
		u.setUserAccount(uA);
		
		userService.save(u);
		users = userService.findAll();
		Assert.notNull(users);
		System.out.println("\nAFTER: \n");
		for (User u2: users) {
			System.out.println(u2.getName() + "-" + u2.getSurname());
		}
		authenticate(null);
	}
	
	
	@Test
	public void testFindAll() {
		System.out.println("========================");
		System.out.println("== FIND ALL USER ==");
		System.out.println("========================");
		Collection<User> users;
		users = userService.findAll();
		Assert.notNull(users);
		for (User u: users) {
			System.out.println(u.getName()+" - "+u.getSurname());
		}
	}
	
	@Test
	public void testFindOne() {
		System.out.println("========================");
		System.out.println("== FIND ONE USER ==");
		System.out.println("========================");
		User u;
		u = userService.findOne(162);
		Assert.isTrue(u.getId()!=0);
		Assert.notNull(u);
		System.out.println(u.getName() + "-"  + u.getSurname());
	}
	
	@Test
	public void testFindByPrincipal(){
		System.out.println("========================");
		System.out.println("== FIND PRINCIPAL ==");
		System.out.println("========================");
		authenticate("user1");
		User user;
		user = userService.findByPrincipal();
		Assert.notNull(user);
		System.out.println(user.getName() + "-" + user.getSurname());
		authenticate(null);
	}
	
	@Test
	public void testMinAvgMaxNumberOfRecipes(){
		System.out.println("========================");
		System.out.println("== MIN,AVG,MAX ==");
		System.out.println("========================");
		authenticate("admin1");
		Assert.notNull(adminService.findByPrincipal());
		Double[] res;
		res = userService.minAvgMaxNumberOfRecipes();
		Assert.notNull(res);
		System.out.println("Min,Avg,Max: "+ res);

		authenticate(null);
	}
	
	@Test
	public void testUserWithMoreRecipes(){
		System.out.println("========================");
		System.out.println("== USERWITHMORERECIPES ==");
		System.out.println("========================");
		authenticate("admin1");
		Assert.notNull(adminService.findByPrincipal());
		Collection<User> u;
		u = userService.userWithMoreRecipes();
		Assert.notNull(u);
			System.out.println("USER" + u);
		authenticate(null);
	}
	
	@Test
	public void testFindByPopularity(){
		System.out.println("========================");
		System.out.println("== FINDBYPOPULARITY ==");
		System.out.println("========================");
		authenticate("admin1");
		Assert.notNull(adminService.findByPrincipal());
		Collection<User> u;
		u = userService.findByPopularity();
		Assert.notNull(u);
		for(User user:u){
			System.out.println("USER" + user);
			}
		authenticate(null);
	}
				
	@Test
	public void testByLikesAndDislikes(){
		System.out.println("========================");
		System.out.println("== FINDBYLIKESANDDISLIKES ==");
		System.out.println("========================");
		authenticate("admin1");
		Admin a = adminService.findByPrincipal();
		System.out.println(a);
		Assert.notNull(a);
		Collection<User> u;
		u = userService.findByLikesAndDislikes();
		Assert.notNull(u);
		for(User user:u){
			System.out.println("USER: " + user.getName());
		}
		authenticate(null);
	}
				
	@Test
	public void testFindByUserAccount(){
		System.out.println("========================");
		System.out.println("== FIND BY USERACCOUNT ==");
		System.out.println("========================");
		User u;
		UserAccount userAccount = userAccountService.findByUserName("user1");
		u = userService.findByUserAccount(userAccount);
		Assert.notNull(u);
		System.out.println(u.getName() + "-" + u.getSurname());
	}
	
	@Test
	public void testFindUserByKeyword(){
		System.out.println("========================");
		System.out.println("== FIND BY KEYWORD ==");
		System.out.println("========================");
		authenticate("admin1");
		Assert.notNull(adminService.findByPrincipal());
		Collection<User> u;
		u = userService.findUserByKeyword("Pedr");
		Assert.notNull(u);
			System.out.println("USER" + u);
		authenticate(null);
	}	
}
