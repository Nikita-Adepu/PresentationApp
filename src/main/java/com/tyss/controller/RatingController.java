package com.tyss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.tyss.entity.Rating;
import com.tyss.service.RatingService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/rating")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	@PostMapping("/adminRate/{adminId}/{studentId}/{presentationId}")
	public Rating ratingPresentation(@PathVariable Integer studentId, @PathVariable Integer presentationId,
			@PathVariable Integer adminId, @RequestBody Rating rating) {
		Rating savedRating = ratingService.ratePresentation(studentId, presentationId, adminId, rating);
		return savedRating;
	}

//	@GetMapping("fetch/{rid}")
//	public ResponseEntity<Rating> getPresentationRating(@PathVariable Integer rid) {
//		ResponseEntity<Rating> fetch = ratingService.getPresentationRatingById(rid);
//		return fetch;
//	}
	
	@GetMapping("/getRating")
	public Rating fetchRating(@RequestParam Integer rid) {
		return ratingService.getRating(rid);
	}
}
