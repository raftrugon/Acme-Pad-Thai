package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Like;
import domain.NoAdmin;
import domain.Recipe;
import repositories.LikeRepository;

@Service
@Transactional
public class LikeService {


	// Managed repository -----------------------------------------------------
	
	@Autowired
	private LikeRepository likeRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private NoAdminService noAdminService;
	@Autowired
	private RecipeService recipeService;

	
	// Constructors -----------------------------------------------------------

	public LikeService() {
		super();
	}
	
	
	// Simple CRUD methods ----------------------------------------------------
	public Like create() {
		NoAdmin na = noAdminService.findByPrincipal();
		Assert.notNull(na);
		Like res;
		res = new Like();
		res.setNoAdmin(na);
	
		return res;
	}

	public Like findOne(int likeId) {
		Assert.isTrue(likeId != 0);		
		Like res = likeRepository.findOne(likeId);
		Assert.notNull(res);
		return res;
	}

	public void save(Like like, Recipe r) {
		NoAdmin na = noAdminService.findByPrincipal();
		Assert.notNull(na);
		Assert.notNull(like);
		//Recipe r = recipeService.findRecipeByLike(like.getId());
		//r.getLikes().add(like);
		Like l = likeRepository.save(like);
		r.getLikes().add(l);
	}

	public void delete(Like like) {
		NoAdmin na = noAdminService.findByPrincipal();
		Assert.notNull(na);
		Assert.notNull(like);
		Assert.isTrue(like.getId() != 0);
		Assert.isTrue(likeRepository.exists(like.getId()));
		
		Recipe r = recipeService.findRecipeByLike(like.getId());
		r.getLikes().remove(like);
		
		likeRepository.delete(like.getId());
	}
	
	//Other business methods---------------------------------------------------

}
