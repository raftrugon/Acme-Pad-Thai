package services;

import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Admin;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;
import repositories.LearningMaterialRepository;

@Service
@Transactional
public class LearningMaterialService {
	
	// Managed Repository ------------------------------------------
	
	@Autowired
	private LearningMaterialRepository learningMaterialRepository;
	
	//Supporting services ------------------------------------------
	@Autowired
	private CookService cookService;
	@Autowired
	private AdminService adminService;
	
	
	//Constructors -------------------------------------------------
	
	public LearningMaterialService() {
		super();
	}
	
	//Simple CRUD methods ------------------------------------------
	
	public LearningMaterial create(MasterClass mc) {
		Cook c = cookService.findByPrincipal();
		Assert.notNull(c);
		LearningMaterial res;
		res = new LearningMaterial();
		res.setAttachment(new ArrayList<String>());
		res.setMasterClass(mc);
		return res;
	}
	
	public Collection<LearningMaterial> findAll() {
		Collection<LearningMaterial> res;
		res = learningMaterialRepository.findAll();
		Assert.notNull(res);		
		return res;
	}

	public LearningMaterial findOne(int learningMaterialId) {
		Assert.isTrue(learningMaterialId != 0);		
		LearningMaterial res = learningMaterialRepository.findOne(learningMaterialId);
		Assert.notNull(res);
		return res;
	}

	
	public void save(LearningMaterial learningMaterial) {
		Cook c = cookService.findByPrincipal();
		Assert.notNull(c);
		Assert.notNull(learningMaterial);
		learningMaterialRepository.save(learningMaterial);
	}
	
	public void delete(LearningMaterial learningMaterial) {
		Cook c = cookService.findByPrincipal();
		Assert.notNull(c);
		Assert.notNull(learningMaterial);
		Assert.isTrue(learningMaterial.getId() != 0);
		Assert.isTrue(learningMaterialRepository.exists(learningMaterial.getId()));	
		learningMaterialRepository.delete(learningMaterial);
	}
	
	//Other business methods----------------------------------------
	
	public Collection<Double> averageByMasterClassGroupByTypes(){
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Collection<Double> res;
		res = learningMaterialRepository.averageByMasterClassGroupByTypes();
		Assert.notNull(res);
		return res;
	}
}
