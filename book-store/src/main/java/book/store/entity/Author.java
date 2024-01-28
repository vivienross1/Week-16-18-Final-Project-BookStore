package book.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity 
@Data 
public class Author {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authorId;
	private String authorFirstName;
	private String authorLastName;
	private String authorGenre;
	private String authorBookName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "book_store_id")
	private BookStore bookStore;
}