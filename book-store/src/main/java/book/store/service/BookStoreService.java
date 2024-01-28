package book.store.service;

import java.util.LinkedList; 
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import book.store.controller.model.BookStoreData;
import book.store.controller.model.BookStoreData.BookStoreReviewer;
import book.store.controller.model.BookStoreData.BookStoreAuthor;
import book.store.dao.ReviewerDao;
import book.store.dao.AuthorDao;
import book.store.dao.BookStoreDao;
import book.store.entity.Reviewer;
import book.store.entity.Author;
import book.store.entity.BookStore;


@Service
public class BookStoreService {
	
	
	@Autowired
	private BookStoreDao bookStoreDao;
	
	@Autowired
	private AuthorDao authorDao;
	
	@Autowired ReviewerDao reviewerDao;

	
	public BookStoreData saveBookStore(BookStoreData bookStoreData) {
		Long bookStoreId = bookStoreData.getBookStoreId();
		BookStore bookStore = findOrCreateBookStore(bookStoreId);
		
		copyBookStoreFields(bookStore, bookStoreData);
		
		BookStore dbBookStore = bookStoreDao.save(bookStore);
		
		return new BookStoreData(dbBookStore);
	}

	
	private void copyBookStoreFields(BookStore bookStore, BookStoreData bookStoreData) {
		bookStore.setBookStoreId(bookStoreData.getBookStoreId());
		bookStore.setBookStoreName(bookStoreData.getBookStoreName());
		bookStore.setBookStoreAddress(bookStoreData.getBookStoreAddress());
		bookStore.setBookStoreCity(bookStoreData.getBookStoreCity());
		bookStore.setBookStoreState(bookStoreData.getBookStoreState());
		bookStore.setBookStoreZip(bookStoreData.getBookStoreZip());
		bookStore.setBookStorePhone(bookStoreData.getBookStorePhone());
	}

	
	private BookStore findOrCreateBookStore(Long bookStoreId) {
		BookStore bookStore;
		
		if (Objects.isNull(bookStoreId)) {
			bookStore = new BookStore();
		} else {
			bookStore = findBookStoreById(bookStoreId);
		}
		
		return bookStore;
	}

	
	private BookStore findBookStoreById(Long bookStoreId) {
		return bookStoreDao.findById(bookStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"BookStore with ID=" + bookStoreId + " does not exist."));
	}

	
	@Transactional(readOnly = false)
	public BookStoreAuthor saveAuthor(Long bookStoreId, BookStoreAuthor bookStoreAuthor) {
		BookStore bookStore = findBookStoreById(bookStoreId);
		Author author = findOrCreateAuthor(bookStoreId, bookStoreAuthor.getAuthorId());
		copyAuthorFields(author, bookStoreAuthor);
		author.setBookStore(bookStore);
		bookStore.getAuthors().add(author);
		
		Author dbAuthor = authorDao.save(author);
		
		return new BookStoreAuthor(dbAuthor);
	}
	
	
	public void copyAuthorFields(Author author, BookStoreAuthor bookStoreAuthor) {
		author.setAuthorId(bookStoreAuthor.getAuthorId());
		author.setAuthorFirstName(bookStoreAuthor.getAuthorFirstName());
		author.setAuthorLastName(bookStoreAuthor.getAuthorLastName());
		author.setAuthorGenre(bookStoreAuthor.getAuthorGenre());
		author.setAuthorBookName(bookStoreAuthor.getAuthorBookName());
	}
	
	
	public Author findOrCreateAuthor(Long bookStoreId, Long authorId) {
		Author author;
		
		if (Objects.isNull(authorId)) {
			author = new Author();
		} else {
			author = findAuthorById(bookStoreId, authorId);
		}
		
		return author;
	}
	
	
	public Author findAuthorById(Long bookStoreId, Long authorId) {
		Author author = authorDao.findById(authorId)
			.orElseThrow(() -> new NoSuchElementException(
					"Author with ID=" + authorId + " does not exist."));
		
		if(author.getBookStore().getBookStoreId() == bookStoreId) {
			return author;
		} else {
			throw new IllegalArgumentException("Book store with ID=" + bookStoreId + " does not have an author with ID=" + authorId);
		}
	}
	
	
	@Transactional(readOnly = false)
	public BookStoreReviewer saveReviewer(Long bookStoreId, BookStoreReviewer bookStoreReviewer) {
		BookStore bookStore = findBookStoreById(bookStoreId);
		Reviewer reviewer = findOrCreateReviewer(bookStoreId, bookStoreReviewer.getReviewerId()); 
		copyReviewerFields(reviewer, bookStoreReviewer);
		reviewer.getBookStores().add(bookStore);
		bookStore.getReviewers().add(reviewer);
		
		Reviewer dbReviewer = reviewerDao.save(reviewer);
		
		return new BookStoreReviewer(dbReviewer);
	}
	
	
	public void copyReviewerFields(Reviewer reviewer, BookStoreReviewer bookStoreReviewer) {
		reviewer.setReviewerId(bookStoreReviewer.getReviewerId());
		reviewer.setReviewerFirstName(bookStoreReviewer.getReviewerFirstName());
		reviewer.setReviewerLastName(bookStoreReviewer.getReviewerLastName());
		reviewer.setReviewerEmail(bookStoreReviewer.getReviewerEmail());
                reviewer.setReviewerRating(bookStoreReviewer.getReviewerRating());
                reviewer.setReviewerComment(bookStoreReviewer.getReviewerComment());
	}
	
	
	public Reviewer findOrCreateReviewer(Long bookStoreId, Long reviewerId) {
		Reviewer reviewer;
		
		if (Objects.isNull(reviewerId)) {
			reviewer = new Reviewer();
		} else {
			reviewer = findReviewerById(bookStoreId, reviewerId);
		}
		
		return reviewer;
	}
	
	
	public Reviewer findReviewerById(Long petStoreId, Long reviewerId) {
		boolean bookStoreIdsMatch = false;
		
		Reviewer reviewer = reviewerDao.findById(reviewerId)
			.orElseThrow(() -> new NoSuchElementException(
					"Reviewer with ID=" + reviewerId + " does not exist."));
		
		Set<BookStore> bookStores = reviewer.getBookStores();
		for (BookStore bookStore : bookStores) {
			if (bookStore.getBookStoreId() == bookStoreId) {
				bookStoreIdsMatch = true;
			}
		}
		
		if(bookStoreIdsMatch) {
			return reviewer;
		} else {
			throw new IllegalArgumentException("Book store with ID=" + bookStoreId + " does not have a reviewer with ID=" + reviewerId);
		}
	}

	
	@Transactional
	public List<BookStoreData> retrieveAllBookStores() {
		List<BookStore> bookStores = bookStoreDao.findAll();
		List<BookStoreData> results = new LinkedList<>();
		
		for (BookStore bookStore : bookStores) {
			BookStoreData bookStoreData = new BookStoreData(bookStore);
			
			bookStoreData.getAuthors().clear();
			bookStoreData.getReviewers().clear();
			
			results.add(bookStoreData);
		}
		
		return results;
	}
	
	
	public BookStoreData retrieveBookStoreById(Long bookStoreId) {
		BookStore bookStore = findBookStoreById(bookStoreId);
		return new BookStoreData(bookStore);
	}

	
	public void deleteBookStoreById(Long bookStoreId) {
		BookStore bookStore = findBookStoreById(bookStoreId);
		bookStoreDao.delete(bookStore);
	}
}