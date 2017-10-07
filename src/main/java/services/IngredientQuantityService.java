package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.IngredientQuantityRepository;
import domain.IngredientQuantity;
import domain.User;

@Service
@Transactional
public class IngredientQuantityService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private IngredientQuantityRepository ingredientQuantityRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------
	public IngredientQuantityService() {
		super();
	}
	
	
	// Simple CRUD methods ----------------------------------------------------
	public IngredientQuantity create() {	
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		IngredientQuantity res;
		res = new IngredientQuantity();
		return res;
	}
	
	public IngredientQuantity findOne(int ingredientQuantityId) {
		Assert.isTrue(ingredientQuantityId != 0);		
		IngredientQuantity res = ingredientQuantityRepository.findOne(ingredientQuantityId);
		Assert.notNull(res);
		return res;
	}

	public void save(IngredientQuantity ingredientQuantity) {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Assert.notNull(ingredientQuantity);
		ingredientQuantity.getIngredient().getIngredientQuantities().add(ingredientQuantity);
		ingredientQuantity.getRecipe().getIngredientQuantities().add(ingredientQuantity);
		if(ingredientQuantity.getMeasure().getIsInteger()) //Comprobar que no es decimal
			Assert.isTrue(ingredientQuantity.getQuantity()%1==0.0);
			
		IngredientQuantity res = ingredientQuantityRepository.save(ingredientQuantity);
		if(ingredientQuantity.getId()==0)
			res.getRecipe().getIngredientQuantities().add(res);
	}

	public void delete(IngredientQuantity ingredientQuantity) {
		User u = userService.findByPrincipal();
		Assert.notNull(u);
		Assert.notNull(ingredientQuantity);
		Assert.isTrue(ingredientQuantity.getId() != 0);
		Assert.isTrue(ingredientQuantityRepository.exists(ingredientQuantity.getId()));
		
		ingredientQuantity.getIngredient().getIngredientQuantities().remove(ingredientQuantity);
		ingredientQuantity.getRecipe().getIngredientQuantities().remove(ingredientQuantity);
		
		ingredientQuantityRepository.delete(ingredientQuantity.getId());
	}
	
	// Other business methods -------------------------------------------------

}
