package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import domain.Banner;
import domain.Sponsor;

@Service
@Transactional
public class BannerService {

	// Managed repository -----------------------------------------------------
	
	@Autowired
	private BannerRepository bannerRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private SponsorService sponsorService;
	
	// Constructors -----------------------------------------------------------

		public BannerService() {
			super();
		}

	// Simple CRUD methods ----------------------------------------------------
		public Banner create() {
			Sponsor s = sponsorService.findByPrincipal();
			Assert.notNull(s);
			Banner res;
			res = new Banner();	
			res.setCostPerDisplay(0.25);
			res.setCurrentNumberOfTimes(0);
			
			return res;
		}	
		
		public Collection<Banner> findAll() {
			Collection<Banner> res;
			res=bannerRepository.findAll();
			Assert.notNull(res);

			return res;
		}

		public Banner findOne(int bannerId) {
			Assert.isTrue(bannerId != 0);
			Banner res;
			res=bannerRepository.findOne(bannerId);
			Assert.notNull(res);

			return res;
		}

		public void save(Banner banner) {
			Assert.notNull(banner);
			bannerRepository.save(banner);
		}	

		public void delete(Banner banner) {
			Sponsor s = sponsorService.findByPrincipal();
			Assert.notNull(s);
			Assert.notNull(banner);
			Assert.isTrue(banner.getId() != 0);
			Assert.isTrue(bannerRepository.exists(banner.getId()));	
			banner.getCampaign().getBanners().remove(banner); //Elimina de su campaña asignada dicho banner
			bannerRepository.delete(banner);

		}
	
		//Other business methods----------------------------------------
		public Collection<Banner> findByActiveCampaigns(){
			Collection<Banner> res = bannerRepository.findByActiveCampaigns();
			Assert.notNull(res);
			return res;
		}
		
		public Collection<Banner> findByActiveStarCampaigns(){
			Collection<Banner> res = bannerRepository.findByActiveStarCampaigns();
			Assert.notNull(res);
			return res;
		}
		
		public Banner showRandomBanner(Collection<Banner> banners){
			List<Banner> banners2 = new ArrayList<Banner>(banners);
			Double random = Math.random()*banners2.size();
			int index = (int) Math.floor(random);
			Banner res = banners2.get(index);
			
			return res;
			
		}
			
}
