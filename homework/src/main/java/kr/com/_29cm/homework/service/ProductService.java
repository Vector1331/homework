package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.Product;
import kr.com._29cm.homework.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findById (Long productId) {
        return productRepository.findById(productId);
    };

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void printAll(){
        List<Product> products = findAll();
        System.out.printf("%2s \t %s %45s %6s \n", "상품번호 " , "상품명 " , "판매가격 " , "재고수 ");
        for(Product product: products) {
            System.out.println(product.getId() + "\t"
                    + product.getName()+ "\t" +  product.getPrice()+ "\t" + product.getStock());
        }
    }

    @Transactional
    public void dataSave(){
        productRepository.dataSave();
    }

}
