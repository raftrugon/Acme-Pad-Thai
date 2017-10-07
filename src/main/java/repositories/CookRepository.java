package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cook;

@Repository
public interface CookRepository extends JpaRepository<Cook, Integer>{
	
	// A/4 - The listing of cooks, sorted according to the number of master classes that have been promoted.
	@Query("select distinct c from Cook c join c.masterClasses mc where mc.isPromoted=True order by c.masterClasses.size DESC")
	Collection<Cook> findAllOrderedByMasterClassesPromoted();
	
	//Añadidas
	
	@Query("select c from Cook c where c.userAccount.id = ?1") 
	Cook findByUserAccount(int id);
	
}
