package repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MasterClass;

@Repository
public interface MasterClassRepository extends JpaRepository<MasterClass, Integer>{
	
	@Query("select mC from MasterClass mC where mC.cook.id = ?1")
	Collection<MasterClass> findByCookId(int cookId);
	
	// A/1 - The minimum, the maximum, the average, and the standard deviation of the number of master classes per cook.
	@Query("select min(c.masterClasses.size), max(c.masterClasses.size), avg(c.masterClasses.size), stddev(c.masterClasses.size) from Cook c")
	Double[] minMaxAvgStddevMasterClassPerCook();
	
	// A/3 - The number of master classes that have been promoted.
	@Query("select count(m) from MasterClass m where m.isPromoted=True")
	Integer numberOfPromoted();
	
	// A/5 - The average number of promoted and demoted master classes per cook.
	@Query("select (select count(*) from Cook c2 join c2.masterClasses m where (m.isPromoted=True and m.cook.id=c.id))*1.0/c.masterClasses.size,(select count(*) from Cook c2 join c2.masterClasses m where (m.isPromoted=False and m.cook.id=c.id))*1.0/c.masterClasses.size from Cook c join c.masterClasses m group by c")
	Collection<Double> averagePromotedAndDemoted();
	
	@Query("select case when (count(*)>0) then true else false end from MasterClass m join m.actors a where (a.id=?1)")
	Boolean isRegistered(int actorId);
	
	@Query("select mC from MasterClass mC where mC.isPromoted = true")
	Collection<MasterClass> findOnlyPromoted();
	
}
