package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Banner;
import domain.Campaign;
import domain.Sponsor;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional

public class CampaignServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private CampaignService campaignService;
			
	//Supporting services --------------------
	@Autowired
	private SponsorService sponsorService;
	
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE CAMPAIGN ==");
		System.out.println("========================");
		authenticate("sponsor1");
		Campaign campaign = campaignService.create();
		Assert.notNull(campaign);
		authenticate(null);
	}
	@Test
	public void testFindAll(){
		System.out.println("========================");
		System.out.println("== FIND ALL CAMPAIGNS ==");
		System.out.println("========================");
		authenticate("sponsor1");
		Collection<Campaign> all;
		all = campaignService.findAll();
		Assert.notNull(all);
		for (Campaign c: all) {
			System.out.println(c.getStartDate() + " " + c.getCost());
		}
		authenticate(null);
	}
	
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE CAMPAIGN ==");
		System.out.println("========================");
		Campaign c;
		c = campaignService.findOne(273);
		Assert.notNull(c);
		System.out.println(c.getStartDate() + " " + c.getCost());
		}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE CAMPAIGN ==");
		System.out.println("========================");
		authenticate("sponsor1");
		Sponsor s = sponsorService.findByPrincipal();
		Assert.notNull(s);	
		Collection<Campaign> all = campaignService.findAll();
		Assert.notNull(all);
		System.out.println("BEFORE: \n");
		for (Campaign ca: all) {
			System.out.println(ca.getId());
		}
		
		System.out.println("Nº de campaigns: " + all.size());
		Campaign c = campaignService.create();
		
		//String string = "Fri Jul 14 04:40:00 CEST 2017";
		Date date = new Date(1500000000000L);
		c.setStartDate(date);
		
		//String string2 = "Sun Sep 13 14:26:40 CEST 2020";
		Date date2 =new Date(1600000000000L);
		c.setEndDate(date2);
		c.setCost(50.2);
		c.setNumberOfBanners(3);
		c.setSponsor(s);
		Collection<Banner> banners = new HashSet<Banner>();
		c.setBanners(banners);
		c.setStarCampaign(false);
		campaignService.save(c);
		all = campaignService.findAll();
		System.out.println("\nAFTER: \n");
		for (Campaign ca: all) {
			System.out.println(ca.getId());
		}
		System.out.println("Nº de campaigns posterior: " + all.size());
		Assert.notNull(all);
		authenticate(null);
	}
	
	
	@Test
	public void testDelete(){
		System.out.println("========================");
		System.out.println("== DELETE CAMPAIGN ==");
		System.out.println("========================");
		authenticate("sponsor1");
		Collection<Campaign> cam = campaignService.findAll();
		Assert.notNull(cam);
		Campaign c = campaignService.findOne(287);
		
		int num = cam.size();
		System.out.println("Numero campañas del sponsor (antes): "+num);
		campaignService.delete(c);
		Assert.notNull(cam);
	
		cam = campaignService.findAll();
		int num2= cam.size();
		System.out.println("Numero campañas del sponsor (después): "+num2);
		authenticate(null);
	}
		

}
