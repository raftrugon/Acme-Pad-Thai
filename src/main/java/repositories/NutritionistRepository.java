package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Nutritionist;

@Repository
public interface NutritionistRepository extends JpaRepository<Nutritionist, Integer>{
	
	@Query("select n from Nutritionist n where n.userAccount.id = ?1") 
	Nutritionist findByUserAccount(int id);
	
	@Query("select n from Nutritionist n where n.curricula.id = ?1") 
	Nutritionist findByCurricula(int curriculaId);

}
