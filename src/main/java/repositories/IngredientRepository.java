package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{
	//Obtener ingredientes de una receta
	@Query("select i from Ingredient i join i.ingredientQuantities iq where iq.recipe.id=?1")
	Collection<Ingredient> findIngredientsFromRecipe(int recipeId);
}
