package repositories;


import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Contest;
import domain.Recipe;

@Repository
public interface ContestRepository extends JpaRepository<Contest,Integer> {
			
	// C/3 - The minimum, the average, and the maximum number of recipes that have qualified for a contest.
	@Query("select min(c.recipes.size),avg(c.recipes.size),max(c.recipes.size) from Contest c")
	Double[] minAvgMaxRecipesQualified();
	
	// C/4 - The contest/s for which more recipes has/have qualified.
	@Query("select c from Contest c where c.recipes.size >= all (select c2.recipes.size from Contest c2)")
	Collection<Contest> contestsWithMoreRecipes();
	
	@Query("select r from Contest c join c.recipes r where c.id=?1 order by r.likes.size DESC")
	List<Recipe> recipesWithMoreLikes(int contestId);
	
	@Query("select case when (count(*)>0) then true else false end from Contest c join c.recipes r where c.id = ?1 and r.ticker = ?2")
	Boolean containsCopy(int contestId, String ticker);

}
