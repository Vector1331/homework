package kr.com._29cm.homework;

import kr.com._29cm.homework.domain.Product;
import kr.com._29cm.homework.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderApplication {

    @Autowired
    ProductService productService;

    public OrderApplication(ProductService productService) {
        this.productService = productService;
    }

    public void orderStart() {
        productService.dataSave();
        System.out.println("상품번호 " + "\t" + "상품명 " + "\t" + "판매가격 " + "\t" + "재고수 ");
        productService.printAll();

    }


}
