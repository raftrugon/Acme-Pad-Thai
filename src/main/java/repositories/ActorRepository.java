package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Integer> {
	
	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccount(int id);
	
	@Query("select a from MasterClass m join m.actors a where m.id = ?1")
	Collection<Actor> actorsRegisteredInAMasterClass(int masterClassId);
	
	@Query("select a from Actor a where a.socialIdentity.id = ?1")
	Actor findBySocialIdentity(int socialIdentityId);

}
