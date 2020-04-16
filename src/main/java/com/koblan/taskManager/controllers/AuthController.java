package com.koblan.taskManager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.koblan.taskManager.exceptions.NoSuchTaskException;
import com.koblan.taskManager.jwt.JwtUtils;
import com.koblan.taskManager.models.SharedTask;
import com.koblan.taskManager.models.Task;
import com.koblan.taskManager.models.User;
import com.koblan.taskManager.payload.JwtResponse;
import com.koblan.taskManager.payload.LoginRequest;
import com.koblan.taskManager.payload.MessageResponse;
import com.koblan.taskManager.payload.SignupRequest;
import com.koblan.taskManager.payload.TaskDTO;
import com.koblan.taskManager.converter.TaskDTOConverter;
import com.koblan.taskManager.payload.UserDTO;
import com.koblan.taskManager.repositories.UserRepository;
import com.koblan.taskManager.security.UserDetailsImpl;



//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	 @GetMapping(value = "/users")
	 public ResponseEntity<List<User>> getAllUsers() {
	        List<User> users = userRepository.findAll();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    }
	
	 @GetMapping(value = "/users/{id}")
	 public ResponseEntity<User> getTasksByUser(@PathVariable String id) throws NoSuchTaskException {
	        User user = userRepository.findById(id).get();
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    }
	
	@GetMapping(value = "/users/email/{email}")
	 public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
	        User user = userRepository.findByEmail(email);
	        if (user!=null) {
	        UserDTO userDto=new UserDTO(user);
	        return new ResponseEntity<>(userDto, HttpStatus.OK);}
	        else {return new ResponseEntity<>(new MessageResponse("Such email does not belong any user"),
	        		HttpStatus.NOT_FOUND);}
	    }

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail())); 
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsernameIgnoreCase(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already exist!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already exist!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getName(),
				             signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}