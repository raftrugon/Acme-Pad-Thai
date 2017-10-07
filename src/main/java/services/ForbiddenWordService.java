package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Admin;
import domain.ForbiddenWord;
import repositories.ForbiddenWordRepository;

@Service
@Transactional
public class ForbiddenWordService {
	
	// Managed repository -----------------------------------------------------
	@Autowired
	private ForbiddenWordRepository forbiddenWordRepository;

	// Supporting services
	@Autowired
	private AdminService adminService;

	// Constructors
	public ForbiddenWordService() {
		super();
	}

	// Simple CRUD methods
	public ForbiddenWord create() {
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		ForbiddenWord res;
		res = new ForbiddenWord();	
		
		return res;
	}
	
	public ForbiddenWord findOne(int forbiddenWordId) {
		Assert.isTrue(forbiddenWordId != 0);
		ForbiddenWord res;
		res=forbiddenWordRepository.findOne(forbiddenWordId);
		Assert.notNull(res);

		return res;
	}

	public Collection<ForbiddenWord> findAll() {
		Collection<ForbiddenWord> res;
		res=forbiddenWordRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public void save(ForbiddenWord forbiddenWord) {
		
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Assert.notNull(forbiddenWord);
		forbiddenWordRepository.save(forbiddenWord);
	}

	public void delete(ForbiddenWord forbiddenWord) {
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Assert.notNull(forbiddenWord);
		forbiddenWordRepository.delete(forbiddenWord);
	}

}
