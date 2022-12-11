package kr.com._29cm.homework;
import kr.com._29cm.homework.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


@SpringBootApplication
@Component
public class HomeworkApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(HomeworkApplication.class, args);

		//스프링 빈 가져오기 & casting
		ApplicationService sBean = (ApplicationService) run.getBean("applicationService");

		sBean.run();
//
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig.class);
//		OrderApplication orderApplication = ctx.getBean(OrderApplication.class);
//		OrderService orderService = ctx.getBean(OrderService.class);
//		orderApplication.dataSave();
//
//		Scanner sc = new Scanner(System.in);
//		String choice = "o";
//		String numInput = "", cntInput ="";
//		Long orderId = 0L;
//		int productId = 0, productCnt = 0;
//
//
//
//		System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
//		choice = sc.nextLine();
//
//		while(choice.equals("o")){
//
//
//			if(!choice.equals("o") && !choice.equals("q")) throw new BaseException("입력이 주문 또는 종료가 아닙니다.");
//			if(choice.equals("o")) {
//				// 전체 상품 출력
//				orderApplication.orderStart();
//				orderId = orderApplication.order().getId();
//			}
//			while(!cntInput.equals(" ")){
//
//				// 주문할 상품번호 ,수량 입력받음
//				System.out.print("상품번호 : ");
//				numInput = sc.nextLine();
//				if(!numInput.equals(" ")) {
//					productId = Integer.parseInt(numInput);
//				}
//
//				System.out.print("수량 : ");
//				cntInput = sc.nextLine();
//				if(!cntInput.equals(" ")) {
//					productCnt = Integer.parseInt(cntInput);
//					orderApplication.orderItem(productId, orderId, productCnt);
//					System.out.println("   ");
//				}
//
//			}
//
//			// 주문 내역(내용, 주문금액, 지불금액) 출력
//			if(orderId != 0L){
//				System.out.println("주문 내역 : ");
//				System.out.println("---------------------------------------");
//				orderApplication.orderDetail(orderId);
//				numInput = "";
//				cntInput = "";
//			}
//
//			System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
//			choice = sc.nextLine();
//		}
//		if(choice.equals("q")) {
//			System.out.println("고객님의 주문 감사합니다.");
//		}

	}


}
