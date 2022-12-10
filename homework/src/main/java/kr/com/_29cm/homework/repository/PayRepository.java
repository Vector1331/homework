package kr.com._29cm.homework.repository;

import kr.com._29cm.homework.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PayRepository  extends JpaRepository<Pay, Long>, CrudRepository<Pay, Long> {
}
