package kr.com._29cm.homework.repository;

import kr.com._29cm.homework.domain.Order;
import kr.com._29cm.homework.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CrudRepository<Product, Long> {
}
