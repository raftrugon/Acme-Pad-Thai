package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import domain.Message;
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	@Query("select case when (count(*)>0) then true else false end from Message m where (m.id=?1) and (m.subject like %?2% or m.body like %?2%)")
	Boolean isSpam(int messageId, String word);
}
