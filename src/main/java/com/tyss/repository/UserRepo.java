package com.tyss.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.entity.Presentation;
import com.tyss.entity.User;
import com.tyss.enums.Role;
import com.tyss.enums.Status;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByEmailAndPassword(String email, String password);
	
	boolean findByStatus(Status status);
	
	List<User> findByRole(Role role);
	
}
