package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Admin;
import domain.Bill;
import domain.Campaign;
import domain.Folder;
import domain.Message;
import domain.Priority;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private SponsorRepository sponsorRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private BillService billService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private FolderService folderService;

	// Constructors -----------------------------------------------------------

		public SponsorService() {
			super();
		}

	// Simple CRUD methods ----------------------------------------------------
		public Sponsor create() {
			Sponsor res;
			res = new Sponsor();
			res.setBills(new ArrayList<Bill>());
			res.setCampaigns(new ArrayList<Campaign>());
			Authority auth = new Authority();
			auth.setAuthority("SPONSOR");
			Collection<Authority> la = new ArrayList<Authority>();
			res.setFolders(new ArrayList<Folder>());
			
			la.add(auth);
			UserAccount ua = new UserAccount();
			ua.setAuthorities(la);
			res.setUserAccount(ua);
			Assert.notNull(res);

			return res;

		}
		
		public Collection<Sponsor> findAll() {
			Collection<Sponsor> res;
			res=sponsorRepository.findAll();
			Assert.notNull(res);

			return res;
		}

		public Sponsor findOne(int sponsorId) {
			Assert.isTrue(sponsorId != 0);
			Sponsor res;
			res=sponsorRepository.findOne(sponsorId);
			Assert.notNull(res);

			return res;
		}

		public void save(Sponsor sponsor) {
			
			if(sponsor.getId()==0){
				Folder inbox = folderService.create();
				inbox.setName("Inbox");
				inbox.setSystem(true);
				Folder outbox = folderService.create();
				outbox.setName("Outbox");
				outbox.setSystem(true);
				Folder trashbox = folderService.create();
				trashbox.setName("Trashbox");
				trashbox.setSystem(true);
				Folder spambox = folderService.create();
				spambox.setName("Spambox");
				spambox.setSystem(true);
				Folder inbox2 = folderService.save(inbox, sponsor);
				Folder outbox2 = folderService.save(outbox, sponsor);
				Folder trashbox2 = folderService.save(trashbox, sponsor);
				Folder spambox2 = folderService.save(spambox, sponsor);
				sponsor.getFolders().add(inbox2);
				sponsor.getFolders().add(outbox2);
				sponsor.getFolders().add(trashbox2);
				sponsor.getFolders().add(spambox2);
			}
			
			Md5PasswordEncoder password = new Md5PasswordEncoder();
			String encodedPassword = password.encodePassword(sponsor.getUserAccount().getPassword(), null);
			sponsor.getUserAccount().setPassword(encodedPassword);
			Assert.notNull(sponsor);
			sponsorRepository.save(sponsor);

		}
		
		public void saveUpdate(Sponsor sponsor) {
			Assert.notNull(sponsor);
			sponsorRepository.save(sponsor);
		}
		
		//Other business methods---------------------------------------------------
		public Sponsor findByPrincipal() {
			Sponsor res;
			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);
			res=findByUserAccount(userAccount);

			return res;

		}
		
		public Sponsor findByUserAccount(UserAccount userAccount) {
			Assert.notNull(userAccount);
			Sponsor res;
			res=sponsorRepository.findByUserAccount(userAccount.getId());

			return res;

		}
		
		public Double[] minAvgMaxCampaignPerSponsor(){
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Double[] res;
			res=sponsorRepository.minAvgMaxCampaignPerSponsor();
			Assert.notNull(res);
			for(int i=0;i<res.length;i++){
				if(res[i]==null)
					res[i]=0.0;
			}
			
			return res;
			
		}
		
		public Collection<String> rankingCompaniesPerNumCampaign(){
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Collection<String> res;
			res=sponsorRepository.rankingCompaniesPerNumCampaign();
			Assert.notNull(res);
			
			return res;
			
		}
		
		public Collection<String> rankingCompaniesPerBills(){
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Collection<String> res;
			res=sponsorRepository.rankingCompaniesPerBills();
			Assert.notNull(res);
			
			return res;
			
		}
		
		public Collection<Sponsor> sponsorWithActiveCampaigns(){
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Collection<Sponsor> res;
			res=sponsorRepository.sponsorWithActiveCampaigns();
			Assert.notNull(res);
			
			return res;
			
		}
		
		public Collection<String> companiesBelowAvg(){
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Collection<String> res;
			res=sponsorRepository.companiesBelowAvg();
			Assert.notNull(res);
			
			return res;
			
		}
		
		public Collection<String> mostExpensiveCompanies(){
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Collection<String> res;
			res=sponsorRepository.mostExpensiveCompanies();
			Assert.notNull(res);
			
			return res;
			
		}
		
		public Double[] minAvgMaxOfCampaignActivesPerSponsor(){
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Double[] res;
			res=sponsorRepository.minAvgMaxOfCampaignActivesPerSponsor();
			Assert.notNull(res);
			for(int i=0;i<res.length;i++){
				if(res[i]==null)
					res[i]=0.0;
			}
			
			return res;
			
		}
		
		public Collection<Campaign> inactiveCampaigns(Sponsor s){
			Collection<Campaign> res = null;
			res=sponsorRepository.inactiveCampaigns(s.getId());
			Assert.notNull(res);
			
			return res;
		}
		
		public Collection<Sponsor> findDefaultingSponsors(){ //Sponsors who have bills unpaid for 
															 //30 days min
			List<Sponsor> res = new ArrayList<Sponsor>();
			Collection<Bill> unpaidBills = billService.findUnpaidBills();
			Assert.notNull(unpaidBills);		
			long monthMilliseconds = 2592000000L;
			for(Bill b: unpaidBills){
				if(b.getIssueDate()!=null && b.getIssueDate().getTime()+ monthMilliseconds < System.currentTimeMillis())
					res.add(b.getSponsor());
			}
				
			return res;
		}
		
		public void sendAdvertisementToDefaultingSponsors(){
			Admin a = adminService.findByPrincipal();
			Assert.notNull(a);
			Collection<Sponsor> defaultingSponsors;
			defaultingSponsors = findDefaultingSponsors();
			Assert.notNull(defaultingSponsors);
			
			//Crear mensaje y tal, mirar en MasterClass Delete
			
			for(Sponsor s: defaultingSponsors){
				Message message = messageService.create();
				message.setActorFrom(a);
				message.setActorTo(s);
				message.setSubject("Una o varias de sus facturas aún no han sido pagadas");
				message.setBody("Estimado señor/a " + s.getName() + " " + s.getSurname() + ",\n\n"
					+"una o varias de sus facturas aún no han sido pagadas tras ser emitidas " +
					"hace 30 días o más. Rogamos que se apresure en el proceso de pago.");
				message.setPriority(Priority.HIGH);
				Assert.notNull(message);
				messageService.save(message);
			}
		}
		
		
}
