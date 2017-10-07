package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.LearningMaterial;
import domain.LearningMaterialType;
import domain.MasterClass;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional

public class LearningMaterialTest extends AbstractTest {

	//Service under test ---------------------
	@Autowired
	private LearningMaterialService learningMaterialService;
		
	//Supporting services --------------------
	
	@Autowired
	private MasterClassService masterClassService;
	
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE LEARNING MATERIAL ==");
		System.out.println("========================");
		authenticate("cook1");
		List<MasterClass> mc = new ArrayList<MasterClass>(masterClassService.findAll());
		LearningMaterial lm = learningMaterialService.create(mc.get(0));
		Assert.notNull(lm);
		authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		System.out.println("========================");
		System.out.println("== FIND ALL LEARNINGMATERIAL ==");
		System.out.println("========================");
		authenticate("cook1");
		Collection<LearningMaterial> all;
		all = learningMaterialService.findAll();
		Assert.notNull(all);
		for (LearningMaterial lm: all) {
			System.out.println(lm.getTitle() + "-" + lm.getBody() + "(" + lm.getLearningMaterialType() + ")");
		}
		authenticate(null);
	}
	
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE LEARNING MATERIAL ==");
		System.out.println("========================");
		authenticate("cook1");
		LearningMaterial lm = learningMaterialService.findOne(203);
		Assert.notNull(lm);
		System.out.println(lm.getTitle() + "-" + lm.getBody() + "(" + lm.getLearningMaterialType() + ")");
		}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE LEARNING MATERIAL ==");
		System.out.println("========================");
		authenticate("cook1");
		MasterClass m = masterClassService.findOne(202);
		Assert.notNull(m);	
		Collection<LearningMaterial> all = learningMaterialService.findAll();
		Assert.notNull(all);
		System.out.println("BEFORE: \n");
		for (LearningMaterial lm: all) {
			System.out.println(lm.getTitle() + "-" + lm.getBody() + "(" + lm.getLearningMaterialType() + ")");
		}
		authenticate(null);
		
		authenticate("cook2");
		LearningMaterial lm = learningMaterialService.create(m);
		
		lm.setTitle("Oh my god");
		lm.setMasterClass(m);
		Collection<String> attachment = new HashSet<String>();
		lm.setAttachment(attachment);
		lm.setBody("hola fsdj");
		lm.setSummary("unbeliabol");
		lm.setLearningMaterialType(LearningMaterialType.TEXT);
		
		learningMaterialService.save(lm);
		all = learningMaterialService.findAll();
		System.out.println("\nAFTER: \n");
		for (LearningMaterial lem: all) {
			System.out.println(lem.getTitle() + "-" + lem.getBody() + "(" + lem.getLearningMaterialType() + ")");
		}
		Assert.notNull(all);
		authenticate(null);
	}
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE LEARNING MATERIAL ==");
		System.out.println("========================");
		authenticate("cook1");
		Collection<LearningMaterial> lma = learningMaterialService.findAll();
		Assert.notNull(lma);
		LearningMaterial lm = learningMaterialService.findOne(203);
		
		int num = lma.size();
		System.out.println("Number of LearningMaterial(BEFORE): "+ num);
		learningMaterialService.delete(lm);
		Assert.notNull(lm);
	
		lma = learningMaterialService.findAll();
		Assert.notNull(lma);
		int num2= lma.size();
		System.out.println("Number of LearningMaterial:(AFTER): " +num2);
		authenticate(null);
	}
	
	@Test
	public void testAverageByMasterClassGroupByTypes(){
		System.out.println("========================");
		System.out.println("== Average ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<Double> res;
		res = learningMaterialService.averageByMasterClassGroupByTypes();
		Assert.notNull(res);
			System.out.println("Average" + res);
		authenticate(null);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
