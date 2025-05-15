package com.tyss.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RatingDTO {

	private Integer communication;

	private Integer confidence;

	private Integer interation;

	private Integer liveliness;

	private Integer usageProps;

}
