package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LearningMaterial;

@Repository
public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Integer>{
	
	// A/2 - The average number of learning materials per master class, grouped by kind of learning material.
	@Query("select avg(m.learningMaterials.size) from MasterClass m join m.learningMaterials l group by l.learningMaterialType")
	Collection<Double> averageByMasterClassGroupByTypes();

}
