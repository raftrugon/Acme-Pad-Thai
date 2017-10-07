package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BillRepository;
import domain.Admin;
import domain.Banner;
import domain.Bill;
import domain.Sponsor;

@Service
@Transactional
public class BillService {
	
	// Managed repository -----------------------------------------------------
	@Autowired
	private BillRepository billRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private AdminService adminService;
	@Autowired
	private SponsorService sponsorService;
	
	// Constructors -----------------------------------------------------------

		public BillService() {
			super();
		}

	// Simple CRUD methods ----------------------------------------------------
		
		public Bill create(){ //A bill is created when a Sponsor's banner is displayed at 1st time in that
							  //month.
			Bill res;
			res = new Bill();
			res.setCreationDate(new Date(System.currentTimeMillis() - 1000));
			res.setCost(0.0);
			res.setDescription(new ArrayList<String>());
			return res;
		}
		
		public Collection<Bill> findAll() {
			Collection<Bill> res;
			res=billRepository.findAll();
			Assert.notNull(res);

			return res;
		}

		public Bill findOne(int billId) {
			Assert.isTrue(billId != 0);
			Bill res;
			res=billRepository.findOne(billId);
			Assert.notNull(res);

			return res;
		}

		public void save(Bill bill) {
			Assert.notNull(bill);
			billRepository.save(bill);
		}	


		//Other business methods	
		public Double[] avgStdBillPaidandNoPaid(){
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Double[] res;
			res=billRepository.avgStdBillPaidandNoPaid();
			Assert.notNull(res);
			for(int i=0;i<res.length;i++){
				if(res[i]==null)
					res[i]=0.0;
			}
			
			return res;
			
		}
		
		public Bill findSponsorBillByCreationDateMonthAndYear(int sponsorId, int month, int year){
			Bill res;
			res=billRepository.findSponsorBillByCreationDateMonthAndYear(sponsorId, month, year);
			Assert.notNull(res);
			
			return res;			
		}
		
		public Collection<Bill> findUnpaidBills(){
			Collection<Bill> res=null;
			res=billRepository.findUnpaidBills();
			Assert.notNull(res);
			return res;
		}
		
		public Collection<Bill> findAvalaibleBills(int sponsorId){
			Sponsor s = sponsorService.findByPrincipal();
			Assert.notNull(s);
			Collection<Bill> res=null;
			res=billRepository.findAvalaibleBills(sponsorId);
			Assert.notNull(res);
			return res;
		}
		
		public void issue(int billId){
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Bill b = billRepository.findOne(billId);
			Assert.notNull(b);
			b.setIssueDate(new Date(System.currentTimeMillis() - 10000));
			billRepository.save(b);
		}
		
		public void pay(int billId){
			Sponsor s = sponsorService.findByPrincipal();
			Assert.notNull(s);
			Bill b = billRepository.findOne(billId);
			Assert.notNull(b);
			b.setPayDate(new Date(System.currentTimeMillis() - 10000));
			billRepository.save(b);
		}
		
		public void updateBill(Banner b){ //Update cost and description when a banner is displayed
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1; //Months start at 0
			int year = cal.get(Calendar.YEAR);

			b.setCurrentNumberOfTimes(b.getCurrentNumberOfTimes()+1);
			Bill res = billRepository.findSponsorBillByCreationDateMonthAndYear(b.getCampaign().getSponsor().getId(), month, year);
			if(res==null)
				res = create();
			res.setCost(res.getCost()+b.getCostPerDisplay());
			b.getCampaign().setCost(b.getCampaign().getCost()+b.getCostPerDisplay());
			updateDescription(res, b);
			
			if(res.getSponsor()==null)
				res.setSponsor(b.getCampaign().getSponsor());
			billRepository.save(res);
		}
		
		private void updateDescription(Bill bi, Banner ba){
			List<String> lista = new ArrayList<String>(bi.getDescription());
			String res = ba.getPicture()+" x1";
			boolean añadido = false;
			for(int i=0;i<lista.size();i++){
				String[] num = lista.get(i).split("x");
				String sinX = "";
				for(int j=0;j<num.length-1;j++){
					sinX = sinX+num[j];
					if(num.length>1 && j<num.length-2)
						sinX = sinX + "x";
				}
				
				String[] num2 = res.split("x");
				String sinXNuevo = "";
				for(int j=0;j<num2.length-1;j++){
					sinXNuevo = sinXNuevo+num2[j];
					if(num2.length>1 && j<num2.length-2)
						sinXNuevo = sinXNuevo + "x";
				}
								
				Integer veces = Integer.parseInt((num[num.length-1]));
				
				if(sinX.equals(sinXNuevo)){
					lista.set(i, sinX.trim() + " x" + (veces+1));
					añadido = true;
				}
					
			}
			
			if(!añadido)
				lista.add(res);
			
			bi.setDescription(lista);
		}
		
//		private String updateDescription(Bill bi, Banner ba){
//			String[] array = bi.getDescription().split("\n");
//			String descripcion = "";
//			String res = ba.getPicture()+" x1";
//			boolean añadido = false;
//			for(int i=0;i<array.length;i++){
//				String[] num = array[i].split("x");
//				String sinX = "";
//				for(int j=0;j<num.length-1;j++){
//					sinX = sinX+num[j];
//					if(num.length>1 && j<num.length-2)
//						sinX = sinX + "x";
//				}
//				
//				String[] num2 = res.split("x");
//				String sinXNuevo = "";
//				for(int j=0;j<num2.length-1;j++){
//					sinXNuevo = sinXNuevo+num2[j];
//					if(num2.length>1 && j<num2.length-2)
//						sinXNuevo = sinXNuevo + "x";
//				}
//								
//				Integer veces = Integer.parseInt((num[num.length-1]));
//				System.out.println(veces);
//				
//				if(sinX.equals(sinXNuevo)){
//					array[i] = sinX.trim() + " x" + (veces+1);
//					añadido = true;
//				}
//					
//				descripcion = descripcion + array[i] + "\n";
//			}
//			
//			if(!añadido)
//				descripcion =  descripcion + res + "\n";
//			return descripcion;
//		}
}
