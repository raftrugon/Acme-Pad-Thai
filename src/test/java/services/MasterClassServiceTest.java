package services;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Actor;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;


import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
	public class MasterClassServiceTest extends AbstractTest {
	
	//Service under test ---------------------
			@Autowired
			private MasterClassService masterClassService;
				
			//Supporting services --------------------
			
			@Autowired
			private CookService cookService;
			
			//Tests ----------------------------------
			
			@Test
			public void testCreate(){
				System.out.println("========================");
				System.out.println("== CREATE MASTERCLASS ==");
				System.out.println("========================");
				authenticate("cook1");
				MasterClass masterClass = masterClassService.create();
				Assert.notNull(masterClass);
				authenticate(null);
			}
			
			@Test
			public void testFindAll(){
				System.out.println("========================");
				System.out.println("== FIND ALL MASTERCLASS ==");
				System.out.println("========================");
				Collection<MasterClass> all;
				all = masterClassService.findAll();
				Assert.notNull(all);
				for (MasterClass m: all) {
					System.out.println(m.getTitle() + "- " + m.getCook());
				}
				authenticate(null);
			}
			
			@Test
			public void testFindOne(){
				System.out.println("========================");
				System.out.println("== FIND ONE MASTERCLASS ==");
				System.out.println("========================");
				authenticate("cook1");
				MasterClass m = masterClassService.findOne(202);
				Assert.notNull(m);
				System.out.println(m.getTitle() + "- " + m.getCook());
				}

			@Test
			public void testSave(){
				System.out.println("========================");
				System.out.println("== SAVE MASTERCLASS ==");
				System.out.println("========================");
				authenticate("cook1");
				Cook c = cookService.findByPrincipal();
				Assert.notNull(c);	
				Collection<MasterClass> all = masterClassService.findAll();
				Assert.notNull(all);
				for (MasterClass m: all) {
					System.out.println(m.getTitle()+"-" + m.getCook());
				}
				
				System.out.println("Number of masterclasses(BEFORE): " + all.size());
				MasterClass m = masterClassService.create();
				m.setCook(c);
				m.setTitle("La thug vida");
				m.setDescription("hi guys");
				m.setIsPromoted(true);
				Collection<Actor> actors = new HashSet<Actor>();
				m.setActors(actors);
				Collection<LearningMaterial> lmas = new HashSet<LearningMaterial>();
				m.setLearningMaterials(lmas);
				masterClassService.save(m);
				all = masterClassService.findAll();
				Assert.notNull(all);
				System.out.println("Number of masterclasses(AFTER): " + all.size());
				authenticate(null);
			}
			
			@Test
			public void testDelete(){
				System.out.println("========================");
				System.out.println("== DELETE MASTERCLASS ==");
				System.out.println("========================");
				authenticate("cook1");
				Collection<MasterClass> ma = masterClassService.findAll();
				Assert.notNull(ma);
				MasterClass m = masterClassService.findOne(211);
				
				int num = ma.size();
				System.out.println("Number of Masterclasses(BEFORE): "+ num);
				masterClassService.delete(m);
				Assert.notNull(ma);
			
				Collection<MasterClass> ma2 = masterClassService.findAll();
				int num2= ma2.size();
				System.out.println("Number of Masterclasses(AFTER): " +num2);
				authenticate(null);
			}
			
			@Test
			public void testFindByCookId(){
				System.out.println("========================");
				System.out.println("== FIND BY COOK ID ====");
				System.out.println("========================");
				System.out.println("COOK ID = 4:");
				Collection<MasterClass> masterclasses;
				masterclasses = masterClassService.findByCookId(4);
				Assert.notNull(masterclasses);
				for(MasterClass m : masterclasses){
					System.out.println(m.getTitle() + "-" +m.getIsPromoted() + "(" + m.getDescription() + ")");
			}
}	
			
			@Test
			public void testMinMaxAvgStddevMasterClassPerCook(){
			System.out.println("========================");
			System.out.println("== MIN,MAX,AVG ==");
			System.out.println("========================");
			authenticate("admin1");
			Double[] res;
			System.out.println("mec1");
			res = masterClassService.minMaxAvgStddevMasterClassPerCook();
			System.out.println("mec2");
			Assert.notNull(res);
			System.out.println("Min,Max,Avg: " + res);
			authenticate(null);
			}
			
			@Test
			public void testNumberOfPromoted(){
				System.out.println("========================");
				System.out.println("== NUMBER OF MASTERCLASSES PROMOTED ==");
				System.out.println("========================");
				authenticate("admin1");
				Integer res;
				res = masterClassService.numberOfPromoted();
				Assert.notNull(res);
					System.out.println("Number of masterclasses promoted: " + res);
				authenticate(null);
				}
			
			@Test
			public void testAveragePromotedAndDemoted(){
				System.out.println("========================");
				System.out.println("== Average ==");
				System.out.println("========================");
				authenticate("admin1");
				Collection<Double> res;
				res = masterClassService.averagePromotedAndDemoted();
				Assert.notNull(res);
					System.out.println("Promoted and Demoted" + res);
				authenticate(null);
			}
			
			
			
			
}
