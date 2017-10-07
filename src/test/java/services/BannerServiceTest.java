package services;

import java.util.Collection;

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

public class BannerServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private BannerService bannerService;
	
	//Supporting services --------------------
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private CampaignService campaignService;
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE BANNER ==");
		System.out.println("========================");
		authenticate("sponsor1");
		Sponsor s = sponsorService.findByPrincipal();
		Assert.notNull(s);	
		Banner banner = bannerService.create();
		Assert.notNull(banner);
		authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		System.out.println("========================");
		System.out.println("== FIND ALL BANNER ==");
		System.out.println("========================");
		Collection<Banner> banners;
		banners = bannerService.findAll();
		Assert.notNull(banners);
		for(Banner b: banners)
			System.out.println(b.getCurrentNumberOfTimes() + "-" + b.getCampaign());
	}
	@Test
	public void testFindOne(){
		System.out.println("========================");
		System.out.println("== FIND ONE BANNER ==");
		System.out.println("========================");
		Banner b = bannerService.findOne(276);
		Assert.notNull(b);
		System.out.println(b.getCurrentNumberOfTimes() + " " + b.getCampaign());
		}
	
	@Test
	public void testSave(){
		System.out.println("========================");
		System.out.println("== SAVE BANNER ==");
		System.out.println("========================");
		authenticate("sponsor1");	
		Campaign campaign = campaignService.findOne(275);
		Collection<Banner>all = bannerService.findAll();
		Assert.notNull(all);
		System.out.println("BEFORE: \n");
		for (Banner ba: all) {
			System.out.println(ba.getCurrentNumberOfTimes() + "-" + ba.getCampaign().getStartDate());
		}
		Banner b = bannerService.create();
		b.setCurrentNumberOfTimes(30);
		b.setPicture("http://willy.com/image");
		b.setCampaign(campaign);
		b.setCostPerDisplay(15.5);
		b.setMaxTimesDisplayed(50);
		bannerService.save(b);
		all = bannerService.findAll();
		Assert.notNull(all);
		System.out.println("\nAFTER: \n");
		for (Banner ba: all) {
			System.out.println(ba.getCurrentNumberOfTimes() + "-" + ba.getCampaign().getStartDate());
		}
		authenticate(null);
	}
		@Test
		public void testDelete(){
			System.out.println("========================");
			System.out.println("== DELETE BANNER ==");
			System.out.println("========================");
			authenticate("sponsor1");
			Sponsor s = sponsorService.findByPrincipal();
			Assert.notNull(s);
			Collection<Banner> ban = bannerService.findAll();
			Assert.notNull(ban);
			System.out.println("BEFORE: \n");
			for(Banner b: ban){
				System.out.println(b.getCampaign() + "-" + b.getCurrentNumberOfTimes());
			}
			Banner b = bannerService.findOne(276);
		
			bannerService.delete(b);
			ban = bannerService.findAll();
			System.out.println("\nAFTER: \n");
			for(Banner ba: ban){
				System.out.println(ba.getCampaign() + "-" + ba.getCurrentNumberOfTimes());
			}
			authenticate(null);
		}
	
	
		@Test
		public void testFindByActiveCampaigns(){
		System.out.println("========================");
		System.out.println("== FIND ACTIVE CAMPAIGNS ==");
		System.out.println("========================");
		authenticate("sponsor1");
		Collection<Banner> banners = bannerService.findByActiveCampaigns();
		Assert.notNull(banners);
		for (Banner b: banners) {
			System.out.println(b.getCampaign().getStartDate()+"-" + b.getCurrentNumberOfTimes());
		}
		authenticate(null);
	}
		
		@Test
		public void testFindByActiveStarCampaigns(){
			System.out.println("========================");
			System.out.println("== FIND BY ACTIVE STAR ==");
			System.out.println("========================");
			authenticate("sponsor1");
			Collection<Banner> banners = bannerService.findByActiveStarCampaigns();
			Assert.notNull(banners);
			for (Banner b: banners) {
				System.out.println(b.getCampaign()+"-" + b.getCurrentNumberOfTimes());
			}
			authenticate(null);
		}
	
	}
	

