package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Admin;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;
import domain.Message;
import domain.Priority;
import repositories.MasterClassRepository;

@Service
@Transactional
public class MasterClassService {
	
	// Managed Repository ------------------------------------------
	
	@Autowired
	private MasterClassRepository masterClassRepository;
			
	//Supporting services ------------------------------------------
	@Autowired
	private CookService cookService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private LearningMaterialService learningMaterialService;
	
	
	
	//Constructors -------------------------------------------------
	
	public MasterClassService() {
		super();
	}
	
	//Simple CRUD methods ------------------------------------------
	
	public MasterClass create() {
		Cook c = cookService.findByPrincipal();
		Assert.notNull(c);
		MasterClass res;
		res = new MasterClass();
		res.setCook(c);
		res.setIsPromoted(false);
		return res;
	}
	
	public Collection<MasterClass> findAll() {
		Collection<MasterClass> res;
		res = masterClassRepository.findAll();
		Assert.notNull(res);		
		return res;
	}

	public MasterClass findOne(int masterClassId) {
		Assert.isTrue(masterClassId != 0);		
		MasterClass res = masterClassRepository.findOne(masterClassId);
		Assert.notNull(res);
		return res;
	}
	
	public void save(MasterClass masterClass) {
		Cook c = cookService.findByPrincipal();
		Assert.notNull(c);
		Assert.notNull(masterClass); 
		masterClassRepository.save(masterClass);
	}
	
	public void delete(MasterClass masterClass) {
		Cook c = cookService.findByPrincipal();
		Assert.notNull(c);
		
		//MANDAR MENSAJE A USUARIOS SUSCRITOS
		Admin ad = adminService.findByUserAccountId(5);
		//Nota: Hemos decidido que el Admin Rafael Trujillo González sea el Actor del
		//que proceden los mensajes de aviso del sistema. Dicho usuario podría ser otro
		//cualquiera de los admins.
		

		Collection<Actor> actors = actorService.actorsRegisteredInAMasterClass(masterClass);
		for(Actor ac: actors){
			Message message = messageService.create();
			message.setActorFrom(ad);
			message.setActorTo(ac);
			message.setSubject("La MasterClass en la que usted estaba registrado/a ha sido eliminada");
			message.setBody("Estimado señor/a " + ac.getName() + " " + ac.getSurname() + ",\n\n"
				+"la MasterClass en la que usted estaba registrado/a ha sido eliminada por su " +
				"respectivo autor. Sentimos las molestias ocasionadas.");
			message.setPriority(Priority.NEUTRAL);
			Assert.notNull(message);
			messageService.save(message);
		}
		
		Assert.notNull(masterClass);
		Assert.isTrue(masterClass.getId() != 0);
		Assert.isTrue(masterClassRepository.exists(masterClass.getId()));	
		
		masterClass.getCook().getMasterClasses().remove(masterClass);
		for(LearningMaterial l:masterClass.getLearningMaterials()){
			masterClass.getLearningMaterials().remove(l);
			learningMaterialService.delete(l);
		}
		masterClassRepository.delete(masterClass);
	}
	
	//Other business methods----------------------------------------

	public Collection<MasterClass> findByCookId(int cookId){
		Collection<MasterClass> res;
		res = masterClassRepository.findByCookId(cookId);
		Assert.notNull(res);
		return res;
	}
		
	public Double[] minMaxAvgStddevMasterClassPerCook(){
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Double[] res;
		res = masterClassRepository.minMaxAvgStddevMasterClassPerCook();
		Assert.notNull(res);
		for(int i=0;i<res.length;i++){
			if(res[i]==null)
				res[i]=0.0;
		}
		return res;
	}
		
	public Integer numberOfPromoted(){
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Integer res;
		res = masterClassRepository.numberOfPromoted();
		if(res==null)
			res=0;
		return res;
	}
		
	public Collection<Double> averagePromotedAndDemoted(){
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		Collection<Double> res;
		res = masterClassRepository.averagePromotedAndDemoted();
		Assert.notNull(res);
		return res;
	}
	
	public Boolean isRegistered(){
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		Boolean res;
		res = masterClassRepository.isRegistered(a.getId());
		Assert.notNull(res);
		return res;	
	}
	
	public void register(int masterClassId){
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		MasterClass m = masterClassRepository.findOne(masterClassId);
		Assert.notNull(m);
		Assert.isTrue(!isRegistered());
		m.getActors().add(a);
		masterClassRepository.save(m);
	}
	
	public void deregister(int masterClassId){
		Actor a = actorService.findByPrincipal();
		Assert.notNull(a);
		MasterClass m = masterClassRepository.findOne(masterClassId);
		Assert.notNull(m);
		Assert.isTrue(isRegistered());
		m.getActors().remove(a);
		masterClassRepository.save(m);
	}
	
	public MasterClass promote(int masterClassId){
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		MasterClass m = masterClassRepository.findOne(masterClassId);
		Assert.notNull(m);
		m.setIsPromoted(true);
		masterClassRepository.save(m);
		return m;
	}
	
	public MasterClass demote(int masterClassId){
		Admin a = adminService.findByPrincipal();
		Assert.notNull(a);
		MasterClass m = masterClassRepository.findOne(masterClassId);
		Assert.notNull(m);
		m.setIsPromoted(false);
		masterClassRepository.save(m);
		return m;
	}
	
	public Collection<MasterClass> findOnlyPromoted(){
		Collection<MasterClass> res;
		res = masterClassRepository.findOnlyPromoted();
		Assert.notNull(res);
		return res;
	}
}
