package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Integer> {
	
	//17.2	Mostrar las recetas por categoría
	@Query("select r from Recipe r join r.categories c where c.id=?1 and r.readOnly = false")
	Collection<Recipe> showRecipesByCategory(int categoryId);

	//17.5 	Buscar por palabra en ticker, sumario o título
	@Query("select r from Recipe r where (r.ticker like %?1% or r.title like %?1% or r.summary like %?1%) and r.readOnly = false")
	Collection<Recipe> findAllRecipesByKeyword(String keyword);
	
	//Descartar copias de recetas participantes en concursos
	@Query("select r from Recipe r where r.readOnly = false")
	Collection<Recipe> showNoReadOnlyRecipes();
	
	//Descartar copias de recetas participantes en concursos y de un solo User
	@Query("select r from Recipe r where r.readOnly = false and r.user.id=?1")
	Collection<Recipe> showNoReadOnlyRecipes(int userId);
	
	@Query("select r from Recipe r where r.readOnly = false order by r.authorDate DESC")
	List<Recipe> showLastRecipesCreated();	
	
	//19.2 Recetas con más de 5 likes y 0 dislikes
	@Query("select r from Recipe r where r.user.id =?1 and (select count(*) from Like l where l member of r.likes and l.isDislike=True)=0 and (select count(*) from Like l where l member of r.likes and l.isDislike=False)>=5")
	Collection<Recipe> showRecipesWithFiveOrMoreLikesAndZeroDislikes(int userId);
	
	//Requirement 9
	@Query("select r from Recipe r join r.likes l where l.noAdmin.id = ?1 and l.isDislike=False")
	Collection<Recipe> lovedRecipes(int noAdminId);
	
	//Requirement 9
	@Query("select r from Recipe r join r.likes l where l.noAdmin.id = ?1 and l.isDislike=True")
	Collection<Recipe> hatedRecipes(int noAdminId);
	
	// C/5 - The average and the standard deviation of number of steps per recipe.
	@Query("select avg(r.steps.size), stddev(r.steps.size) from Recipe r where r.readOnly = false")
	Double[] avgStddevNumberOfSteps();
	
	// C/6 - The average and the standard deviation of number of ingredients per recipe.
	@Query("select avg(r.ingredientQuantities.size), stddev(r.ingredientQuantities.size) from Recipe r where r.readOnly = false")
	Double[] avgStddevNumberOfIngredients();
	
	@Query("select r from Recipe r join r.likes l where l.id=?1")
	Recipe findRecipeByLike(int likeId);
	
	@Query("select count(*) from Recipe r join r.likes l where r.id = ?1 and l.isDislike=False") 
	Integer numberOfLikes(int recipeId);
	
	@Query("select count(*) from Recipe r join r.likes l where r.id = ?1 and l.isDislike=True") 
	Integer numberOfDislikes(int recipeId);
}
