package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.Item;
import kr.com._29cm.homework.domain.Order;
import kr.com._29cm.homework.domain.OrderItem;
import kr.com._29cm.homework.exception.SoldOutException;
import kr.com._29cm.homework.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@Transactional
@SpringBootTest
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @Autowired
    ItemRepository itemRepository;


    @Test
    void 주문_아이템_재고_차감() throws InterruptedException {

        int threadCount = 30;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        int initStock = 60;
        int orderCount = 2;

        Long itemId = 377169L;

        for(int i = 0; i< threadCount; i++ ){
            executorService.submit(() -> {
                try {
                    Optional<Item> byId = itemRepository.findById(itemId);
                    Item item = byId.get();
                    OrderItem oi = OrderItem.builder()
                            .count(orderCount)
                            .itemId(itemId)
                            .item(item)
                            .build();

                    orderService.reduceItemStock(oi);
                } catch (Exception e) {
                   e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Optional<Item> afterReduceOpt = itemRepository.findById(itemId);
        Item afterReduceItem = afterReduceOpt.orElseThrow();
        System.out.println(">>>이후 재고수량" + afterReduceItem.getStock());

        assertEquals("기대되는 재고수량 : 재고 - 스레드개수 * 주문수량"
                , afterReduceItem.getStock()
                , initStock - threadCount * orderCount);
    }

    @Test
    void SoldOutException_상황_발생() throws InterruptedException {
        int threadCount = 31;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        int initStock = 60;
        int orderCount = 2;

        Long itemId = 377169L;

        try {
            for(int i = 0; i< threadCount; i++ ){
                executorService.submit(() -> {
                    try {
                        Optional<Item> byId = itemRepository.findById(itemId);
                        Item item = byId.get();
                        OrderItem oi = OrderItem.builder()
                                .count(orderCount)
                                .itemId(itemId)
                                .item(item)
                                .build();

                        orderService.reduceItemStock(oi);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
        } catch (Exception e) {
            assertEquals("Sold Out", e, SoldOutException.class);
        }
    }
}