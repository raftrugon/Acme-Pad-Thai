package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bill;


@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	//B/5 - The average and the standard deviation of paid and unpaid monthly bills.
	@Query("select distinct (select count(*) from Bill b where not b.payDate = null)/(select count(*) from Bill b)*1.0,(select distinct sqrt(((select count(*)from Bill b where not b.payDate = null)/(select count(*) from Bill b)) - ((select count(*) from Bill b where not b.payDate = null)/(select count(*) from Bill b)*1.0 * (select count(*) from Bill b where not b.payDate = null)/(select count(*) from Bill b)*1.0)) from Bill b), (select count(*) from Bill b where b.payDate = null)/(select count(*)from Bill b)*1.0,(select distinct sqrt(((select count(*) from Bill b where b.payDate = null)/(select count(*) from Bill b)) - ((select count(*) from Bill b where b.payDate = null)/(select count(*) from Bill b)*1.0 * (select count(*) from Bill b where b.payDate = null)/ (select count(*) from Bill b)*1.0)) from Bill b) from Bill b")
	Double[] avgStdBillPaidandNoPaid();
	
	@Query("select b from Sponsor s join s.bills b where b.payDate = null")
	Collection<Bill> findUnpaidBills();
	
	@Query("select b from Bill b where b.sponsor.id=?1 and MONTH(b.creationDate)=?2 and YEAR(b.creationDate)=?3")
	Bill findSponsorBillByCreationDateMonthAndYear(int sponsorId, int month, int year);
	
	@Query("select b from Sponsor s join s.bills b where s.id = ?1 and not b.issueDate = null")
	Collection<Bill> findAvalaibleBills(int sponsorId);
	

	
}
