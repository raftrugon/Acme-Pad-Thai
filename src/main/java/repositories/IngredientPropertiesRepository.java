package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import domain.IngredientProperties;

@Repository
public interface IngredientPropertiesRepository  extends JpaRepository<IngredientProperties, Integer>{
	
}
