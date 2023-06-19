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

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest // 컨트롤러, 필터, 컨트롤러어드바이스 띄어줌
public class BookControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean // Ioc 환경에 bean에 등록됨
	private BookService bookService;

	@Test
	public void saveTest() throws Exception {
		log.info("saveTest =====================================================");
		// given
		Book book = new Book(null, "스프링따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book); // json형식으로 변환
		when(bookService.save(book)).thenReturn(new Book(1L, "스프링따라하기", "코스"));

		// when ( 테스트 실행 / BDDMockito 방식 )
		ResultActions resultActions = mockMvc.perform(post("/book").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content).accept(MediaType.APPLICATION_JSON_UTF8));

		// then ( 검증 / BDDMockito 방식 )
		resultActions.andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("스프링따라하기"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void showAll() throws Exception {
		log.info("showAll =====================================================");
		// given
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L, "123", "456"));
		books.add(new Book(2L, "1234", "4567"));
		when(bookService.showAll()).thenReturn(books);

		// when ( 테스트 실행 / BDDMockito 방식 )
		ResultActions resultActions = mockMvc.perform(get("/book").accept(MediaType.APPLICATION_JSON_UTF8));

		// then ( 검증 / BDDMockito 방식 )
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$.[0].title").value("123")).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void show() throws Exception {
		log.info("show =====================================================");
		// given
		Long id = 1L;
		when(bookService.show(id)).thenReturn(new Book(1L, "123", "456"));

		// when ( 테스트 실행 / BDDMockito 방식 )
		ResultActions resultActions = mockMvc.perform(get("/book/{id}", id).accept(MediaType.APPLICATION_JSON_UTF8));

		// then ( 검증 / BDDMockito 방식 )
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.title").value("123"))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void updateTest() throws Exception {
		log.info("updateTest =====================================================");
		// given
		Long id = 1L;
		Book book = new Book(null, "리엑트따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book); // json형식으로 변환
		when(bookService.update(id, book)).thenReturn(new Book(1L, "리엑트따라하기", "코스"));

		// when ( 테스트 실행 / BDDMockito 방식 )
		ResultActions resultActions = mockMvc.perform(put("/book/{id}",id).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content).accept(MediaType.APPLICATION_JSON_UTF8));

		// then ( 검증 / BDDMockito 방식 )
		resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.title").value("리엑트따라하기"))
				.andDo(MockMvcResultHandlers.print()); 
	}
	
	@Test
	public void deleteTest() throws Exception {
		log.info("updateTest =====================================================");
		// given
		Long id = 1L;

		when(bookService.delete(id)).thenReturn("OK");

		// when ( 테스트 실행 / BDDMockito 방식 )
		ResultActions resultActions = mockMvc.perform(delete("/book/{id}",id).accept(MediaType.TEXT_PLAIN));

		// then ( 검증 / BDDMockito 방식 )
		resultActions.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()); 
		
		MvcResult requestResult = resultActions.andReturn();
		String result = requestResult.getResponse().getContentAsString();
		
		assertEquals("OK", result);
	}

}
