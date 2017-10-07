package repositories;

import java.util.Collection;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import domain.Campaign;
import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

	//B/1 - The minimum, the average, and the maximum number of campaigns per sponsor.
	@Query("select min(s.campaigns.size), avg(s.campaigns.size), max(s.campaigns.size) from Sponsor s")
	Double[] minAvgMaxCampaignPerSponsor();
	
	//B/2 - The minimum, the average, and the maximum number of active campaigns per sponsor.
	@Query("select min(s.campaigns.size),avg(s.campaigns.size),max(s.campaigns.size) from Sponsor s where s.id in (select c.sponsor from Campaign c where c.startDate<=NOW() and c.endDate>=NOW())")
	Double[] minAvgMaxOfCampaignActivesPerSponsor();
	
	//B/3 - The ranking of companies according the number of campaigns that they've organised via their sponsors.
	@Query("select s.company from Sponsor s order by s.campaigns.size DESC")
	Collection<String> rankingCompaniesPerNumCampaign();
	
	//B/4 - The ranking of companies according their monthly bills.
	@Query("select s.company from Sponsor s join s.bills b group by s.company order by sum(b.cost) DESC")
	Collection<String> rankingCompaniesPerBills();
	
	//B/6 - The sponsors who have not managed a campaign for the last three months.
	@Query("select s from Sponsor s where not s.id=all(select s2.id from Sponsor s2 join s2.campaigns c where ((MONTH(c.endDate)<MONTH(NOW())) and ((MONTH(c.endDate)+3)>=MONTH(NOW())) and YEAR(c.endDate)=YEAR(NOW())) or ((c.startDate < NOW() and c.endDate > NOW())))")
	Collection<Sponsor> sponsorWithActiveCampaigns();
	
	//B/7 - The companies that have spent less than the average in their campaigns.
	@Query("select distinct s.company from Sponsor s join s.campaigns c where (select avg(c.cost)from Campaign c where c.sponsor.id=s.id) < all (select avg(c2.cost) from Campaign c2) )")
	Collection<String> companiesBelowAvg();
	
	//B/8 - The companies that have spent at least 90% the maximum amount of money that a company has spent on a campaign.
	@Query("select distinct s.company from Sponsor s join s.campaigns c where (select avg(c.cost) from Campaign c where c.sponsor.id=s.id)>= (0.9*(select max(c2.cost) from Campaign c2))")
	Collection<String> mostExpensiveCompanies();
	
	
	//Añadidas
	@Query("select s from Sponsor s where s.userAccount.id = ?1") 
	Sponsor findByUserAccount(int id);
	
	@Query("select c from Sponsor s join s.campaigns c where s.id=?1 and not (c.startDate<NOW() and c.endDate>NOW())")
	Collection<Campaign> inactiveCampaigns(Integer id);
	
}
