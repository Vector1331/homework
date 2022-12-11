package kr.com._29cm.homework.repository;

import kr.com._29cm.homework.domain.Order;
import kr.com._29cm.homework.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {
    private final EntityManager em;
    public List<OrderItem> findAllByOrderId(Long orderId){
        return em.createQuery("select oi from OrderItem oi where oi.orderId = :orderId", OrderItem.class)
                .setParameter("orderId", orderId)
                .getResultList();

    }

    public void save(OrderItem orderItem) {
        em.persist(orderItem);
    }

    public void saveAll(List<OrderItem> orderItems) {
        for(OrderItem orderItem : orderItems) {
            save(orderItem);
        }
    }


    public OrderItem findById(Long orderItemId) {
        return em.createQuery("select oi from OrderItem  oi where oi.id = :id", OrderItem.class)
                .setParameter("id", orderItemId)
                .getSingleResult();
    }
}
