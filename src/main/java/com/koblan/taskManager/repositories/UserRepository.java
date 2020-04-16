package com.koblan.taskManager.repositories;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.koblan.taskManager.models.User;

public interface UserRepository extends MongoRepository<User, String> {
	  User findByUsernameIgnoreCase (String username);
	  Boolean existsByUsernameIgnoreCase(String username);
	  Boolean existsByEmail(String email);
	  User findByEmail(String email);
	  Optional<User> findById(String id);
	}
