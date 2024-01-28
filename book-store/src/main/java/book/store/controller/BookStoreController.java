package book.store.controller;

import java.util.List; 
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import book.store.controller.model.BookStoreData;
import book.store.controller.model.BookStoreData.BookStoreAuthor;
import book.store.controller.model.BookStoreData.BookStoreReviewer;
import book.store.service.BookStoreService;


@RestController 
@RequestMapping("/book_store") 
@Slf4j
public class BookStoreController {
	@Autowired
	private BookStoreService bookStoreService;
	
	@PostMapping()
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookStoreData insertBookStore(@RequestBody BookStoreData bookStoreData) {
		log.info("Creating the book store {}", bookStoreData);
		return bookStoreService.saveBookStore(bookStoreData);
	}
	
	@PutMapping("/{bookStoreId}")
	public BookStoreData updateBookStore(@PathVariable Long bookStoreId, @RequestBody BookStoreData bookStoreData) {
		bookStoreData.setBookStoreId(bookStoreId);
		log.info("Updating book store {}", bookStoreData);
		return bookStoreService.saveBookStore(bookStoreData);
	}
	
	@PostMapping("/{bookStoreId}/author")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookStoreAuthor insertAuthor(@PathVariable Long bookStoreId, @RequestBody BookStoreAuthor bookStoreAuthor) {
		log.info("Creating author {} for book store with ID={}", bookStoreAuthor, bookStoreId);
		return bookStoreService.saveAuthor(bookStoreId, bookStoreAuthor);
	}
	
	
	@PostMapping("/{bookStoreId}/reviewer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookStoreReviewer insertReviewer(@PathVariable Long bookStoreId, @RequestBody BookStoreReviewer bookStoreReviewer) {
		log.info("Creating reviewer {} for book store with ID={}", bookStoreReviewer, bookStoreId);
		return bookStoreService.saveReviewer(bookStoreId, bookStoreReviewer);
	}
	
	
	@GetMapping()
	public List<BookStoreData> retrieveAllBookStores() {
		log.info("Retrieve all book stores.");
		return bookStoreService.retrieveAllBookStores();
	}
	
	
	@GetMapping("/{bookStoreId}")
	public BookStoreData retrieveBookStoreById(@PathVariable Long bookStoreId) {
		log.info("Retrieving book store by ID={}", bookStoreId);
		return bookStoreService.retrieveBookStoreById(bookStoreId);
	}
	
	
	@DeleteMapping("/{bookStoreId}")
	public Map<String, String> deleteBookStoreById(@PathVariable Long bookStoreId) {
		log.info("Deleting book store with ID={}", bookStoreId);
		bookStoreService.deleteBookStoreById(bookStoreId);
		
		return Map.of("message", "Deletion of book store with ID=" + bookStoreId 
				+ " was successful.");
	}
}