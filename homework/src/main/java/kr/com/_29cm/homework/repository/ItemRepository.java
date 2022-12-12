package kr.com._29cm.homework.repository;

import kr.com._29cm.homework.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, CrudRepository<Item, Long> {

    @Modifying
    @Query(
            "update Item set stock = stock - :qty where id = :itemId"
    )
    int reduceStock(@Param("itemId") Long itemId, @Param("qty") int qty);


    @Modifying
    @Query(
            "update Item set stock = stock + :qty where id = :itemId"
    )
    int addStock(@Param("itemId") Long itemId, @Param("qty") int qty);
}
