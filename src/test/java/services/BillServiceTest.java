package services;



import java.util.Collection;
import java.util.Date;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Banner;
import domain.Bill;

import domain.Sponsor;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional

public class BillServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private BillService billService;
		
	//Supporting services --------------------
	@Autowired
	private BannerService bannerService;
	@Autowired
	private SponsorService sponsorService;
	//Tests ----------------------------------
			
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE BILL ==");
		System.out.println("========================");
		Bill bill = billService.create();
		Assert.notNull(bill);
		authenticate(null);
	}
	
	
	@Test
	public void testFindAll(){
		System.out.println("========================");
		System.out.println("== FIND ALL BILLS ==");
		System.out.println("========================");
		Collection<Bill> all;
		all = billService.findAll();
		Assert.notNull(all);
		for (Bill b: all) {
			System.out.println(b.getCreationDate()+ "-" + b.getDescription() + "(" + b.getSponsor() + ")");
		}
	}
	
	
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE BILL ==");
		System.out.println("========================");
		Bill b;
		b = billService.findOne(270);
		Assert.isTrue(b.getId()!=0);
		Assert.notNull(b);
		System.out.println(b.getCreationDate()+ "-" + b.getDescription() + "(" + b.getSponsor() + ")");
		}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE BILL ==");
		System.out.println("========================");
		
		Collection<Bill> all;
		all = billService.findAll();
		Assert.notNull(all);
		System.out.println("BEFORE: \n");
		for (Bill b: all) {
			System.out.println(b.getCreationDate() + "-" + b.getPayDate() + "(" + b.getSponsor() + ")");
		}
		
		Bill bill = billService.create();
		bill.setCost(50.5);
		bill.getDescription().add("Bill of the month");
		
		//String string = "30-10-2016 10:30";
		Date date = new Date(1000000000000L);
		bill.setCreationDate(date);
		
		
		
		//String string2 = "01-11-2016 00:00";
		Date date2 =new Date(1000000000000L);
		bill.setIssueDate(date2);
		
		//String string3 = "02-11-2016 15:12";
		
		Date date3 = new Date(1000000000000L);
		bill.setIssueDate(date3);
		
		Sponsor s = sponsorService.findOne(198);
		bill.setSponsor(s);
		Assert.notNull(s);
		
		billService.save(bill);
		all = billService.findAll();
		Assert.notNull(all);
		System.out.println("\nAFTER: \n");
		for (Bill b2: all) {
			System.out.println(b2.getCreationDate() + "-" + b2.getPayDate() + "(" + b2.getSponsor() + ")");
		}
		authenticate(null);
	}
	
	
	@Test
	public void testAvgStdBillPaidAndNoPaid(){
		System.out.println("========================");
		System.out.println("== AVG , STD");
		System.out.println("========================");
		authenticate("admin1");
		Double[] res;
		res = billService.avgStdBillPaidandNoPaid();
		Assert.notNull(res);
			System.out.println("Avg and Std" + res);
		authenticate(null);
	}
	
	
	@Test
	public void testFindSponsorBillByCreationDateMonthAndYear(){
		System.out.println("========================");
		System.out.println("== Find Bill");
		System.out.println("========================");
		Sponsor res = sponsorService.findOne(197);
		Assert.notNull(res);
		
		
		Bill s = billService.findSponsorBillByCreationDateMonthAndYear(res.getId(),1,2014);
		Assert.notNull(s);
		System.out.println("La factura es: " + s.getCreationDate() + "-" + s.getDescription());
	}
	
	
	@Test
	public void testFindUnpaidBills(){
		System.out.println("========================");
		System.out.println("== Unpaid Bills");
		System.out.println("========================");
		authenticate("sponsor1");
		Collection<Bill> res = billService.findUnpaidBills();
		Assert.notNull(res);
			System.out.println("Unpaid Bills" + res);
		authenticate(null);
	}
	
	
	@Test
	public void testUpdateBill(){
		System.out.println("========================");
		System.out.println("== Update bill");
		System.out.println("========================");
		Banner b= bannerService.findOne(274);
		Assert.notNull(b);

		Sponsor s = sponsorService.findOne(197);

	    
		Bill res = billService.findSponsorBillByCreationDateMonthAndYear(s.getId(),1,2014);
		Assert.notNull(res);
		billService.updateBill(b);
		Assert.notNull(res);
		
		System.out.println(res.getCost() + "-" + res.getDescription());
	}
}
