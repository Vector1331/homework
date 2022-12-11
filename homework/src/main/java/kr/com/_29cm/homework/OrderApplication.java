package kr.com._29cm.homework;

import kr.com._29cm.homework.domain.OrderItem;
import kr.com._29cm.homework.domain.Product;
import kr.com._29cm.homework.service.OrderService;
import kr.com._29cm.homework.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;


@Component
@RequiredArgsConstructor
public class OrderApplication {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    public OrderApplication(ProductService productService) {
        this.productService = productService;
    }


    public void orderStart() {
        productService.dataSave();

        productService.printAll();

    }


    public Long order(int productId, int productCnt) {
        return orderService.order(Long.valueOf(productId), productCnt);

    }

    public void orderDetail(Long orderId) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        List<OrderItem> orderItems = orderService.getOrderItems(orderId);
        String itemName;

        int orderCount;
        for(OrderItem orderItem : orderItems) {
            Long pid = orderItem.getPid();
            itemName = productService.findById(pid).getName();
            orderCount = orderItem.getCount();
            System.out.println(itemName + " - " + orderCount + " 개 ");
        }
        int orderPrice = orderService.getOrderPrice(orderId);
        int shippingFee = orderService.getShippingFee(orderPrice);
        int payPrice = orderService.getPayPrice(orderId);

        System.out.println("---------------------------------------");
        System.out.println("주문 금액 : " + decimalFormat.format(orderPrice) + "원");

        if(shippingFee > 0){
            System.out.println("배송비 : " + decimalFormat.format(shippingFee) + "원");
        }

        System.out.println("---------------------------------------");
        System.out.println("지불 금액 : " +  decimalFormat.format(payPrice) + "원") ;
        System.out.println("---------------------------------------");
    }
}
