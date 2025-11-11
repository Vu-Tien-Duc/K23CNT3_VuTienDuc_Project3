package k23cnt3.vtdLesson06.repository;


import k23cnt3.vtdLesson06.entity.VtdCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VtdCustomerRepository extends JpaRepository<VtdCustomer, Long> {
}
