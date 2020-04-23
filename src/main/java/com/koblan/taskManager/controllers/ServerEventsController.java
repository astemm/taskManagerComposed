package com.koblan.taskManager.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.koblan.taskManager.listeners.MongoEvent;
import com.koblan.taskManager.listeners.SharedTasksListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/events")
public class ServerEventsController implements ApplicationListener<MongoEvent> {
	
	@Autowired
	SharedTasksListener listener;
	List<String> list=new ArrayList<String>();
	String userId=new String();
	//SharedTasksListener listener=new SharedTasksListener();

	@GetMapping(path="/share",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	private Mono<String> getUserId() {
		//userId=new String();
		Mono<String> event=Mono.just(userId);
		Flux<String> events=Flux.fromIterable(list);
		//events.subscribe(System.out::println);
		return event;
	}
	
	@GetMapping(path="/sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getUserId2() 
    {
          SseEmitter emitter = new SseEmitter();
          ExecutorService executor = Executors.newSingleThreadExecutor();
          executor.execute(() -> 
          {
                 try  {
                            randomDelay();
                            emitter.send(userId);
                      emitter.complete();

                } catch (IOException e) {
                      emitter.completeWithError(e);
                }
          });
          executor.shutdown();
          return emitter;
    }

    private void randomDelay() {
          try {
                Thread.sleep(1000);
          } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
          }
    }
	
	@Override
    public void onApplicationEvent(MongoEvent event) {
		list.add(event.getMessage());
		userId=event.getMessage();
        System.out.println("Received event - " + event.getMessage());
        System.out.println("lsize "+list.size());
    }
	
	
	
}
