package kr.com._29cm.homework;
import java.util.List;
import java.util.Scanner;

import kr.com._29cm.homework.domain.Product;
import kr.com._29cm.homework.exception.BaseException;
import kr.com._29cm.homework.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


@SpringBootApplication(scanBasePackages = "kr.com._29cm.homework")
@Component
public class HomeworkApplication {
	ProductService productService;
	/*static OrderApplication orderApp;

	@Autowired
	public HomeworkApplication(OrderApplication orderApp) {
		this.orderApp = orderApp;
	}*/

	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
		OrderApplication orderApplication = ctx.getBean(OrderApplication.class);
		Scanner sc = new Scanner(System.in);

		System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
		String choice = sc.next();

		if(!choice.equals("o") && !choice.equals("q")) throw new BaseException("입력이 주문 또는 종료가 아닙니다.");
		if(choice.equals("o")) {
			//orderApp.orderStart();
			orderApplication.orderStart();
		}



	}


}
