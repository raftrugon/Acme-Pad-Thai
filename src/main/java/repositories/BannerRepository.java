package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Banner;



@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

	@Query("select b from Banner b where b.campaign.startDate<NOW() and b.campaign.endDate>NOW() and b.currentNumberOfTimes < maxTimesDisplayed")
	Collection<Banner> findByActiveCampaigns();
	@Query("select b from Banner b where (b.campaign.startDate<NOW() and b.campaign.endDate>NOW()) and " +
			"b.campaign.starCampaign=True and b.currentNumberOfTimes < maxTimesDisplayed")
	Collection<Banner> findByActiveStarCampaigns();
}
