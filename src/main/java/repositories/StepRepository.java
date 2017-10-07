package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Step;

@Repository
public interface StepRepository extends JpaRepository<Step,Integer>{
	@Query("select max(s.numberOfStep) from Recipe r join r.steps s where r.id = ?1") 
	Integer lastNumberOfStep(int recipeId);
	
	@Query("select s.numberOfStep from Recipe r join r.steps s where r.id = ?1") 
	Collection<Integer> getNumberOfSteps(int recipeId);
	
	@Query("select s from Step s where s.recipe.id = ?1 order by s.numberOfStep ASC")
	List<Step> findStepsOrderByNumber(int recipeId);
}
