package com.erik.learn.mongodblearn.persistent.repository;

import com.erik.learn.mongodblearn.persistent.model.Todo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TodoRepositoryTest {

	@Autowired
	private TodoRepository todoRepository;
	private LocalDateTime testDateTime;

	@Before
	public void setUp() {
		todoRepository.deleteAll();
		testDateTime = LocalDateTime.of(2018, 10, 10, 0, 0, 0);
		List<Todo> todoList = generateTodoList(testDateTime);
		todoRepository.saveAll(todoList);
	}

	@Test
	public void findByDeadlineAfter() {

		List<Todo> retrievedTodo = todoRepository.findByDeadlineAfter(testDateTime.plusHours(2));
		assertThat(retrievedTodo).hasSize(2);
		assertThat(retrievedTodo.stream().map(Todo::getContent)).containsExactly("Water the plants", "Buy Groceries");
	}

	@Test
	public void findByContentContaining() {
		List<Todo> retrievedTodo = todoRepository.findByContentContaining("Buy");
		assertThat(retrievedTodo).hasSize(2);
		assertThat(retrievedTodo.stream().map(Todo::getContent)).containsExactly("Buy Groceries", "Buy Headset");
	}

	private List<Todo> generateTodoList(LocalDateTime testDateTime) {
		return Arrays.asList(Todo.builder()
						.content("Water the plants")
						.deadline(testDateTime.plusHours(6))
						.build(),
				Todo.builder()
						.content("Buy Groceries")
						.deadline(testDateTime.plusHours(8))
						.build(),
				Todo.builder()
						.content("Buy Headset")
						.deadline(testDateTime.plusHours(1))
						.build(),
				Todo.builder()
						.content("Read a book")
						.deadline(testDateTime.plusHours(1))
						.build()
		);
	}
}