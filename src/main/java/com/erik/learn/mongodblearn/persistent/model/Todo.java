package com.erik.learn.mongodblearn.persistent.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

	@Id
	private String id;
	private String content;
	private LocalDateTime deadline;

}
