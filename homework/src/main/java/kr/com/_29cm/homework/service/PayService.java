package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.Pay;
import kr.com._29cm.homework.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PayService {
    private final PayRepository payRepository;

    /**
     * 결제 생성 */
    @Transactional
    public Long pay(Long orderId, int payPrice) {
        Pay pay = new Pay();
        pay.setOrderId(orderId);
        pay.setPayPrice(payPrice);
        payRepository.save(pay);

        return pay.getId();
    }

}
