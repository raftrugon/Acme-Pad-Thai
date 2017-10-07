package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.IngredientRepository;
import domain.Ingredient;
import domain.IngredientProperties;
import domain.Nutritionist;



@Service
@Transactional
public class IngredientService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private IngredientRepository ingredientRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private NutritionistService nutritionistService;
	
	// Constructors -----------------------------------------------------------
	public IngredientService() {
			super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	public Ingredient create() {
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Ingredient res = new Ingredient();
		Collection<IngredientProperties> ing = new ArrayList<IngredientProperties>();
		res.setIngredientProperties(ing);
	
		return res;
	}
	
	public Collection<Ingredient> findAll() {
		Collection<Ingredient> res;
		res=ingredientRepository.findAll();
		Assert.notNull(res);

		return res;
	}
	
	public Ingredient findOne(int ingredientId) {
		Assert.isTrue(ingredientId != 0);		
		Ingredient res = ingredientRepository.findOne(ingredientId);
		Assert.notNull(res);
		return res;
	}

	public void save(Ingredient ingredient) {
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Assert.notNull(ingredient);
		ingredientRepository.save(ingredient);
	}

	public void delete(Ingredient ingredient) {
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Assert.notNull(ingredient);
		Assert.isTrue(ingredient.getId() != 0);
		Assert.isTrue(ingredientRepository.exists(ingredient.getId()));
		Assert.isTrue(ingredient.getIngredientQuantities().isEmpty());
		
		for(IngredientProperties i: ingredient.getIngredientProperties())
			i.setIngredient(null);
				
		ingredientRepository.delete(ingredient.getId());
	}
	
	// Other business methods -------------------------------------------------
	public Collection<Ingredient> findIngredientsFromRecipe(int recipeId){
		Collection<Ingredient> res;
		res=ingredientRepository.findIngredientsFromRecipe(recipeId);
		Assert.notNull(res);
	
		return res;
	}
}
