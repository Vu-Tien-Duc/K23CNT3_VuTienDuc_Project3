package k23cnt3.vtdExamPractical07.repository;

import k23cnt3.vtdExamPractical07.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
