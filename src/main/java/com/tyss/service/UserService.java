package com.tyss.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tyss.dto.UserDTO;
import com.tyss.dto.UserLoginDTO;
import com.tyss.entity.User;
import com.tyss.enums.Role;
import com.tyss.enums.Status;
import com.tyss.exceptions.UserNotFoundException;
import com.tyss.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	/**
	 * register : User can register the user with provided details and check for the
	 * duplicate email id.
	 * 
	 * @param it will take User entity object
	 * @param it will find for Email in DB if it is present it will not insert that
	 *           record in DB and provide the message with HttpStatus(BAD_REQUEST)
	 *           else it will save it and provide the message with HttpStatus(OK)
	 * @return responseEntity (message along with status code)
	 */
	public ResponseEntity<String> registerUser(User user) {
		Optional<User> opt = userRepo.findByEmail(user.getEmail());
		if (opt.isPresent()) {
			return new ResponseEntity<String>("User is Already Exist with this Email", HttpStatus.BAD_REQUEST);
		} else {
			userRepo.save(user);
			return new ResponseEntity<String>("User registered successfully", HttpStatus.OK);
		}
	}

	/**
	 * login : User can login to the account by his email and password
	 * 
	 * @param it will take loginDTO entity object
	 * @param it will check email and password if it is exist in DB the user is
	 *           loggedIn along it will return success message along with
	 *           httpStatus(Ok) else it will return failed message along with httpStatus(BAD_REQUEST)
	 * 
	 */
	public ResponseEntity<String> loginUser(UserLoginDTO logindto) {
		Optional<User> opt = userRepo.findByEmailAndPassword(logindto.getEmail(), logindto.getPassword());

		if (opt.isPresent()) {

			return new ResponseEntity<String>("User loggedIn successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User is Not Exist with this Email", HttpStatus.BAD_REQUEST);
		}
	}

//	public ResponseEntity<String> getUserById(Integer id) {
//	    User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User is not Exist"));
//
//	    if (user.getStatus() != Status.ACTIVE) {
//	        return new ResponseEntity<>("User Not found or Status is INACTIVE", HttpStatus.NOT_FOUND);
//	    }
//
//	    return new ResponseEntity<>("User found: " + user.getName(), HttpStatus.OK);
//	}

	/**
	 * get : User can be able to fetch their details based on the id.
	 * 
	 * @param takes user id
	 * @param find the id in DB if found returns user object else throws UserNotFoundException
	 * @param check user is active or not if not active it will throw an exception 
	 * @return it will return userDTO object
	 * @throws UserNotFoundException
	 * */
	public ResponseEntity<UserDTO> getUserById(Integer id) {
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User does not exist"));

		if (user.getStatus() != Status.ACTIVE) {
			throw new UserNotFoundException("User Not found or Status is INACTIVE");
		}

		return ResponseEntity.ok(new UserDTO(user));
	}

	
	
	public ResponseEntity<List<User>> getAllStudentIfAdmin(Integer id) {

		User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		if (user.getRole().equals(Role.ADMIN) && user.getStatus().equals(Status.ACTIVE)) {
			List<User> students = userRepo.findByRole(Role.STUDENT);

			return new ResponseEntity<>(students, HttpStatus.OK);
		}
		return new ResponseEntity("Admin Not Found Or Admin is INACTIVE", HttpStatus.BAD_REQUEST);
	}
	
	

	public ResponseEntity<String> updateStudentStatus(Integer adminId, Integer studentId, Status status) {
		User adminUser = userRepo.findById(adminId)
				.orElseThrow(() -> new RuntimeException("User is not found with Admin ID: " + adminId));

		if (adminUser.getRole().equals(Role.ADMIN) && adminUser.getStatus().equals(Status.ACTIVE)) {
			User studentUser = userRepo.findById(studentId)
					.orElseThrow(() -> new RuntimeException("User not found with Student ID: " + studentId));

			if (studentUser.getRole().equals(Role.STUDENT)) {
				studentUser.setStatus(status);
				userRepo.save(studentUser);
				return new ResponseEntity<>("Student status updated to " + status.name(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Provided Id does not belong to a Student.", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(
					"The Provided Id not belongs to Admin or Only Active Admin can update student status.",
					HttpStatus.UNAUTHORIZED);
		}
	}

//	public ResponseEntity<String> updateStudentStatus(Integer adminId, Integer studentId, Status status) {
//		// Fetch and validate admin
//		User adminUser = userRepo.findById(adminId).orElseThrow(() -> new RuntimeException("User is not found with Admin ID: " + adminId));
//
//		if (!adminUser.getRole().equals(Role.ADMIN) || !adminUser.getStatus().equals(Status.ACTIVE)) {
//			return new ResponseEntity<>("Only ACTIVE ADMIN can update student status.", HttpStatus.UNAUTHORIZED);
//		}
//		// Fetch and validate student
//		User studentUser = userRepo.findById(studentId).orElseThrow(() -> new RuntimeException("User not found with Student ID: " + studentId));
//
//		if (!studentUser.getRole().equals(Role.STUDENT)) {
//			return new ResponseEntity<>("Provided ID does not belong to a STUDENT.", HttpStatus.BAD_REQUEST);
//		}
//		// Update status
//		studentUser.setStatus(status);
//		userRepo.save(studentUser);
//
//		return new ResponseEntity<>("Student status updated to " + status.name(), HttpStatus.OK);
//	}

}
