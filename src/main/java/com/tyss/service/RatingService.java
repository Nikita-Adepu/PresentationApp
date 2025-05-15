package com.tyss.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tyss.entity.Presentation;
import com.tyss.entity.Rating;
import com.tyss.entity.User;
import com.tyss.enums.Role;
import com.tyss.repository.PresentationRepo;
import com.tyss.repository.RatingRepo;
import com.tyss.repository.UserRepo;

@Service
public class RatingService {

	@Autowired
	private RatingRepo ratingRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PresentationRepo presentationRepo;

	/**
	 * Method--- rate : admin can rate the presentation by student id and
	 * presentation id.
	 *
	 * @param studentId      the ID of the student whose presentation is being rated
	 *                       and must not be null
	 * @param presentationId the ID of the presentation to be rated and must not be
	 *                       null
	 * @param adminId        the ID of the admin who is rating the presentation and
	 *                       must not be null
	 * @param rating         the rating details and must not be null
	 * @return the saved rating entity
	 * @throws RuntimeException if the admin or student is not found, if the user is
	 *                          not authorized, or if the presentation does not
	 *                          exist
	 */
	public Rating ratePresentation(Integer studentId, Integer presentationId, Integer adminId, Rating rating) {

		User admin = userRepo.findById(adminId)
				.orElseThrow(() -> new RuntimeException("Admin not found with ID " + adminId));
//		System.out.println("User Role: " + admin.getRole());

		if (admin.getRole() != Role.ADMIN) {
			throw new RuntimeException("Only admin can rate presentations....!");
		}
		
		User student = userRepo.findById(studentId)
				.orElseThrow(() -> new RuntimeException("Student not found with ID " + studentId));
		if (!student.getRole().equals(Role.STUDENT)) {
			throw new RuntimeException("User with ID " + studentId + " is not a student.");
		}

		Presentation presentation = presentationRepo.findById(presentationId)
				.orElseThrow(() -> new RuntimeException("Presentation not found with ID " + presentationId));

		Rating rate = new Rating();
		rate.setCommunication(rating.getCommunication());
		rate.setConfidence(rating.getConfidence());
		rate.setInteration(rating.getInteration());
		rate.setLiveliness(rating.getLiveliness());
		rate.setUsageProps(rating.getUsageProps());

		double total = (rating.getCommunication() + rating.getConfidence() + rating.getInteration()
				+ rating.getLiveliness() + rating.getUsageProps()) / 5.0;
		rate.setTotalScore(total);

		rate.setUser(student); // user who gave the rating (admin)
		rate.setPresentation(presentation);

		return ratingRepo.save(rate);
	}

//	public ResponseEntity<Rating> getPresentationRatingById(Integer rid) {
//        Optional<Rating> rating = ratingRepo.findById(rid);
//        if (rating.isPresent()) {
//            return new ResponseEntity<>(rating.get(), HttpStatus.OK);  // 200 OK if found
//        } else {
//            throw new RuntimeException("Rating not found for presentation ID: " + rid);  // Exception if not found
//        }
//    }

	/** 
	 * Method--- admin can get the rating of particular presentation.
	 * @param this method will take one argument that is rid to fetch the paticular rating details based on the id
	 * @return paticular rating record based on given id
	 * */
	public Rating getRating(Integer rid) {
		return ratingRepo.findById(rid).get();

	}
}
