package com.erik.learn.mongodblearn;

import com.erik.learn.mongodblearn.persistent.model.Todo;
import com.erik.learn.mongodblearn.persistent.repository.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

	private final ConfigurableApplicationContext context;
	private final TodoRepository todoRepository;
	private final ObjectMapper objectMapper;

	@Override
	public void run(String... args) {

		simulateRepository();
		simulateObjectMapper();
		context.close();
	}

	private void simulateObjectMapper() {
		Todo todo = Todo.builder()
				.content("Water the plants")
				.deadline(LocalDateTime.of(2018, 10, 20, 0, 0, 0))
				.build();

		try {
			String todoInJson = objectMapper.writeValueAsString(todo);
			System.out.println(todoInJson);
			Todo todoFromJson = objectMapper.readValue(todoInJson, Todo.class);
			String todoFromJsonInJson = objectMapper.writeValueAsString(todoFromJson);
			System.out.println(todoFromJsonInJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void simulateRepository() {
		todoRepository.deleteAll();
		LocalDateTime now = LocalDateTime.now();
		List<Todo> todoList = Arrays.asList(
				Todo.builder()
						.content("Water the plants")
						.deadline(now.plusDays(1))
						.build(),
				Todo.builder()
						.content("Buy groceries")
						.deadline(now.plusHours(6))
						.build(),
				Todo.builder()
						.content("Read a book")
						.deadline(now)
						.build()
		);
		todoRepository.saveAll(todoList);

		List<Todo> filteredTodo = todoRepository.findByDeadlineAfter(LocalDateTime.now().plusHours(2));
		filteredTodo.forEach(System.out::println);
	}
}
