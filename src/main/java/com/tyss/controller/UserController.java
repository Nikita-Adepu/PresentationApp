package com.tyss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.dto.UserDTO;
import com.tyss.dto.UserLoginDTO;
import com.tyss.entity.User;
import com.tyss.enums.Status;
import com.tyss.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		ResponseEntity<String> register = userService.registerUser(user);
		return register;
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO logindto) {
		ResponseEntity<String> login = userService.loginUser(logindto);
		return login;
	}

	@GetMapping("details/{id}")
//	@GetMapping(value = "details/{id}", produces = "application/json")
	public ResponseEntity<UserDTO> getUserDetails(@PathVariable Integer id) {
		ResponseEntity<UserDTO> user = userService.getUserById(id);
		return user;
	}

	@GetMapping("admin/students/{id}")
	public ResponseEntity<List<User>> getStudentsIfAdmin(@PathVariable Integer id) {
		return userService.getAllStudentIfAdmin(id);
	}

//	@PutMapping("updateStatus/{adminId}/{studId}")
//	public ResponseEntity<String> updateStatus(@PathVariable(name = "id") Integer adminId, @PathVariable(name = "id") Integer studId, @RequestParam Status status) {
//		return userService.updateStatus(adminId, studId, status);
//	}
	
	@PutMapping("/updateStatus/{adminId}/{studentId}")
    public ResponseEntity<String> updateStudentStatus(
            @PathVariable Integer adminId,
            @PathVariable Integer studentId,
            @RequestParam Status status) {

        return userService.updateStudentStatus(adminId, studentId, status);
    }
}
