package com.example.demo.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Book;
import com.example.demo.model.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional // 각각의 테스트함수가 종료될때마다 트랜잭션을 rollback해줌
@AutoConfigureMockMvc // MockMvc를 Ioc에 등록
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // 가짜 톰켓으로 테스트
public class BookControllerIntegreTest { // 모든 Bean을 Ioc에 올리고 테스트

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private EntityManager entityManager;

	@BeforeEach
	public void init() {
		entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
	}

	@Test
	public void saveTest() throws Exception {
		log.info("saveTest =====================================================");
		// given
		Book book = new Book(null, "스프링따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book); // json형식으로 변환

		// when
		ResultActions resultActions = mockMvc.perform(post("/book").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content).accept(MediaType.APPLICATION_JSON_UTF8));

		// then
		resultActions.andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("스프링따라하기"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void showAll() throws Exception {
		log.info("showAll =====================================================");
		// given
		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "123", "456"));
		books.add(new Book(null, "1234", "4567"));

		bookRepository.saveAll(books);

		// when
		ResultActions resultActions = mockMvc.perform(get("/book").accept(MediaType.APPLICATION_JSON_UTF8));

		// then
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$.[1].title").value("1234")).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void show() throws Exception {
		log.info("show =====================================================");
		// given
		Long id = 2L;
		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "123", "456"));
		books.add(new Book(null, "1234", "4567"));

		bookRepository.saveAll(books);

		// when
		ResultActions resultActions = mockMvc.perform(get("/book/{id}", id).accept(MediaType.APPLICATION_JSON_UTF8));

		// then
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.title").value("1234"))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void updateTest() throws Exception {
		log.info("updateTest =====================================================");
		// given
		Long id = 2L;
		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "123", "456"));
		books.add(new Book(null, "1234", "4567"));
		
		bookRepository.saveAll(books);
		
		Book book = new Book(null, "리엑트따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book); // json형식으로 변환

		// when
		ResultActions resultActions = mockMvc.perform(put("/book/{id}",id).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content).accept(MediaType.APPLICATION_JSON_UTF8));

		// then
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.title").value("리엑트따라하기"))
				.andDo(MockMvcResultHandlers.print()); 
	}
	
	@Test
	public void deleteTest() throws Exception {
		log.info("updateTest =====================================================");
		// given
		Long id = 1L;
		List<Book> books = new ArrayList<>();
		books.add(new Book(null, "123", "456"));
		books.add(new Book(null, "1234", "4567"));
		
		bookRepository.saveAll(books);


		// when ( 테스트 실행 / BDDMockito 방식 )
		ResultActions resultActions = mockMvc.perform(delete("/book/{id}",id).accept(MediaType.TEXT_PLAIN));

		// then ( 검증 / BDDMockito 방식 )
		resultActions.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()); 
		
		MvcResult requestResult = resultActions.andReturn();
		String result = requestResult.getResponse().getContentAsString();
		
		assertEquals("OK", result);
	}

}
