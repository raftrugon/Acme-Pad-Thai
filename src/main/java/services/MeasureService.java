package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Measure;
import repositories.MeasureRepository;

@Service
@Transactional
public class MeasureService {
	
	// Managed repository -----------------------------------------------------
	@Autowired
	private MeasureRepository measureRepository;

	// Supporting services

	// Constructors
	public MeasureService() {
		super();
	}

	// Simple CRUD methods
	public Measure create() {
		Measure res;
		res = new Measure();	
		
		return res;
	}
	
	public Measure findOne(int measureId) {
		Assert.isTrue(measureId != 0);
		Measure res;
		res=measureRepository.findOne(measureId);
		Assert.notNull(res);

		return res;
	}

	public Collection<Measure> findAll() {
		Collection<Measure> res;
		res=measureRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public void save(Measure measure) {
		
		Assert.notNull(measure);
		measureRepository.save(measure);
	}

}
