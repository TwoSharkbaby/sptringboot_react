package com.example.demo.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Book;
import com.example.demo.model.BookRepository;
import com.example.demo.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {

	@InjectMocks // 가짜 bookService객체를 만들때 @Mock로 등록된 모든 객체들을 주입받음
	private BookService bookService;
	
	@Mock
	private BookRepository bookRepository;
	
	@Test
	public void saveTest() {
		
		//given
		Book book = new Book();
		book.setTitle("제목1");
		book.setAuthor("저자1");
		
		// stub
		when(bookRepository.save(book)).thenReturn(book);
		
		// execute
		Book bookEntity = bookRepository.save(book);
		
		// then
		assertEquals(bookEntity, book);
	}
	
}
