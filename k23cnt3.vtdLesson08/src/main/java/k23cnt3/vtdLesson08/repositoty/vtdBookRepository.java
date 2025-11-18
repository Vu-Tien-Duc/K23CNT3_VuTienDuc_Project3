package k23cnt3.vtdLesson08.repositoty;

import k23cnt3.vtdLesson08.entity.vtdBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface vtdBookRepository extends JpaRepository<vtdBook, Long> {
}
