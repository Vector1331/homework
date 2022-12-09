package kr.com._29cm.homework.repository;

import kr.com._29cm.homework.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, CrudRepository<Order, Long> {

}
