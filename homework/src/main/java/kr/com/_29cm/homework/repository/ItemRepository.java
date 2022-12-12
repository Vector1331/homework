package kr.com._29cm.homework.repository;

import kr.com._29cm.homework.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, CrudRepository<Item, Long> {


}
