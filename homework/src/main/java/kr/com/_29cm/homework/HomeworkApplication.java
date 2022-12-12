package kr.com._29cm.homework;
import kr.com._29cm.homework.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;


@SpringBootApplication
@Component
public class HomeworkApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(HomeworkApplication.class, args);

		//스프링 빈 가져오기 & casting
		ApplicationService sBean = (ApplicationService) run.getBean("applicationService");

		sBean.run();

	}


}
