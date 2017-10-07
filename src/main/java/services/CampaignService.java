package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Banner;
import domain.Campaign;
import domain.Sponsor;


import repositories.CampaignRepository;


@Service
@Transactional
public class CampaignService {

	// Managed repository -----------------------------------------------------
	
	@Autowired
	private CampaignRepository campaignRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private SponsorService sponsorService;
	
	// Constructors -----------------------------------------------------------

		public CampaignService() {
			super();
		}

	// Simple CRUD methods ----------------------------------------------------
		public Campaign create() {
			Sponsor s = sponsorService.findByPrincipal();
			Assert.notNull(s);
			Campaign res;
			res = new Campaign();
			res.setBanners(new ArrayList<Banner>());
			res.setSponsor(s);
			res.setCost(0.0);
			return res;
		}
		
		public Collection<Campaign> findAll() {
			Collection<Campaign> res;
			res=campaignRepository.findAll();
			Assert.notNull(res);

			return res;
		}

		public Campaign findOne(int campaignId) {
			Assert.isTrue(campaignId != 0);
			Campaign res;
			res=campaignRepository.findOne(campaignId);
			Assert.notNull(res);

			return res;
		}

		public void save(Campaign campaign) {
			Sponsor s = sponsorService.findByPrincipal();
			Assert.notNull(s);
			Assert.isTrue(campaign.getStartDate().getTime()>System.currentTimeMillis()); //La campa–a
																			//no es activa ni ha pasado.
			Assert.isTrue(campaign.getEndDate().getTime()>campaign.getStartDate().getTime()); 
			Assert.notNull(campaign);
			Campaign res = campaignRepository.save(campaign);
			if(!s.getCampaigns().contains(res))
				s.getCampaigns().add(res);
		}	

		public void delete(Campaign campaign) {
			Sponsor s = sponsorService.findByPrincipal();
			Assert.notNull(s);
			Assert.isTrue(campaign.getStartDate().getTime()>System.currentTimeMillis()); //La campa–a
																			//no es activa ni ha pasado.
			Assert.notNull(campaign);
			Assert.isTrue(campaign.getId() != 0);
			Assert.isTrue(campaignRepository.exists(campaign.getId()));
			
			campaign.getSponsor().getCampaigns().remove(campaign); //Elimina la campa–a de su Sponsor
			
			campaignRepository.delete(campaign);
		}
		
		//Other business methods-------------------------------------------------

}
