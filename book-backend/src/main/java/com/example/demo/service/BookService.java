package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Book;
import com.example.demo.model.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

	private final BookRepository bookRepository;

	@Transactional
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Transactional(readOnly = true)
	public Book show(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("id를 확인해주세요"));
	}

	@Transactional(readOnly = true)
	public List<Book> showAll() {
		return bookRepository.findAll();
	}

	@Transactional
	public Book update(Long id, Book book) {
		Book bookEntity = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("id를 확인해주세요"));
		bookEntity.setTitle(book.getTitle());
		bookEntity.setAuthor(book.getAuthor());
		return bookEntity;
	}

	@Transactional
	public String delete(Long id) {
		bookRepository.deleteById(id);
		return "OK";
	}

}
