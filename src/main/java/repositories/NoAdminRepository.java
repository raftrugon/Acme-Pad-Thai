package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.NoAdmin;

@Repository
public interface NoAdminRepository  extends JpaRepository<NoAdmin,Integer>{
	@Query("select na from NoAdmin na where na.userAccount.id = ?1")
	NoAdmin findByUserAccount(int noAdminId);
	

}
