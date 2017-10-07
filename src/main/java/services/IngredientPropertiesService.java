package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.IngredientProperties;
import domain.Nutritionist;

import repositories.IngredientPropertiesRepository;

@Service
@Transactional
public class IngredientPropertiesService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private IngredientPropertiesRepository ingredientPropertiesRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private NutritionistService nutritionistService;

	// Constructors -----------------------------------------------------------
	public IngredientPropertiesService() {
		super();
	}
	
	
	// Simple CRUD methods ----------------------------------------------------
	public IngredientProperties create() {	
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		IngredientProperties res;
		res = new IngredientProperties();
	
		return res;
	}
	
	public Collection<IngredientProperties> findAll() {
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Collection<IngredientProperties> res;
		res=ingredientPropertiesRepository.findAll();
		Assert.notNull(res);

		return res;
	}
	
	public IngredientProperties findOne(int ingredientPropertiesId) {
		Assert.isTrue(ingredientPropertiesId != 0);		
		IngredientProperties res = ingredientPropertiesRepository.findOne(ingredientPropertiesId);
		Assert.notNull(res);
		return res;
	}

	public void save(IngredientProperties ingredientProperties) {
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Assert.notNull(ingredientProperties);
		ingredientPropertiesRepository.save(ingredientProperties);		
	}

	public void delete(IngredientProperties ingredientProperties) {
		Nutritionist n = nutritionistService.findByPrincipal();
		Assert.notNull(n);
		Assert.notNull(ingredientProperties);
		Assert.isTrue(ingredientProperties.getId() != 0);
		Assert.isTrue(ingredientPropertiesRepository.exists(ingredientProperties.getId()));
		Assert.isNull(ingredientProperties.getIngredient());
				
		ingredientPropertiesRepository.delete(ingredientProperties.getId());
	}
	
	// Other business methods -------------------------------------------------

}
