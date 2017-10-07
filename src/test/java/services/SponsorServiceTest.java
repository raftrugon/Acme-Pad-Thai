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


import domain.Bill;
import domain.Campaign;
import domain.CreditCard;
import domain.Folder;
import domain.Sponsor;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class SponsorServiceTest extends AbstractTest {
	
	//Service under test ---------------------
	@Autowired
	private SponsorService sponsorService;
	
	//Supporting services --------------------
	@Autowired
	private UserAccountService userAccountService;
	
	//Tests ----------------------------------
	
	@Test
	public void testCreate(){
		System.out.println("========================");
		System.out.println("== CREATE SPONSOR ==");
		System.out.println("========================");
		authenticate("admin1");
		Sponsor sponsor = sponsorService.create();
		Assert.notNull(sponsor);
		authenticate(null);
	}
	
	@Test
	public void testFindAll() {
		System.out.println("========================");
		System.out.println("== FIND ALL SPONSOR ==");
		System.out.println("========================");
		Collection<Sponsor> all;
		all = sponsorService.findAll();
		Assert.notNull(all);
		for (Sponsor s: all) {
			System.out.println(s.getName()+ "-" + s.getAddress());
		}
	}
	
	@Test
	public void testFindOne() {
		System.out.println("========================");
		System.out.println("== FIND ONE SPONSOR ==");
		System.out.println("========================");
		Sponsor s;
		s = sponsorService.findOne(198);
		Assert.isTrue(s.getId()!=0);
		Assert.notNull(s);
		System.out.println(s.getName() + "-"  + s.getAddress());
	}
	
	@Test
	public void testSave() {
		System.out.println("========================");
		System.out.println("== SAVE SPONSOR ==");
		System.out.println("========================");
		authenticate("sponsor1");
		
		Collection<Sponsor> all;
		all = sponsorService.findAll();
		Assert.notNull(all);
		System.out.println("BEFORE: \n");
		for (Sponsor s: all) {
			System.out.println(s.getName() + "-" + s.getAddress());
		}
		Sponsor sponsor = sponsorService.create();
		sponsor.setName("nuevo nombre");
		sponsor.setSurname("apellidos new");
		sponsor.setEmail("email@us.es");
		sponsor.setPhone("999333221");
		sponsor.setAddress("Calle Matuer nº2");
		sponsor.setCompany("NewSkill");
		CreditCard c = new CreditCard();
		c.setHolderName("Rafael Rodríguez Cerdeño");
		c.setBrandName("Cajasol");
		c.setNumber("4556123163707078");
		c.setExpirationMonth("12");
		c.setExpirationYear("18");
		c.setCvvCode(266);
		sponsor.setCreditCard(c);
		Collection<Campaign> campaigns = new HashSet<Campaign>();
		sponsor.setCampaigns(campaigns);
		Collection<Bill> bills = new HashSet<Bill>();
		sponsor.setBills(bills);
		Collection<Folder> folders = new HashSet<Folder>();
		sponsor.setFolders(folders);
		
		UserAccount uA = new UserAccount();
		Authority a = new Authority();
		a.setAuthority("SPONSOR");
		Collection<Authority> authority = new HashSet<Authority>();
		authority.add(a);
		uA.setAuthorities(authority);
		uA.setPassword("sponsor");
		uA.setUsername("sponsor8");
		sponsor.setUserAccount(uA);
		
		sponsorService.save(sponsor);
		all = sponsorService.findAll();
		Assert.notNull(all);
		System.out.println("\nAFTER: \n");
		for (Sponsor sp: all) {
			System.out.println(sp.getName() + "-" + sp.getAddress());
		}
		authenticate(null);
	}
	
	
	@Test
	public void testFindByPrincipal(){
		System.out.println("========================");
		System.out.println("== FIND PRINCIPAL ==");
		System.out.println("========================");
		authenticate("sponsor1");
		Sponsor sponsor;
		sponsor = sponsorService.findByPrincipal();
		Assert.notNull(sponsor);
		System.out.println(sponsor.getName() + "-" + sponsor.getAddress());
		authenticate(null);
	}
	
	
	@Test
	public void testFindByUserAccount(){
		System.out.println("========================");
		System.out.println("== FIND BY USERACCOUNT ==");
		System.out.println("========================");
		Sponsor s;
		UserAccount userAccount = userAccountService.findByUserName("sponsor1");
		s = sponsorService.findByUserAccount(userAccount);
		Assert.notNull(s);
		System.out.println(s.getName() + "-" + s.getAddress());
	}

	
	@Test
	public void testMinAvgMaxCampaignPerSponsor(){
		System.out.println("========================");
		System.out.println("== MIN,MAX,AVG ==");
		System.out.println("========================");
		authenticate("admin1");
		Double[] res;
		res = sponsorService.minAvgMaxCampaignPerSponsor();
		Assert.notNull(res);
			System.out.println("Min,Max,Avg" + res);
		authenticate(null);
}
	
	
	@Test
	public void testRankingCompaniesPerNumCampaign(){
		System.out.println("========================");
		System.out.println("== Ranking ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<String> res;
		res = sponsorService.rankingCompaniesPerNumCampaign();
		Assert.notNull(res);
			System.out.println("Ranking" + res);
		authenticate(null);
	}
	
	@Test
	public void testRankingCompaniesPerBill(){
		System.out.println("========================");
		System.out.println("== Ranking bills ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<String> res;
		res = sponsorService.rankingCompaniesPerBills();
		Assert.notNull(res);
			System.out.println("Ranking per bills" + res);
		authenticate(null);
	}
	
	@Test
	public void testSponsorWithActiveCampaign(){
		System.out.println("========================");
		System.out.println("== Sponsors with active campaigns ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<Sponsor> res;
		res = sponsorService.sponsorWithActiveCampaigns();
		Assert.notNull(res);
			System.out.println("Sponsors" + res);
		authenticate(null);
	}
	
	
	@Test
	public void testCompaniesBelowAvg(){
		System.out.println("========================");
		System.out.println("== Companies ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<String> res;
		res = sponsorService.companiesBelowAvg();
		Assert.notNull(res);
			System.out.println("Companies" + res);
		authenticate(null);
	}
	
	
	@Test
	public void testMostExpensiveCompanies(){
		System.out.println("========================");
		System.out.println("== Most Expensive Companies ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<String> res;
		res = sponsorService.mostExpensiveCompanies();
		Assert.notNull(res);
			System.out.println("Most expensive Companies are: " + res);
		authenticate(null);
	}
	
	
	@Test
	public void testMinAvgMaxOfCampaignActivesPerSponsor(){
		System.out.println("========================");
		System.out.println("== MIN,AVG,MAX ==");
		System.out.println("========================");
		authenticate("admin1");
		Double[] res;
		res = sponsorService.minAvgMaxOfCampaignActivesPerSponsor();
		Assert.notNull(res);
			System.out.println("Results are : " + res);
		authenticate(null);
	}
	
	
	@Test
	public void testInactiveCampaigns(){
		System.out.println("========================");
		System.out.println("== Inactive Campaigns ==");
		System.out.println("========================");
		authenticate("sponsor1");
		Sponsor s = sponsorService.findByPrincipal();
		Collection<Campaign> res;
		res = sponsorService.inactiveCampaigns(s);
		Assert.notNull(res);
			System.out.println("Inactive Campaigns: " + res);
		authenticate(null);
	}
	
	
	@Test
	public void testFindDefaultingSponsors(){
		System.out.println("========================");
		System.out.println("== Defaulting Sponsors ==");
		System.out.println("========================");
		authenticate("admin1");
		Collection<Sponsor> res;
		res = sponsorService.findDefaultingSponsors();
		Assert.notNull(res);
		authenticate(null);
	}
}