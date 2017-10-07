package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Actor;
import domain.Folder;
import domain.ForbiddenWord;
import domain.Message;

import repositories.MessageRepository;


@Service
@Transactional
public class MessageService {
	
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private MessageRepository messageRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private FolderService folderService;
	@Autowired
	private ForbiddenWordService forbiddenWordService;
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

		public MessageService() {
			super();
		}

	// Simple CRUD methods ----------------------------------------------------
		public Message create() {
			Actor a = actorService.findByPrincipal();
			Assert.notNull(a);
			Message res;
			res = new Message();	
			res.setActorFrom(a);
			
			return res;
		}

		public Message findOne(int messageId) {
			Assert.isTrue(messageId != 0);
			Message res;
			res=messageRepository.findOne(messageId);
			Assert.notNull(res);

			return res;
		}

		public void save(Message message) {		
			Actor a = actorService.findByPrincipal();
			Assert.notNull(a);
			message.setDeliveryDate(new Date(System.currentTimeMillis() - 1000));
			Assert.notNull(message);
			Collection<ForbiddenWord> forbiddenWords = forbiddenWordService.findAll();
			Assert.notNull(forbiddenWords);

			Folder f = null;
			f = folderService.findInboxFrom(message.getActorTo().getId());
			Assert.notNull(f);
			message.setFolder(f);
			Message res;
			res = messageRepository.save(message);
			
			boolean spam = false;
			for(ForbiddenWord fw:forbiddenWords){
				if(isSpam(res.getId(), fw.getName())){
					spam = true;
					break;
				}
			}
			
			if(spam){
				f = folderService.findSpamboxFrom(res.getActorTo().getId());
				Assert.notNull(f);
				res.setFolder(f);
			}

			Assert.notNull(res);
			Message copy = create();
			copy.setSubject(message.getSubject());
			copy.setBody(message.getBody());
			copy.setDeliveryDate(message.getDeliveryDate());
			copy.setPriority(message.getPriority());
			copy.setActorFrom(message.getActorFrom());
			copy.setActorTo(message.getActorTo());
			Folder f2 = folderService.findOutboxFrom(message.getActorFrom().getId());
			Assert.notNull(f2);
			copy.setFolder(f2);
			f.getMessages().add(message);
			f2.getMessages().add(copy);
			messageRepository.save(res);
			messageRepository.save(copy);

		}	

		public void delete(Message message) {
			Actor a = actorService.findByPrincipal();
			Assert.notNull(a);
			Assert.notNull(message);
			Assert.isTrue(message.getId() != 0);
			Assert.isTrue(messageRepository.exists(message.getId()));
			
			if(message.getFolder().getName().equals("Trashbox")){
				message.getFolder().getMessages().remove(message);
				
				messageRepository.delete(message);
			}
			else{//Al eliminarse, el mensaje se envía a la carpeta Trashbox
				Folder f = folderService.findTrashboxFrom(a.getId());
				message.getFolder().getMessages().remove(message);
				message.setFolder(f);
				message.getFolder().getMessages().add(message);
			}
			
		
		}
		
		//Other business method-----------------------------------------------
		
		public boolean isSpam(int messageId, String word){
			Boolean res;
			Assert.notNull(messageId);
			Assert.notNull(word);
			res = messageRepository.isSpam(messageId, word);
			Assert.notNull(res);
			
			return res;
		}
		
		public void move(Message m, Folder f){
			Actor a = actorService.findByPrincipal();
			Assert.notNull(a);
			Assert.notNull(m);
			Assert.notNull(f);
			f.getMessages().add(m);
			m.getFolder().getMessages().remove(m);
			m.setFolder(f);
			messageRepository.save(m);
		}
		
}
