package kr.com._29cm.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationService {

    private final OrderService orderService;
    private final ItemService itemService;

    public void run() {
        Scanner sc = new Scanner(System.in);
        String choice = "o";

        while(true){
            System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
            choice = sc.nextLine();

            if(StringUtils.equalsAny(choice, "o", "q", "quit")) {
                if(choice.equals("o")) {
                    itemService.printAll();
                    orderService.startOrder();

                } else if(choice.equals("q") || choice.equals("quit")) {
                    log.info("고객님의 주문 감사합니다.");
                    break;
                }
            } else {
                log.info("입력이 주문 또는 종료가 아닙니다.");
            }


		}
    }
}
