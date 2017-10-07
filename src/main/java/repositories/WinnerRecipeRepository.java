package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.WinnerRecipe;

@Repository
public interface WinnerRecipeRepository extends JpaRepository<WinnerRecipe,Integer>{

}
