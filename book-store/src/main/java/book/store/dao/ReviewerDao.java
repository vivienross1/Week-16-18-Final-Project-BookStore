package book.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import book.store.entity.Reviewer;


public interface ReviewerDao extends JpaRepository<Reviewer, Long> {

}