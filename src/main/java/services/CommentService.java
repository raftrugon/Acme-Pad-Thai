package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;
import domain.NoAdmin;

@Service
@Transactional
public class CommentService {
	
	// Managed repository -----------------------------------------------------
	@Autowired
	private CommentRepository commentRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private NoAdminService noAdminService;

	
	// Constructors -----------------------------------------------------------
	public CommentService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	public Comment create() {
		NoAdmin nu = noAdminService.findByPrincipal();
		Assert.notNull(nu);
		Comment res = new Comment();
		Date date = new Date();
		res.setDate(date);
		
		return res;
	}
	
	public Comment findOne(int commentId) {
		Assert.isTrue(commentId != 0);		
		Comment res = commentRepository.findOne(commentId);
		Assert.notNull(res);
		return res;
	}
	
	public void save(Comment comment) {
		NoAdmin nu = noAdminService.findByPrincipal();
		Assert.notNull(nu);
		Assert.notNull(comment);
		commentRepository.save(comment);
	}
	
	// Other business methods -------------------------------------------------

	public List<Comment> findCommentsOrderByDate(int recipeId){
		List<Comment> res;
		res = commentRepository.findCommentsOrderByDate(recipeId);
		return res;
	}
	

}
