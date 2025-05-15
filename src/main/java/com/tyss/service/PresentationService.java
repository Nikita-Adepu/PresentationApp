package com.tyss.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tyss.entity.Presentation;
import com.tyss.entity.User;
import com.tyss.enums.PresentationStatus;
import com.tyss.enums.Role;
import com.tyss.enums.Status;
import com.tyss.exceptions.UserNotFoundException;
import com.tyss.repository.PresentationRepo;
import com.tyss.repository.UserRepo;

@Service
public class PresentationService {

	@Autowired
	private PresentationRepo presentationRepo;

	@Autowired
	private UserRepo userRepo;

	/**
	 * Method-----assign : Admin can assign the presentation to the student with
	 * student id.
	 * 
	 * @param adminId      -only admin can be able to assign presentations to the
	 *                     student
	 * @param studentId    -admin assigning the presentation to the student using
	 *                     student id
	 * @param presentation entity object
	 * @return if id belongs to student or id status is active then it will return
	 *         saved presentation else it will return provided msg
	 * @throws if provided id not belongs to admin or id not found in db it will
	 *            throw runtime exception Provided Id not belongs to Admin and if
	 *            provided id not belongs to student or id not found in db it will
	 *            throw Student Not Found or Provided Id not belongs to Student
	 */

	public ResponseEntity<String> assignPresentation(Integer adminId, Integer studentId, Presentation presentation) {
		User admin = userRepo.findById(adminId)
				.orElseThrow(() -> new RuntimeException("Provided Id not belongs to Admin" + adminId));

		if (admin.getRole().equals(Role.ADMIN) && admin.getStatus().equals(Status.ACTIVE)) {
			User student = userRepo.findById(studentId).orElseThrow(() -> new UserNotFoundException(
					"Student Not Found or Provided Id not belongs to Student" + studentId));

			if (student.getRole().equals(Role.STUDENT) && student.getStatus().equals(Status.ACTIVE)) {
				presentation.setUser(student);
				presentationRepo.save(presentation);
				return new ResponseEntity<String>("Presentation Assigned Successfully ", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Provided Id does not belong to a Student or Student is InActive",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>("The Provided Id not belongs to Admin or Admin is not Active",
					HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * get : can fetch the presentation with id.
	 *
	 * @param id to get the presentation based on particular id
	 * @return presentation object and an HTTP 200 OK status.
	 * @throws if id not present in Database it will throw RuntimeException
	 *            Presenatation not Assigned with provided id
	 */
	public ResponseEntity<Presentation> getPresentationByID(Integer id) {

		Presentation presentation = presentationRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Presenatation not Assigned to this Id " + id));
		return new ResponseEntity<>(presentation, HttpStatus.OK);
//		return ResponseEntity.ok(presentation);
	}

	/**
	 * getAll : get all the presentation by student id(Student).
	 *
	 * @param it will take id (student)
	 * @return if found, it will return list of presentation objects based on the id
	 *         and HTTP status 200 (OK) else (Bad Request) with a message.
	 * @throws UserNotFoundException if the student with the given ID does not
	 *                               exist.
	 */
	public ResponseEntity<List<Presentation>> getPresentationByStudentId(Integer id) {

		User user = userRepo.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Not Found with this Student Id " + id));

		if (user.getRole().equals(Role.STUDENT)) {
			List<Presentation> allPresentation = user.getPresentations();
			return new ResponseEntity<>(allPresentation, HttpStatus.OK);
		}

		return new ResponseEntity("No presentations found for student ID: " + id, HttpStatus.BAD_REQUEST);
	}

	/**
	 * changeStatus : student can change the presentation status.
	 *
	 * @param it                 will take the studentID of the student whose
	 *                           presentation status is to be updated.
	 * @param pid                the ID of the presentation.
	 * @param presentationStatus the new status to be set for the presentation
	 * @para it will check the provided studentId is student or not and active or
	 *       not if id belongs to student and active it will find pid if it is
	 *       fetched it set the presentationStatus and save it and return it
	 * @return it will return success message and HTTP 200 (OK) if
	 *         updated successfully, or HTTP 304 (Not Modified) if the update was
	 *         not applied.
	 * @throws RuntimeException if the student or the presentation is not found.
	 */
	public ResponseEntity<String> updateStudentStatus(Integer studentId, Integer pid,
			PresentationStatus presentationStatus) {
		User Student = userRepo.findById(studentId)
				.orElseThrow(() -> new RuntimeException("User is not found with Student ID: " + studentId));

		if (Student.getRole().equals(Role.STUDENT) && Student.getStatus().equals(Status.ACTIVE)) {

			Presentation presentation = presentationRepo.findById(pid)
					.orElseThrow(() -> new RuntimeException("Presentation not found with Student ID: " + pid));

			presentation.setPresentationStatus(presentationStatus);
			presentationRepo.save(presentation);
			return new ResponseEntity<>("Student PresentationStatus updated to " + presentationStatus.name(),
					HttpStatus.OK);
		}
		return new ResponseEntity<>("PresentationStatus not Updated", HttpStatus.NOT_MODIFIED);
	}

//	public ResponseEntity<List<Presentation>> getPresentationByStudentId(Integer id) {
//	    User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found with Student Id " + id));
//
//	    if (user.getRole().equals(Role.STUDENT)) {
//	        List<Presentation> allPresentation = user.getPresentations();
//
//	        if (allPresentation == null || allPresentation.isEmpty()) {
//	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	        }
//
//	        return new ResponseEntity<>(allPresentation, HttpStatus.OK);
//	    }
//
//	    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	}

//	public ResponseEntity<String> assignPresentation(Integer id, Presentation presentation) {
//		User student = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Student Not Found"));
//
//		if (student.getRole().equals(Role.STUDENT) && student.getStatus().equals(Status.ACTIVE)) {
//			presentation.setUser(student);
//			presentationRepo.save(presentation);
//			return new ResponseEntity<String>("Presentation Assigned Successfully", HttpStatus.OK);
//		}
//		return new ResponseEntity("You cannot Assign the Presentation to this Student because Student is INACTIVE", HttpStatus.BAD_REQUEST);
//	}

}
