package com.example.demo.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Book;
import com.example.demo.model.BookRepository;

@Transactional
@AutoConfigureTestDatabase(replace = Replace.ANY) // 가짜 디비로 테스트
@DataJpaTest // Repository들을 Ioc에 다 등록해둠
public class BookRepositoryUnitTest {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void saveTest() {
		//given
		Book book = new Book(null, "제목1", "저자1");
		
		//when
		Book bookEntity = bookRepository.save(book);
		
		//then
		assertEquals("제목1", bookEntity.getTitle());
	}
	
}
