package repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {
	@Query("select f from Actor a join a.folders f where a.id=?1 and f.name='Inbox'")
	Folder findInboxFrom(int actorId);
	
	@Query("select f from Actor a join a.folders f where a.id=?1 and f.name='Spambox'")
	Folder findSpamboxFrom(int actorId);
	
	@Query("select f from Actor a join a.folders f where a.id=?1 and f.name='Outbox'")
	Folder findOutboxFrom(int actorId);
	
	@Query("select f from Actor a join a.folders f where a.id=?1 and f.name='Trashbox'")
	Folder findTrashboxFrom(int actorId);
}
