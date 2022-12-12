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

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public void printAll(){
        List<Item> items = findAll();
        log.info("%2s \t %s %45s %6s \n", "상품번호 " , "상품명 " , "판매가격 " , "재고수 ");
        for(Item item : items) {
            log.info(item.getId() + "\t"
                    + item.getName()+ "\t" +  item.getPrice()+ "\t" + item.getStock());
        }
    }



}
