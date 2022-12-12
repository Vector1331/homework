package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.Item;
import kr.com._29cm.homework.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;

    /**
     * 상품 정보 출력
     */
    public void printAll(){
        List<Item> items = itemRepository.findAll();
        System.out.printf("%2s \t %s %45s %6s \n", "상품번호 " , "상품명 " , "판매가격 " , "재고수 ");
        for(Item item : items) {
            log.info("{} \t {} \t {} \t {}" , item.getId(), item.getName()
                    ,item.getPrice(), item.getStock());
        }
    }



}
