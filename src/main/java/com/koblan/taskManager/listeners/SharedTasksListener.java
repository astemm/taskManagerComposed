package com.koblan.taskManager.listeners;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
//import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import com.koblan.taskManager.models.SharedTask;

@Component
public class SharedTasksListener extends AbstractMongoEventListener<SharedTask> {
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	private String receivingUserId;
	
	public String getReceivingUserId() {
		return receivingUserId;
	}

	@Override
	public void onAfterSave(AfterSaveEvent<SharedTask> event) {
		SharedTask shared=event.getSource();
		receivingUserId=shared.getReceivingUserId();
		MongoEvent customSpringEvent = new MongoEvent(this, receivingUserId);
        applicationEventPublisher.publishEvent(customSpringEvent);
     //   System.out.println("user - " + receivingUserId);
	}
	
	@Override
	public void onAfterDelete(AfterDeleteEvent<SharedTask> event) {
		Document sharedDoc=event.getDocument();
		receivingUserId=sharedDoc.get("receivingUserId",String.class);
	}

}
