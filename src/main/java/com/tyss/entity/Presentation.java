package com.tyss.entity;

import java.util.List;

import com.tyss.enums.PresentationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="presentation_details")
@Getter
@Setter
@NoArgsConstructor
public class Presentation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pid;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
//	@OneToMany(mappedBy = "presentation")
//    private List<Rating> ratings;
	
	private String course;
	
	private String topic;
	
	@Enumerated(EnumType.STRING)
	private PresentationStatus presentationStatus;
	
	private Double userTotalScore;
	
}
