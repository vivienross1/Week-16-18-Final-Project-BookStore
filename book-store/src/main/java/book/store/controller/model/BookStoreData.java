package book.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import book.store.entity.Reviewer;
import book.store.entity.BookStore;
import book.store.entity.Author;

@Data
@NoArgsConstructor
public class BookStoreData {
	private Long bookStoreId;
	private String bookStoreName;
	private String bookStoreAddress;
	private String bookStoreCity;
	private String bookStoreState;
	private String bookStoreZip;
	private String bookStorePhone;
	private Set<BookStoreReviewer> reviewers = new HashSet<>();
	private Set<BookStoreAuthor> authors = new HashSet<>();

	
	public BookStoreData (BookStore bookStore) {
		bookStoreId = bookStore.getBookStoreId();
		bookStoreName = bookStore.getBookStoreName();
		bookStoreAddress = bookStore.getBookStoreAddress();
		bookStoreCity = bookStore.getBookStoreCity();
		bookStoreState = bookStore.getBookStoreState();
		bookStoreZip = bookStore.getBookStoreZip();
		bookStorePhone = bookStore.getBookStorePhone();
		
		for (Reviewer reviewer : bookStore.getReviewers()) {
			reviewers.add(new BookStoreReviewer(reviewer));
		}
		
		for (Author author : bookStore.getAuthors()) {
			authors.add(new BookStoreAuthor(author));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class BookStoreReviewer {
		private Long reviewerId;
		private String reviewerFirstName;
		private String reviewerLastName;
		private String reviewerEmail;
                private String reviewerRating;
                private String reviewerComment;
		
		
		public BookStoreReviewer(Reviewer reviewer) {
			reviewerId = reviewer.getReviewerId();
			reviewerFirstName = reviewer.getReviewerFirstName();
			reviewerLastName = reviewer.getReviewerLastName();
			reviewerEmail = reviewer.getReviewerEmail();
            reviewerRating = reviewer.getReviewerRating();
            reviewerComment = reviewer.getReviewerComment();
		}
	}

	@Data
	@NoArgsConstructor
	public static class BookStoreAuthor {
		private Long authorId;
		private String authorFirstName;
		private String authorLastName;
		private String authorGenre;
		private String authorBookName;
		
		
		public BookStoreAuthor(Author author) {
			authorId = author.getAuthorId();
			authorFirstName = author.getAuthorFirstName();
			authorLastName = author.getAuthorLastName();
			authorGenre = author.getAuthorGenre();
			authorBookName = author.getAuthorBookName();
		}
	}
}