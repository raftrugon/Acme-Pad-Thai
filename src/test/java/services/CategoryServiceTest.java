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


import domain.Category;




import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional

public class CategoryServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private CategoryService categoryService;
		
	//Supporting services --------------------
	
	//Tests ----------------------------------

	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE CATEGORY ==");
		System.out.println("========================");
		authenticate("admin1");
		Category c = categoryService.create();
		Assert.notNull(c);
		authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		System.out.println("========================");
		System.out.println("== FIND ALL CATEGORIES ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<Category> cat;
		cat= categoryService.findAll();
		Assert.notNull(cat);
		for(Category c: cat){
			System.out.println(c.getName() + "-" + c.getTags());
		}  
		authenticate(null);
	}
	
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE ITEM ==");
		System.out.println("========================");
		authenticate("admin1");
		Category c;
		c = categoryService.findOne(40);
		Assert.notNull(c);
		System.out.println(c.getName() + "-" + c.getTags());
	}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE CATEGORY==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<Category> all = categoryService.findAll();
		System.out.println("BEFORE: \n");
		for (Category ca: all) {
			System.out.println(ca.getName() + "-" + ca.getTags());
		}
		Category c = categoryService.create();
		c.setDescription("puzzle chocolate ");
		c.setName("puzzle");
		Collection<String> tags = new HashSet<String>();
		c.setTags(tags);
		Collection<Category> categories = new HashSet<Category>();
		c.setCategoriesSons(categories);
		Category cas = categoryService.findOne(40);
		c.setCategoryFather(cas);

		categoryService.save(c);
		all = categoryService.findAll();
		System.out.println("\nAFTER: \n");
		for (Category casa: all) {
			System.out.println(casa.getName() + "-" + casa.getTags());
		}
		authenticate(null);
	}
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE CATEGORY ==");
		System.out.println("========================");
		authenticate("admin2");
		Collection<Category> all = categoryService.findAll();
		System.out.println("BEFORE: \n");
		for (Category ca: all) {
			System.out.println(ca.getName() + "-" + ca.getTags());
		}
		Category c = categoryService.findOne(45);
		categoryService.delete(c);
		
		all = categoryService.findAll();
		System.out.println("\nAFTER: \n");
		for (Category c2: all) {
			System.out.println(c2.getName() + "-" + c2.getTags());
		}
		authenticate(null);
	}
	
	@Test
	public void testNestedCategories(){
		System.out.println("========================");
		System.out.println("== Nested Categories ==");
		System.out.println("========================");
		authenticate("admin2");
		Category c = categoryService.findOne(40);
		Assert.notNull(c);
		Collection<Category> res;
		res = categoryService.nestedCategories(c);
		Assert.notNull(res);
		System.out.println("NestedCategories : " + res);
		authenticate(null);
		
	}
			
		
}
