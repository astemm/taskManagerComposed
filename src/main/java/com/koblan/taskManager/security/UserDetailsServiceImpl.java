package com.koblan.taskManager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koblan.taskManager.models.User;
import com.koblan.taskManager.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    	
	        User user = userRepository.findByUsernameIgnoreCase(username);
	        if (user==null) throw new UsernameNotFoundException("Username " + username+ " not found");
	        return UserDetailsImpl.build(user);
	}

}
