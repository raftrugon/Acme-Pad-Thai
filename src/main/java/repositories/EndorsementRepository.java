package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer>{
	
	@Query("select e from Curricula c join c.endorsements e where c.id = ?1 and e.actor.id = ?2") 
	Endorsement findByActor(int curriculaId, int actorId);
}
