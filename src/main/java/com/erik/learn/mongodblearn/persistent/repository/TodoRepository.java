package com.erik.learn.mongodblearn.persistent.repository;

import com.erik.learn.mongodblearn.persistent.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {

	List<Todo> findByDeadlineAfter(LocalDateTime localDateTime);
	List<Todo> findByContentContaining(String contentPart);
}
