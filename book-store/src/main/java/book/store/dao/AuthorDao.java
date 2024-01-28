package book.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import book.store.entity.Author;


public interface AuthorDao extends JpaRepository<Author, Long> {

}