package com.koblan.taskManager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class FrontEndController {
	
	//utility method to correctly redirect login and logout angular actions to home 
	//subpage when accessing as static angular resources inside spring boot app
	@RequestMapping({"/home","/register","/auth/users"})
    public String home() {
        return "forward:/index.html";
    }
}