package com.tyss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.entity.Presentation;
import com.tyss.enums.PresentationStatus;
import com.tyss.service.PresentationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/presentation")
public class PresentationController {

	@Autowired
	private PresentationService presentationService;

	@PostMapping("/assign/{adminId}/{studentId}")
	public ResponseEntity<String> assignPresentation(@PathVariable Integer adminId, @PathVariable Integer studentId,
			@RequestBody Presentation presentation) {
		return presentationService.assignPresentation(adminId, studentId, presentation);
	}

	@GetMapping("fetchPresentation/{id}")
	public ResponseEntity<Presentation> getPresentationById(@PathVariable Integer id) {
		return presentationService.getPresentationByID(id);
	}

	@GetMapping("/getpresbyStudId/{id}")
	public ResponseEntity<List<Presentation>> getMethodName(@PathVariable Integer id) {
		return presentationService.getPresentationByStudentId(id);
	}

	@PutMapping("/updatePresentationStatus/{studentId}/{pid}")
	public ResponseEntity<String> updateStudentStatus(@PathVariable Integer studentId, @PathVariable Integer pid,
			@RequestParam PresentationStatus presentationStatus) {
		return presentationService.updateStudentStatus(studentId, pid, presentationStatus);
	}

}
