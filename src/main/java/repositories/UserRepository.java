package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	// C/1 - The minimum, the average, and the maximum number of recipes per user.
	@Query("select min(u.recipes.size),avg(u.recipes.size),max(u.recipes.size) from User u")
	Double[] minAvgMaxNumberOfRecipes();
	
	// C/2 - The user/s who has/have authored more recipes.
	@Query("select u from User u where(select count(*) from Recipe r where r.readOnly = false and r.user=u )>= all(select(select count(*) from Recipe r2 where r2.user=u2) from User u2)")
	Collection<User> userWithMoreRecipes();
	
	// C/7 - A listing of users in descending order of popularity.
	@Query("select u from User u order by u.noAdmins.size DESC")
	Collection<User> findByPopularity();
	
	// C/8 - A listing of users in descending order regarding the average number of likes that their recipes get.
	@Query("select distinct u from User u join u.recipes r where r.readOnly = false and r.likes.size > 0 order by(select count(*) from Recipe r join r.likes l where r.user.id=u.id and l.isDislike=False)*1.0/(select count(*) from Recipe r join r.likes l where r.user.id=u.id) )DESC")
	Collection<User> findByLikesAndDislikes();
	
	//Añadidas
	
	@Query("select u from User u where u.userAccount.id = ?1") 
	User findByUserAccount(int id);
	
	// Search for a user using a single keyword that must appear verbatim in his or her name.
	@Query("select u from User u where (u.name like %?1%)")
	Collection<User> findUserByKeyword(String keyword);
}
