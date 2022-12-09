package kr.com._29cm.homework.repository;

import kr.com._29cm.homework.domain.Order;
import kr.com._29cm.homework.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository  extends JpaRepository<OrderItem, Long>, CrudRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderId(Long orderId);
}
