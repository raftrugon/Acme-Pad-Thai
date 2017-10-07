package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Admin;
import domain.Category;
import domain.Recipe;

@Service
@Transactional
public class CategoryService {
	
	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository categoryRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private AdminService adminService;
	@Autowired
	private RecipeService recipeService;
	
	// Constructors -----------------------------------------------------------

		public CategoryService() {
			super();
		}

	// Simple CRUD methods ----------------------------------------------------
		public Category create() {
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Category res;
			res = new Category();	
			res.setTags(new ArrayList<String>());
			res.setCategoriesSons(new ArrayList<Category>());
			res.setRecipes(new ArrayList<Recipe>());

			return res;

		}
		
		public Collection<Category> findAll() {
			Collection<Category> res;
			res=categoryRepository.findAll();
			Assert.notNull(res);

			return res;
		}

		public Category findOne(int categoryId) {
			Assert.isTrue(categoryId != 0);
			Category res;
			res=categoryRepository.findOne(categoryId);
			Assert.notNull(res);

			return res;
		}

		public void save(Category categoryh) {
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Assert.notNull(categoryh);
			categoryRepository.save(categoryh);
			

		}	

		public void delete(Category category) {
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Assert.notNull(category);
			Assert.isTrue(category.getId() != 0);
			Assert.isTrue(categoryRepository.exists(category.getId()));	
			Collection<Recipe> recipes = recipeService.showRecipesByCategory(category.getId());
			Assert.notNull(recipes);
			Assert.isTrue(recipes.isEmpty());
			categoryRepository.delete(category);

		}
		//Other business methods---------------------------------------------
		
		public Collection<Category> nestedCategories(Category category){
			Assert.notNull(category);
			Collection<Category> res = new ArrayList<Category>();
			res.add(category);
			Category next = category;
			Category father = category.getCategoryFather();//Father category
			
			while(father!=null){
				res.add(father);
				next = father;
				father = next.getCategoryFather();			
			}
			
			return res;		
		}
}
