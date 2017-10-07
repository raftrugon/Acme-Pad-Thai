package services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;



import domain.Actor;
import domain.Folder;
import domain.Message;


import repositories.FolderRepository;

@Service
@Transactional
public class FolderService {
	
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private FolderRepository folderRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private MessageService messageService;
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

		public FolderService() {
			super();
		}

	// Simple CRUD methods ----------------------------------------------------
		public Folder create() {
			Folder res;
			res = new Folder();	
			res.setMessages(new ArrayList<Message>());
			res.setSystem(false);

			return res;

		}

		public Folder findOne(int folderId) {
			Assert.isTrue(folderId != 0);
			Folder res;
			res=folderRepository.findOne(folderId);
			Assert.notNull(res);

			return res;
		}

		public void save(Folder folder) { //Actualización de carpeta
			Actor a = actorService.findByPrincipal();
			Assert.notNull(a);
			Assert.notNull(folder);
			folderRepository.save(folder);
		}
		
		public Folder save(Folder folder, Actor actor) {//Guardado de carpeta 1.ª vez
			Assert.notNull(folder);
			Folder folder2 = folderRepository.save(folder);
			actor.getFolders().add(folder2);
			
			return folder2;
		}

		public void delete(Folder folder) {
			Actor a = actorService.findByPrincipal();
			Assert.notNull(a);
			Assert.notNull(folder);
			Assert.isTrue(folder.getId()!=0);
			Assert.isTrue(folderRepository.exists(folder.getId()));
			Assert.isTrue(!folder.getSystem());
			for(Message m: folder.getMessages()) //Los messages de una folder son borrados
				messageService.delete(m);
			
			a.getFolders().remove(folder);
				
			folderRepository.delete(folder);
		}
		
		//Other business method-----------------------------------------------
		
		public Folder findInboxFrom(int actorId){
			Assert.notNull(actorId);
			Assert.isTrue(actorId!=0);
			Folder res;
			res = folderRepository.findInboxFrom(actorId);
			Assert.notNull(res);
			
			return res;
		}
		
		public Folder findSpamboxFrom(int actorId){
			Assert.notNull(actorId);
			Assert.isTrue(actorId!=0);
			Folder res;
			res = folderRepository.findSpamboxFrom(actorId);
			Assert.notNull(res);
			
			return res;
		}
		
		public Folder findOutboxFrom(int actorId){
			Assert.notNull(actorId);
			Assert.isTrue(actorId!=0);
			Folder res;
			res = folderRepository.findOutboxFrom(actorId);
			Assert.notNull(res);
			
			return res;
		}

		public Folder findTrashboxFrom(int actorId){
			Assert.notNull(actorId);
			Assert.isTrue(actorId!=0);
			Folder res;
			res = folderRepository.findTrashboxFrom(actorId);
			Assert.notNull(res);
			
			return res;
		}

}
