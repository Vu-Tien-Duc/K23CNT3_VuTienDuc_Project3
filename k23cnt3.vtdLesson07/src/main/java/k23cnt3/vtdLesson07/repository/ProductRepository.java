package k23cnt3.vtdLesson07.repository;

import k23cnt3.vtdLesson07.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends
        JpaRepository<Product, Long> {
}
