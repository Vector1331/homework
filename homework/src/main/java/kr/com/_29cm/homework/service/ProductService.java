package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.Product;
import kr.com._29cm.homework.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<Product> findById (Long productId) {
        return productRepository.findById(productId);
    };

}
